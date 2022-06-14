from django import forms
from django.db.models import fields
from django.forms import ModelForm, widgets

from HobbiesApp.models import User

class SignUpForm(ModelForm):
    '''
        Form for creating new users when they sign up
    '''
    username = forms.CharField(max_length=50, widget=forms.TextInput(attrs={'class':'form-control mt-5','type': 'text', 'placeholder':'Username'}), label="")
    password = forms.CharField(max_length=128, widget=forms.TextInput(attrs={'class':'form-control mt-3','type': 'text', 'placeholder':'Password'}), label="")
    email = forms.EmailField(max_length=50, widget=forms.EmailInput(attrs={'class':'form-control mt-3','type': 'email', 'placeholder':'Email'}), label="")
    city = forms.CharField(max_length=30, widget=forms.TextInput(attrs={'class':'form-control mt-3','type': 'text', 'placeholder':'City'}), label="")
    dob = forms.DateField(widget=forms.DateInput(attrs={'class':'form-control mt-3','type': 'date'}), label="")
    class Meta:
        model = User
        fields = ['username', 'password', 'email', 'city', 'dob']

class LoginForm(forms.Form):
    '''
        Form that takes login credentials to allow the user to log in
    '''
    username = forms.CharField(max_length=50, widget=forms.TextInput(attrs={'id':'login_username_input', 'class':'form-control mt-5','type': 'text', 'placeholder':'Username'}), label="")
    password = forms.CharField(max_length=128, widget=forms.TextInput(attrs={'id':'login_password_input', 'class':'form-control mt-3','type': 'password', 'placeholder':'Password'}), label="")
    fields = ['username', 'password']