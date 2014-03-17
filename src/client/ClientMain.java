package client;

import model.impl.ModelImpl;
import gui.GuiMain;
import protocol.TransferObject;

public class ClientMain {
    public static Boolean loggedin;
    
    public static final String serverIP = "localhost";
    public static final int serverPort = 54545;
    private static ClientConnection clientConn;
    private static ConnectionListener listener;

    public ClientMain() {
    	/*
        clientConn = new ClientConnection(serverIP, serverPort);
        listener = new ConnectionListener(clientConn.getConnectionSocket(), this);
        
        // Startar ConnectionListener
        Thread listenThread = new Thread(listener);
        listenThread.start();
        
        */
//        boolean login = validateLogin("pedervl", "kake55");
//        System.out.println("LOGIN: " + login);
        
//        clientConn.sendTransferObject(new TransferObject(MessageType.REQUEST, RequestType.ADD_MEETING, new Meeting(UUID.randomUUID().toString())));
//        new Gui(model);
//        listener.stop();
//        clientConn.close();
//        System.out.println("exit");
//        if (true) System.exit(0);
    }
    
    public static boolean validateLogin(String login, String passwd){
    	/*
        // Sender en beskjed
        TransferObject loginObject = new TransferObject(MessageType.REQUEST, TransferType.LOGIN, login, passwd);
        clientConn.sendTransferObject(loginObject);
        
        long start = new GregorianCalendar().getTimeInMillis();
        while (getLoggedin() == null && start < start + 2000){
        }
        if (loggedin == null) loggedin = false;
        return loggedin;
        */
    	return true;
    }

    public static synchronized Boolean getLoggedin() {
        return loggedin;
    }

    public synchronized void setLoggedin(Boolean loggedin) {
        ClientMain.loggedin = loggedin;
    }

    public static void sendTransferObject(TransferObject obj) {
        clientConn.sendTransferObject(obj);
    }
    
    public static void main(String[] args) {
		ClientMain client = new ClientMain();
		GuiMain gui = new GuiMain();
		gui.showLogin();
		System.out.println("Login ferdig");
		
		// Laste inn modell her
		
		// Starte hovedvindu her, og gi referanse til modell
		gui.showMainPanel(null);
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
		System.exit(0);
	}
}
