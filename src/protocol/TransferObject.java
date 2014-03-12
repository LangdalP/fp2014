package protocol;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TransferObject implements Serializable {
	
	private static final long serialVersionUID = 8543485611020498748L;
	
	private final MessageType msgType;		// RESPONSE ELLER REQUEST
	private final RequestType reqType;		// Sjå RequestType.java, "NOT_A_REQUEST" om msgType er RESPONSE
	private final ResponseType respType;	// Sjå ResponseType.java, "NOT_A_RESPONSE" om msgType er REQUEST
	private final List<Object> objects = new ArrayList<>();
	
	public TransferObject(MessageType msgType, RequestType reqType, ResponseType respType, Object... inObjects) {
		this.msgType = msgType;
		this.reqType = reqType;
		this.respType = respType;
		
		for (Object obj : inObjects) {
			objects.add(obj);
		}
	}

	public MessageType getMsgType() {
		return msgType;
	}

	public RequestType getReqType() {
		return reqType;
	}

	public ResponseType getRespType() {
		return respType;
	}
	
	public Object getObject(int i) {
		if (i >= 0 && i < objects.size()) {
			return objects.get(i);
		} else {
			// Skal ikkje skje
			return null;
		}
	}
	
}
