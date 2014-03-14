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
	private Map<String, Meeting> mapFutureMeetings;
    private Map<String, Employee> mapEmployees;
    private Map<String, MeetingRoom> mapMeetingRooms;
    private Map<String, Group> groups;
    private ServerModelSyncronizer sync = null;

    /** oppretter ny tom modell. */
    public ModelImpl() {
        this.mapFutureMeetings = new HashMap<>();
        this.mapMeetingRooms = new HashMap<>();
        this.groups = new HashMap<>();
        mapEmployees = new HashMap<>();
    }



    // Syncronizer har som oppgave � gi beskjed til kliener om at server har gjort endring p� sin modell
    public void setSyncronizer(ServerModelSyncronizer sync) {
        this.sync = sync;
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
        for (Group grp : groups) {
        	this.groups.put(grp.getGroupName(), grp);
        }
    }

    public Map<String, Group> getGroups() {
        return groups;
    }

    public boolean isMeetingRoomAvailable(Date date, String meetingRoomId){
        return true;
    }

    public Map<String, Meeting> getMapFutureMeetings() {
        return mapFutureMeetings;
    }

    public Map<String, Employee> getMapEmployees() {
        return mapEmployees;
    }

    @Override
    public void addMeeting(Meeting meeting){
        mapFutureMeetings.put(meeting.getMeetingID(), meeting);
    }

    @Override
    public void addAttendeeToMeeting(Meeting meeting, Attendee attendee) {
        mapFutureMeetings.get(meeting.getMeetingID()).addAttendee(attendee);
        
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

    @Override
    public Map<String, Meeting> getAllMeetings() {
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

}
