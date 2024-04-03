from datetime import datetime, timedelta
from codeClinicCalendar import calendar as cc_calendar
from user.credentials import main
import patient_booking

import re

#Currently "overwrites" the previous event. 


def update_event(chosen_id, username):

    # username = credentials.main()
    service = cc_calendar.con()

    start = patient_booking.secure_book(chosen_id)[1]['dateTime']
    end = patient_booking.secure_book(chosen_id)[2]['dateTime']

    user_desc = input("Please provide a description of your issue: ")

    while user_desc == "" or user_desc == " ":
        user_desc = input("Please provide a description of your issue: ")
    
    # print(user_desc)

    event_result = service.events().update(
    calendarId='primary',
    eventId= chosen_id,
    body={
        "summary": patient_booking.secure_book(chosen_id)[3],
        "description": user_desc,
        "start": {"dateTime": start, "timeZone": 'GMT +02:00'},
        "end": {"dateTime": end, "timeZone": 'GMT +02:00'},
        "attendees":[{'email': username+'@student.wethinkcode.co.za'}]
        },
    ).execute()

    print("Your booking has now been confirmed")
    print("\nYou have successfully booked the following slot:")
    start = event_result['start'].get('dateTime', event_result['start'].get('date'))

    #Display format
    x = start, event_result['summary']
    x = " ".join(x)

    x = re.sub("T", " " , x, 1) # removing T(time indicator)
    x = re.sub("\+02:00", "", x) # removing timezone
    x = re.sub(":00", "" , x, 1) # removing seconds
    x = re.split(" ", x, 2)

    #Changing date format
    dmy = x[0].split("-")
    dmy.reverse()
    x[0] = "-".join(dmy)

    # print("id: ", event_result['id'])
    # print("starts at: ", event_result['start']['dateTime'])
    # print("ends at: ", event_result['end']['dateTime'])
    print(f"Date         : {x[0]}\nTime of slot : {x[1]}")
    print("Summary      :", event_result['summary'])
    print("Description  :", event_result['description'])
    print("Attendees    :", username+"@student.wethinkcode.co.za") #event_result['attendees'])


# if __name__ == "__main__":
#     abc = patient_booking.book_command()
#     iddd = patient_booking.confirm_book(abc)
#     update_event(iddd, 'majansen')
