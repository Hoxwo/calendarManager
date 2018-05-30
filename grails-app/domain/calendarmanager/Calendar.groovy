package calendarmanager

import grails.compiler.GrailsCompileStatic
import org.grails.datastore.gorm.*

@GrailsCompileStatic
public class Calendar implements GormEntity<Calendar> {

	private String name
	private String user

	static constraints = {
		name blank:false
		user blank:false
	}

	public Calendar() {
		this.name = "New Calendar"
		this.user = "Default User"
	}

	public Calendar(String name, String user) {
		this.name = name
		this.user = user
	}

	public String getName() {
		return this.name
	}

	public String getUser() {
		return this.user
	}

	public void setName(String name) {
		this.name = name
	}

	public void setUser(String user) {
		this.user = user
	}

}