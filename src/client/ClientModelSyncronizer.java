package client;

import java.util.Date;
import java.util.List;
import java.util.Map;

import model.*;
import protocol.*;

public class ClientModelSyncronizer implements CalendarModel {
	
	private final ClientConnection conn;
	
	public ClientModelSyncronizer(ClientConnection conn) {
		this.conn = conn;
	}
	
	@Override
	public void addMeeting(Meeting meeting) {
		TransferObject obj = new TransferObject(MessageType.REQUEST, TransferType.ADD_MEETING, meeting);
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
    public void addGroupToMeeting(Meeting meeting, Group group) {

    }

    @Override
    public void setAttendeeStatus(Meeting meeting, Attendee attendee, boolean attendeeStatus) {

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
    public Map<String, Meeting> getOldMeetings() {
        return null;
    }


    @Override
	public List<Meeting> getMeetings(List<Employee> emps) {
		// TODO Auto-generated method stub
		return null;
	}

    @Override
    public void setAlarm(Meeting meeting, Attendee attendee, Date alarmTime) {

    }

    @Override
    public Map<String, Employee> getMapEmployees() {
        return null;
    }

    @Override
    public Map<String, Group> getMapGroups() {
        return null;
    }

    @Override
    public Map<String, Meeting> getMapFutureMeetings() {
        return null;
    }


}
