package gui.MeetingPanels;


import client.ClientModelImpl;
import gui.GuiMain;
import gui.GuiTimeOfDay;
import gui.MeetingPanels.NewMeetingPanel;
import model.Attendee;
import model.Employee;
import model.Meeting;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class InfoMeetingPanel extends NewMeetingPanel {

	public InfoMeetingPanel(ClientModelImpl model, MeetingModel meeting) {
		super(model, meeting);
        System.out.println("MEETING: " + meeting.getMeetingID());
        init();
	}
	
	public void init(){
		//gj�r knapper utrykkbare
		
		//venstre
		
		descText.setEditable(false);
		descText.setText("test");
		datePicker.setEditable(false);
        durationDropdown.setEditable(false);
        durationDropdown.setEnabled(false);
        startTimeDropdown.setEditable(false);
        startTimeDropdown.setEnabled(false);
		addEmpList.setEnabled(false);
		extraField.setEditable(false);
		roomRadioButton.setEnabled(false);
		locationRadioButton.setEnabled(false);
		roomsDropdown.setEnabled(false);
		locationTextField.setFocusable(false);
        sendEmailButton.setVisible(false);

		//knapper
		
//		rb.setVisible(false);
//		lb.setLabel("Hjem");
	}

    @Override
    public JButton getLeftButton() {
        return getHomeButton();
    }

    @Override
    public JButton getRightButton() {
        return getDeleteMeetingButton();
    }

    @Override
    protected DefaultListModel<Employee> getEmployeeDefaultListModel() {
        DefaultListModel<Employee> nameListModel = new DefaultListModel<>();
        for (Attendee att : mModel.getMapAttendees().values()){
            nameListModel.addElement(model.getMapEmployees().get(att.getEmployee().getUsername()));

        }
        return nameListModel;
    }



}

