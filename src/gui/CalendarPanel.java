package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import model.Meeting;
import client.ClientModelImpl;

public class CalendarPanel extends JPanel {
	
	private static final Dimension TOP_LABEL_DIMENSION = new Dimension(80, 40);
	private static final Dimension TIME_LABEL_DIMENSION = new Dimension(80, 30);
	
	private GridBagLayout layout = new GridBagLayout();
	private ClientModelImpl model;
	
	public CalendarPanel(ClientModelImpl model) {
		setLayout(layout);
		this.model = model;
		
		init();
	}
	
	private void init() {
		GridBagConstraints c = new GridBagConstraints();
		
		CalendarTimePanel timePanel = new CalendarTimePanel();
		c.gridx = 0; c.gridy = 0; c.gridheight = 1; c.gridwidth = 1;
		add(timePanel, c);
		Date nowDay = new Date();
		Date[] allDaysOfWeek = getAllDaysOfWeek(nowDay);
		for (Date day : allDaysOfWeek) {
			CalendarDayPanel dayPanel = new CalendarDayPanel(day, new HashMap<String, Meeting>());
			c.gridx += 1;
			add(dayPanel, c);
		}
	}
	
	private Date[] getAllDaysOfWeek(Date dayDate) {
		int millisecsInDay = 24*60*60*1000;
		
		int currentDayNum = dayDate.getDay();
		int correctedDayNum = currentDayNum - 1;
		int mondayNum = 0;
		int tuesdayNum = 1;
		int wednesdayNum = 2;
		int thursdayNum = 3;
		int fridayNum = 4;
		int saturdayNum = 5;
		int sundayNum = 6;
		
		Date monday = new Date(dayDate.getTime() - millisecsInDay*(correctedDayNum-mondayNum));
		Date tuesday = new Date(dayDate.getTime() - millisecsInDay*(correctedDayNum-tuesdayNum));
		Date wednesday = new Date(dayDate.getTime() - millisecsInDay*(correctedDayNum-wednesdayNum));
		Date thursday = new Date(dayDate.getTime() - millisecsInDay*(correctedDayNum-thursdayNum));
		Date friday = new Date(dayDate.getTime() - millisecsInDay*(correctedDayNum-fridayNum));
		Date saturday = new Date(dayDate.getTime() - millisecsInDay*(correctedDayNum-saturdayNum));
		Date sunday = new Date(dayDate.getTime() - millisecsInDay*(correctedDayNum-sundayNum));
		
		Date[] returnArray = {monday, tuesday, wednesday, thursday, friday, saturday, sunday};
		return returnArray;
	}
	
	// Lagar panelet for ein dag, dvs. ei kolonne i kalenderen
	private class CalendarDayPanel extends JPanel {
		
		private Date dayDate;
		private Map<String, Meeting> meetingsThisDay;
		private GridBagConstraints c = new GridBagConstraints();
		
		public CalendarDayPanel(Date dayDate, Map<String, Meeting> meetingsThisDay) {
			this.dayDate = dayDate;
			this.meetingsThisDay = meetingsThisDay;
			
			setLayout(new GridBagLayout());
			
			
			initTopLabel();
			initMeetings();
		}
		
		private void initTopLabel() {
			GridBagConstraints cTop = new GridBagConstraints();
			int halfHeight = TOP_LABEL_DIMENSION.height/2;
			Dimension halfHeightDim = new Dimension(TOP_LABEL_DIMENSION.width, halfHeight);
			
			JPanel dayPanel = new JPanel();
			dayPanel.setLayout(new GridBagLayout());
			
			String formattedDate = new SimpleDateFormat("dd.MM.yyyy").format(dayDate);
			JLabel dateLabel = new JLabel(formattedDate);
			cTop.gridx = 0; cTop.gridy = 0; cTop.gridwidth = 1; cTop.gridheight = 1;
			dayPanel.add(dateLabel, cTop);
			
			String dayOfWeek = new SimpleDateFormat("EEEE", Locale.getDefault()).format(dayDate);
			JLabel dayNameLabel = new JLabel(dayOfWeek);
			cTop.gridy = 1;
			dayPanel.add(dayNameLabel, cTop);
			
			dayPanel.setPreferredSize(TOP_LABEL_DIMENSION);
			dayPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
			
			c.gridx = 0; c.gridy = 0; c.gridwidth = 1; c.gridheight = 1;
			add(dayPanel, c);
			
		}
		
		private void initMeetings() {
			// Container-panel
			MeetingContainerPanel mCont = new MeetingContainerPanel();
			mCont.setPreferredSize(new Dimension(80, 390));
			c.gridx = 0; c.gridy = 1; c.gridwidth = 1; c.gridheight = 1;
			add(mCont, c);
			
			
		}
	}
	
	// Inneheld alle møte, og teiknar horisontale strekar bort til tidspunkta
	private class MeetingContainerPanel extends JPanel {
		
		public MeetingContainerPanel() {
			setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.GRAY));
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			g.setColor(Color.GRAY);
			
			int numLines = 12;
			int linePos = 30;
			for (int i=0; i<numLines; i++) {
				g.drawLine(0, linePos, 80, linePos);
				
				linePos += 30;
			}
			
		}
		
		
		
	}
	
	// Lagar venstresida av kalenderen, dvs. alle tidspunkta
	private class CalendarTimePanel extends JPanel {
		
		public CalendarTimePanel() {
			
			setLayout(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();
			Border brdr = BorderFactory.createLineBorder(Color.BLACK, 1);
			
			JLabel timeText = new JLabel("  Klokkeslett");
			timeText.setBorder(brdr);
			timeText.setPreferredSize(TOP_LABEL_DIMENSION);
			c.gridx = 0; c.gridy = 0; c.gridwidth = 1; c.gridwidth = 1;
			add(timeText, c);
			
			Border bottomlessBorder = BorderFactory.createMatteBorder(0, 1, 1, 1, Color.BLACK);
			addTimeLabels(c, brdr);
		}
		
		private void addTimeLabels(GridBagConstraints c, Border brdr) {
			int hoursStart = 8;
			int hoursEnd = 20;
			
			for (int i=hoursStart; i<= hoursEnd; i++) {
				c.gridy = c.gridy + 1;
				
				String labelString = Integer.toString(i);
				JLabel timeLabel = new JLabel("  " + labelString + ": 00");
				timeLabel.setBorder(brdr);
				timeLabel.setPreferredSize(TIME_LABEL_DIMENSION);
				add(timeLabel, c);
				
			}
			
		}
		
		
		
	}

	public static void main(String[] args) {
		
		CalendarPanel calPan = new CalendarPanel(null);
		
		JFrame frame = new JFrame("Topptekst");
		
		frame.setContentPane(calPan);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		
		
		
	}
}
