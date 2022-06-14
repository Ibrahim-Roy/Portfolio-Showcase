from django.urls import path

from . import views

from django.conf import settings
from django.conf.urls.static import static


#import the required apis that work at the backend to satisfy requests
from HobbiesApp.api import similar_hobbies, user_api, hobby_api, friend_request_api

"""
    STATIC_URL --> to access the custom static files located in the 'static' folder.
"""

urlpatterns = [
    #health
    path('health/', views.health),
    #index
    path('', views.index, name='index'),
    # signup page
    path('signup/', views.signup, name="signup"),
    # login page
    path('login/',views.login, name='login'),
    # profile page
    path('profile/',views.profile, name='profile'),
    # similar hobbies page
    path('similar/', views.list_similar_hobbies, name="list similar"),
    
    # logout function
    path('logout/',views.logout, name='logout'),
    # upload image view function
    path('uploadimage/',views.upload_image, name='uploadimage'),
    
    #api paths
    path('api/user/<int:user_id>/', user_api, name="user api"),
    path('api/hobby/', hobby_api, name="hobby api"),
    path('api/similar/<int:user_id>', similar_hobbies, name="similar api"),
    path('api/friend-request/<int:to_user_id>', friend_request_api, name="friend request api"),

] + static(settings.STATIC_URL, document_root=settings.STATIC_ROOT)