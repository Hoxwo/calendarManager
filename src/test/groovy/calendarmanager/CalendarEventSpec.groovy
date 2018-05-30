package calendarmanager

import org.apache.commons.lang3.time.DateUtils
import grails.test.hibernate.HibernateSpec
import java.util.*

class CalendarEventSpec extends HibernateSpec {

	//reference date for comparisons 
	private Date referenceDate = new Date()

	//fill in test event for setAndGet tests
	Calendar calendar = new Calendar("Family Calendar", "Seth Rogen")
	Date eventDate = DateUtils.addDays(referenceDate, 10)
	Date reminderDate = DateUtils.addDays(referenceDate, 9)
	private CalendarEvent testEvent = new CalendarEvent(calendar, "Premiere Day", eventDate, "Los Angeles", new ArrayList<String>(), reminderDate, true)

	def setup() {
	}

	def cleanup() {
	}

	public void testSave() {
		when:
		//valid data
		testEvent.save()

		then:
		CalendarEvent.count() == 1
	}

	public void testSaveConstraints() {
		when:
		//invalid data
		CalendarEvent testEventInvalid = testEvent
		testEventInvalid.setCalendarName("")
		testEventInvalid.setCalendarUser("")
		testEventInvalid.setEventTitle("")
		testEventInvalid.save()

		then:
		testEventInvalid.hasErrors()
		testEventInvalid.errors.getFieldError("calendarName")
		testEventInvalid.errors.getFieldError("calendarUser")
		testEventInvalid.errors.getFieldError("eventTitle")
		CalendarEvent.count() == 0
	}

	public void testConstructors() {
		when:
		//set up some test data to pass into event
		Calendar calendar = new Calendar("Work Calendar", "Steve Jobs")
		Date eventDate = DateUtils.addDays(referenceDate, 10)
		Date reminderDate = DateUtils.addDays(referenceDate, 9)

		//test constructor that accepts calendar object
		CalendarEvent calendarEvent1 = new CalendarEvent(calendar, "Launch Day", eventDate, "San Diego", new ArrayList<String>(), reminderDate, true)
		
		then:
		calendarEvent1.getCalendarName() == "Work Calendar"
		calendarEvent1.getCalendarUser() == "Steve Jobs"
		calendarEvent1.getEventTitle() == "Launch Day"
		calendarEvent1.getEventDate() == DateUtils.addDays(referenceDate, 10)
		calendarEvent1.getLocation() == "San Diego"
		calendarEvent1.getAttendeeList().size() == 0
		calendarEvent1.getReminderTime() == DateUtils.addDays(referenceDate, 9)
		calendarEvent1.getReminderSent() == true

		when:
		//test constructor that accepts Strings for calendar fields
		CalendarEvent calendarEvent2 = new CalendarEvent(calendar.getName(), calendar.getUser(), "Launch Day", eventDate, "San Diego", new ArrayList<String>(), reminderDate, true)
		
		then:
		calendarEvent2.getCalendarName() == "Work Calendar"
		calendarEvent2.getCalendarUser() == "Steve Jobs"
		calendarEvent2.getEventTitle() == "Launch Day"
		calendarEvent2.getEventDate() == DateUtils.addDays(referenceDate, 10)
		calendarEvent2.getLocation() == "San Diego"
		calendarEvent2.getAttendeeList().size() == 0
		calendarEvent2.getReminderTime() == DateUtils.addDays(referenceDate, 9)
		calendarEvent2.getReminderSent() == true
	}

	public void testSetAndGetCalendarName() {
		given:
		testEvent.getCalendarName() == "Family Calendar"
		
		when:
		testEvent.setCalendarName("Social Calendar")
		
		then:
		testEvent.getCalendarName() == "Social Calendar"
	}

	public void testSetAndGetCalendarUser() {
		given:
		testEvent.getCalendarUser() == "Seth Rogen"
	
		when:
		testEvent.setCalendarUser("Scarlet Johannson")
		
		then:
		testEvent.getCalendarUser() == "Scarlet Johannson"
	}

	public void testSetAndGetEventTitle() {
		given:
		testEvent.getEventTitle() == "Premiere Day"
		
		when:
		testEvent.setEventTitle("Birthday Party")
		
		then:
		testEvent.getEventTitle() == "Birthday Party"
	}

	public void testSetAndGetEventDate() {
		given:
		//date is currently set to 10 days in the future
		testEvent.getEventDate().after(DateUtils.addDays(referenceDate, 5))
	
		when:
		//set it to 2 days in the future
		testEvent.setEventDate(DateUtils.addDays(referenceDate, 2))
		
		then:
		testEvent.getEventDate().before(DateUtils.addDays(referenceDate, 5))
	}

	public void testSetAndGetLocation() {
		given:
		testEvent.getLocation() == "Los Angeles"
	
		when:
		testEvent.setLocation("Atlanta")
		
		then:
		testEvent.getLocation() == "Atlanta"
	}

	public void testSetAndGetReminderTime() {
		given:
		//date is currently set to 9 days in the future
		testEvent.getReminderTime().after(DateUtils.addDays(referenceDate, 5))
	
		when:
		//set it to 1 day in the future
		testEvent.setReminderTime(DateUtils.addDays(referenceDate, 1))
		
		then:
		testEvent.getReminderTime().before(DateUtils.addDays(referenceDate, 5))
	}

	public void testSetAndGetAttendeeList() {
		given:
		List<String> attendeesNew = new ArrayList<String>()
		attendeesNew.add("Rachael Ray")
		testEvent.getAttendeeList().size() == 0
	
		when:
		testEvent.setAttendeeList(attendeesNew)
		
		then:
		testEvent.getAttendeeList().size() == 1
	}

	public void testSetAndGetReminderSent() {
		given:
		testEvent.getReminderSent()
	
		when:
		testEvent.setReminderSent(false)
		
		then:
		!testEvent.getReminderSent()
	}

	public void testAddAttendee() {
		given:
		testEvent.getAttendeeList().size() == 0
		
		when:
		//test that we add a unique new attendee
		testEvent.addAttendee("Frodo Baggins")

		then:
		//he was not already on the list, now he has been added
		testEvent.getAttendeeList().size() == 1

		when:
		//test that we add a unique new attendee
		testEvent.addAttendee("Bilbo Baggins")

		then:
		//he was not already on the list, now he has been added
		testEvent.getAttendeeList().size() == 2

		when:
		//test that we don't add a duplicate attendee
		testEvent.addAttendee("Bilbo Baggins")

		then:
		//he was already on the list
		testEvent.getAttendeeList().size() == 2
	}
}
	