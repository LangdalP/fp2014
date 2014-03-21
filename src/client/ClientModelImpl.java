package client;

import model.*;
import model.impl.ModelImpl;
import protocol.MessageType;
import protocol.TransferObject;
import protocol.TransferType;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kenneth on 18.03.14.
 */
public class ClientModelImpl extends ModelImpl {

	private Map<String, MeetingRoom> mapMeetingRoomAvailable;
    private String username;
    private PropertyChangeSupport pcs;

    public static String ROOMS = "availableRooms";
    public static String SYNC_CALENDAR = "SYNC_CALENDAR";

    public ClientModelImpl(Map<String, Meeting> mapFutureMeetings, Map<String, Employee> mapEmployees, Map<String, MeetingRoom> mapMeetingRooms, Map<String, Group> mapGroups) {
        super(mapFutureMeetings, mapEmployees, mapMeetingRooms, mapGroups);
        pcs = new PropertyChangeSupport(this);
        mapMeetingRoomAvailable = new HashMap<>();
    }


    public Map<String, MeetingRoom> getMapMeetingRoomAvailable() {
        return mapMeetingRoomAvailable;
    }

    @Override
    public void addMeeting(Meeting meeting) {
        super.addMeeting(meeting);
        System.out.println("fire sync model");
        pcs.firePropertyChange(SYNC_CALENDAR, null, null);
    }

    @Override
    public void addAttendeeToMeeting(Meeting meeting, Attendee attendee) {
        super.addAttendeeToMeeting(meeting, attendee);
        pcs.firePropertyChange(SYNC_CALENDAR, null,null);
    }

    @Override
    public void removeAttendeeFromMeeting(Meeting meeting, Attendee attendee) {
        super.removeAttendeeFromMeeting(meeting, attendee);
        System.out.println("should be null. " + getMapFutureMeetings().get(meeting.getMeetingID()));
        pcs.firePropertyChange(SYNC_CALENDAR, null, null);
    }

    @Override
    public void setAttendeeStatus(Meeting meeting, Attendee attendee, boolean attendeeStatus) {
        super.setAttendeeStatus(meeting, attendee, attendeeStatus);
        pcs.firePropertyChange(SYNC_CALENDAR, null,null);
    }

    public void setMapMeetingRoomAvailable(Map<String, MeetingRoom> mapMeetingRoomAvailable) {
        Map<String, MeetingRoom> oldMap = mapMeetingRoomAvailable;
        this.mapMeetingRoomAvailable = mapMeetingRoomAvailable;
        System.out.println("fire pcs");
        pcs.firePropertyChange(ROOMS, null, null);   //sender varsel. mottaker får oppdaterte verdier fra modellen.
    }
    
    @Override
	public void setAttendeeLastNotification(Meeting meeting, Attendee att,
			Date lastNot) {
		super.setAttendeeLastNotification(meeting, att, lastNot);
		pcs.firePropertyChange(SYNC_CALENDAR, null, null);
	}


	@Override
	public void removeMeeting(String meetingid) {
		super.removeMeeting(meetingid);
		pcs.firePropertyChange(SYNC_CALENDAR, null, null);
	}
	

    @Override
	public void editMeeting(Meeting meetingEdited) {
		super.editMeeting(meetingEdited);
		pcs.firePropertyChange(SYNC_CALENDAR, null, null);
	}


	public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

	public void addPropertyChangeListener(PropertyChangeListener listener){
        pcs.addPropertyChangeListener(listener);
    }

}
