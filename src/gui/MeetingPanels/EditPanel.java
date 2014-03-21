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
            }
        });
       return button;
    }

    @Override
    public String[] getRooms(boolean init) {
        List<MeetingRoom> rooms = null;
        if (init) rooms = new ArrayList<>(model.getMapMeetingRoom().values());
        else rooms = new ArrayList<>(model.getMapMeetingRoomAvailable().values());
        String[] roomsArr = new String[rooms.size()+1];
        roomsArr[0] = "Velg rom";
        if (mModel.getMeetingRoom() != null) roomsArr[0] = mModel.getMeetingRoom().getName();
        for (int i = 1; i < rooms.size(); i++){
            roomsArr[i] = rooms.get(i).getName();
        }
        return roomsArr;
    }
}
