from django.test import TestCase, override_settings
from django.test import Client
from django.contrib import auth
from . import models

from django.contrib.staticfiles.testing import StaticLiveServerTestCase
from selenium.webdriver.chrome.webdriver import WebDriver
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.common.by import By 
from selenium.webdriver.support import expected_conditions as EC

import time

@override_settings(STATICFILES_STORAGE='django.contrib.staticfiles.storage.StaticFilesStorage')
class MyTests(TestCase):

    @classmethod
    def setUpTestData(cls):

        # Set up data for the whole TestCase
        test_user = models.User.objects.create(username='user1',email='user1@outlook.com',city='London',dob='2021-03-12')
        test_user.set_password('123')
        test_user.save()

        """
            Assigns test_user1 3 Hobbies: Hobby1, Hobby2, Hobby3
        """
        #Creates 3 Hobbies: Hobby1, Hobby2, Hobby3
        for i in range(1,4):
            name = f"Hobby{i}"
            description = f"{name} Description"
            hobby = models.Hobby.objects.create(name=name, description=description)
            hobby.save()
            test_user.hobbies.add(hobby)
        

    def test1(self):
        """
            Checks the Login Functionality
        """
        #Setting up new Client
        self.client = Client()

        #Profile View function should initially redirect because the user is not logged in yet
        response = self.client.get('/profile/')
        self.assertEqual(response.status_code, 302)

        #Login View Function must redirect to the profile page (i.e. the Profile View Function)
        response = self.client.post('/login/', {'username': 'user1', 'password': '123'})
        self.assertEqual(response.status_code, 302)

        #Profile View Function should not redirect now because the user is logged in
        response = self.client.get('/profile/')
        self.assertEqual(response.status_code, 200)
    
    def test2(self):
        """
            Checks the Signup Functionality
        """
        #Setting up new Client
        self.client = Client()

        #Profile View function should initially redirect because the user is not logged in yet
        response = self.client.get('/profile/')
        self.assertEqual(response.status_code, 302)

        #Signup View Function must redirect to the login page (i.e. the Login View Function). There should also now be a user2 in the DB.
        response = self.client.post('/signup/', {
            'username': 'user2', 
            'password': '123', 
            'email':'user2@outlook.com', 
            'city':'London', 
            'dob':'2020-03-12'
            })
        self.assertEqual(response.status_code, 302)

        #Login View Function must redirect to the profile page (i.e. the Profile View Function) because the user credentials should exist in the system.
        response = self.client.post('/login/', {'username': 'user2', 'password': '123'})
        self.assertEqual(response.status_code, 302)

        #Profile View Function should not redirect now because the user is logged in
        response = self.client.get('/profile/')
        self.assertEqual(response.status_code, 200)

'''
    Selenium testing works but commented out for deployment purposes
    (Uncomment to run test)
'''        

# USERNAME = 'user1'
# PASSWORD = '123'
# EMAIL = 'user1@outlook.com'
# CITY = 'London'
# DOB = '10-08-2021'
# DOB_TEST = '05-05-2015'

# HOBBY1 = 'Paintballing'
# HOBBY1_DESCRIPTION = 'Shooting people with paint'

# HOBBY2 = 'Fishing'
# HOBBY2_DESCRIPTION = 'Using a rod and waiting for sea creatures to hook into it'

# HOBBY3 = 'Bowling'
# HOBBY3_DESCRIPTION = 'Throwing a heavy ball at some objects'

# @override_settings(STATICFILES_STORAGE='django.contrib.staticfiles.storage.StaticFilesStorage')
# class MySeleniumTests(StaticLiveServerTestCase):

#     @classmethod
#     def setUpClass(cls):
#         super().setUpClass()
#         cls.selenium = WebDriver()
#         cls.selenium.maximize_window()
#         cls.selenium.implicitly_wait(10)

#     @classmethod
#     def tearDownClass(cls):
#         cls.selenium.quit()
#         super().tearDownClass()
    
#     def test_edit_dob(self):
#         """
#             Signup a new user
#         """
#         self.selenium.get('%s%s' % (self.live_server_url, '/signup/'))
#         # enteres the username
#         username_input = self.selenium.find_element_by_name("username")
#         username_input.send_keys(USERNAME)
#         # enters the password
#         password_input = self.selenium.find_element_by_name("password")
#         password_input.send_keys(PASSWORD)
#         # enters the email
#         email_input = self.selenium.find_element_by_name("email")
#         email_input.send_keys(EMAIL)
#         # enters the city
#         city_input = self.selenium.find_element_by_name("city")
#         city_input.send_keys(CITY)
#         # enters the date of bith
#         dob_input = self.selenium.find_element_by_name("dob")
#         dob_input.send_keys(DOB)

#         self.selenium.find_element_by_xpath('//input[@type="submit"]').click()

#         time.sleep(2)
        
#         """
#             Login using the credentials used to sign up
#         """
#         #Enters Credentials belonging to user1 created in the setUpClass method
#         username_input = self.selenium.find_element_by_css_selector("#login_username_input")
#         username_input.send_keys(USERNAME)
#         password_input = self.selenium.find_element_by_css_selector("#login_password_input")
#         password_input.send_keys(PASSWORD)
#         time.sleep(1)

#         #Press Login Button
#         self.selenium.find_element_by_xpath('//input[@type="submit"]').click()
        
#         """
#             Once Logged in the user should be redirected to profile page
#             In the Profile Page the user's date of birth is changed to be that of the test dob value
#         """
#         #Makes Selenium wait until the edit button exists
#         WebDriverWait(self.selenium, 20).until(EC.element_to_be_clickable((By.CSS_SELECTOR, "#account_details_edit_btn"))).click()
#         WebDriverWait(self.selenium, 20).until(EC.element_to_be_clickable((By.CSS_SELECTOR, "#account_details_save_btn")))
#         dob_input = self.selenium.find_element_by_css_selector('#edit_dob_input')
#         dob_input.send_keys(DOB_TEST)
#         time.sleep(1)
#         save_btn = self.selenium.find_element_by_css_selector('#account_details_save_btn')
#         save_btn.click()
#         time.sleep(5)

#     def test_hobbies_list(self):
#         """
#             Signup a new user
#         """
#         self.selenium.get('%s%s' % (self.live_server_url, '/signup/'))
#         # enteres the username
#         username_input = self.selenium.find_element_by_name("username")
#         username_input.send_keys(USERNAME)
#         # enters the password
#         password_input = self.selenium.find_element_by_name("password")
#         password_input.send_keys(PASSWORD)
#         # enters the email
#         email_input = self.selenium.find_element_by_name("email")
#         email_input.send_keys(EMAIL)
#         # enters the city
#         city_input = self.selenium.find_element_by_name("city")
#         city_input.send_keys(CITY)
#         # enters the date of bith
#         dob_input = self.selenium.find_element_by_name("dob")
#         dob_input.send_keys(DOB)

#         self.selenium.find_element_by_xpath('//input[@type="submit"]').click()

#         time.sleep(1)
        
#         """
#             Login using the credentials used to sign up
#         """
#         #Enters Credentials belonging to user1 created in the setUpClass method
#         username_input = self.selenium.find_element_by_css_selector("#login_username_input")
#         username_input.send_keys(USERNAME)
#         password_input = self.selenium.find_element_by_css_selector("#login_password_input")
#         password_input.send_keys(PASSWORD)
#         time.sleep(1)

#         #Press Login Button
#         self.selenium.find_element_by_xpath('//input[@type="submit"]').click()

#         """
#             Once Logged in the user should be redirected to profile page
#             In the Profile Page three new hobbies should be created and then two should be selected
#         """
#         #Creating first hobby
#         WebDriverWait(self.selenium, 20).until(EC.element_to_be_clickable((By.CSS_SELECTOR, "#create_hobby_btn")))
#         dob_input = self.selenium.find_element_by_css_selector('#new_hobbie_text_input')
#         dob_input.send_keys(HOBBY1)
#         dob_input = self.selenium.find_element_by_css_selector('#new_hobbie_description_input')
#         dob_input.send_keys(HOBBY1_DESCRIPTION)
#         time.sleep(1)
#         save_btn = self.selenium.find_element_by_css_selector('#create_hobby_btn')
#         save_btn.click()

#         #Creating second hobby
#         WebDriverWait(self.selenium, 20).until(EC.element_to_be_clickable((By.CSS_SELECTOR, "#create_hobby_btn")))
#         dob_input = self.selenium.find_element_by_css_selector('#new_hobbie_text_input')
#         dob_input.send_keys(HOBBY2)
#         dob_input = self.selenium.find_element_by_css_selector('#new_hobbie_description_input')
#         dob_input.send_keys(HOBBY2_DESCRIPTION)
#         time.sleep(1)
#         save_btn = self.selenium.find_element_by_css_selector('#create_hobby_btn')
#         save_btn.click()

#         #Creating third hobby
#         WebDriverWait(self.selenium, 20).until(EC.element_to_be_clickable((By.CSS_SELECTOR, "#create_hobby_btn")))
#         dob_input = self.selenium.find_element_by_css_selector('#new_hobbie_text_input')
#         dob_input.send_keys(HOBBY3)
#         dob_input = self.selenium.find_element_by_css_selector('#new_hobbie_description_input')
#         dob_input.send_keys(HOBBY3_DESCRIPTION)
#         time.sleep(1)
#         save_btn = self.selenium.find_element_by_css_selector('#create_hobby_btn')
#         save_btn.click()

#         time.sleep(1)

#         #Selecting first hobby
#         WebDriverWait(self.selenium, 20).until(EC.element_to_be_clickable((By.CSS_SELECTOR, "#select_hobby_input")))
#         dob_input = self.selenium.find_element_by_css_selector('#select_hobby_input')
#         dob_input.send_keys(HOBBY1)
#         time.sleep(1)
#         save_btn = self.selenium.find_element_by_css_selector('#select_hobby_btn')
#         save_btn.click()

#         #Selecting third hobby
#         WebDriverWait(self.selenium, 20).until(EC.element_to_be_clickable((By.CSS_SELECTOR, "#select_hobby_input")))
#         dob_input = self.selenium.find_element_by_css_selector('#select_hobby_input')
#         dob_input.send_keys(HOBBY3)
#         time.sleep(1)
#         save_btn = self.selenium.find_element_by_css_selector('#select_hobby_btn')
#         save_btn.click()
        
#         time.sleep(2)

#         """
#             Removes first hobby
#         """
#         #removing third hobby
#         WebDriverWait(self.selenium, 20).until(EC.element_to_be_clickable((By.CSS_SELECTOR, f"#{HOBBY1}_remove_btn"))).click()
#         time.sleep(2)