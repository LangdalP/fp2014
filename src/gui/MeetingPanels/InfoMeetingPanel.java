package gui.MeetingPanels;


import client.ClientMain;
import client.ClientModelImpl;
import model.Attendee;
import model.Employee;
import protocol.MessageType;
import protocol.TransferObject;
import protocol.TransferType;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        deleteButton.setVisible(false);

        participateYesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClientMain.sendTransferObject(new TransferObject(MessageType.REQUEST, TransferType.SET_ATTENDEE_STATUS, mModel.meeting(), mModel.getUserAttende(), true));
            }
        });

        participateNoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClientMain.sendTransferObject(new TransferObject(MessageType.REQUEST, TransferType.SET_ATTENDEE_STATUS, mModel.meeting(), mModel.getUserAttende(), false));
            }
        });

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

