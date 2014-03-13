package client;

import java.util.ArrayList;
import java.util.List;

import model.Employee;
import model.Group;
import model.Meeting;
import model.MeetingRoom;
import model.impl.ModelImpl;

public class ClientMain {
	
	public static final String serverIP = "localhost";
	public static final int serverPort = 54545;
	
	public static void main(String[] args) {
		
		// Kode for � starte heile klienten
		
		// Lagar tom modell
		ModelImpl model = new ModelImpl();
		//init model from server
                
		ClientConnection clientConn = new ClientConnection(serverIP, serverPort);
		ConnectionListener listener = new ConnectionListener(clientConn.getConnectionSocket());
		
		// Startar ConnectionListener
		Thread listenThread = new Thread(listener);
		listenThread.start();
                
//                new Gui(model);
		
		
	}

}
