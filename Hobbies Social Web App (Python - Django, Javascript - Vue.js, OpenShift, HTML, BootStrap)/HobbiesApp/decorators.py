from django.shortcuts import redirect

def when_logged_in(view_function):
    '''
        Redirects to profile page when the user is logged in and tries
        to access any view that uses this decorator
    ''' 
    def decorator(request):
        if request.user.is_authenticated:
            return redirect('profile')
        return view_function(request)
    return decorator