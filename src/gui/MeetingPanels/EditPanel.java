package gui.MeetingPanels;

import client.ClientModelImpl;
import gui.MeetingPanels.NewMeetingPanel;
import model.Meeting;

import javax.swing.*;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditPanel extends NewMeetingPanel {

	public EditPanel(ClientModelImpl model, MeetingModel meeting) {
		super(model, meeting);
		// TODO Auto-generated constructor stub
		init();
	
	}
	
	public void init(){
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 2;
		c.gridy = 8;
		c.gridheight = 1;
		c.gridwidth = 1;
		JButton deleteButton = new JButton("Slett");
		rp.add(deleteButton, c);
		
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
