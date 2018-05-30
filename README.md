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

4. Use /day endpoint to find all events within one day for a specified calendar and user

5. Use /week endpoint to find all events within one week for a specified calendar and user

6. Use the /month endpoint to find all events within one month for a specified calenar and user
