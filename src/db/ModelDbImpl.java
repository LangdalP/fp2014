package db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Attendee;
import model.CalendarModel;
import model.Employee;
import model.Group;
import model.Meeting;
import model.MeetingRoom;
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

    public ModelDbImpl(ModelImpl model) {
    	this.model = model;
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
    public void addAttendeeToMeeting(Meeting meeting, Attendee attendee) {
    }

    @Override
    public void removeAttendeeFromMeeting(Meeting meeting, Attendee emp) {
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

    @Override
    public List<Meeting> getMeetings(List<Employee> emps) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setAlarm(Attendee attendee) {

        //To change body of implemented methods use File | Settings | File Templates.
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
    	List<MeetingRoom> dbMeetingRooms = dbService.getMeetingRooms(); // Møteromma har tom liste upcomingMeetings
    	for (MeetingRoom dbRoom : dbMeetingRooms) {
    		List<Meeting> dbMeetings = dbService.getUpcomingMeetingsInMeetingRoom(dbRoom.getName());
    		for (Meeting emptyMeet : dbMeetings) {
    			// Fyller m�terom med avtalar fr� modellen
    			Meeting modelMeet = model.getMapFutureMeetings().get(emptyMeet.getMeetingID());
    			dbRoom.addUpcomingMeetings(modelMeet);
    			
    			// Setter referansen i Meeting modelMeet til dette m�terommet
    			modelMeet.setMeetingRoomBooked(true);
    			modelMeet.setMeetingRoom(dbRoom);
    			
    		}
    	}
//    	return dbMeetingRooms;
        return new HashMap<>();
    }
}
