from __future__ import print_function
import datetime
import pickle
import os.path
from googleapiclient.discovery import build
from google_auth_oauthlib.flow import InstalledAppFlow
from google.auth.transport.requests import Request
from datetime import timedelta

from user import credentials

#For calender output
import re
import string
import sys

cancel = False
if "cancel" in sys.argv:
    cancel = True

# If modifying these scopes, delete the file token.pickle.
SCOPES = ['https://www.googleapis.com/auth/calendar']


def con():
    """Shows basic usage of the Google Calendar API.
    Prints the start and name of the next 10 events on the user's calendar.
    """
    creds = None
    # The file token.pickle stores the user's access and refresh tokens, and is
    # created automatically when the authorization flow completes for the first
    # time.
    if os.path.exists('cc.pickle'):
        with open('cc.pickle', 'rb') as token:
            creds = pickle.load(token)
    # If there are no (valid) credentials available, let the user log in.
    if not creds or not creds.valid:
        if creds and creds.expired and creds.refresh_token:
            creds.refresh(Request())
        else:
            flow = InstalledAppFlow.from_client_secrets_file(
                'client_secret.json', SCOPES)
            creds = flow.run_local_server(port=0)
        # Save the credentials for the next run
        with open('cc.pickle', 'wb') as token:
            pickle.dump(creds, token)

    #print("\u001b[32;1mCODE CLINIC CALENDAR CONNECTION SUCCESSFUL\u001b[0m")
    service = build('calendar', 'v3', credentials=creds)
    return service


def connect():
    """Shows basic usage of the Google Calendar API.
    Prints the start and name of the next 10 events on the user's calendar.
    """
    creds = None
    # The file token.pickle stores the user's access and refresh tokens, and is
    # created automatically when the authorization flow completes for the first
    # time.
    if os.path.exists('cc.pickle'):
        with open('cc.pickle', 'rb') as token:
            creds = pickle.load(token)
    # If there are no (valid) credentials available, let the user log in.
    if not creds or not creds.valid:
        if creds and creds.expired and creds.refresh_token:
            creds.refresh(Request())
        else:
            flow = InstalledAppFlow.from_client_secrets_file(
                'client_secret.json', SCOPES)
            creds = flow.run_local_server(port=0)
        # Save the credentials for the next run
        with open('cc.pickle', 'wb') as token:
            pickle.dump(creds, token)

    print("\u001b[32;1mCODE CLINIC CALENDAR CONNECTION SUCCESSFUL\u001b[0m")
    service = build('calendar', 'v3', credentials=creds)
    return service

def print_calendar(service):
    # Call the Calendar API
    # now = datetime.datetime.utcnow().isoformat() + 'Z' # 'Z' indicates UTC time
    now = datetime.datetime.today()
    week = now + datetime.timedelta(days=7)
    now = now.isoformat() + 'Z'
    week = week.isoformat() + 'Z'
    # print("----now: ",now,"----")
    # print("----week: ",week,"----")
    # week = now + datetime.timedelta(days=7)
    #print('')
    #print('Getting the upcoming events for the week -- Code Clinic Calendar')
    # calendar_list_entry = service.calendarList().get(calendarId='primary').execute()
    # print(calendar_list_entry)
    events_result = service.events().list(calendarId='primary', timeMin=now,
                                        timeMax=week, singleEvents=True,
                                        orderBy='startTime').execute()
    events = events_result.get('items', [])

    eventId_list_cc = []

    if not events:
        #print('No upcoming events found.')
        a=1+1

    file = open("appointment_slots2.txt", "w+")

    for i, event in enumerate(events, 1):
        start = event['start'].get('dateTime', event['start'].get('date'))
        eventId_list_cc.append(event['id'])
        file.write('%s:%s\n' % (start, event['summary']))
    
    #print(str("Slot #" + str(i)))

        x = start, event['summary']
        x = " ".join(x)

        x = re.sub("T", " " , x, 1) # removing T(time indicator)
        x = re.sub("\+02:00", "", x) # removing timezone
        x = re.sub(":00", "" , x, 1) # removing seconds
        x = re.split(" ", x, 2)

        #Changing date format
        dmy = x[0].split("-")
        dmy.reverse()
        x[0] = "-".join(dmy)
    file.close()

        #print(f"Date         : {x[0]}\nTime of slot : {x[1]}\nName of event: {x[2]}\n")
    # print(eventId_list_cc)
    return eventId_list_cc, events


def show_calendar(service):

    # Call the Calendar API
    # now = datetime.datetime.utcnow().isoformat() + 'Z' # 'Z' indicates UTC time
    now = datetime.datetime.today()
    week = now + datetime.timedelta(days=7)
    now = now.isoformat() + 'Z'
    week = week.isoformat() + 'Z'
    # print("----now: ",now,"----")
    # print("----week: ",week,"----")
    # week = now + datetime.timedelta(days=7)
    print('')
    print('Getting the upcoming events for the week -- Code Clinic Calendar')
    # calendar_list_entry = service.calendarList().get(calendarId='primary').execute()
    # print(calendar_list_entry)
    events_result = service.events().list(calendarId='primary', timeMin=now,
                                        timeMax=week, singleEvents=True,
                                        orderBy='startTime').execute()
    events = events_result.get('items', [])

    eventId_list_cc = []

    if not events:
        print('No upcoming events found.')

    # print(cancel)
    count = 0
    for event in events:
        start = event['start'].get('dateTime', event['start'].get('date'))
        if cancel == True:
            try: 
                if len(event['attendees']) >= 1:
                    eventId_list_cc.append(event['id'])
                    # print("Slot booked")
                    count += 1
                    print(str("Slot #" + str(count)))

                # stuff = print_calendar(service)[1]
                # for i in stuff:
                #     if username+"@student.wethinkcode.co.za" in i['attendees']['email']:
                #         eventId_list_cc.append(event['id'])
                #         count += 1
                #         print(str("Slot #" + str(count)))
            except: 
                pass
        else:
            try: 
                if len(event['attendees']) >= 1:
                    pass
            except:
                eventId_list_cc.append(event['id'])
                count += 1 
                print(str("Slot #" + str(count)))

        x = start, event['summary']
        x = " ".join(x)

        x = re.sub("T", " " , x, 1) # removing T(time indicator)
        x = re.sub("\+02:00", "", x) # removing timezone
        x = re.sub(":00", "" , x, 1) # removing seconds
        x = re.split(" ", x, 2)

        #Changing date format
        dmy = x[0].split("-")
        dmy.reverse()
        x[0] = "-".join(dmy)

        if cancel == True:
            try: 
                if len(event['attendees']) >= 1:
                    # print("Slot booked")
                    print(f"Date         : {x[0]}\nTime of slot : {x[1]}\nName of event: {x[2]}\n")   
            except:
                pass
        else:
            try: 
                if len(event['attendees']) >= 1:
                    # print("Slot booked")
                    pass    
            except:
                print(f"Date         : {x[0]}\nTime of slot : {x[1]}\nName of event: {x[2]}\n")
                # print(eventId_list_cc)
    return eventId_list_cc, events







def show_calendar_v(service):

    # Call the Calendar API
    # now = datetime.datetime.utcnow().isoformat() + 'Z' # 'Z' indicates UTC time
    now = datetime.datetime.today()
    week = now + datetime.timedelta(days=7)
    now = now.isoformat() + 'Z'
    week = week.isoformat() + 'Z'
    # print("----now: ",now,"----")
    # print("----week: ",week,"----")
    # week = now + datetime.timedelta(days=7)
    print('')
    print('Getting the upcoming events for the week -- Code Clinic Calendar')
    # calendar_list_entry = service.calendarList().get(calendarId='primary').execute()
    # print(calendar_list_entry)
    events_result = service.events().list(calendarId='primary', timeMin=now,
                                        timeMax=week, singleEvents=True,
                                        orderBy='startTime').execute()
    events = events_result.get('items', [])

    eventId_list_cc = []

    if not events:
        print('No upcoming events found.')

    # print(cancel)
    count = 0
    for event in events:
        start = event['start'].get('dateTime', event['start'].get('date'))
        if cancel == True:
            try: 
                if len(event['attendees']) == 1:
                    eventId_list_cc.append(event['id'])
                    # print("Slot booked")
                    count += 1
                    print(str("Slot #" + str(count)))

                # stuff = print_calendar(service)[1]
                # for i in stuff:
                #     if username+"@student.wethinkcode.co.za" in i['attendees']['email']:
                #         eventId_list_cc.append(event['id'])
                #         count += 1
                #         print(str("Slot #" + str(count)))
            except: 
                pass
        else:
            try: 
                if len(event['attendees']) >= 1:
                    pass
            except:
                eventId_list_cc.append(event['id'])
                count += 1 
                print(str("Slot #" + str(count)))

        x = start, event['summary']
        x = " ".join(x)

        x = re.sub("T", " " , x, 1) # removing T(time indicator)
        x = re.sub("\+02:00", "", x) # removing timezone
        x = re.sub(":00", "" , x, 1) # removing seconds
        x = re.split(" ", x, 2)

        #Changing date format
        dmy = x[0].split("-")
        dmy.reverse()
        x[0] = "-".join(dmy)

        if cancel == True:
            try: 
                if len(event['attendees']) >= 1:
                    # print("Slot booked")
                    print(f"Date         : {x[0]}\nTime of slot : {x[1]}\nName of event: {x[2]}\n")   
            except:
                pass
        else:
            try: 
                if len(event['attendees']) >= 1:
                    # print("Slot booked")
                    pass    
            except:
                print(f"Date         : {x[0]}\nTime of slot : {x[1]}\nName of event: {x[2]}\n")
                # print(eventId_list_cc)
    return eventId_list_cc, events