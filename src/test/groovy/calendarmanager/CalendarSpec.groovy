package calendarmanager

import grails.test.hibernate.HibernateSpec

class CalendarSpec extends HibernateSpec {

	def setup() {
	}

	def cleanup() {
	}

	public void testSave() {
		when:
		Calendar calendarValid = new Calendar("Test name 1", "Test user 1")
		calendarValid.save()

		then:
		Calendar.count() == 1

		when:
		Calendar calendarValid2 = new Calendar("Test name 2", "Test user 1")
		calendarValid2.save()

		then:
		Calendar.count() == 2
		Calendar.first().name == "Test name 1"
	}

	public void testSaveConstraints() {
		when:
		//invalid data
		Calendar calendarInvalidName = new Calendar("", "Test user")
		calendarInvalidName.save()

		then:
		calendarInvalidName.hasErrors()
		calendarInvalidName.errors.getFieldError("name")
		Calendar.count() == 0

		when:
		//invalid data
		Calendar calendarInvalidUser = new Calendar("Test name", "")
		calendarInvalidUser.save()

		then:
		calendarInvalidUser.hasErrors()
		calendarInvalidUser.errors.getFieldError("user")
		Calendar.count() == 0

		when:
		//valid data
		Calendar calendarValid = new Calendar("Test name", "Test user")
		calendarValid.save()

		then:
		Calendar.count() == 1
	}

	public void testDefaultConstructor() {
		when:
		Calendar defaultCalendar = new Calendar()
		
		then:
		defaultCalendar.getName() == "New Calendar"
		defaultCalendar.getUser() == "Default User"
	}

	public void testConstructor() {
		when:
		Calendar calendar = new Calendar("Work Calendar", "Steve Jobs")
		
		then:
		calendar.getName() == "Work Calendar"
		calendar.getUser() == "Steve Jobs"
	}

	public void testSetAndGetName() {
		when:
		Calendar calendar = new Calendar("Work Calendar", "Steve Jobs")
		
		then:
		calendar.getName() == "Work Calendar"

		when:
		calendar.setName("Social Calendar")
		
		then:
		assert calendar.getName() == "Social Calendar"
	}

	public void testSetAndGetUser() {
		when:
		Calendar calendar = new Calendar("Work Calendar", "Steve Jobs")
		
		then:
		calendar.getUser() == "Steve Jobs"

		when:
		calendar.setUser("Mike Tyson")
		
		then:
		calendar.getUser() == "Mike Tyson"
	}
}
