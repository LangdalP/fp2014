package protocol;

import db.ModelDbService;

import model.*;
import model.impl.ModelImpl;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Christoffer Buvik
 * Date: 07.03.14
 * Time: 14:08
 * To change this template use File | Settings | File Templates.
 */
public class RequestHandler {
    private static RequestHandler instance;
    private static ModelImpl model;
//    private static CalendarModel dbModelImpl;

    public RequestHandler(ModelImpl model) {
        this.model = model;
//        dbModelImpl = new ModelDbImpl();
        instance = this;
    }

    public static RequestHandler getInstance(){
        if (model == null) throw new IllegalStateException("model må opprettes. ");
        return instance;
    }

    public static boolean handleLogin(TransferObject obj) {
        TransferType type = obj.getTransferType();
        if (type == null || type == TransferType.LOGIN){
            String login = String.valueOf(obj.getObject(0));
            String passwd = String.valueOf(obj.getObject(1));
            Employee emp = new ModelDbService().getEmployeeWithPassword(login);
            if (emp != null && emp.getPassword().equals(passwd)) return true;
        }
        return false;
    }

    public static void handleInit(TransferObject obj, String username, ObjectOutputStream objOutput) throws IOException {
        TransferObject transferObject = new TransferObject(MessageType.RESPONSE, TransferType.INIT_MODEL,
                model.getMapEmployees(),
                model.getMapGroups(),
                model.getMapMeetingRoom(),
                model.getMapFutureMeetings()
        );
        
        objOutput.writeObject(transferObject);

    }
    public static void handleRequest(TransferObject obj, ObjectOutputStream objOutput) throws IOException {
        if (obj.getMsgType() != MessageType.REQUEST) return;

        TransferType type = obj.getTransferType();
        if (type == null) return;
        System.out.println("HandleRequest:");


        switch (type) {
            case ADD_MEETING:{
                Meeting meeting = (Meeting) obj.getObject(0);
                model.addMeeting(meeting);
//                dbModelImpl.addMeeting(meeting);
                //sync.addMeeting(meeting);
                System.out.println(model);
                break;
            }
            case ADD_ATTENDEE_TO_MEETING:{
                Meeting meeting = (Meeting) obj.getObject(0);
                Attendee attendee = (Attendee) obj.getObject(1);
                model.addAttendeeToMeeting(meeting, attendee);
                break;
            }


            case REMOVE_ATTENDEE_FROM_MEETING:{
                Meeting meeting = (Meeting) obj.getObject(0);
                Attendee attendee = (Attendee) obj.getObject(1);
                model.removeAttendeeFromMeeting(meeting, attendee);
//                sync.removeAttendeeFromMeeting(meeting, attendee);
                break;
            }
            case GET_AVAILABLE_MEETING_ROOMS:{
                MeetingRoom mr = (MeetingRoom) obj.getObject(0);
                Date meetingStart = (Date) obj.getObject(1);
                Integer duration = (Integer) obj.getObject(2);
                Integer minAttendees = (Integer) obj.getObject(3);
                System.out.println(mr + "\t" + meetingStart + "\t" + duration + "\t" + minAttendees);
                Map<String, MeetingRoom> map = model.getAvailableMeetingRooms(mr, meetingStart, duration, minAttendees);
                System.out.println("send back ");
                objOutput.writeObject(new TransferObject(MessageType.RESPONSE, TransferType.GET_AVAILABLE_MEETING_ROOMS, map));
                break;
            }


            case GET_EMPLOYEES: {
                List<Employee> list = new ArrayList<>(model.getMapEmployees().values());
                System.out.println("employees: " + list.size());
                objOutput.writeObject(new TransferObject(MessageType.RESPONSE, TransferType.GET_EMPLOYEES, list));
                break;
            }
            case GET_MEETINGS_BY_EMPLOYEE: {
                Employee emp = (Employee)obj.getObject(0);
                List<Meeting> list = model.getMeetingsByEmployee(emp);
                objOutput.writeObject(new TransferObject(MessageType.RESPONSE, TransferType.GET_EMPLOYEES, list));
                break;
            }
            case GET_MEETINGS_BY_EMPLOYEES: {
                List<Employee> emps = (List<Employee>) obj.getObject(0);
                List<Meeting> meetings = new ArrayList<>();
                for (Employee emp : emps){
                    meetings.addAll(model.getMeetingsByEmployee(emp));
                }
                objOutput.writeObject(new TransferObject(MessageType.RESPONSE, TransferType.GET_MEETINGS_BY_EMPLOYEES, meetings));
                break;
            }


        }
	}
	

}
