package protocol;

import db.ModelDbImpl;
import db.ModelDbService;

import model.Attendee;
import model.CalendarModel;
import model.Employee;
import model.Meeting;
import model.impl.ModelImpl;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

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
    private static CalendarModel dbModelImpl;

    public RequestHandler(ModelImpl model) {
        this.model = model;
        dbModelImpl = new ModelDbImpl(model);
        instance = this;
    }

    public static RequestHandler getInstance(){
        if (model == null) throw new IllegalStateException("model må opprettes. ");
        return instance;
    }

    public static boolean handleLogin(TransferObject obj) {
        TransferType type = obj.getTransferType();        if (type == null || type == TransferType.LOGIN){
            String login = String.valueOf(obj.getObject(0));
            String passwd = String.valueOf(obj.getObject(1));
            Employee emp = new ModelDbService().getEmployee(login);
            if (emp.getPassword().equals(passwd)) return true;
        }
        return false;
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
                dbModelImpl.addMeeting(meeting);
                //sync.addMeeting(meeting);
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
            case GET_EMPLOYEES: {
                List<Employee> list = model.getEmployees();
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
