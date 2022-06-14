from django.shortcuts import redirect, render, get_object_or_404, HttpResponse
from django.contrib import auth
from django.contrib.auth.decorators import login_required
from django.http import Http404

from .decorators import when_logged_in
from .models import Friend_Request, User, Friend_Request
from .forms import SignUpForm, LoginForm
import json

@when_logged_in
def index(request):
    return render(request, 'mainapp/base.html')

@login_required
def list_similar_hobbies(request):
    '''
        Displays list of all users oredering them relative to number
        of hobbies in common with the user logged in
    '''
    user = request.user
    user_dict = user.to_dict()
    return render(request, 'mainapp/pages/listofusers.html', {
        'similar_hobbies_api': user_dict['similar_hobbies_api'],
    })

@when_logged_in
def signup(request):
    """
        View function that loads the signup page if the request is not of type post
        If the request is of type post then the view function creates a user with the inputs of the form and redirects to the login page
    """
    form = SignUpForm()
    if request.method == "POST":
        form = SignUpForm(request.POST)
        if form.is_valid():
            username=form.cleaned_data['username']
            password = form.cleaned_data['password']
            email = form.cleaned_data['email']
            city = form.cleaned_data['city']
            dob = form.cleaned_data['dob']
            user = User.objects.create(username=username,email=email,city=city,dob=dob)
            user.set_password(password)
            user.save()
            return redirect('login')
    return render(request, 'mainapp/auth/signup.html', {
        'form': form,
    })

@when_logged_in
def login(request):
    """
        View function that loads the login page if the request is not of type post
        If the request is of type post then the view function uses Djangos authentication framework to login the user
    """
    form = LoginForm()
    if request.method == "POST":
        form = LoginForm(request.POST)
        if form.is_valid():
            user = auth.authenticate(username=form.cleaned_data['username'],password=form.cleaned_data['password'])
            if user is not None:
                auth.login(request, user)
                return redirect('profile')
            # If the user object is None it means authentication failed
            return render(request, 'mainapp/errors/error.html', {
                'error_message': 'Error Invalid Credentials'
            })
    #If request method is not POST or it's an invalid form
    return render(request, 'mainapp/auth/login.html', {
        'form': form,
    })

@login_required
def profile(request):
    '''
        Shows the logged in users details, allows the details to be edited
    '''
    return render(request, 'mainapp/pages/profile.html')

@login_required
def logout(request):
    '''
        Logs user out
    '''
    auth.logout(request)
    return redirect('login')

@login_required
def upload_image(request):
    '''
        Allows user to upload profile images
    '''
    user = request.user
    if 'img_file' in request.FILES:
        image_file = request.FILES['img_file']
        user.image = image_file
        user.save()
        return HttpResponse(user.image.url)
    else:
        raise Http404('Image file not received')

def health(request):
    """Takes an request as a parameter and returns 200 OK"""
    return HttpResponse("200 OK")
