def intro_user():
    user_name = input("Please enter your username: ")
    print("\033[5;31mWelcome \033[0m" "\033[5;31m" +user_name+ "\033[0m" "\033[5;31m to WETHINKCODE Code Clinic!\033[0m")
    return user_name


def specify_user():
    clinic = input("Please specify whether you are a patient or volunteer: ")
    if clinic.lower() == "patient":
        patient_user()
    elif clinic.lower() == "volunteer":
        volunteer_user()
    else:
        print("Sorry I don't understand.")
        return specify_user()


def patient_user():
    patient_comms = ["BOOK    - Choose one of the given time slots available and book it.",
    "CANCEL  - Cancel the booking.",
    "CHECK   - Shows the user that the booking was confirmed",
    "REFRESH - Shows new time slots available.",
    "OFF     - Exits the Code Clinic interface."]
    print("Here are your available commands:")
    patient_listed_commands = ""
    for k in patient_comms:
        patient_listed_commands += k+"\n"
    print(patient_listed_commands, end="")
    user_commands()


def volunteer_user():
    volunteer_comms = ["CREATE - Creates an available time slot for a fellow peer to choose from.",
    "CANCEL - Cancel your time slot.",
    "CHECK  - Shows the user their time slots",
    "OFF    - Exits the Code Clinic interface."]
    print("Here are your available commands:")
    volunteer_listed_commands = ""
    for k in volunteer_comms:
        volunteer_listed_commands += k+"\n"
    print(volunteer_listed_commands, end="")
    user_commands()


def user_commands():
    user_input = input("Please insert a vaild command from the list above to continue: ")
    # if user_input.lower() == "book":
    #     book_command()
    # elif user_input.lower() == "create":
    #     create_command()
    # elif user_input.lower() == "cancel":
    #     cancel_command()
    # elif user_input.lower() == "check":
    #     check_command()
    # elif user_input.lower() == "refresh":
    #     refresh_command()
    if user_input.lower() == "off":
        off_command()
    # elif user_input.lower() == "":
    #     user_commands()
    # else:
    #     user_commands()
# def location_user(): #ask this after they select the create/book command
    # location = ("Are you from JHB Campus or CPT Campus?(Please insert either JHB for Johannesburg or CPT for Cape Town): ")


def off_command():
    print("Thank you for using the WETHINKCODE Code Clinic.\nYour session has ended...")


def start():
    user_name = ""
    user_name = intro_user
    clinic = ""
    intro_user()
    specify_user()
