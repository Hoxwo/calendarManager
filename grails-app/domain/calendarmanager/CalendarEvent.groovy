package calendarmanager

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import grails.compiler.GrailsCompileStatic
import org.grails.datastore.gorm.*

@GrailsCompileStatic
public class CalendarEvent implements GormEntity<CalendarEvent> {

	transient DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.ENGLISH);
	private transient Calendar calendar
	private String calendarName
	private String calendarUser
	private String eventTitle
	private Date eventDate
	private String location
	private ArrayList<String> attendeeList = new ArrayList<String>()
	private Date reminderTime
	private boolean reminderSent

	static constraints = {
		calendarName blank:false
		calendarUser blank:false
		eventTitle blank:false
		location blank:false
	}

	/** Constructors */

	//this constructor will take a Calendar object for setting the event's calendar name and user name
	public CalendarEvent(Calendar calendar, String eventTitle, Date eventDate, String location, ArrayList<String> attendeeList,
							Date reminderTime, Boolean reminderSent) {
		this.calendar = calendar
		this.calendarName = calendar.getName()
		this.calendarUser = calendar.getUser()
		this.eventTitle = eventTitle
		this.eventDate = eventDate
		this.location = location
		this.attendeeList = attendeeList
		this.reminderTime = reminderTime
		this.reminderSent = reminderSent
	}

	//this constructor will take a Calendar name and user name for setting the event's calendar name and user name
	public CalendarEvent(String calendarName, String calendarUser, String eventTitle, Date eventDate, String location, ArrayList<String> attendeeList,
							Date reminderTime, Boolean reminderSent) {
		this.calendar = new Calendar(calendarName, calendarUser)
		this.calendarName = calendarName
		this.calendarUser = calendarUser
		this.eventTitle = eventTitle
		this.eventDate = eventDate
		this.location = location
		this.attendeeList = attendeeList
		this.reminderTime = reminderTime
		this.reminderSent = reminderSent
	}

    //this constructor will take a Calendar name and user name for setting the event's calendar name and user name, and takes no attendee list
	public CalendarEvent(String calendarName, String calendarUser, String eventTitle, String eventDateAsString, String location,
							String reminderTimeAsString, boolean reminderSent) {

		this.calendar = new Calendar(calendarName, calendarUser)
		this.calendarName = calendarName
		this.calendarUser = calendarUser
		this.eventTitle = eventTitle
		this.eventDate = format.parse(eventDateAsString);
		this.location = location
		this.attendeeList = new ArrayList<String>()
		this.reminderTime = format.parse(reminderTimeAsString);
		this.reminderSent = reminderSent
	}

	/**
 	 * 	Adds an attendee to the attendee list if the attendee is not already present
 	 */
 	public void addAttendee(String attendee) {
 		if(attendeeList != null) {
 			if(!attendeeList.contains(attendee)) {
 				attendeeList.add(attendee)
 			}
 		} else {
 			attendeeList = new ArrayList<String>()
 			attendeeList.add(attendee)
 		}
 	}

	/** Getters */

	public Calendar getCalendar() {
		return this.calendar
	}

	public String getCalendarName() {
		return this.calendarName
	}

	public String getCalendarUser() {
		return this.calendarUser
	}

	public String getEventTitle() {
		return this.eventTitle
	}

	public Date getEventDate() {
		return this.eventDate
	}

	public String getLocation() {
		return this.location
	}

	public ArrayList<String> getAttendeeList() {
		return this.attendeeList
	}

	public Date getReminderTime() {
		return this.reminderTime
	}

	public boolean getReminderSent() {
		return this.reminderSent
	}

	/** Setters */

	public Calendar setCalendar(Calendar calendar) {
		this.calendar = calendar
	}

	public String setCalendarName(String calendarName) {
		this.calendarName = calendarName
	}

	public String setCalendarUser(String calendarUser) {
		this.calendarUser = calendarUser
	}

	public String setEventTitle(String eventTitle) {
		this.eventTitle = eventTitle
	}

	public Date setEventDate(Date eventDate) {
		this.eventDate = eventDate
	}

	public String setLocation(String location) {
		this.location = location
	}

	public ArrayList<String> setAttendeeList(ArrayList<String> attendees) {
		this.attendeeList = attendees
	}

	public Date setReminderTime(Date reminderTime) {
		this.reminderTime = reminderTime
	}

	public boolean setReminderSent(boolean reminderSent) {
		this.reminderSent = reminderSent
	}



}

//day-week-month test
//curl -i -H "Content-Type:application/json" -X POST localhost:8080/events -d '{"calendarName":"Personal","calendarUser":"Chuck","eventTitle":"Birthday Party","eventDate":"2018-05-30T12:00:00.000-0000","location":"Houston","reminderTime":"2018-05-29T12:00:00.000-0000","reminderSent":false}'
//curl -i -H "Content-Type:application/json" -X POST localhost:8080/events -d '{"calendarName":"Personal","calendarUser":"Chuck","eventTitle":"Flight","eventDate":"2018-06-04T12:00:00.000-0000","location":"Dallas","reminderTime":"2018-06-03T12:00:00.000-0000","reminderSent":false}'
//curl -i -H "Content-Type:application/json" -X POST localhost:8080/events -d '{"calendarName":"Personal","calendarUser":"Chuck","eventTitle":"Concert","eventDate":"2018-06-17T12:00:00.000-0000","location":"Atlanta","reminderTime":"2018-06-16T12:00:00.000-0000","reminderSent":false}'
//curl -i -H "Content-Type:application/json" -X POST localhost:8080/events -d '{"calendarName":"Personal","calendarUser":"Chuck","eventTitle":"Anniversary","eventDate":"2018-06-30T12:00:00.000-0000","location":"Alaska","reminderTime":"2018-06-29T12:00:00.000-0000","reminderSent":false}'
//attendeelist
//curl -i -H "Content-Type:application/json" -X POST localhost:8080/events -d '{"calendarName":"Personal","calendarUser":"Chuck","eventTitle":"Anniversary","eventDate":"2018-06-30T12:00:00.000-0000","location":"Alaska","attendeeList":["Bill","Will","Phil"],"reminderTime":"2018-06-29T12:00:00.000-0000","reminderSent":false}'
//attendeelist + Calendar Object
//curl -i -H "Content-Type:application/json" -X POST localhost:8080/events -d '{"calendar":{"name":"Personal","user":"Chuck"},"eventTitle":"Anniversary","eventDate":"2018-06-30T12:00:00.000-0000","location":"Alaska","attendeeList":["Bill","Will","Phil"],"reminderTime":"2018-06-29T12:00:00.000-0000","reminderSent":false}'
//curl -i -H "Content-Type:application/json" -X POST localhost:8080/events -d '{"calendarName":"Family Calendar"}'