# calendarManager
A Small Grails application for scheduling and showing calendar events

Build Instructions:
1. Clone the repo
2. To run tests and compile, run "grails test-app"
3. To just compile, run "grails compile"
4. To run the application after compiling, run "grails run-app"

Functionality:
1. POST new events using JSON

2. Update (PUT) events using their Hibernate ID and JSON

3. DELETE events using their hibernate ID

4. Use /day endpoint to find all events within one day for a specified calendar and user

5. Use /week endpoint to find all events within one week for a specified calendar and user

6. Use the /month endpoint to find all events within one month for a specified calenar and user
