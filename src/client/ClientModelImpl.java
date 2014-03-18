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
    private Map<String, Boolean> mapMeetingRoomAvailable;

    public ClientModelImpl(Map<String, Meeting> mapFutureMeetings, Map<String, Employee> mapEmployees, Map<String, MeetingRoom> mapMeetingRooms, Map<String, Group> mapGroups) {
        super(mapFutureMeetings, mapEmployees, mapMeetingRooms, mapGroups);
        mapMeetingRoomAvailable = new HashMap<>();
        for (MeetingRoom mr : mapMeetingRooms.values()){
            mapMeetingRoomAvailable.put(mr.getName(), null);
        }
    }

    @Override
    public boolean isMeetingRoomAvailable(MeetingRoom mr, Date meetingStart, Integer duration) {
            return mapMeetingRoomAvailable.get(mr.getName());
    }

    public Map<String, Boolean> getMapMeetingRoomAvailable() {
        return mapMeetingRoomAvailable;
    }
}
