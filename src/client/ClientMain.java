package client;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.Employee;
import model.Group;
import model.Meeting;
import model.MeetingRoom;
import model.impl.ModelImpl;
import protocol.MessageType;
import protocol.RequestType;
import protocol.ResponseType;
import protocol.TransferObject;

public class ClientMain {
    public static Boolean loggedin;
    
    public static final String serverIP = "localhost";
    public static final int serverPort = 54545;
    private static ClientConnection clientConn;
    private static ConnectionListener listener;

    public ClientMain() {
        clientConn = new ClientConnection(serverIP, serverPort);
        listener = new ConnectionListener(clientConn.getConnectionSocket(), this);
    
        // Kode for � starte heile klienten
        
        // Lagar tom modell
        ModelImpl model = new ModelImpl();
        //init model from server
        
        // Startar ConnectionListener
        Thread listenThread = new Thread(listener);
        listenThread.start();
        
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
        // Sender en beskjed
        TransferObject loginObject = new TransferObject(MessageType.REQUEST, RequestType.LOGIN, login, passwd);
        clientConn.sendTransferObject(loginObject);
        
        long start = new GregorianCalendar().getTimeInMillis();
        while (getLoggedin() == null && start < start + 2000){
        }
        if (loggedin == null) loggedin = false;
        return loggedin;
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
    
    
    
    
    
}
