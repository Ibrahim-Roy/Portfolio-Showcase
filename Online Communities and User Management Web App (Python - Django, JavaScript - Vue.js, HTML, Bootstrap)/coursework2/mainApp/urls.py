"""
The 'urlpatterns' list routes URLS to views
"""
from django.urls import path

from . import views

#import the required apis that work at the backend to satisfy requests
from mainApp.api import communities_api, users_api, community_api, user_api, create_community_api, create_user_api

urlpatterns = [
    #path to index page calls the index view
    path('', views.index, name="index"),

    #paths to api's that handle get, put, delete and post request for communities
    path('api/communities', communities_api, name="communities api"),
    path('api/community/<int:community_id>/', community_api, name="community api"),
    path('api/create_community/post', create_community_api, name="create community api"),
    
    #paths to api's that handle get, put, delete and post request for users
    path('api/users', users_api, name="users api"),
    path('api/user/<int:user_id>/', user_api, name="user api"),
    path('api/create_user/post', create_user_api, name="create user api")
]