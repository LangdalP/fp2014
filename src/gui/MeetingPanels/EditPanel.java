package gui.MeetingPanels;

import client.ClientModelImpl;
import gui.MeetingPanels.NewMeetingPanel;
import model.Meeting;

public class EditPanel extends NewMeetingPanel {

	public EditPanel(ClientModelImpl model, MeetingModel meeting) {
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
