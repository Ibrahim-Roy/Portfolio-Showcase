import json

from django.http import JsonResponse
from django.http.response import HttpResponseBadRequest
from django.shortcuts import get_object_or_404
from datetime import datetime

from .models import Community, User

def communities_api(request):
    '''
        API that returns all the communities stored in the
        database in JSON format (Handles GET request)
    '''
    return JsonResponse({
        'communities': [
            community.to_dict()
            for community in Community.objects.all()
        ]
    })

def users_api(request):
    '''
        API that returns all the users stored in the
        database in JSON format (Handles GET request)
    '''
    return JsonResponse({
        'users': [
            user.to_dict()
            for user in User.objects.all()
        ]
    })

def community_api(request, community_id):
    '''
        API entry point for each community
        Used to delete, or edit community
        (Handles PUT, DELETE requests)
    '''
    
    community = get_object_or_404(Community, id=community_id)

    if(request.method == "PUT"):
        body = json.loads(request.body)
        community.name = body['name']
        community.launchDate = (datetime.strptime(body['launchDate'], '%Y-%m-%d')).date()
        community.save()
        return JsonResponse({})

    if(request.method == "DELETE"):
        community.delete()
        return JsonResponse({})

    return HttpResponseBadRequest("Invalid method")

def user_api(request, user_id):

    '''
        API entry point for each user
        Used to delete, or edit user or user communities
        (Handles PUT, DELETE requests)
    '''
    
    user = get_object_or_404(User, id=user_id)

    if(request.method == "PUT"):
        body = json.loads(request.body)
        if(body['removeCommunity'] == True):
            community = get_object_or_404(Community, id=int(body['community_id']))
            user.joined_communities.remove(community)
        elif(body['addCommunity'] == True):
            community = get_object_or_404(Community, id=int(body['community_id']))
            user.joined_communities.add(community)
        else:
            user.name = body['name']
            user.username = body['username']
            user.date_of_birth = (datetime.strptime(body['dob'], '%Y-%m-%d')).date()
            user.age = int(body['age'])
            user.save()
        return JsonResponse({})

    if(request.method == "DELETE"):
        user.delete()
        return JsonResponse({})

    return HttpResponseBadRequest("Invalid method")

def create_community_api(request):
    '''
        API entry point to create new communities
        (Handles POST request)
    '''

    if(request.method == "POST"):
        body = json.loads(request.body)
        newCommunity_name = body['name']
        newCommunity_launchDate = (datetime.strptime(body['launchDate'], '%Y-%m-%d')).date()
        Community.objects.create(name=newCommunity_name, launchDate = newCommunity_launchDate)
        return JsonResponse({})

    return HttpResponseBadRequest("Invalid method")

def create_user_api(request):
    '''
        API entry point to create new users
        (Handles POST request)
    '''

    if(request.method == "POST"):
        body = json.loads(request.body)
        newUser_name = body['name']
        newUser_username = body['username']
        newUser_dob = (datetime.strptime(body['dob'], '%Y-%m-%d')).date()
        newUser_age = int(body['age'])
        newUser_joied_communities = get_object_or_404(Community, id=int(body['joined_communities']))
        x = User.objects.create(name=newUser_name, username=newUser_username, date_of_birth=newUser_dob, age=newUser_age)
        x.joined_communities.add(newUser_joied_communities)
        return JsonResponse({})
    
    return HttpResponseBadRequest("Invalid method")