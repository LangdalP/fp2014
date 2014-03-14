package db;

import java.util.List;

import model.Attendee;
import model.CalendarModel;
import model.Employee;
import model.Meeting;
import model.MeetingRoom;

/**
 * Created with IntelliJ IDEA.
 * User: Christoffer Buvik
 * Date: 12.03.14
 * Time: 16:46
 * To change this template use File | Settings | File Templates.
 */
public class ModelDbImpl implements CalendarModel {
    private ModelDbService dbService;

    public ModelDbImpl() {
        dbService = new ModelDbService();
    }


    @Override
    public void addMeeting(Meeting meeting) {
        System.out.println("add meeting to database: " + meeting.toString());
        dbService.addMeeting(meeting);
        //dbService.updateMeetingIdToAttendees(meeting.getMeetingID(), meeting.getAttendees());
        //oppdater tabell deltaker_ansatt //oppdater andre avhengigheter i database.
    }

    @Override
    public void addEmployteeToMeeting(Meeting meeting, Attendee attendee) {
    }

    @Override
    public void removeEmployeeFromMeeting(Meeting meeting, Employee emp) {
    }

    @Override
    public void addGroupToMeeting(Meeting meeting, String groupname) {
    }

    @Override
    public void setAttendeeStatus(Attendee attendee, boolean attendeeStatus) {

    }

    @Override
    public void editMeeting(Meeting meeting) {
    }

    @Override
    public void removeMeeting(String meetingid) {
//        dbService.removeMeetingById(String meetingid);
    }

    @Override
    public void reserveMeetingRoom(MeetingRoom meetingRoom, Meeting meeting) {
    }

    @Override
    public List<Meeting> getMeetingsByEmployee(Employee employee) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Meeting> getAllMeetings() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Meeting> getMeetings(List<Employee> emps) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setAlarm(Attendee attendee) {

        //To change body of implemented methods use File | Settings | File Templates.
    }
}
