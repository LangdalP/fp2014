package db;

import model.*;

import java.util.List;

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

        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void addEmployteeToMeeting(Meeting meeting, Attendee attendee) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void removeEmployeeFromMeeting(Meeting meeting, Employee emp) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void addGroupToMeeting(Meeting meeting, String groupname) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setAttendeeStatus(Attendee attendee, boolean attendeeStatus) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void editMeeting(Meeting meeting) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void removeMeeting(String meetingid) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void reserveMeetingRoom(MeetingRoom meetingRoom, Meeting meeting) {
        //To change body of implemented methods use File | Settings | File Templates.
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
