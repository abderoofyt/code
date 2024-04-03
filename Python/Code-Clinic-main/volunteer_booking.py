from datetime import datetime, timedelta
from time import time
#! choose which calendar to import to make changes
from personalCalendar import calendar
from codeClinicCalendar import calendar as cc
from user import credentials

def create_command(username):
    # Creates a one hour event tomorrow 10 AM GMT +02:00
    # username = credentials.main()
    cc_service = cc.connect()
    service = calendar.connect(username)
    
    # heading = input("Heading: ")
    # description = input("Description: ")

    start_date = input("Start date (yyyy-mm-dd) : ")
    start_time = input("Start time (12:00) : ")
    start = (str(start_date)+"T"+str(start_time)+":00")
    time = start_time.split(":")
    
    if int(time[1]) == 30:
        time[0] = 00
    elif int(time[1]) > 30:
        time[0] = int(time[0]) + 1
        time[1] = int(time[1]) - 30
    elif int(time[1]) < 30:
        time[1] = int(time[1]) + 30
    
    end_time = "T"+str(time[0])+":"+str(time[1])+":00"
    end = (str(start_date)+str(end_time))
    event_result = service.events().insert(   \
    calendarId='primary',   \
    body={
        "summary": 'Automating calendar',
        "description": 'This is a test event of creating an event on google calendar with python.',
        "start": {"dateTime": start, "timeZone": 'GMT +02:00'},
        "end": {"dateTime": end, "timeZone": 'GMT +02:00'},
        "attendees": {'email': username+"@student.wethinkcode.co.za"},
        }
    ).execute()
    event_result = cc_service.events().insert(   \
    calendarId='primary',   \
    body={
        "summary": 'Automating calendar',
        "description": 'This is a test event of creating an event on google calendar with python.',
        "start": {"dateTime": start, "timeZone": 'GMT +02:00'},
        "end": {"dateTime": end, "timeZone": 'GMT +02:00'},
        }
    ).execute()
    
    print("Created event")
    print("id: ", event_result['id'])
    print("summary: ", event_result['summary'])
    print("starts at: ", event_result['start']['dateTime'])
    print("ends at: ", event_result['end']['dateTime'])
