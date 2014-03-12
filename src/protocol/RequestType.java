package protocol;

public enum RequestType {
	NOT_A_REQUEST,					// Viser at beskjeden ikkje er request
	LOGIN,							// Obj0 = String username, Obj 1 = String password
	ADD_MEETING,					// Obj0 = Meeting meeting
	ADD_EMPLOYEE_TO_MEETING,		// Obj0 = Meeting, Obj1 = Attendee
	REMOVE_EMPLOYEE_FROM_MEETING	// Obj0 = Meeting, Obj1 = Employee
}
