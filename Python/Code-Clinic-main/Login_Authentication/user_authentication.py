import csv
from Login_Authentication import decrypter
from Login_Authentication import encrypter
import stdiomask as stdiomask
from personalCalendar import calendar

global username
global check

def main():
    """
    This function opens the file and read the lines in the file.
    """
    with open("users.json","r") as file:
        file_reader = csv.reader(file)
        user_find(file_reader)
        file.close()


def user_find(file):
    """
    This function requires user input for username.
    Checks file if username does exist.
    """
    global username
    global check
    username = input("Enter your username: ")
    user1 = encrypter.encrypt_username(username)
    check = False
    
    for row in file:
        if row[0] == user1:
            username = decrypter.decrypt_username(user1)
            print("Username " +username+ " found")
            user_found = [row[0],row[1]]
            pass_check(user_found)
            check = True
            break
        else:
            check = False
            # print("Username not found please create one")
            continue
    if check == False:
        print("Username not found please create one")


def pass_check(user_found):
    """
    Checks if the password matches the one stores in the file.
    """
    password = ''
    while password != user_found[1]:
        password = stdiomask.getpass("Please enter your password: ")
        pass1 = encrypter.encrypt_password(password)
        if user_found[1] == pass1:
            print("Password successful!")
            break
        else:
            print("Password inserted was not a match. Please try again..")

def start():
    global username
    global check
    main()
    if check == True:
        calendar.connect(str(username))
    return username, check

