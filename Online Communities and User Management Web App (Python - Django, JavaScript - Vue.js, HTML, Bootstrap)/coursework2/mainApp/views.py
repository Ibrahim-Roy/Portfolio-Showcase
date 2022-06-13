from django.shortcuts import render

#Renders the index.html template 
def index(request):
    return render(request, 'mainApp/index.html')