package client;

import gui.GuiMain;

import java.util.GregorianCalendar;

import model.impl.ModelImpl;
import protocol.MessageType;
import protocol.TransferObject;
import protocol.TransferType;

public class ClientMain {
    private static Boolean loggedin;
    private static String username;

    private static boolean modelLoaded = false;
    private static ClientModelImpl model = null;
    
    private static final String serverIP = "localhost";
    private static final int serverPort = 54545;
    private static ClientConnection clientConn;
    private static ConnectionListener listener;

    public ClientMain() {
        clientConn = new ClientConnection(serverIP, serverPort);
        listener = new ConnectionListener(clientConn.getConnectionSocket(), this);
        
        // Startar ConnectionListener
        Thread listenThread = new Thread(listener);
        listenThread.start();
        
    }
    
    public static boolean validateLogin(String login, String passwd){
        // Sender en beskjed
        TransferObject loginObject = new TransferObject(MessageType.REQUEST, TransferType.LOGIN, login, passwd);
        clientConn.sendTransferObject(loginObject);
        
        long start = new GregorianCalendar().getTimeInMillis();
        while (getLoggedin() == null && start < start + 2000){
        }
        if (loggedin == null) setLoggedin(false);
        username = login;
        return loggedin;
    }

    public static synchronized Boolean getLoggedin() {
        return loggedin;
    }

    public static synchronized void setLoggedin(Boolean loggedin) {
        ClientMain.loggedin = loggedin;
    }
    
    public static synchronized void setModel(ClientModelImpl model) {
    	modelLoaded = true;
    	ClientMain.model = model;
    }
    
    public static synchronized ClientModelImpl getModel() {
    	return ClientMain.model;
    }

    public static void sendTransferObject(TransferObject obj) {
        clientConn.sendTransferObject(obj);
    }
    
    public static void sendLogout() {
    	TransferObject logout = new TransferObject(MessageType.REQUEST, TransferType.LOGOUT);
    	sendTransferObject(logout);
    }
    
    public static void closeConnection() {
    	listener.stop();
    	clientConn.close();
    }
    
    public static void shutdownClient() {
    	System.exit(0);
    }
    
    public static void main(String[] args) {
		ClientMain client = new ClientMain();
		GuiMain gui = new GuiMain();
		gui.showLogin();

		System.out.println("Login ferdig");

		// Laste inn modell her
		TransferObject pls_get_model = new TransferObject(MessageType.REQUEST, TransferType.INIT_MODEL);
		// Sp�r om modell
		ClientMain.sendTransferObject(pls_get_model);
		while (ClientMain.getModel() == null) {
			//
		}

        getModel().setUsername(username);
		System.out.println(ClientMain.getModel());
		gui.showMainPanel(ClientMain.getModel());
		
		// Starte hovedvindu her, og gi referanse til modell
		
		/*
        ClientMain.validateLogin("test@epost.no", "passord");

        
        Employee emp = new Employee("test@epost.no", "a", "b");
        Map<String, Attendee> attendees = new HashMap<>();
        MeetingRoom meetingRoom = new MeetingRoom("P15", 20, null);
        Date date = new Date();                
        Meeting meeting = new Meeting(UUID.randomUUID().toString(), date, 45, "fad", "P15", emp, attendees, 0, meetingRoom);
        ClientMain.sendTransferObject(new TransferObject(MessageType.REQUEST, TransferType.ADD_MEETING, meeting));

        
        //stopper client applikasjon. 
        if (true) System.exit(0);
        */
	}
}
