package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import model.Employee;
import model.Meeting;
import client.ClientModelImpl;

public class CalendarPanel extends JPanel {
	
	private static final Dimension TOP_LABEL_DIMENSION = new Dimension(80, 40);
	private static final Dimension TIME_LABEL_DIMENSION = new Dimension(80, 30);
	private static final int DAY_COLUMN_MAX_WIDTH = 80;
	private static final int VERT_PX_PER_HOUR = 30;
	
	private ClientModelImpl model;
	private List<Employee> emps = new ArrayList<>();
	
	private JPanel calendarContainer = new JPanel();
	private Date dayInWeek = new Date();
	private CalendarTimePanel timePanel = new CalendarTimePanel();
	private GridBagConstraints c2;
	private HashMap<Employee, List<Meeting>> meetingsByEmployee = new HashMap<>();
	
	public CalendarPanel(ClientModelImpl model) {
		setLayout(new GridBagLayout());
		this.model = model;
		
		
		init();
	}
	
	private void init() {
		GridBagConstraints c = new GridBagConstraints();
		c2 = new GridBagConstraints();
		
		Employee myEmployee = model.getMapEmployees().get(model.getUsername());
		emps.add(myEmployee);
		System.out.println(myEmployee);
		meetingsByEmployee.put(myEmployee, model.getMeetingsByEmployee(myEmployee));
		
		calendarContainer.setLayout(new GridBagLayout());
		c.gridx = 0; c.gridy = 0; c.gridheight = 1; c.gridwidth = 1;
		calendarContainer.add(timePanel, c);
		Date[] allDaysOfWeek = getAllDaysOfWeek(dayInWeek);
		for (Date day : allDaysOfWeek) {
			List<Meeting> myMeetingsThisDay = getMeetingsOnDate(meetingsByEmployee.get(myEmployee), day);
			Map<Employee, List<Meeting>> mapEmpMeets = new HashMap<>();
			mapEmpMeets.put(myEmployee, myMeetingsThisDay);
			CalendarDayPanel dayPanel = new CalendarDayPanel(day, mapEmpMeets);
			c.gridx += 1;
			calendarContainer.add(dayPanel, c);
		}
		
		
		JButton prevWeek = new JButton(new PrevWeekAction("<"));
		c2.gridx = 0; c2.gridy = 0; c2.gridheight = 10; c2.gridwidth = 1;
		add(prevWeek, c2);
		
		c2.gridx = 1; c2.gridy = 0; c2.gridheight = 10; c2.gridwidth = 10;
		add(calendarContainer, c2);
		
		JButton nextWeek = new JButton(new NextWeekAction(">"));
		c2.gridx = 11; c2.gridy = 0; c2.gridheight = 10; c2.gridwidth = 1;
		add(nextWeek, c2);

	}
	
	// TODO Fikse bug
	public void changeWeekBack() {
		System.out.println("Inside changeWeekBack");
		long currentMs = dayInWeek.getTime();
		long newTimeMs = currentMs - (7*24*60*60*1000);
		dayInWeek = new Date(newTimeMs);
		for (int i = 7; i>0; i--) {
			calendarContainer.remove(i);
		}
		Date[] allDaysOfWeek = getAllDaysOfWeek(dayInWeek);
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0; c.gridy = 0; c.gridheight = 1; c.gridwidth = 1;
		for (Date day : allDaysOfWeek) {
			// TODO treng metode for å finne alle avtalar per dag, kanskje også per ansatt
			//CalendarDayPanel dayPanel = new CalendarDayPanel(day, new HashMap<String, Meeting>());
			c.gridx += 1;
			//calendarContainer.add(dayPanel, c);
		}
		repaint();
	}
	
	// TODO Fikse bug
	public void changeWeekForward() {
		System.out.println("Inside changeWeekForward");
		long currentMs = dayInWeek.getTime();
		long newTimeMs = currentMs + (7*24*60*60*1000);
		dayInWeek = new Date(newTimeMs);
		for (int i = 7; i>0; i--) {
			calendarContainer.remove(i);
		}
		Date[] allDaysOfWeek = getAllDaysOfWeek(dayInWeek);
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0; c.gridy = 0; c.gridheight = 1; c.gridwidth = 1;
		for (Date day : allDaysOfWeek) {
			// TODO treng metode for å finne alle avtalar per dag, kanskje også per ansatt
			//CalendarDayPanel dayPanel = new CalendarDayPanel(day, new HashMap<String, Meeting>());
			c.gridx += 1;
			//calendarContainer.add(dayPanel, c);
		}
		repaint();
	}
	
	public void setEmployees(List<Employee> emps) {
		this.emps = emps;
		// TODO oppdatere kalender
	}
	
	// Lagar panelet for ein dag, dvs. ei kolonne i kalenderen
	private class CalendarDayPanel extends JPanel {
		
		private Date dayDate;
		private Map<Employee, List<Meeting>> meetingsThisDay;
		private GridBagConstraints c = new GridBagConstraints();
		
		public CalendarDayPanel(Date dayDate, Map<Employee, List<Meeting>> meetingsThisDay) {
			this.dayDate = dayDate;
			this.meetingsThisDay = meetingsThisDay;
			
			setLayout(new GridBagLayout());
			setPreferredSize(new Dimension(80, 430));
			setMinimumSize(new Dimension(80, 430));
			
			System.out.println("Creating day: " + dayDate.toString());
			
			initTopLabel();
			initMeetings();
		}
		
		public void setMeetings(Map<String, Meeting> meetingsThisDay) {
			//this.meetingsThisDay = meetingsThisDay;
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
			
			// TODO: Finne forskjellige fargar
			
			for (Employee emp : meetingsThisDay.keySet()) {
				// Noke koder for farge her
				List<Meeting> empMeets = meetingsThisDay.get(emp);
				for (Meeting empMeeting : empMeets) {
					mCont.addMeeting(empMeeting);
				}
				
			}
		}
	}
	
	// Inneheld alle møte, og teiknar horisontale strekar bort til tidspunkta
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
			
			int startHour = meeting.getMeetingTime().getHours();
			int startMinute = meeting.getMeetingTime().getMinutes();
			int duration = meeting.getDuration();
			
			// Sjekkar at møtet er mellom 8 og 20:
			if (startHour<8 || startHour>20) return;
			
			// Kjører sjekk om det blir overlapp
			numColumns = calculateMaxOverlap();
			colWidth = DAY_COLUMN_MAX_WIDTH/numColumns;
			// Fjernar alle avtalePanel frå hovudpanelet
			removeAll();
			addedMeetings.clear();
			
			for (Meeting meet: meetings) {
				float startH = hourAndMinutesToFloat(meet.getMeetingTime().getHours(), meet.getMeetingTime().getMinutes());
				float endH = startH + (float) (meet.getDuration()/60);
				if (meet.getDuration() % 60 > 0) endH += 0.5;
				
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
					max = Math.max(max, meetingsAtTime);
				}
			}
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
	
	private class PrevWeekAction extends AbstractAction {
		
		public PrevWeekAction(String text) {
			putValue(Action.NAME, text);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			changeWeekBack();
		}
		
	}
	
	private class NextWeekAction extends AbstractAction {
		
		public NextWeekAction(String text) {
			putValue(Action.NAME, text);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			changeWeekForward();
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
	
	private List<Meeting> getMeetingsOnDate(List<Meeting> inMeets, Date date) {
		Date startOfDay = new Date(date.getTime());
		startOfDay.setHours(8);
		startOfDay.setMinutes(0);
		startOfDay.setSeconds(0);
		
		Date endOfDay = new Date(date.getTime());
		endOfDay.setHours(21);
		endOfDay.setMinutes(0);
		endOfDay.setSeconds(0);
		
		List<Meeting> outMeets = new ArrayList<>();
		
		for (Meeting meet : inMeets) {
			Date meetTime = meet.getMeetingTime();
			int meetDuration = meet.getDuration();
			Date meetEndTime = new Date(meetTime.getTime() + meetDuration*60*1000);
			
			if (meetTime.after(startOfDay) && meetTime.before(endOfDay) && meetEndTime.before(endOfDay)) {
				outMeets.add(meet);
			}
		}
		
		return outMeets;
	}
	
}

/*
TESTKODE

if (dayDate.getDay() == new Date().getDay()) {
Meeting meeting1 = new Meeting("whatever id");
Date testTime = new Date();
testTime.setHours(12);
testTime.setMinutes(0);
meeting1.setDuration(60);
meeting1.setMeetingTime(testTime);
meeting1.setDescription("Møte");
mCont.addMeeting(meeting1);

Meeting meeting2 = new Meeting("whatever id2");
Date testTime2 = new Date();
testTime2.setHours(12);
testTime2.setMinutes(30);
meeting2.setDuration(60);
meeting2.setMeetingTime(testTime2);
meeting2.setDescription("Møte2");
mCont.addMeeting(meeting2);

Meeting meeting3 = new Meeting("whatever id3");
Date testTime3 = new Date();
testTime3.setHours(11);
testTime3.setMinutes(30);
meeting3.setDuration(60);
meeting3.setMeetingTime(testTime3);
meeting3.setDescription("Møte3");
mCont.addMeeting(meeting3);

Meeting meeting4 = new Meeting("whatever id4");
Date testTime4 = new Date();
testTime4.setHours(10);
testTime4.setMinutes(0);
meeting4.setDuration(90);
meeting4.setMeetingTime(testTime4);
meeting4.setDescription("Møte4");
mCont.addMeeting(meeting4);

Meeting meeting5 = new Meeting("whatever id5");
Date testTime5 = new Date();
testTime5.setHours(13);
testTime5.setMinutes(0);
meeting5.setDuration(90);
meeting5.setMeetingTime(testTime5);
meeting5.setDescription("Møte5");
mCont.addMeeting(meeting5);

Meeting meeting6 = new Meeting("whatever id6");
Date testTime6 = new Date();
testTime6.setHours(9);
testTime6.setMinutes(0);
meeting6.setDuration(60);
meeting6.setMeetingTime(testTime6);
meeting6.setDescription("Møte6");
mCont.addMeeting(meeting6);
}
*/