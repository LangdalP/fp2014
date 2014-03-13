package protocol;

import db.ModelDbImpl;
import model.CalendarModel;
import model.Meeting;
import protocol.MessageType;
import protocol.RequestType;
import protocol.ResponseType;
import protocol.TransferObject;

/**
 * Created with IntelliJ IDEA.
 * User: Christoffer Buvik
 * Date: 07.03.14
 * Time: 14:08
 * To change this template use File | Settings | File Templates.
 */
public class RequestHandler {
    private static RequestHandler instance;
    private static CalendarModel model;

    public RequestHandler(CalendarModel model) {
        this.model = model;
        instance = this;
    }

    public static RequestHandler getInstance(){
        if (model == null) throw new NullPointerException("model må opprettes. ");
        return instance;
    }

    public static void handleRequest(TransferObject obj) {
		RequestType type = obj.getReqType();
        if (type == null) return;

        System.out.println("HandleRequest:");

        switch (type) {
            case LOGIN: {
                System.out.println("handle login");
                break;
            }

            case ADD_MEETING:{
                Meeting meeting = (Meeting) obj.getObject(0);
                model.addMeeting(meeting);
                new ModelDbImpl().addMeeting(meeting);
                //sync.addMeeting(meeting);
                break;
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
