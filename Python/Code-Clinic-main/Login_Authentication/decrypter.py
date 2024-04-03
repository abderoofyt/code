def decrypt_username(username):
    """
    This function decrypts the username.

    Args:
        username (string): Username inputted by user.
    """
    decrypt_user = ''
    for letter in username:
        if letter == " ":
            decrypt_user += " "
        else :
            decrypt_user += chr(ord(letter) - 3)
        
    return decrypt_user


def decrypt_password(password):
    """
    This function decrypts the password.

    Args:
        password (string): Password inputted by user.
    """
    decrypt_pass = ''
    for letter in password:
        if letter == " ":
            decrypt_pass += " "
        else :
            decrypt_pass += chr(ord(letter) - 3)
        
    return decrypt_pass