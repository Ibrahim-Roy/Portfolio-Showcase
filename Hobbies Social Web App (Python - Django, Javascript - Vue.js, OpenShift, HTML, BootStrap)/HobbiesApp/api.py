import json
from datetime import date

from django.http import JsonResponse
from django.http.response import HttpResponseBadRequest
from django.shortcuts import get_object_or_404
from django.http import HttpResponseBadRequest

from .models import Hobby, User, Friend_Request

def user_api(request, user_id):
    '''
        API that returns all user information stored in the
        database in JSON format (Handles GET request)
        PUT requests can edit these details, add hobbies and 
        remove hobbies.

    '''
    user = get_object_or_404(User, id=user_id)

    if(request.method == "GET"):
        return JsonResponse({
            'user': user.to_dict()
        })
    
    if(request.method == "PUT"):
        body = json.loads(request.body)
        if(body['addHobby'] == True):
            hobby = get_object_or_404(Hobby, id=int(body['id']))
            user.hobbies.add(hobby)
        elif(body['removeHobby'] == True):
            hobby = get_object_or_404(Hobby, id=int(body['id']))
            user.hobbies.remove(hobby)
        else:
            user.email = body['email']
            user.city = body['city']
            user.dob = body['dob']
            user.save()
        return JsonResponse({})

def hobby_api(request):
    '''
        API entry point to create new hobbies
        (Handles POST request)
        Get request returns a list of all existing hobbies
    '''
    if(request.method == "GET"):
        return JsonResponse({
        'hobbies': [
            hobby.to_dict()
            for hobby in Hobby.objects.all()
        ]
    })

    if(request.method == "POST"):
        body = json.loads(request.body)
        hobby_name = body['name']
        hobby_description = body['description']
        new_hobby = Hobby.objects.create(name = hobby_name, description = hobby_description)
        new_hobby.save()
        return JsonResponse({})

def similar_hobbies(request, user_id):
    '''
        Returns a list of users ordered according to
        similar hobbies relative to the user logged in
    '''

    user_similarity = {}

    # Gets all the hobbies of the main user we are going to find similar users for
    main_user = get_object_or_404(User, id=user_id)
    main_user_hobbies = main_user.hobbies.all()

    #Gets all the ids of the main users friend
    exclude_ids = []
    exclude_ids.append(main_user.id)


    # Gets all the users in the database excluding the main user
    all_users = User.objects.exclude(id__in=exclude_ids)
    

    if request.method == "GET":
        # Compares against all users in the database
        for crnt_user in all_users:
            # Gets all of the hobbies of the current user
            crnt_user_hobbies = crnt_user.hobbies.all() 

            #Calculating the age
            today = date.today()
            age = None #Initialised to None because not all users have age, e.g. admins

            #Splitting date into year, month, day
            if crnt_user.dob != None:
                #Splitting date into year, month, day
                dob = (str(crnt_user.dob)).split('-')
                dob_date = date(int(dob[0]), int(dob[1]), int(dob[2]))
                age = today.year - dob_date.year - ((today.month, today.day) < (dob_date.month, dob_date.day))

            # Creates a entry in the user_similarity dictionary for the current user 
            # To keep track of the number of hobbies it has in common with the main user
            user_similarity[crnt_user.id] = {
                'id': crnt_user.id,
                'username': crnt_user.username,
                'common_hobbies_count': 0,
                'age': str(age),
                'city': crnt_user.city,
                'friend_request_api' : crnt_user.to_dict()['friend_request_api'],
                'profile_image' : crnt_user.to_dict()['image'],
            }

            for crnt_user_hobby in crnt_user_hobbies:
                for main_user_hobby in main_user_hobbies:
                    if crnt_user_hobby.id == main_user_hobby.id:
                        user_similarity_obj = user_similarity[crnt_user.id] 
                        user_similarity_obj['common_hobbies_count'] += 1
        
        #Adds the information to an array
        unsorted_users_array = []
        for u in user_similarity:
            user_object = user_similarity[u] # Format is [user_id, number_of_similar_hobbies]
            unsorted_users_array.append(user_object)

        #Sort the array and yield the top category of scam for that month_year
        sorted_users_array = sorted(unsorted_users_array, reverse=True, key=lambda x: x['common_hobbies_count']) #Sorts based on the value
        return JsonResponse({
            'users': sorted_users_array
        })

def friend_request_api(request, to_user_id):
    if(request.method == "POST"):
        """
            So that other users can make friend requests to the user (i.e. to_user_id)
        """
        print("Works")
        body = json.loads(request.body)
        from_user_id = body['from_user_id']
        from_user = get_object_or_404(User, id=from_user_id)
        to_user = get_object_or_404(User, id=to_user_id)
        friend_request, created = Friend_Request.objects.get_or_create(
            from_user=from_user, to_user=to_user
        )
        if created:
            return JsonResponse({})
        else:
            return HttpResponseBadRequest("Friend Request Already Created")
    if(request.method == "GET"):
        """
            So that the user (i.e. to_user_id) can see all the friend requests made to them
        """
        user = get_object_or_404(User, id=to_user_id)
        friend_request_object = Friend_Request.objects.filter(to_user=user)
        list_of_friend_requests = []
        for r in friend_request_object:
            list_of_friend_requests.append(r.to_dict())
        return JsonResponse({
            'friend_requests': list_of_friend_requests
        })
    if(request.method == "DELETE"):
        """
            So that the user (i.e. to_user_id) can accept a friend request made to them
        """
        body = json.loads(request.body)
        friend_request_id = body['request_id']
        user = get_object_or_404(User, id=to_user_id)
        friend_request = get_object_or_404(Friend_Request,id=friend_request_id)
        if friend_request.to_user == user:
            if(body['accept']) :
                user.friends.add(friend_request.from_user)
                friend_request.from_user.friends.add(user)
            friend_request.delete()
            return JsonResponse({})
        else:
            return HttpResponseBadRequest('Friend Request Not Accepted')