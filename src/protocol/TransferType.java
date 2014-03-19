package protocol;


public enum TransferType {
    /** Viser at beskjeden ikkje er request. */
    NOT_A_REQUEST,

    /** REQUEST  ingen objekter. RESPONSE  Obj0=mapEmployees, Obj1 = mapGroups, Obj2 = mapMeetingRooms, Obj3 = mapFutureMeetings */
    INIT_MODEL,

    /** REQUEST: Obj0 = String username, Obj 1 = String password
     *  RESPONSE: Obj0 = boolean //true hvis login ok, false hvis logik failed.
    */
    LOGIN,
    
    /** REQUEST: Ingen objects
     * RESPONSE: ?
     */
    LOGOUT,
    
    /** Obj0 = Meeting meeting */
    ADD_MEETING,

    /** Obj0 = Meeting, Obj1 = Attendee */
    ADD_ATTENDEE_TO_MEETING,

    /** Obj0 = Meeting, Obj1 = Employee*/
    REMOVE_ATTENDEE_FROM_MEETING,

    /**REQUEST: Obj0=MeetingRoom, Obj1=Date meetingStart, Obj2=int duration, Obj3=int minAttendees, RESPONS: Obj0=Map<String, MeetingRoom>   */
    GET_AVAILABLE_MEETING_ROOMS,

    /* GET REQUESTS Object er tom som default.*/

    /** REQUEST: INGEN, RESPONSE: obj0 = List<Employee>  */
    GET_EMPLOYEES,
    /**  REQUEST: INGEN, RESPONSE: obj0 = List<Meeting> */
    GET_MEETINGS_ALL,
    /**  REQUEST: obj0 = List<Employee>, RESPONSE: obj0 = List<Meeting> */
    GET_MEETINGS_BY_EMPLOYEES,
    /**  REQUEST: obj0 = Employee, RESPONSE: obj0 = List<Meeting> */
    GET_MEETINGS_BY_EMPLOYEE,



    ;
}
