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
import java.util.List;
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
	private static final int DAY_COLUMN_MAX_WIDTH = 80;
	private static final int VERT_PX_PER_HOUR = 30;
	
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
			setPreferredSize(new Dimension(80, 430));
			setMinimumSize(new Dimension(80, 430));
			
			initTopLabel();
			initMeetings();
		}
		
		public void setMeetings(Map<String, Meeting> meetingsThisDay) {
			this.meetingsThisDay = meetingsThisDay;
		}
		
		private void redrawMeetings() {
			// TODO kode
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
			c.gridx = 0; c.gridy = 1; c.gridwidth = 1; c.gridheight = 1; c.fill = GridBagConstraints.BOTH;
			c.anchor = GridBagConstraints.NORTHWEST;
			add(mCont, c);
			
			if (dayDate.getDay() == new Date().getDay()) {
				Meeting meeting1 = new Meeting("whatever id");
				Date testTime = new Date();
				testTime.setHours(12);
				testTime.setMinutes(0);
				meeting1.setDuration(60);
				meeting1.setMeetingTime(testTime);
				meeting1.setDescription("M�te");
				mCont.addMeeting(meeting1);
				
				Meeting meeting2 = new Meeting("whatever id2");
				Date testTime2 = new Date();
				testTime2.setHours(12);
				testTime2.setMinutes(30);
				meeting2.setDuration(60);
				meeting2.setMeetingTime(testTime2);
				meeting2.setDescription("M�te2");
				mCont.addMeeting(meeting2);
				
				Meeting meeting3 = new Meeting("whatever id3");
				Date testTime3 = new Date();
				testTime3.setHours(11);
				testTime3.setMinutes(30);
				meeting3.setDuration(60);
				meeting3.setMeetingTime(testTime3);
				meeting3.setDescription("M�te3");
				mCont.addMeeting(meeting3);
				
				Meeting meeting4 = new Meeting("whatever id4");
				Date testTime4 = new Date();
				testTime4.setHours(10);
				testTime4.setMinutes(0);
				meeting4.setDuration(90);
				meeting4.setMeetingTime(testTime4);
				meeting4.setDescription("M�te4");
				mCont.addMeeting(meeting4);
				
				Meeting meeting5 = new Meeting("whatever id5");
				Date testTime5 = new Date();
				testTime5.setHours(13);
				testTime5.setMinutes(0);
				meeting5.setDuration(90);
				meeting5.setMeetingTime(testTime5);
				meeting5.setDescription("M�te5");
				mCont.addMeeting(meeting5);
				
				Meeting meeting6 = new Meeting("whatever id6");
				Date testTime6 = new Date();
				testTime6.setHours(9);
				testTime6.setMinutes(0);
				meeting6.setDuration(60);
				meeting6.setMeetingTime(testTime6);
				meeting6.setDescription("M�te6");
				mCont.addMeeting(meeting6);
			}
		}
	}
	
	// Inneheld alle m�te, og teiknar horisontale strekar bort til tidspunkta
	private class MeetingContainerPanel extends JPanel {
		
		int numColumns = 1;
		int colWidth = 80;
		List<Meeting> meetings = new ArrayList<>();
		List<Meeting> addedMeetings = new ArrayList<>();
		
		public MeetingContainerPanel() {
			setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.GRAY));
			setPreferredSize(new Dimension(80, 390));
			setMinimumSize(new Dimension(80, 390));
			setLayout(null);
		}
		
		public void addMeeting(Meeting meeting) {
			meetings.add(meeting);
			
			System.out.println("Trying to add meeting to gui: " + meeting.getMeetingID());
			
			int startHour = meeting.getMeetingTime().getHours();
			int startMinute = meeting.getMeetingTime().getMinutes();
			int duration = meeting.getDuration();
			
			// Sjekkar at m�tet er mellom 8 og 20:
			if (startHour<8 || startHour>20) return;
			System.out.println("Meeting between 8 and 20");
			
			// Kj�rer sjekk om det blir overlapp
			numColumns = calculateMaxOverlap();
			System.out.println("Calc Max Overlap: " + numColumns);
			colWidth = DAY_COLUMN_MAX_WIDTH/numColumns;
			System.out.println("Col width: " + colWidth);
			// Fjernar alle avtalePanel fr� hovudpanelet
			removeAll();
			addedMeetings.clear();
			
			for (Meeting meet: meetings) {
				System.out.println("Starting to add meeting: " + meet.getMeetingID());
				float startH = hourAndMinutesToFloat(meet.getMeetingTime().getHours(), meet.getMeetingTime().getMinutes());
				float endH = startH + (float) (meet.getDuration()/60);
				if (meet.getDuration() % 60 > 0) endH += 0.5;
				System.out.println("startH: " + startH + " | endH: " + endH);
				
				int overlaps = 0;
				
				// Beregning av overlapp
				int numHalfHours = 2*(meet.getDuration()/60);
				if (meet.getDuration() % 60 > 0) numHalfHours += 1;
				
				int currentHour = meet.getMeetingTime().getHours();
				int currentMinutes = meet.getMeetingTime().getMinutes();
				for (int i=0; i<numHalfHours; i++) {
					overlaps = Math.max(overlaps, numAddedMeetingsAtTime(currentHour, currentMinutes));
					
					if (currentMinutes == 30) {
						currentMinutes = 0;
						currentHour++;
					} else {
						currentMinutes = 30;
					}
					
				}
				// Beregning av overlapp ferdig
				
				System.out.println("Overlaps: " + overlaps);
				int colPos = overlaps;
				int xPos = colWidth*colPos;
				int yPos = calculateVertPosFromTime(meet.getMeetingTime().getHours(), meet.getMeetingTime().getMinutes());
				
				CalendarMeetingPanel meetPan = new CalendarMeetingPanel(meet, colWidth);
				meetPan.setBounds(xPos, yPos, colWidth, meetPan.getHeight());
				add(meetPan);
				addedMeetings.add(meet);
			}
			
			repaint();
		}
		
		private int calculateMaxOverlap() {
			
			int max = 0;
			
			for (int hour=8; hour<20; hour++) {
				for (int mins=0; mins<60; mins+=30) {
					int meetingsAtTime = numMeetingsAtTime(hour, mins);
					System.out.println("Meetings at " + hour + ":" + mins + " - " + meetingsAtTime);
					max = Math.max(max, meetingsAtTime);
				}
			}
			System.out.println("Max overlap: " + max);
			return max;
		}
		
		private int calculateVertPosFromTime(int hour, int minutes) {
			int correctedHour = hour - 8;
			int y = correctedHour * VERT_PX_PER_HOUR;
			if (minutes == 30) y+= (VERT_PX_PER_HOUR/2);
			
			return y;
		}
		
		private int numMeetingsAtTime(int hour, int minutes) {
			int num = 0;
			float timeFloat = (float) hour;
			if(minutes == 30) timeFloat += 0.5;
			
			for (Meeting meet : meetings) {
				float startHour = (float) meet.getMeetingTime().getHours();
				if (meet.getMeetingTime().getMinutes() == 30) startHour += 0.5;
				
				float endHour = startHour + (float) (meet.getDuration()/60);
				if (meet.getDuration() % 60 > 0) endHour += 0.5;
				
				if ((timeFloat >= startHour) && (timeFloat <= endHour)) {
					num++;
				}
			}
			return num;
		}
		
		private int numAddedMeetingsAtTime(int hour, int minutes) {
			int num = 0;
			float timeFloat = (float) hour;
			if(minutes == 30) timeFloat += 0.5;
			
			for (Meeting meet : addedMeetings) {
				float startHour = (float) meet.getMeetingTime().getHours();
				if (meet.getMeetingTime().getMinutes() == 30) startHour += 0.5;
				
				float endHour = startHour + (float) (meet.getDuration()/60);
				if (meet.getDuration() % 60 > 0) endHour += 0.5;
				
				if ((timeFloat >= startHour) && (timeFloat <= endHour)) {
					num++;
				}
			}
			return num;
		}
		
		private float hourAndMinutesToFloat(int hour, int minutes) {
			float startHour = (float) hour;
			if (minutes == 30) startHour += 0.5;
			return startHour;
		}
		
		private int hourFromFloat(float time) {
			return ((int) time);
		}
		
		private int minutesFromFloat(float time) {
			float remainder = time%1;
			
			if ((remainder < 0.1) && (remainder > -0.1)) {
				return 0;
			} else {
				return 30;
			}
		}
		
		// Teiknar horisontale linjer kvar time
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
