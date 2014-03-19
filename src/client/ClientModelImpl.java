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

    public ClientModelImpl(Map<String, Meeting> mapFutureMeetings, Map<String, Employee> mapEmployees, Map<String, MeetingRoom> mapMeetingRooms, Map<String, Group> mapGroups) {
        super(mapFutureMeetings, mapEmployees, mapMeetingRooms, mapGroups);
        pcs = new PropertyChangeSupport(this);
        mapMeetingRoomAvailable = new HashMap<>();
    }


    public Map<String, MeetingRoom> getMapMeetingRoomAvailable() {
        return mapMeetingRoomAvailable;
    }

    public void setMapMeetingRoomAvailable(Map<String, MeetingRoom> mapMeetingRoomAvailable) {
        Map<String, MeetingRoom> oldMap = mapMeetingRoomAvailable;
        this.mapMeetingRoomAvailable = mapMeetingRoomAvailable;
        System.out.println("nr listeners: " + pcs.getPropertyChangeListeners().length);
        for (PropertyChangeListener list : pcs.getPropertyChangeListeners()){
            pcs.firePropertyChange(new PropertyChangeEvent(list, ROOMS, oldMap, this.mapMeetingRoomAvailable));
            System.out.println(list.getClass());
        }
        pcs.firePropertyChange(ROOMS, oldMap, this.mapMeetingRoomAvailable);
        for (MeetingRoom mr : this.mapMeetingRoomAvailable.values()){
            System.out.println(mr);
        }
        System.out.println("fire pcs");
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
