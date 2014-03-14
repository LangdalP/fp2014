package client;

import java.util.List;
import java.util.Map;

import protocol.RequestType;
import protocol.TransferObject;
import protocol.RequestHandler;
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
	public void addMeeting(Meeting meeting) {
		TransferObject obj = RequestHandler.createTransferObjectRequest(RequestType.ADD_MEETING, meeting);
		conn.sendTransferObject(obj);
	}
	
	@Override
	public void addAttendeeToMeeting(Meeting meeting, Attendee attendee) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeAttendeeFromMeeting(Meeting meeting, Attendee attendee) {
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
	public Map<String, Meeting> getAllMeetings() {
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
