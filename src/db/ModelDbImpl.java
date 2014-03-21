package db;

import java.util.Date;
import java.util.List;
import java.util.Map;

import model.*;
import model.impl.ModelImpl;

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
        this.dbService = new ModelDbService();
    }

    @Override
    public void addMeeting(Meeting meeting) {
    	if(meeting.getMeetngLocation() == null)
    		dbService.addMeetingAtOffice(meeting);
    	else
        dbService.addMeeting(meeting);
    }

    @Override
    public void addAttendeeToMeeting(Meeting meeting, Attendee attendee) {
    	dbService.addAttendee(meeting, attendee);
    }

    @Override
    public void removeAttendeeFromMeeting(Meeting meeting, Attendee emp) {
    	dbService.removeAttendee(meeting, emp);
    }

    @Override
    public void addGroupToMeeting(Meeting meeting, Group group) {
        for (Employee emp: group.getEmployees()){
            dbService.addAttendee(meeting, new Attendee(emp, false, false, new Date(), false, null));
        }
    }

    @Override
    public void editMeeting(Meeting meeting) {
        dbService.updateMeeting(meeting);
    }

    @Override
    public void removeMeeting(String meetingid) {
        dbService.removeMeetingById(meetingid);
    }

    @Override
    public List<Meeting> getMeetingsByEmployee(Employee employee) throws Exception{
        throw new Exception("trengs ikke. FÃ¥r ut fra mapFutureMeetings");
    }

    @Override
    public List<Meeting> getMeetings(List<Employee> emps) throws Exception {
        throw new Exception("not supported");
    }

	@Override
	public void setAttendeeStatus(Meeting meeting, Attendee attendee, boolean attendeeStatus) {
        attendee.setAttendeeStatus(attendeeStatus);
        attendee.setHasResponded(true);
        dbService.updateAttendee(attendee, meeting);

    }

    @Override
    public void setAlarm(Meeting meeting, Attendee attendee, Date alarmTime) {
        attendee.setAlarmTime(alarmTime);
        attendee.setHasAlarm(true);
        dbService.updateAttendee(attendee, meeting);
    }


	@Override
	public Map<String, Meeting> getOldMeetings() {
        Map<String, Meeting> map = dbService.getMapMeetings(true);
        return map;
	}


	@Override
	public Map<String, Employee> getMapEmployees() {
		return dbService.getEmployees();
	}


	@Override
	public Map<String, Group> getMapGroups() {
        return dbService.getMapGroups();
	}


	@Override
	public Map<String, Meeting> getMapFutureMeetings() {
        Map<String, Meeting> map = dbService.getMapMeetings(null); // Hentar eigentlig alle møte no
        return map;
	}

    @Override
    public Map<String, MeetingRoom> getMapMeetingRoom() {
        return dbService.getMeetingRooms();
    }
    
    public void updateAttendee(Attendee att, Meeting meeting) {
    	dbService.updateAttendee(att, meeting);
    }


}
