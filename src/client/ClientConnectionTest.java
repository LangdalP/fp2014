package client;

import java.util.*;

import model.Meeting;
import protocol.MessageType;
import protocol.RequestType;
import protocol.TransferObject;

import model.Attendee;
import model.Employee;
import model.MeetingRoom;
import protocol.TransferType;

public class ClientConnectionTest {

        //eksempel kode på hvordan MainClient kan brukes av GUI. 
        //Main klasse i gui trenger en instans av ClientMain og 
    
	public static void main(String[] args) {
		ClientMain client = new ClientMain();
//		client.validateLogin("pedervl", "kake55");
		
                ClientMain.validateLogin("test@epost.no", "passord");

                
                Employee emp = new Employee("test@epost.no", "a");
                Map<String, Attendee> attendees = new HashMap<>();
                MeetingRoom meetingRoom = new MeetingRoom("P15", 20);
                Date date = new Date();                
                Meeting meeting = new Meeting(UUID.randomUUID().toString(), date, 45, "fad", "P15", emp, attendees, 0, meetingRoom);
                client.sendTransferObject(new TransferObject(MessageType.REQUEST, TransferType.ADD_MEETING, meeting));
		
                
                //stopper client applikasjon. 
                if (true) System.exit(0);
	}

}
