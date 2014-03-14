package model.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Attendee;
import model.CalendarModel;
import model.Employee;
import model.Group;
import model.Meeting;
import model.MeetingRoom;
import server.ServerModelSyncronizer;

public class ModelImpl implements CalendarModel {
	private List<Meeting> futureMeetings;
	private List<Employee> employees;
	private List<MeetingRoom> meetingRooms;
	private List<Group> groups;
	private ServerModelSyncronizer sync = null;
        
    /**
     * oppretter ny tom modell.
     */
    public ModelImpl() {
        this.futureMeetings = new ArrayList<>();
        this.meetingRooms = new ArrayList<>();
        this.groups = new ArrayList<>();
        this.employees = new ArrayList<>();
    }
    
    
    
    // Syncronizer har som oppgave � gi beskjed til kliener om at server har gjort endring p� sin modell
    public void setSyncronizer(ServerModelSyncronizer sync) {
        this.sync = sync;
    }
    
    public List<Meeting> getFutureMeetings() {
        return futureMeetings;
    }
    
    
    public List<MeetingRoom> getMeetingRooms() {
        return meetingRooms;
    }
    
    public void setFutureMeetings(List<Meeting> futureMeetings) {
        this.futureMeetings = futureMeetings;
    }
    
    public void setMeetingRooms(List<MeetingRoom> meetingRooms) {
        this.meetingRooms = meetingRooms;
    }
    
    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }
    
    

	public List<Group> getGroups() {
		return groups;
	}

        @Override
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



	public List<Employee> getEmployees() {
		return employees;
	}



	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}
	
	public Employee getEmployeeByEmail(String email) {
		for (Employee emp : employees) {
			if (emp.getUsername().equals(email)) {
				return emp;
			}
		}
		return null;
	}
}
