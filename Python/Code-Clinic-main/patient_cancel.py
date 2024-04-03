from codeClinicCalendar import calendar as cc_calendar
from user import user
from user import credentials
import codeClinicCalendar
import codeClinicCalendar.calendar as cc
from patient_booking import confirm_book, slot_allocation


def cancel_select(num_list):
    service = cc_calendar.con()
    events = cc.show_calendar(service)[0]
    # id_dict = slot_allocation()[1]

    # if len(events) == 0:
    if not events:
        print("No events available for cancelation.")

    else:
        user_sel =  int(input("\nWhich slot would you like to cancel? "))

        while user_sel != int and user_sel not in num_list:
            print(user_sel, "is not a valid slot number. Please select a valid slot number.")
            user_sel =  int(input("\nWhich slot would you like to book? "))
        print("\n"+f"You have selected slot {user_sel}.")

        return user_sel


def cancel_confirm(user_sel):
    """Gets event_id based on user chosen slot.

    Args:
        user_sel (int): selected slot

    Returns:
        [string]: chosen_id
    """
    # event_id = cc.print_calendar(service)[0]

    id_dict = slot_allocation()[1]

    for k in id_dict:
        if user_sel == k:
            cancel_id = id_dict[k]
            return cancel_id


def check_attendees(cancel_id, username):
    service = cc_calendar.con()
    stuff = cc.print_calendar(service)[1]
    # print(stuff)

    for i in stuff:
        if i['id'] == cancel_id:
            if username+"@student.wethinkcode.co.za" not in i['attendees'][0]['email']:
                print("You do not have permission to cancel this booking. It's not yours hahahahahahaha!")
                return True
            else:
                return False                


def cancel_book(cancel_id):
    service = cc_calendar.con()
    stuff = cc.print_calendar(service)[1]

    creator = []

    for i in stuff:
        if i['id'] == cancel_id:
            # print("Stuff is:\n",i)
            # print("Main creator stuff", i['creator']['email'])
            # print("Creator is a type object of ", type(i['creator']))
            creator.append(i['creator']['email'])
    # print(creator[0])

    if stuff:
        vol_main = creator[0]

        EVENT = {
            'attendees' : {'email': vol_main}
        }
        EVENT_ID = cancel_id
        e = service.events().patch(calendarId='primary', 
        eventId=EVENT_ID, sendNotifications=False, body=EVENT).execute()

        print("Your booking has now been cancelled.")

    else: 
        pass
