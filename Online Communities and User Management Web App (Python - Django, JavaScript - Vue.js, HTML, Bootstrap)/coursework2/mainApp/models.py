from django.db import models
from django.urls import reverse

# Community Model
class Community(models.Model):
    name = models.CharField(max_length=20)
    launchDate = models.DateField()

    def __str__(self):
        return self.name

    def to_dict(self):
        return {
            'id': self.id,
            'name': self.name,
            'launch_date': self.launchDate,
            'members': list(self.members.all().values()),
            'api': reverse('community api', kwargs={'community_id': self.id}),
            'editing': False,
        }

#User Model
class User(models.Model):
    name = models.CharField(max_length=20)
    username = models.CharField(max_length=20)
    date_of_birth = models.DateField()
    age = models.IntegerField()
    joined_communities = models.ManyToManyField(Community, related_name="members")

    def __str__(self):
        return self.name

    def to_dict(self):
        return {
            'id': self.id,
            'name': self.name,
            'username': self.username,
            'dob': self.date_of_birth,
            'age': self.age,
            'joined_communities': list(self.joined_communities.all().values()),
            'api': reverse('user api', kwargs={'user_id': self.id}),
            'editing': False,
        }