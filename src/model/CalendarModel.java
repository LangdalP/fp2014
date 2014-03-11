package model;

import java.util.List; 

public interface CalendarModel{
	
	public void addEmployeeToLoggedIn(Employee emp);
	public void addMeeting(Meeting meeting);
	public void addEmployteeToMeeting(Meeting meeting, Attendee attendee);
	public void removeEmployeeFromMeeting(Meeting meeting, Employee emp);
	public void addGroupToMeeting(Meeting meeting, String groupname);
	public void setAttendeeStatus(Attendee attendee, boolean attendeeStatus);
	public void editMeeting(Meeting meeting);
	public void removeMeeting(String meetingid);
	public void reserveMeetingRoom(MeetingRoom meetingRoom, Meeting meeting);
	public List<Meeting> getUpcomingMeetings();
	public List<Meeting> getAllMeetings(); //henter fra modell OG database
	public List<Meeting> getMeetings(List<Employee> emps);
	public void setAlarm(Attendee attendee);

}
