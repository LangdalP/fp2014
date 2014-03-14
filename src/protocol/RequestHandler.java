package protocol;

import db.ModelDbImpl;
import db.ModelDbService;
import java.net.ServerSocket;
import java.net.Socket;
import model.CalendarModel;
import model.Meeting;
import model.impl.ModelImpl;
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
    private static ModelImpl model;
    private static CalendarModel dbService;

    public RequestHandler(ModelImpl model) {
        this.model = model;
        dbService = new ModelDbImpl();
        instance = this;
    }

    public static RequestHandler getInstance(){
        if (model == null) throw new IllegalStateException("model m� opprettes. ");
        return instance;
    }

    public static boolean handleLogin(TransferObject obj) {
        RequestType type = obj.getReqType();        if (type == null || type == RequestType.LOGIN){
            String login = String.valueOf(obj.getObject(0));
            String passwd = String.valueOf(obj.getObject(1));
            System.out.println("handle login: " + passwd);
            return new ModelDbService().validateLogin(login, passwd);
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
//                model.addMeeting(meeting);
                System.out.println("MEETING " + meeting);
                try {
                    new ModelDbImpl().addMeeting(meeting);
                    
                } catch(Exception e){
                    e.printStackTrace();
                }
                
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
