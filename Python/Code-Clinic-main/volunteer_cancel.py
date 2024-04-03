from codeClinicCalendar import calendar as cc_calendar
from user import user
from user import credentials

import codeClinicCalendar
import codeClinicCalendar.calendar as cc

from patient_booking import confirm_book, slot_allocation

def slot_allocation_v():
    service = cc_calendar.connect()
    event_id = cc.show_calendar_v(service)[0]
    # print(event_id)
    num_list = []
    try:
        if len(cc.show_calendar_v.events['attendees']) == 1:
            pass
    except:
        for i in range(len(event_id)):
            i += 1
            num_list.append(i)
        #print(num_list)
        id_dict = {num_list[i]: event_id[i] for i in range(len(num_list))}
    #print(id_dict)
    return num_list, id_dict

# Promp user to ask which event they want to cancel 
def user_choose(num_list):
    service = cc_calendar.con()
    events = cc.show_calendar_v(service)[0]

    if not events:
        print("No events available for cancelation.")

    else:
        user_sel =  int(input("\nWhich slot would you like to cancel? "))

        if user_sel != int and user_sel not in num_list:
            print(user_sel, "is not a valid slot number. Please select a valid slot number.")
            user_sel =  int(input("\nWhich slot would you like to book? "))
        print("\n"+f"You have selected slot {user_sel}.")

        # THE USER NUMBER GETS RETURNED (AN INTEGER )
        return user_sel

# check if patient exist in event 

# find which event they want to cancel 

def find_event(user_sel):
    id_dict = slot_allocation()[1]

    for k in id_dict:
        if user_sel == k:
            return id_dict[k]
            
# USE THE RETURNED NUMBER TO FIND EVENT ID 

# check if they are the creator

def check_creator(id_returned, username):
    service = cc_calendar.con()
    events = cc.show_calendar_v(service)[1]
    
    for event in events:
        # print(event['creator']['email'])
        # print(id_returned)
        # print(event)
        if event == id_returned:
            if str(username)+"@student.wethinkcode.co.za" in event['creator']['email']:
                return True
            return False

# def volunteer_cancel():
#     service = cc_calendar.con()
#     stuff = cc.print_calendar(service)[1]

#     creator = []

#     # for i in stuff:
# #         if i['id'] == cancel_id:
# #             # print("Stuff is:\n",i)
# #             # print("Main creator stuff", i['creator']['email'])
# #             # print("Creator is a type object of ", type(i['creator']))
# #             creator.append(i['creator']['email'])
# #     return True

# # Actually cancels the event

def delete_slot(num_list):
    user = user_choose(num_list)
    id = find_event(user)
    if check_creator(id, user) == True:
        service = cc.con()
        service.events().delete(calendarId='primary', eventId='eventId').execute()

    else:
        print('dont delete')