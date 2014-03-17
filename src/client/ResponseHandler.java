package client;

import model.Employee;
import model.Group;
import model.Meeting;
import model.MeetingRoom;
import model.impl.ModelImpl;
import protocol.MessageType;
import protocol.TransferObject;
import protocol.TransferType;

import java.util.Map;

/**
 * Created by Kenneth on 17.03.14.
 */
public class ResponseHandler {
    private ModelImpl model;

    public ResponseHandler(ModelImpl model) {
    }

    public ModelImpl handleInit(TransferObject obj){
        /** REQUEST  ingen objekter. RESPONSE  Obj0=mapEmployees, Obj1 = mapGroups, Obj2 = mapMeetingRooms, Obj3 = mapFutureMeetings */
        Map<String, Employee> employeeMap = (Map<String, Employee>) obj.getObject(0);
        Map<String, Group> groupMap = (Map<String, Group>) obj.getObject(1);
        Map<String, MeetingRoom> meetingRoomMap = (Map<String, MeetingRoom>) obj.getObject(2);
        Map<String, Meeting> futureMeetingMap = (Map<String, Meeting>) obj.getObject(3);
        model = new ModelImpl(futureMeetingMap, employeeMap, meetingRoomMap, groupMap);
        return model;
    }

    public void handleResponse(TransferObject obj){
        if (!obj.getMsgType().equals(MessageType.RESPONSE)) return;
        switch (obj.getTransferType()){
            case ADD_MEETING:{
                model.addMeeting((Meeting) obj.getObject(0));
                break;
            }

        }


    }
}
