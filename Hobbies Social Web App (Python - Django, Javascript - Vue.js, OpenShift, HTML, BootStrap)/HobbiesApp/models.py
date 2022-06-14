from django.contrib.auth.models import AbstractUser
from django.db import models
from django.db.models.fields import related
from django.urls import reverse

class Hobby(models.Model):
    """
        A Model subclass representing a Hobby that many users may have
        Therefore, it has a many-to-many realtionship with User
    """
    name = models.CharField(max_length=30)
    description = models.CharField(max_length=200)

    def to_dict(self):
        """
            Method that returns the dictionary representation of Hobby model class
        """
        return{
            'id': self.id,
            'name': self.name if self.name else None,
            'description': self.description if self.description else None,
        }

class User(AbstractUser):
    """
        A custom User class that inherits from AbstractUser
    """
    username = models.CharField(max_length=50, unique=True)
    image = models.ImageField(upload_to='profile_images')
    email = models.EmailField(max_length=50)
    city = models.CharField(max_length=30)
    dob = models.DateField(null=True)
    hobbies = models.ManyToManyField(
        to=Hobby,
        blank=True,
        related_name="users"
    )
    friends = models.ManyToManyField(
        "User",
        blank=True
    )

    def to_dict(self):
        """
            Method that returns the dictionary representation of User model class
        """
        return{
            'id': self.id,
            'username': self.username,
            'image': self.image.url if self.image else None,
            'email': self.email if self.email else None,
            'city': self.city if self.city else None,
            'dob': str(self.dob) if self.dob else None,
            'hobbies': list(self.hobbies.all().values()) if self.hobbies else None,
            'friends': list(self.friends.all().values()) if self.friends else None,
            'api': reverse('user api', kwargs={'user_id': self.id}),
            'similar_hobbies_api': reverse('similar api', kwargs={'user_id': self.id}),
            'friend_request_api': reverse('friend request api', kwargs={'to_user_id': self.id}),
        }

class Friend_Request(models.Model):
    from_user = models.ForeignKey(
        User, related_name='from_user', on_delete=models.CASCADE
    )
    to_user = models.ForeignKey(
        User, related_name='to_user', on_delete=models.CASCADE
    )
    def to_dict(self):
        """
            Method that returns the dictionary representation of Friend_Request model class
        """
        return{
            'id': self.id,
            'from_user': self.from_user.to_dict() if self.from_user else None,
            'to_user': self.to_user.to_dict() if self.to_user else None,
        }