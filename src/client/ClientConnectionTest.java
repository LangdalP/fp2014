package client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.Meeting;
import protocol.MessageType;
import protocol.RequestType;
import protocol.TransferObject;

import java.util.UUID;
import model.Attendee;
import model.Employee;
import model.MeetingRoom;

public class ClientConnectionTest {

        //eksempel kode på hvordan MainClient kan brukes av GUI. 
        //Main klasse i gui trenger en instans av ClientMain og 
    
	public static void main(String[] args) {
		ClientMain client = new ClientMain();
//		client.validateLogin("pedervl", "kake55");
		
                ClientMain.validateLogin("test@epost.no", "passord");

                
                Employee emp = new Employee("test@epost.no", "a", "b");
                List<Attendee> attendees = new ArrayList<>();
                MeetingRoom meetingRoom = new MeetingRoom("P15", 20, null);
                Date date = new Date();                
                Meeting meeting = new Meeting(UUID.randomUUID().toString(), date, 45, "fad", "P15", emp, attendees, 0, meetingRoom);
//                client.sendTransferObject(new TransferObject(MessageType.REQUEST, RequestType.ADD_MEETING, meeting));
		
                
                //stopper client applikasjon. 
                if (true) System.exit(0);
	}

}
