# calendarManager
A Small Grails application for scheduling and showing calendar events

Build Instructions:
1. Clone the repo
2. To run tests and compile, run "grails test-app"
3. To just compile, run "grails compile"
4. To run the application after compiling, run "grails run-app"

Functionality:
1. POST new events using JSON
```
(xenial)kc@localhost:~/Desktop/calendarManager_clean/calendarManager$ curl -i -H "Content-Type:application/json" -X POST localhost:8080/events -d '{"calendarName":"Personal","calendarUser":"Chuck","eventTitle":"Birthday Party","eventDate":"2018-05-31T12:00:00.000-0000","location":"Houston","attendeeList":["Bill","Will","Phil"],"reminderTime":"2018-05-30T12:00:00.000-0000","reminderSent":false}'
HTTP/1.1 201 
X-Application-Context: application:development
Location: http://localhost:8080/events/1
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Wed, 30 May 2018 15:12:06 GMT

{"id":1,"Calendar":"Calendar: Personal - User: Chuck","Location and Date":"Location: Houston - Event Date: Thu May 31 08:00:00 EDT 2018","Reminder":"Reminder Time: Wed May 30 08:00:00 EDT 2018 - Reminder Sent? false","Attendees":"[Bill, Will, Phil]"}
```

2. Update (PUT) events using their Hibernate ID and JSON
```
(xenial)kc@localhost:~/Desktop/calendarManager_clean/calendarManager$ curl -i -H "Content-Type:application/json" -X PUT localhost:8080/events/1 -d '{"location":"Atlanta"}'UT localhost:8080/events/1 -d '{"location":HTTP/1.1 200 
X-Application-Context: application:development
Location: http://localhost:8080/events/1
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Wed, 30 May 2018 15:14:46 GMT

{"id":1,"Calendar":"Calendar: Personal - User: Chuck","Location and Date":"Location: Atlanta - Event Date: 2018-05-31 08:00:00.0","Reminder":"Reminder Time: 2018-05-30 08:00:00.0 - Reminder Sent? false","Attendees":"[]"}
```

3. DELETE events using their hibernate ID
```
(xenial)kc@localhost:~/Desktop/calendarManager_clean/calendarManager$ curl -i -H "Content-Type:application/json" -X DELETE localhost:8080/events/1                            
HTTP/1.1 204 
X-Application-Context: application:development
Date: Wed, 30 May 2018 15:16:37 GMT

(xenial)kc@localhost:~/Desktop/calendarManager_clean/calendarManager$ curl -i -H "Content-Type:application/json" -X GET localhost:8080/events  
HTTP/1.1 200 
X-Application-Context: application:development
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Wed, 30 May 2018 15:16:48 GMT

[]
```
4. GET all events
```
(xenial)kc@localhost:~/Desktop/calendarManager_clean/calendarManager$ curl -i -H "Content-Type:application/json" -X GET localhost:8080/events
HTTP/1.1 200 
X-Application-Context: application:development
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Wed, 30 May 2018 15:21:52 GMT

[{"id":2,"Calendar":"Calendar: Personal - User: Chuck","Location and Date":"Location: Houston - Event Date: 2018-05-31 08:00:00.0","Reminder":"Reminder Time: 2018-05-30 08:00:00.0 - Reminder Sent? false","Attendees":"[]"},{"id":3,"Calendar":"Calendar: Personal - User: Chuck","Location and Date":"Location: Dallas - Event Date: 2018-06-05 08:00:00.0","Reminder":"Reminder Time: 2018-06-04 08:00:00.0 - Reminder Sent? false","Attendees":"[]"},{"id":4,"Calendar":"Calendar: Personal - User: Chuck","Location and Date":"Location: Atlanta - Event Date: 2018-06-18 08:00:00.0","Reminder":"Reminder Time: 2018-06-17 08:00:00.0 - Reminder Sent? false","Attendees":"[]"},{"id":5,"Calendar":"Calendar: Personal - User: Chuck","Location and Date":"Location: Alaska - Event Date: 2018-07-01 08:00:00.0","Reminder":"Reminder Time: 2018-06-30 08:00:00.0 - Reminder Sent? false","Attendees":"[]"}]
```

5. Use /day endpoint to find all events within one day for a specified calendar and user
```
(xenial)kc@localhost:~/Desktop/calendarManager_clean/calendarManager$ curl -i -H "Content-Type:application/json" -X GET "localhost:8080/events/day?calendarName=Personal&calendarUser=Chuck"
HTTP/1.1 200 
X-Application-Context: application:development
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Wed, 30 May 2018 15:23:12 GMT

[{"id":2,"Calendar":"Calendar: Personal - User: Chuck","Location and Date":"Location: Houston - Event Date: 2018-05-31 08:00:00.0","Reminder":"Reminder Time: 2018-05-30 08:00:00.0 - Reminder Sent? false","Attendees":"[]"}]
```

6. Use /week endpoint to find all events within 7 days for a specified calendar and user
```
(xenial)kc@localhost:~/Desktop/calendarManager_clean/calendarManager$ curl -i -H "Content-Type:application/json" -X GET "localhost:8080/events/week?calendarName=Personal&calendarUser=Chuck"
HTTP/1.1 200 
X-Application-Context: application:development
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Wed, 30 May 2018 15:23:55 GMT

[{"id":2,"Calendar":"Calendar: Personal - User: Chuck","Location and Date":"Location: Houston - Event Date: 2018-05-31 08:00:00.0","Reminder":"Reminder Time: 2018-05-30 08:00:00.0 - Reminder Sent? false","Attendees":"[]"},{"id":3,"Calendar":"Calendar: Personal - User: Chuck","Location and Date":"Location: Dallas - Event Date: 2018-06-05 08:00:00.0","Reminder":"Reminder Time: 2018-06-04 08:00:00.0 - Reminder Sent? false","Attendees":"[]"}]
```

7. Use the /month endpoint to find all events within 30 days for a specified calenar and user
```
(xenial)kc@localhost:~/Desktop/calendarManager_clean/calendarManager$ curl -i -H "Content-Type:application/json" -X GET "localhost:8080/events/month?calendarName=Personal&calendarUser=Chuck"
HTTP/1.1 200 
X-Application-Context: application:development
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Wed, 30 May 2018 15:24:36 GMT

[{"id":2,"Calendar":"Calendar: Personal - User: Chuck","Location and Date":"Location: Houston - Event Date: 2018-05-31 08:00:00.0","Reminder":"Reminder Time: 2018-05-30 08:00:00.0 - Reminder Sent? false","Attendees":"[]"},{"id":3,"Calendar":"Calendar: Personal - User: Chuck","Location and Date":"Location: Dallas - Event Date: 2018-06-05 08:00:00.0","Reminder":"Reminder Time: 2018-06-04 08:00:00.0 - Reminder Sent? false","Attendees":"[]"},{"id":4,"Calendar":"Calendar: Personal - User: Chuck","Location and Date":"Location: Atlanta - Event Date: 2018-06-18 08:00:00.0","Reminder":"Reminder Time: 2018-06-17 08:00:00.0 - Reminder Sent? false","Attendees":"[]"}]
```

NOTE: Steps 4-7 were run after these curl commands (POSTs to fill the app with data)
```
//curl -i -H "Content-Type:application/json" -X POST localhost:8080/events -d '{"calendarName":"Personal","calendarUser":"Chuck","eventTitle":"Birthday Party","eventDate":"2018-05-31T12:00:00.000-0000","location":"Houston","attendeeList":["Bill","Will","Phil"],"reminderTime":"2018-05-30T12:00:00.000-0000","reminderSent":false}'
//curl -i -H "Content-Type:application/json" -X POST localhost:8080/events -d '{"calendarName":"Personal","calendarUser":"Chuck","eventTitle":"Flight","eventDate":"2018-06-05T12:00:00.000-0000","location":"Dallas","attendeeList":["Jane","John"],"reminderTime":"2018-06-04T12:00:00.000-0000","reminderSent":false}'
//curl -i -H "Content-Type:application/json" -X POST localhost:8080/events -d '{"calendarName":"Personal","calendarUser":"Chuck","eventTitle":"Concert","eventDate":"2018-06-18T12:00:00.000-0000","location":"Atlanta","attendeeList":["Seymour"],"reminderTime":"2018-06-17T12:00:00.000-0000","reminderSent":false}'
//curl -i -H "Content-Type:application/json" -X POST localhost:8080/events -d '{"calendarName":"Personal","calendarUser":"Chuck","eventTitle":"Anniversary","eventDate":"2018-07-01T12:00:00.000-0000","location":"Alaska","attendeeList":["Willie","Will","William"],"reminderTime":"2018-06-30T12:00:00.000-0000","reminderSent":false}'

```
