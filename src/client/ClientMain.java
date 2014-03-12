package client;

import java.util.ArrayList;
import java.util.List;

import model.Employee;
import model.Group;
import model.Meeting;
import model.MeetingRoom;

public class ClientMain {
	
	public static final String serverIP = "localhost";
	public static final int serverPort = 54545;
	
	public static void main(String[] args) {
		
		// Kode for å starte heile klienten
		
		// Lagar tom modell
		
		List<Meeting> futureMeetings = new ArrayList<>();
		List<Employee> employeesLoggedIn = new ArrayList<>();
		List<MeetingRoom> meetingRooms = new ArrayList<>();
		List<Group> groups = new ArrayList<>();
		
		ClientModelImpl model = new ClientModelImpl(futureMeetings, employeesLoggedIn, meetingRooms, groups);
		
		ClientConnection clientConn = new ClientConnection(serverIP, serverPort);
		ConnectionListener listener = new ConnectionListener(clientConn.getConnectionSocket());
		
		// Startar ConnectionListener
		Thread listenThread = new Thread(listener);
		listenThread.start();
		
		
	}

}
