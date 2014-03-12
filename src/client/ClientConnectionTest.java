package client;

import protocol.MessageType;
import protocol.RequestType;
import protocol.ResponseType;
import protocol.TransferObject;

public class ClientConnectionTest {
	
	public static final String serverIP = "localhost";
	public static final int serverPort = 54545;
	
	public static void main(String[] args) {
		
		ClientConnection clientConn = new ClientConnection(serverIP, serverPort);
		ConnectionListener listener = new ConnectionListener(clientConn.getConnectionSocket());
		
		// Startar ConnectionListener
		Thread listenThread = new Thread(listener);
		listenThread.start();
		
		// Sender en beskjed
		TransferObject loginObject = new TransferObject(MessageType.REQUEST, RequestType.LOGIN, ResponseType.NOT_A_RESPONSE, "pedervl", "kake55");
		clientConn.sendTransferObject(loginObject);
		
		// Stoppar klientTråden for å sjekke at listeneren printar uavhengig av vår tråd
		try {
			Thread.sleep(5*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		clientConn.close();
		
	}

}
