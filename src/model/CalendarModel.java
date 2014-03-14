package model;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Christoffer Buvik
 * Date: 11.03.14
 * Time: 14:52
 * To change this template use File | Settings | File Templates.
 */
public interface CalendarModel {
    public void addMeeting(Meeting meeting);                                                //krav 2

    public void addAttendeeToMeeting(Meeting meeting, Attendee attendee);                    //krav 3

    public void removeAttendeeFromMeeting(Meeting meeting, Attendee attendee);                    //krav 3

    public void addGroupToMeeting(Meeting meeting, String groupname);                        //krav 3

    public void setAttendeeStatus(Attendee attendee, boolean attendeeStatus);                //krav 3

    public void editMeeting(Meeting meeting);                                                //krav 4

    public void removeMeeting(String meetingid);                                            //krav 5 og 9

    public void reserveMeetingRoom(MeetingRoom meetingRoom, Meeting meeting);                //krav 6 og 10

    public List<Meeting> getMeetingsByEmployee(Employee employee);                             //krav 7 og 8

    public Map<String, Meeting> getAllMeetings();                //(henter fra modell OG database)	//krav 11 og 12

    public List<Meeting> getMeetings(List<Employee> emps);                                    //krav 13

    public void setAlarm(Attendee attendee);                                                //krav 14

}
