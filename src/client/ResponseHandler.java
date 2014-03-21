package client;

import model.*;
import protocol.MessageType;
import protocol.TransferObject;

import java.util.Map;

/**
 * Created by Kenneth on 17.03.14.
 */
public class ResponseHandler {
    private ClientModelImpl model;

    public ResponseHandler() {
    }

    public ClientModelImpl handleInit(TransferObject obj){
        /** REQUEST  ingen objekter. RESPONSE  Obj0=mapEmployees, Obj1 = mapGroups, Obj2 = mapMeetingRooms, Obj3 = mapFutureMeetings */
        Map<String, Employee> employeeMap = (Map<String, Employee>) obj.getObject(0);
        Map<String, Group> groupMap = (Map<String, Group>) obj.getObject(1);
        Map<String, MeetingRoom> meetingRoomMap = (Map<String, MeetingRoom>) obj.getObject(2);
        Map<String, Meeting> futureMeetingMap = (Map<String, Meeting>) obj.getObject(3);
        model = new ClientModelImpl(futureMeetingMap, employeeMap, meetingRoomMap, groupMap);
        return model;
    }

    public void handleResponse(TransferObject obj){
        if (!obj.getMsgType().equals(MessageType.RESPONSE)) return;
        System.out.println("Client HandleResponse ");
        switch (obj.getTransferType()){
            case ADD_MEETING:{
                model.addMeeting((Meeting) obj.getObject(0));
                break;
            }
            case GET_AVAILABLE_MEETING_ROOMS:{
                Map<String, MeetingRoom> availableMeetingRooms = (Map<String, MeetingRoom>) obj.getObject(0);
                model.setMapMeetingRoomAvailable(availableMeetingRooms);

                break;
            }
            case ADD_ATTENDEE_TO_MEETING:{
                model.addAttendeeToMeeting((Meeting) obj.getObject(0), (Attendee)obj.getObject(1));

                break;
            }
            case REMOVE_ATTENDEE_FROM_MEETING:{
                model.removeAttendeeFromMeeting((Meeting) obj.getObject(0), (Attendee) obj.getObject(1));
                break;
            }
            case SET_ATTENDEE_STATUS: {
                model.setAttendeeStatus((Meeting) obj.getObject(0), (Attendee) obj.getObject(1), (boolean) obj.getObject(2));
                break;
            }
            case REMOVE_MEETING: {
            	String meetingID = (String) obj.getObject(0);
            	model.removeMeeting(meetingID);
            	break;
            }
            case EDIT_MEETING: {
            	Meeting newMeeting = (Meeting) obj.getObject(0);
            	model.editMeeting(newMeeting);
            	break;
            }

        }


    }
}
