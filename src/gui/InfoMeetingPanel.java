package gui;


import javax.swing.JButton;

import client.ClientModelImpl;

public class InfoMeetingPanel extends NewMeetingPanel {
	
	
		
		
		
	
	
	public InfoMeetingPanel(ClientModelImpl model) {
		super(model);
		init();
	}
	
	public void init(){
		//gjør knapper utrykkbare
		
		//venstre
		
		descText.setEditable(false);
		descText.setText("test");
//		descText.setVisible(false);
		
		datePicker.setEditable(false);
		
		startTimeDropdown.setEnabled(false);
		durationDropdown.setEnabled(false);
		
	//	String a = Integer.toString((int) durationDropdown.getSelectedItem());
		 
	//	 System.out.println(a); Kan være alternativ til gråuet ut dropdown?
		
	
		//høyre
		
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

