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
	
}
