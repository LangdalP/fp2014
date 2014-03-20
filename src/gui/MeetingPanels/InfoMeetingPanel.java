package gui.MeetingPanels;


import client.ClientModelImpl;
import gui.MeetingPanels.NewMeetingPanel;
import model.Meeting;

public class InfoMeetingPanel extends NewMeetingPanel {
	
	
		
		
		
	
	
	public InfoMeetingPanel(ClientModelImpl model, Meeting meeting) {
		super(model, meeting);
		init();
	}
	
	public void init(){
		//gj�r knapper utrykkbare
		
		//venstre
		
		descText.setEditable(false);
		descText.setText("test");
		datePicker.setEditable(false);
		startTimeDropdown.setEnabled(false);
		durationDropdown.setEnabled(false);
		addEmpList.setEnabled(false);
		extraField.setEditable(false);
		roomRadioButton.setEnabled(false);
		locationRadioButton.setEnabled(false);
		roomsDropdown.setEnabled(false);
		locationTextField.setFocusable(false);
		
		
		//knapper
		
		rb.setVisible(false);
		lb.setLabel("Hjem");
	}
	
}

