from Login_Authentication import encrypter
import re
import json
import stdiomask as stdiomask


def write_new_user():
    """
    Adds new user to the file.
    """
    with open("users.json", "a+") as file_ob:
        file_ob.seek(0)
        data = file_ob.read(100)
        user = ''
        pswd = ''
        if len(data) > 0:
            username,password = user_and_pass()
            user = encrypter.encrypt_username(username)
            pswd = encrypter.encrypt_password(password)
            if robot_test() == False:
                file_ob.write("\n"+user + "," + pswd + "")


def new_username():
    """
    Asks for username and checks if it already exists in the file and if not, it adds it.
    """
    username = input("Please enter your username: ")
    user1 = encrypter.encrypt_username(username)
    with open("users.json", 'r') as usernames:    
        if user1 in usernames.read():
            usernames.close()
            print("That username already exists.")
            return new_username()
        else:
            usernames.close()
            return username


def password():
    """
    Checks if the password inputted matches the one entered previously.
    """
    password1 = validate()
    password_check = ''
    while password1 != password_check:
        password_check = stdiomask.getpass("Please re-enter your password: ")
    return password1
    

def validate():
    """
    Consists of user input for a password and checks if it matches the required criteria for a password.
    """
    while True:
        password = stdiomask.getpass("Please enter a password longer than 8 characters: ")
        if len(password) < 8:
            print("Make sure your password consists of at lest 8 letters")
        elif re.search('[0-9]',password) is None:
            print("Make sure your password has a number in it")
        elif re.search('[A-Z]',password) is None: 
            print("Make sure your password has a capital letter in it")
        else:
            print("Now that is what you call a password!")
            return password


def robot_test():
    """
    Asks the user if they're a robot or not.
    """
    valid_answers = ['1', '2']
    answer = input("Are you a robot? \n 1) No. \n 2) Yes? \n")
    while answer in valid_answers:
        if answer == '1':
            print("YOU ARE NOW A MEMBER OF THE WETHINKCODE CODE CLINIC!")
            return False
            break
        else:
            print ("GET A LIFE")
            return True
            break


def user_and_pass():
    user = new_username()
    passwd = password()
    return user,passwd


def start():
    write_new_user()