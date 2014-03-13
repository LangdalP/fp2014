package protocol;

public enum RequestType {
        /** Viser at beskjeden ikkje er request. */
	NOT_A_REQUEST,					
	
        /** Obj0 = String username, Obj 1 = String password*/
        LOGIN,
        
        /** Obj0 = Meeting meeting */
	ADD_MEETING,	
        
        /** Obj0 = Meeting, Obj1 = Attendee */
	ADD_EMPLOYEE_TO_MEETING,		 
        
        /** Obj0 = Meeting, Obj1 = Employee*/
	REMOVE_EMPLOYEE_FROM_MEETING,
        ;
}
