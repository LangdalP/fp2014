package gui;

import client.ClientModelImpl;
import model.Meeting;

public class EditPanel extends NewMeetingPanel {

	public EditPanel(ClientModelImpl model, Meeting meeting) {
		super(model, meeting);
		// TODO Auto-generated constructor stub
		init();
	
	}
	
	public void init(){
		
		
		//knapper
		
		rb.setLabel("Lagre");
		lb.setLabel("Hjem");
	}

	/**
	 * @param args
	 */
	

}
