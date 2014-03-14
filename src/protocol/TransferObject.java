package protocol;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TransferObject implements Serializable {
	
	private static final long serialVersionUID = 8543485611020498748L;
	
	private final MessageType msgType;		// RESPONSE ELLER REQUEST
	private  RequestType reqType;		// Sj� RequestType.java, "NOT_A_REQUEST" om msgType er RESPONSE
	private  TransferType respType;	// Sj� TransferType.java, "NOT_A_RESPONSE" om msgType er REQUEST
	private  TransferType transferType;
    private final List<Object> objects = new ArrayList<>();


    public TransferObject(MessageType msgType, TransferType transferType, Object... inObjects) {
        this.msgType = msgType;
        this.transferType = transferType;
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

	public TransferType getRespType() {
		return respType;
	}

    public TransferType getTransferType() {
        return transferType;
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
