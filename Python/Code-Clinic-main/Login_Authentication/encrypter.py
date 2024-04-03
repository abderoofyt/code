def encrypt_username(username):
    """
    This function encrypts the username.

    Args:
        username (string): Username inputted by user.
    """
    encrypt_user = ''
    for letter in username:
        if letter == " ":
            encrypt_user += " "
        else :
            encrypt_user += chr(ord(letter) +3)
        
    return encrypt_user


def encrypt_password(password):
    """
    This function encrypts the password.

    Args:
        password (string): Password inputted by user.
    """
    encrypt_pass = ''
    for letter in password:
        if letter == " ":
            encrypt_pass += " "
        else :
            encrypt_pass += chr(ord(letter) + 3)
        
    return encrypt_pass