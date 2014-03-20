package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JPanel;

import model.Meeting;

public class CalenderDiverseTest {

	public static void main(String[] args) {
		JPanel containerPanel = new JPanel();
		JPanel boxPanel1 = new JPanel();
		JPanel boxPanel2 = new JPanel();
		
		containerPanel.setLayout(new GridLayout(26, 1));
		containerPanel.setPreferredSize(new Dimension(80, 400));
		containerPanel.setBackground(Color.CYAN);
		
		boxPanel1.setPreferredSize(new Dimension(80, 40));
		boxPanel1.setBackground(Color.ORANGE);
		
		boxPanel2.setPreferredSize(new Dimension(80, 40));
		boxPanel2.setBackground(Color.RED);
		
		containerPanel.add(boxPanel1);
		containerPanel.add(boxPanel2);
		
		Date testDate = new Date();
		testDate.setHours(12);
		testDate.setMinutes(30);
		Meeting meet = new Meeting("ein id");
		meet.setMeetingTime(testDate);
		meet.setDuration(90);
		
		float startHour = ((float) meet.getMeetingTime().getHours());
		if (meet.getMeetingTime().getMinutes() == 30) startHour += 0.5;
		// Skal gi 12.5
		System.out.println(startHour);
		
		float endHour = startHour + ((float) (meet.getDuration()/60));
		if (meet.getDuration() % 60 > 0) endHour += 0.5;
		// Skal gi 14.0
		System.out.println(endHour);
		
		JFrame frame = new JFrame("Test");
		frame.setContentPane(containerPanel);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		
	}

}
