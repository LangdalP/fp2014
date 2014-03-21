package client;

import model.Employee;
import model.Group;
import model.Meeting;
import model.MeetingRoom;
import model.impl.ModelImpl;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
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
    public static String SYNC_ADD_MEETING = "SYNC_ADD_MEETING";

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
        pcs.firePropertyChange(SYNC_ADD_MEETING, null, null);
    }

    public void setMapMeetingRoomAvailable(Map<String, MeetingRoom> mapMeetingRoomAvailable) {
        Map<String, MeetingRoom> oldMap = mapMeetingRoomAvailable;
        this.mapMeetingRoomAvailable = mapMeetingRoomAvailable;
        System.out.println("fire pcs");
        pcs.firePropertyChange(ROOMS, null, null);   //sender varsel. mottaker får oppdaterte verdier fra modellen.
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
