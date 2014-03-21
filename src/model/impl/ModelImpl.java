package model.impl;

import java.util.*;

import db.ModelDbImpl;
import db.ModelDbService;
import model.*;

/**
 *  Modell for server og klient. Modellen skal være oppdatert til enhvert tid.
 *
 *  KOMMENTAR: feltene skal være final fordi tilstand til modellen skal opprettes kun èn gang.
 *  CalendarModel har som hensikt å tilby alle metoder for å endre tilstand i modellen.
 *  Bruk toString for å se på tilstand.
 */
public class ModelImpl implements CalendarModel {
	/** key = meetingID */
	private final Map<String, Meeting> mapFutureMeetings;
	/** key = userName */
    private final Map<String, Employee> mapEmployees;
    /** key = roomName */
    private final Map<String, MeetingRoom> mapMeetingRooms;
    /** Key gruppe.navn, Value list med epost for ansatt.  */
    private final Map<String, Group> mapGroups; //



    public ModelImpl(Map<String, Meeting> mapFutureMeetings, Map<String, Employee> mapEmployees, Map<String, MeetingRoom> mapMeetingRooms, Map<String, Group> mapGroups) {
        this.mapFutureMeetings = mapFutureMeetings;
        this.mapEmployees = mapEmployees;
        this.mapMeetingRooms = mapMeetingRooms;
        this.mapGroups = mapGroups;
    }

    @Override
    public Map<String, Meeting> getMapFutureMeetings() {
        return mapFutureMeetings;
    }

    @Override
    public Map<String, MeetingRoom> getMapMeetingRoom() {
        return mapMeetingRooms;
    }

    @Override
    public Map<String, Employee> getMapEmployees() {
        return mapEmployees;
    }

    @Override
    public Map<String, Group> getMapGroups() {
        return mapGroups;
    }

    @Override
    public void addMeeting(Meeting meeting){
        //@todo sjekk om møterom er ledig.

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
    public void removeMeeting(String meetingid) {
        mapFutureMeetings.remove(meetingid);

    }

    @Override
    public List<Meeting> getMeetingsByEmployee(Employee employee) {
        List<Meeting> meetings = new ArrayList<>();
        for (Meeting m : mapFutureMeetings.values()){
            for (Attendee att : m.getAttendees()){
            	System.out.println(att);
                if (att.getEmployee().getUsername().equals(employee.getUsername())) meetings.add(m);
            }
        }
        return meetings;
    }

    @Override
    public Map<String, Meeting> getOldMeetings() {
        return new ModelDbService().getMapMeetings(true);
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
    public void setAlarm(Meeting meeting, Attendee attendee, Date alarmTime) {
        String username = attendee.getEmployee().getUsername();
        mapFutureMeetings.get(meeting.getMeetingID()).getMapAttendees().get(username).setAlarmTime(alarmTime);
        mapFutureMeetings.get(meeting.getMeetingID()).getMapAttendees().get(username).setHasAlarm(true);
    }

    @Override
    public void addGroupToMeeting(Meeting meeting, Group group) {
         List<Employee> groupMembers = mapGroups.get(group.getGroupName()).getEmployees();
        for (Employee emp : groupMembers){
            Date lastNotification = new Date();
            Attendee att = new Attendee(mapEmployees.get(emp.getUsername()), false, false, lastNotification, false, null);
            mapFutureMeetings.get(meeting.getMeetingID()).addAttendee(att);
        }

    }

    @Override
    public void setAttendeeStatus(Meeting meeting, Attendee attendee, boolean attendeeStatus) {
        mapFutureMeetings.get(meeting.getMeetingID()).getMapAttendees().get(attendee.getEmployee().getUsername()).setHasResponded(true);
        mapFutureMeetings.get(meeting.getMeetingID()).getMapAttendees().get(attendee.getEmployee().getUsername()).setAttendeeStatus(attendeeStatus);
    }

    @Override
    public void editMeeting(Meeting meetingEdited) {
        mapFutureMeetings.put(meetingEdited.getMeetingID(), meetingEdited);
    }



    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ModelImpl{" + "\n");
        sb.append("mapFutureMeetings:\n");
        for (Meeting m : mapFutureMeetings.values()) sb.append(m.toString()+"\n\n");
        for (MeetingRoom mr : mapMeetingRooms.values()) sb.append(mr.toString()+"\n");
        for (Employee e : mapEmployees.values()) sb.append(e.toString()+"\n");
        for (Group g : mapGroups.values()) sb.append(g.toString()+"\n");
        sb.append("\n");
        return sb.toString();
    }

    public Map<String, MeetingRoom> getAvailableMeetingRooms(MeetingRoom mr, Date meetingStart, Integer duration, Integer minAttendees) {
        if (minAttendees == null) minAttendees = 0;
        Map<String, MeetingRoom> availableRooms = new HashMap<>();
        long mStart = meetingStart.getTime();
        long mEnd = meetingStart.getTime() + (duration * 60 * 1000); //end in millisecond.
        List<MeetingRoom> roomsNotAvailable = new ArrayList<>();

        //finner alle rom som IKKE er ledige.
        for (Meeting m : mapFutureMeetings.values()){
            if (m.getMeetingTime().getTime() >= mStart && m.getMeetingTime().getTime() <= mEnd){
                 //not available
            }
            else roomsNotAvailable.add(m.getMeetingRoom());
        }

        //legger til alle ledige rom i map.
        for (MeetingRoom m : mapMeetingRooms.values()){
            if (!roomsNotAvailable.contains(m) && m.getMaxPeople() >= minAttendees) availableRooms.put(m.getName(), m);
        }
        return availableRooms;
    }


}
