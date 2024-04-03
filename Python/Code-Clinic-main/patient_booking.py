#For booking events
from codeClinicCalendar import calendar as cc_calendar
from user import user
from user import credentials
import codeClinicCalendar
import codeClinicCalendar.calendar as cc

def slot_allocation():
    service = cc_calendar.connect()
    event_id = cc.show_calendar(service)[0]
    # print(event_id)
    num_list = []
    try:
        if len(cc.show_calendar.events['attendees']) >= 1:
            pass
    except:
        for i in range(len(event_id)):
            i += 1
            num_list.append(i)
        #print(num_list)
        id_dict = {num_list[i]: event_id[i] for i in range(len(num_list))}
    #print(id_dict)
    return num_list, id_dict


def book_command(num_list):
    """Allows the user to choose a slot. 

    Returns:
        [int]: 
    """
    # username = credentials.main()
    service = cc_calendar.con()

    eventId_list_cc = cc.print_calendar(service)[0]
    # print(eventId_list_cc) 
    if not num_list:
        print("No events available for booking.")
    else:
        user_ans =  int(input("\nWhich slot would you like to book? "))

        while user_ans != int and user_ans not in num_list:
            print(user_ans, "is not a valid slot number. Please select a valid slot number.")
            user_ans =  int(input("\nWhich slot would you like to book? "))
        print("\n"+f"You have selected slot {user_ans}.")

        return user_ans


def confirm_book(user_ans, id_dict):
    """Gets event_id based on user chosen slot.

    Args:
        user_ans (int): selected slot

    Returns:
        [string]: chosen_id
    """
    if len(id_dict) != 0:
        for key in id_dict:
            if user_ans == key:
                chosen_id = (id_dict[key])
                # print (chosen_id)
                return chosen_id, id_dict
    else:
        pass


def secure_book(chosen_id):
    """Gets infomation of the associated event_id.

    Args:
        chosen_id ([str]):

    Returns:
        [list]: i
        [dict]: start_time
        [dict]: end_time
        [str] : summ
    """
    service = cc_calendar.con()

    stuff = cc.print_calendar(service)[1]

    #print("Your booking has now been confirmed")

    for i in stuff:
        if i['id'] == chosen_id:
            start_time = i['start']
            end_time = i['end']
            summ = i['summary']
            # print(i)
            # print('\n')
            # print("This is the start:", i['start'])
            # print("This is the end:", i['end'])
            return i, start_time, end_time, summ
