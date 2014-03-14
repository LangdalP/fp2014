package protocol;

import db.ModelDbImpl;
import db.ModelDbService;

import model.Attendee;
import model.CalendarModel;
import model.Employee;
import model.Meeting;
import model.impl.ModelImpl;

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
        RequestType type = obj.getReqType();        if (type == null || type == RequestType.LOGIN){
            String login = String.valueOf(obj.getObject(0));
            String passwd = String.valueOf(obj.getObject(1));
            Employee emp = new ModelDbService().getEmployee(login);
            if (emp.getPassword().equals(passwd)) return true;
        }
        return false;
    }

    public static void handleRequest(TransferObject obj) {
        RequestType type = obj.getReqType();
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
            }
        }
	}
	
	 
	public static TransferObject createTransferObjectRequest(RequestType reqType, Object... inObjects){
        	return new TransferObject(MessageType.REQUEST, reqType, inObjects);
    	}

    	public static TransferObject createTransferObjectResponse(ResponseType respType, Object... inObjects){
        	return new TransferObject(MessageType.RESPONSE, respType, inObjects);
    	}

}
