package client;

import java.util.List;

import protocol.RequestType;
import protocol.TransferObject;
import server.RequestHandler;
import model.Attendee;
import model.CalendarModel;
import model.Employee;
import model.Meeting;
import model.MeetingRoom;

public class ClientModelSyncronizer implements CalendarModel {
	
	private final ClientConnection conn;
	
	public ClientModelSyncronizer(ClientConnection conn) {
		this.conn = conn;
	}
	
	@Override
	public void addEmployeeToLoggedIn(Employee emp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addMeeting(Meeting meeting) {
		// Må sende objekt
		TransferObject obj = RequestHandler.createTransferObjectRequest(RequestType.ADD_MEETING, meeting);
		
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
