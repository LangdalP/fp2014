package model.impl;

import java.util.*;

import db.ModelDbService;
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
    
    
    
    private Map<String, Meeting> mapFutureMeetings;
    private Map<String, Employee> mapEmployees;
    private Map<String, MeetingRoom> mapMeetingRooms;
    private List<Group> groups;
    private ServerModelSyncronizer sync = null;

    /** oppretter ny tom modell. */
    public ModelImpl() {
        this.mapFutureMeetings = new HashMap<>();
        this.mapMeetingRooms = new HashMap<>();
        this.groups = new ArrayList<>();
        mapEmployees = new HashMap<>();
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

    public List<Meeting> getFutureMeetings() {
        return new ArrayList<>(mapFutureMeetings.values());
    }


    public List<MeetingRoom> getMapMeetingRooms() {
        return new ArrayList<>(mapMeetingRooms.values());
    }

    public void setFutureMeetings(Map<String, Meeting> futureMeetings) {
        this.mapFutureMeetings = futureMeetings;
    }

    public void setMapMeetingRooms(List<MeetingRoom> meetingRooms) {
        for (MeetingRoom mr : meetingRooms)
            mapMeetingRooms.put(mr.getName(), mr);
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }


    public List<Group> getGroups() {
        return groups;
    }



    public boolean isMeetingRoomAvailable(Date date, String meetingRoomId){
        return true;
    }

    @Override
    public void addMeeting(Meeting meeting){
        mapFutureMeetings.put(meeting.getMeetingID(), meeting);
    }

    @Override
    public void addAttendeeToMeeting(Meeting meeting, Attendee attendee) {
        meeting.addAttendee(attendee);
        mapFutureMeetings.put(meeting.getMeetingID(), meeting);

    }

    @Override
    public void removeAttendeeFromMeeting(Meeting meeting, Attendee attendee) {
        mapFutureMeetings.get(meeting.getMeetingID()).removeAttendee(attendee);
    }

    @Override
    public void addGroupToMeeting(Meeting meeting, String groupname) {
//		mapFutureMeetings.get(meeting.getMeetingID()).

    }

    @Override
    public void setAttendeeStatus(Attendee attendee, boolean attendeeStatus) {


    }

    @Override
    public void editMeeting(Meeting meeting) {
        // TODO Auto-generated method stub

    }

    @Override
    public void removeMeeting(String meetingid) {
        mapFutureMeetings.remove(meetingid);

    }

    @Override
    public void reserveMeetingRoom(MeetingRoom meetingRoom, Meeting meeting) {
        mapMeetingRooms.get(meetingRoom.getName()).addUpcomingMeetings(meeting);
    }

    @Override
    public List<Meeting> getMeetingsByEmployee(Employee employee) {
        List<Meeting> meetings = new ArrayList<>();
        for (Meeting m : mapFutureMeetings.values()){
            for (Attendee att : m.getAttendees()){
                if (att.getEmployee().getUsername().equals(employee.getUsername())) meetings.add(m);
            }
        }
        return meetings;
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
=======
        return new ArrayList<>(mapFutureMeetings.values());
    }

    @Override
    public List<Meeting> getAllMeetings() {
        return new ModelDbService().getAllMeetings();
    }

    @Override
    public List<Meeting> getMeetings(List<Employee> emps) {
        List<Meeting> meetings = new ArrayList<>();
        for (Employee emp : emps){
            meetings.addAll(getMeetingsByEmployee(emp));
        }
        return meetings;
    }

    @Override
    public void setAlarm(Attendee attendee) {
//        attendee.
    }
>>>>>>> 709e0f011cba75a34fbaff4f36961df9ec6f872e
}
