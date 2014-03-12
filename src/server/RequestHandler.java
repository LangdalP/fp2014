package server;

import protocol.TransferObject;

/**
 * Created with IntelliJ IDEA.
 * User: Christoffer Buvik
 * Date: 07.03.14
 * Time: 14:08
 * To change this template use File | Settings | File Templates.
 */
public class RequestHandler {
	
	public void handleRequest(TransferObject obj) {
		switch (obj.getReqType()) {
		case LOGIN:
			// code
		}
	}
	
	 
	public static TransferObject createTransferObjectRequest(RequestType reqType, Object... inObjects){
        	return new TransferObject(MessageType.REQUEST, reqType, inObjects);
    	}

    	public static TransferObject createTransferObjectResponse(ResponseType respType, Object... inObjects){
        	return new TransferObject(MessageType.RESPONSE, respType, inObjects);
    	}

}
