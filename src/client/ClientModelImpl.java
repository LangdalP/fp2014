package client;

import model.Employee;
import model.Group;
import model.Meeting;
import model.MeetingRoom;
import model.impl.ModelImpl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kenneth on 18.03.14.
 */
public class ClientModelImpl extends ModelImpl {
    private Map<String, MeetingRoom> mapMeetingRoomAvailable;
    private String username;

    public ClientModelImpl(Map<String, Meeting> mapFutureMeetings, Map<String, Employee> mapEmployees, Map<String, MeetingRoom> mapMeetingRooms, Map<String, Group> mapGroups) {
        super(mapFutureMeetings, mapEmployees, mapMeetingRooms, mapGroups);
        mapMeetingRoomAvailable = new HashMap<>();
    }


    public Map<String, MeetingRoom> getMapMeetingRoomAvailable() {
        return mapMeetingRoomAvailable;
    }

    public void setMapMeetingRoomAvailable(Map<String, MeetingRoom> mapMeetingRoomAvailable) {
        this.mapMeetingRoomAvailable = mapMeetingRoomAvailable;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
