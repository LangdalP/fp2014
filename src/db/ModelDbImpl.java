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
    private ModelImpl model;

    @Override
    public void addMeeting(Meeting meeting) {
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
    public void reserveMeetingRoom(MeetingRoom meetingRoom, Meeting meeting) {
        //@todo fix this
    }

    @Override
    public List<Meeting> getMeetingsByEmployee(Employee employee) throws CompileNotSupportedException{
        throw new CompileNotSupportedException("trengs ikke. Får ut fra mapFutureMeetings");
    }

    /*
    @Override
    public Map<String, Meeting> getAllMeetings() {
        Map<String, Meeting> dbMeetings = dbService.getAllMeetings(); // Har forel�pig ikkje attendees
        for (Meeting meet : dbMeetings.values()) {
        	List<Attendee> dbAttendees = dbService.getAttendees(meet.getMeetingID());
        	Attendee correctedAttendee;
        	for (Attendee dbatt : dbAttendees) {
        		correctedAttendee = new Attendee(model.getm.get(dbatt.getEmployee().getName()),
        				dbatt.getHasResponded(), dbatt.getAttendeeStatus(), dbatt.getLastNotification(),
        				dbatt.getHasAlarm(), dbatt.getAlarmTime());
        		meet.addAttendee(correctedAttendee);
        	}
        }
        // Har attendees, men ikkje meetingroom
        return dbMeetings;
    }
    */

    @Override
    public List<Meeting> getMeetings(List<Employee> emps) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
    
    public Map<String, Employee> getEmployees() {
    	return dbService.getEmployees();
    }


    /*
    public List<Group> getGroups() {
    	// Gruppene er tomme naar dei kjem fraa get
    	Map<String, Group> dbGroups = dbService.getGroups();
    	for (Group grp : dbGroups) {
    		// Finner dei ansatte i gruppa
    		List<Employee> empsInGrp = dbService.getEmployeesInGroup(grp);
    		for (Employee emp : empsInGrp) {
    			grp.addEmployees(model.getMapEmployees().get(emp.getUsername()));
    		}
    	}
    	return dbGroups;
	}
    */

    public Map<String, MeetingRoom> getMeetingRooms() {
    	Map<String, MeetingRoom> dbRoomsMap = dbService.getMeetingRooms();	// M�terommet har ingen upcomingMeetings
    	for (MeetingRoom dbRoom : dbRoomsMap.values()) {
    		List<Meeting> dbMeetings = dbService.getUpcomingMeetingsInMeetingRoom(dbRoom.getName());
    		for (Meeting emptyMeet : dbMeetings) {
    			// Fyller m�terom med m�ter
    			Meeting modelMeet = model.getMapFutureMeetings().get(emptyMeet.getMeetingID());
    			dbRoom.addUpcomingMeetings(modelMeet);
    			
    			// Setter referansen i Meeting modelMeet til dette m�terommet
    			modelMeet.setMeetingRoomBooked(true);
    			modelMeet.setMeetingRoom(dbRoom);
    			
    		}
    	}
    	return dbRoomsMap;
    }


	@Override
	public void setAttendeeStatus(Meeting meeting, Attendee attendee,
			boolean attendeeStatus) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Map<String, Meeting> getOldMeetings() {
		return dbService.getMapMeetings(true);
	}


	@Override
	public Map<String, Employee> getMapEmployees() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Map<String, Group> getMapGroups() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Map<String, Meeting> getMapFutureMeetings() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setAlarm(Meeting meeting, Attendee attendee, Date alarmTime) {
		// TODO Auto-generated method stub
		
	}
}
