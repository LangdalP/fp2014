package gui.MeetingPanels;

import client.ClientModelImpl;
import gui.MeetingPanels.NewMeetingPanel;
import model.Meeting;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditPanel extends NewMeetingPanel {

	public EditPanel(ClientModelImpl model, MeetingModel meeting) {
		super(model, meeting);
		// TODO Auto-generated constructor stub
		init();
	
	}
	
	public void init(){


    }


    @Override
    public JButton getLeftButton() {
        return getHomeButton();
    }

    @Override
    public JButton getRightButton() {
        JButton button = new JButton("UPDATE");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("update clicked.");
            }
        });
       return button;
    }
}
