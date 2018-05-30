package calendarmanager

import org.apache.commons.lang3.time.DateUtils
import calendarmanager.CalendarEvent
import grails.rest.*
import grails.converters.*

class CalendarEventController extends RestfulController {
    static responseFormats = ['json', 'xml']
    Date currentDate = new Date()
    Date dayReference = DateUtils.addDays(new Date(), 1)
    Date weekReference = DateUtils.addDays(new Date(), 7)
	Date monthReference = DateUtils.addDays(new Date(), 30)

    CalendarEventController() {
        super(CalendarEvent)
    }

     def month(String calendarName, String calendarUser) { 
    	if(calendarName && calendarUser) {
    		def query = CalendarEvent.where {
    			(calendarName == "${calendarName}"
    			 && calendarUser == "${calendarUser}"
    			 && eventDate >= currentDate
    			 && eventDate < monthReference)
    		}
    		respond query.list()

    	} else {
    		respond([])
    	}
    }

     def week(String calendarName, String calendarUser) { 
    	if(calendarName && calendarUser) {
    		def query = CalendarEvent.where {
    			(calendarName == "${calendarName}" 
    				&& calendarUser == "${calendarUser}"
    			    && eventDate >= currentDate
    			    && eventDate < weekReference)
    		}
    		respond query.list()
    	} else {
    		respond([])
    	}
    }

    def day(String calendarName, String calendarUser) { 
    	if(calendarName && calendarUser) {
    		def query = CalendarEvent.where {
    			(calendarName == "${calendarName}"
    			 && calendarUser == "${calendarUser}"
    			 && eventDate >= currentDate
    			 && eventDate <= dayReference)
    		}
    		respond query.list()
    	} else {
    		respond([])
    	}
    }
}
