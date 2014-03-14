package client;

import java.util.Date;
import java.util.List;

import model.Attendee;
import model.CalendarModel;
import model.Employee;
import model.Group;
import model.Meeting;
import model.MeetingRoom;

public class ClientModelImpl implements CalendarModel{
	
	private List<Meeting> futureMeetings;
	private List<Employee> employeesLoggedIn;
	private List<MeetingRoom> meetingRooms;
	private List<Group> groups;
	private ClientModelSyncronizer sync;
	
	public ClientModelImpl(List<Meeting> futureMeetings,
			List<Employee> employeesLoggedIn, List<MeetingRoom> meetingRooms,
			List<Group> groups) {
		this.futureMeetings = futureMeetings;
		this.employeesLoggedIn = employeesLoggedIn;
		this.meetingRooms = meetingRooms;
		this.groups = groups;
	}
	
	// Syncronizer har som oppgave å gi beskjed til server om at klienten har gjort endring på sin modell
	private void setSyncronizer(ClientModelSyncronizer sync) {
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
		sync.addMeeting(meeting);
	}
	
	public void removeMeeting(Meeting meeting){
		futureMeetings.remove(meeting);
	}
	
	public boolean isMeetingRoomAvailable(Date date, String meetingRoomId){
		return true;
	}


	public void addEmployeeToLoggedIn(Employee emp) {
		employeesLoggedIn.add(emp);
	}
	

	
	// Metoda tek inn ein employee, og set andre attendee-innstillingar til default verdiar
	public void addEmployteeToMeeting(Meeting meeting, Employee employee) {
		Date nowDate = new Date();
		Attendee defaultAttendee = new Attendee(employee, false, false, nowDate, false, nowDate);
		meeting.addAttendee(defaultAttendee);
	}
	
	
	@Override
	public void removeEmployeeFromMeeting(Meeting meeting, Employee emp) {
		Attendee empAttendee = null;
		// Metoda tek inn ein employee, så må først finne den korresponderande attendeen
		for (Attendee att : meeting.getAttendees()) {
			if (emp.getUsername().equals(att.getEmployee().getUsername())) {
				empAttendee = att;
			}
		}
		
		if (empAttendee != null) {
			meeting.removeAttendee(empAttendee);
		}
		
	}

	@Override
	public void addGroupToMeeting(Meeting meeting, String groupname) {
		Group group = null;
		// Leitar etter gruppe med namnet groupname
		for (Group grp : groups) {
			if (grp.getGroupName().equals(groupname)) {
				group = grp;
			}
		}
		
		if (group != null) {
			for (Employee empInGrp : group.getEmployees()) {
				
			}
		}
	}

	@Override
	public void setAttendeeStatus(Attendee attendee, boolean attendeeStatus) {
		attendee.setAttendeeStatus(attendeeStatus);
		attendee.setHasResponded(true);
	}

	@Override
	public void editMeeting(Meeting meeting) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeMeeting(String meetingid) {
		// CODE
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

	// Metoda tek inn eit attendee, så programmet må lage attendeen først
	@Override
	public void addAttendeeToMeeting(Meeting meeting, Attendee attendee) {
		// TODO Auto-generated method stub
		
	}

}
