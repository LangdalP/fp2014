package server;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Attendee;
import model.CalendarModel;
import model.Employee;
import model.Group;
import model.Meeting;
import model.MeetingRoom;

public class ServerModelImpl implements CalendarModel {
	private List<Meeting> futureMeetings;
	private List<Employee> employeesLoggedIn;
	private List<MeetingRoom> meetingRooms;
	private List<Group> groups;
	private ServerModelSyncronizer sync = null;

    /**
     * oppretter ny tom modell.
     */
	public ServerModelImpl() {
		this.futureMeetings = new ArrayList<>();
		this.employeesLoggedIn = new ArrayList<>();
		this.meetingRooms = new ArrayList<>();
		this.groups = new ArrayList<>();
	}
	
	// Syncronizer har som oppgave � gi beskjed til kliener om at server har gjort endring p� sin modell
	public void setSyncronizer(ServerModelSyncronizer sync) {
		this.sync = sync;
	}

	public List<Meeting> getFutureMeetings() {
		return futureMeetings;
	}

	public List<Employee> getEmployeesLoggedIn() {
		return employeesLoggedIn;
	}

	public List<MeetingRoom> getMeetingRooms() {
		return meetingRooms;
	}

	public List<Group> getGroups() {
		return groups;
	}

	public void addMeeting(Meeting meeting){
		futureMeetings.add(meeting);
	}
	
	public void removeMeeting(Meeting meeting){
		futureMeetings.remove(meeting);
	}
	
	public boolean isMeetingRoomAvailable(Date date, String meetingRoomId){
		return true;
	}

	@Override
	public void addEmployteeToMeeting(Meeting meeting, Attendee attendee) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeEmployeeFromMeeting(Meeting meeting, Employee emp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addGroupToMeeting(Meeting meeting, String groupname) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAttendeeStatus(Attendee attendee, boolean attendeeStatus) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void editMeeting(Meeting meeting) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeMeeting(String meetingid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reserveMeetingRoom(MeetingRoom meetingRoom, Meeting meeting) {
		// TODO Auto-generated method stub
		
	}

    @Override
    public List<Meeting> getMeetingsByEmployee(Employee employee) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }


    public List<Meeting> getUpcomingMeetings() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Meeting> getAllMeetings() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Meeting> getMeetings(List<Employee> emps) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAlarm(Attendee attendee) {
		// TODO Auto-generated method stub
		
	}
}
