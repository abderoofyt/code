from Login_Authentication import encrypter
from Login_Authentication import decrypter


def reset_main():
    """
    Takes user name and checks whether the encrypted version is in the json file.
    If it does match, it deletes the entry.
    """
    username = input("Please enter your username: ")
    if len(username) > 0:
        user1 = encrypter.encrypt_username(username)
        dec_user1 = decrypter.decrypt_username(user1)
        with open("users.json", 'r') as usernames:    
            if user1 in usernames.read():
                with open("users.json", "r") as file:
                    lines = file.readlines()
                with open("users.json", "w") as file:
                    for line in lines:
                        line = line.split(",")
                        if user1 == line[0]:
                            pass
                        else :
                            file.write("" + line[0] + "," + line[1] + "")
    else :
        return reset_main()
            
reset_main()