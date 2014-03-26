package gui.MeetingPanels;

import client.ClientMain;
import client.ClientModelImpl;
import gui.GuiMain;
import gui.GuiTimeOfDay;
import gui.MeetingPanels.NewMeetingPanel;
import model.Meeting;
import model.MeetingRoom;
import protocol.MessageType;
import protocol.TransferObject;
import protocol.TransferType;

import javax.swing.*;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class EditPanel extends NewMeetingPanel {

	public EditPanel(ClientModelImpl model, MeetingModel meeting) {
		super(model, meeting);
//		init();
	
	}
	
	public void init(){
    }


    @Override
    public JButton getLeftButton() {
        return getHomeButton();
    }

    @Override
    public JButton getRightButton() {
        JButton button = new JButton("Endre");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	mModel.setDescription(descText.getText());
                mModel.setMeetingTime(GuiTimeOfDay.getDate(mModel.getMeetingTime(), startTimeDropdown));
                
                if (locationRadioButton.isSelected()) {
             	   mModel.setMeetingLocation(locationTextField.getText());
                }
                
                if (roomRadioButton.isSelected()) {
             	   String roomName = (String) roomsComboBoxModel.getSelectedItem();
//             	   System.out.println(roomName);
             	   mModel.setMeetingRoom(model.getMapMeetingRoom().get(roomName));
                }
                
//                System.out.println("size: " + mModel.getMapAttendees().size());
                Meeting meeting = mModel.meeting();
                meeting.setMeetingOwner(model.getMapEmployees().get(model.getUsername()));
            	
            	model.editMeeting(meeting);
                ClientMain.sendTransferObject(new TransferObject(MessageType.REQUEST, TransferType.EDIT_MEETING, meeting));
                pcs.firePropertyChange(GuiMain.SHOW_HOME, null, null);

                if (mModel.getUserAttende().getAttendeeStatus()){
                    ClientMain.sendTransferObject(new TransferObject(MessageType.REQUEST, TransferType.SET_ATTENDEE_STATUS, mModel.meeting(), mModel.getUserAttende(), true));
                }
                if (mModel.getUserAttende().getHasResponded() && !mModel.getUserAttende().getAttendeeStatus()){
                    ClientMain.sendTransferObject(new TransferObject(MessageType.REQUEST, TransferType.SET_ATTENDEE_STATUS, mModel.meeting(), mModel.getUserAttende(), false));

                }
            }
        });
       return button;
    }

}
