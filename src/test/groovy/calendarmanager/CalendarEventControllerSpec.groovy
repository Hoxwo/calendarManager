package calendarmanager

import org.apache.commons.lang3.time.DateUtils
import calendarmanager.Calendar
import calendarmanager.CalendarEvent
import grails.test.hibernate.HibernateSpec
import grails.testing.web.controllers.ControllerUnitTest
import grails.plugin.json.view.mvc.JsonViewResolver

class CalendarEventControllerSpec extends HibernateSpec implements ControllerUnitTest<CalendarEventController> {

	static doWithSpring = {
		jsonSmartViewResolver(JsonViewResolver)
	}

    def setup() {
    	Date referenceDate = new Date()

	    //calendar will have 0 events today, 1 in the week, and 3 in the month
	    Calendar calendar1 = new Calendar("Movie Calendar", "Seth Rogen")
		Date eventDate1 = DateUtils.addDays(referenceDate, 4)
		Date reminderDate1 = DateUtils.addDays(referenceDate, 3)
		Date eventDate2 = DateUtils.addDays(referenceDate, 9)
		Date reminderDate2 = DateUtils.addDays(referenceDate, 8)
		Date eventDate3 = DateUtils.addDays(referenceDate, 21)
		Date reminderDate3 = DateUtils.addDays(referenceDate, 20)
		Date eventDate4 = DateUtils.addDays(referenceDate, 45)
		Date reminderDate4 = DateUtils.addDays(referenceDate, 44)
		CalendarEvent testEvent1 = new CalendarEvent(calendar1, "Premiere Day", eventDate1, "Los Angeles", new ArrayList<String>(), reminderDate1, true)
		CalendarEvent testEvent2 = new CalendarEvent(calendar1, "Launch Party", eventDate2, "San Francisco", new ArrayList<String>(), reminderDate2, true)
		CalendarEvent testEvent3 = new CalendarEvent(calendar1, "Talk Show 1", eventDate3, "San Antonio", new ArrayList<String>(), reminderDate3, true)
		CalendarEvent testEvent4 = new CalendarEvent(calendar1, "Talk Show 2", eventDate4, "New York City", new ArrayList<String>(), reminderDate4, true)

		//calendar will have 0 events today, 0 in the week, and 2 in the month
		Calendar calendar2 = new Calendar("Family Calendar", "John Goodman")
		Date goodmanEventDate1 = DateUtils.addDays(referenceDate, 12)
		Date goodmanReminderDate1 = DateUtils.addDays(referenceDate, 11)
		Date goodmanEventDate2 = DateUtils.addDays(referenceDate, 17)
		Date goodmanReminderDate2 = DateUtils.addDays(referenceDate, 16)
		Date goodmanEventDate3 = DateUtils.addHours(referenceDate, 6)
		Date goodmanReminderDate3 = DateUtils.addHours(referenceDate, 6)
		CalendarEvent testEvent5 = new CalendarEvent(calendar2, "Birthday Party", goodmanEventDate1, "Houston", new ArrayList<String>(), goodmanReminderDate1, true)
		CalendarEvent testEvent6 = new CalendarEvent(calendar2, "Family Dinner", goodmanEventDate2, "Dallas", new ArrayList<String>(), goodmanReminderDate2, true)
		CalendarEvent testEvent7 = new CalendarEvent(calendar2, "Flight", goodmanEventDate3, "Atlanta", new ArrayList<String>(), goodmanReminderDate3, true)

		CalendarEvent.saveAll(
			testEvent1,
			testEvent2,
			testEvent3,
			testEvent4,
			testEvent5,
			testEvent6,
			testEvent7
		)

    }

    def cleanup() {
    }

    void testWeekActionOneResultMovie() {
    	when:
        controller.week("Movie Calendar", "Seth Rogen")
        
        then:
        response.json.size() == 1
    }

    void testWeekActionOneResultFamily() {
        when:
        controller.week("Family Calendar", "John Goodman")
        
        then:
        response.json.size() == 1
    }

    void testMonthActionThreeResultsMovie() {
        when:
        controller.month("Movie Calendar", "Seth Rogen")
        
        then:
        response.json.size() == 3
    }

    void testMonthActionThreeResultsFamily() {
        when:
        controller.month("Family Calendar", "John Goodman")
                
        then:
        response.json.size() == 3
    }

    void testDayActionNoResultsMovie() {
        when:
        controller.day("Movie Calendar", "Seth Rogen")
                
        then:
        response.json.size() == 0
    }

    void testDayActionOneResultFamily() {
        when:
        controller.day("Family Calendar", "John Goodman")
                
        then:
        response.json.size() == 1
    }
}