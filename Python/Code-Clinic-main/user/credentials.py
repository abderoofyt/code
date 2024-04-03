#CreateCred.py 
#Creates a credential file. 
from cryptography.fernet import Fernet 
import re 
import ctypes 
import time 
import os 
import sys 
class Credentials(): 
    def __init__(self): 
        self.__username = "" 
        self.__key = ""  
        self.__key_file = 'key.key'
    @property
    def username(self): 
        return self.__username 
    @username.setter 
    def username(self,username): 
        while (username == ''): 
            username = input('Enter a proper User name, blank is not accepted:') 
        self.__username = username 
    def create_cred(self): 
        """ 
        This function is responsible for encrypting the password and create  key file for 
        storing the key and create a credential file with user name and password 
        """
        username = self.__username
        cred_filename = username+'.json'
        with open(cred_filename,'w') as file_in: 
            file_in.write("#Credential file:\nUsername={}\n"
            .format(self.__username)) 
            file_in.write("++"*20) 
        #If there exists an older key file, This will remove it. 
        if(os.path.exists(self.__key_file)): 
            os.remove(self.__key_file) 
        #Open the Key.key file and place the key in it. 
        #The key file is hidden. 
        try: 
            os_type = sys.platform 
            if (os_type == 'linux'): 
                self.__key_file = '.' + self.__key_file 
            with open(self.__key_file,'w') as key_in: 
                key_in.write(self.__key.decode()) 
                #Hidding the key file. 
                #The below code snippet finds out which current os the scrip is running on and does the taks base on it. 
                if(os_type == 'win32'): 
                    ctypes.windll.kernel32.SetFileAttributesW(self.__key_file, 2) 
                else: 
                    pass
        except AttributeError:
            pass

        self.__username = "" 
        self.__key = "" 
        self.__key_file
def main():
    # Creating an object for Credentials class 
    creds = Credentials() 
    #Accepting credentials 
    creds.username = input("Please enter your username: ") 
    username = creds.username 
    # print("Enter the epiry time for key file in minutes, [default:Will never expire]") 
    # creds.expiry_time = int(input("Enter time:") or '-1') 
    #calling the Credit 
    creds.create_cred() 
    print("**"*20) 
    print("Cred file created successfully at {}"
    .format(time.ctime())) 
    # if not(creds.expiry_time == -1): 
    #     os.startfile('expire.py') 
    print("**"*20)
    return username
