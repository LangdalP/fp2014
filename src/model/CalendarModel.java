package model;

import java.util.Date;
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

    public void addGroupToMeeting(Meeting meeting, Group group);                        //krav 3

    public void setAttendeeStatus(Meeting meeting, Attendee attendee, boolean attendeeStatus);                //krav 3

    public void editMeeting(Meeting meeting);                                                //krav 4

    public void removeMeeting(String meetingid);                                            //krav 5 og 9

    public void reserveMeetingRoom(MeetingRoom meetingRoom, Meeting meeting);                //krav 6 og 10

    public List<Meeting> getMeetingsByEmployee(Employee employee) throws Exception;                             //krav 7 og 8

    /**
     * Gamle møter kan ikke endres på, slettes eller legges til.
     * @return liste over gamle møter.
     */
    public Map<String, Meeting> getOldMeetings();                //(henter fra database)	//krav 11 og 12

    public List<Meeting> getMeetings(List<Employee> emps) throws Exception;                                    //krav 13

    public void setAlarm(Meeting meeting, Attendee attendee, Date alarmTime);                                                //krav 14

    public Map<String, Employee> getMapEmployees();

    public Map<String, Group> getMapGroups();

    public Map<String, Meeting> getMapFutureMeetings();

    public Map<String, MeetingRoom> getMapMeetingRoom();
}
