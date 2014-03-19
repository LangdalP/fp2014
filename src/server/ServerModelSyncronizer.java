package server;

import model.*;
import protocol.MessageType;
import protocol.TransferObject;
import protocol.TransferType;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ServerModelSyncronizer implements CalendarModel{
    private String username;

    @Override
    public void addMeeting(Meeting meeting) {
        System.out.println("ADDMEETING: " + getClass() + "\t" + username);
        Map<String, ObjectOutputStream> map = MultiThreadedServer.getClients();
        for (String username : MultiThreadedServer.getClients().keySet()){
            if (username.equals(this.username)) continue;  //skal ikke oppdatere seg selv.
            if (!meeting.getMapAttendees().keySet().contains(username))  continue; //sender møtet kun til de som deltar.
            System.out.println("sendTo to user: " + username);
            try {
                map.get(username).writeObject(new TransferObject(MessageType.RESPONSE, TransferType.ADD_MEETING, meeting));
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

    }

    @Override
    public void addAttendeeToMeeting(Meeting meeting, Attendee attendee) {

    }

    @Override
    public void removeAttendeeFromMeeting(Meeting meeting, Attendee attendee) {

    }

    @Override
    public void addGroupToMeeting(Meeting meeting, Group group) {

    }

    @Override
    public void setAttendeeStatus(Meeting meeting, Attendee attendee, boolean attendeeStatus) {

    }

    @Override
    public void editMeeting(Meeting meeting) {

    }

    @Override
    public void removeMeeting(String meetingid) {

    }

    @Override
    public List<Meeting> getMeetingsByEmployee(Employee employee) throws Exception {
        return null;
    }

    @Override
    public Map<String, Meeting> getOldMeetings() {
        return null;
    }

    @Override
    public List<Meeting> getMeetings(List<Employee> emps) throws Exception {
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

    @Override
    public Map<String, MeetingRoom> getMapMeetingRoom() {
        return null;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
