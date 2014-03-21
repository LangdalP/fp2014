package gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import model.Employee;
import model.Meeting;
import client.ClientModelImpl;

public class CalendarPanel extends JPanel implements PropertyChangeListener, MouseListener {
	
	private static final Dimension TOP_LABEL_DIMENSION = new Dimension(80, 40);
	private static final Dimension TIME_LABEL_DIMENSION = new Dimension(80, 30);
	private static final int DAY_COLUMN_MAX_WIDTH = 80;
	private static final int VERT_PX_PER_HOUR = 30;
	
	private ClientModelImpl model;
	private List<Employee> empsToShow = new ArrayList<>();
	
	private JPanel calendarContainer = new JPanel();
	private Date dayInWeek = new Date();
	private CalendarTimePanel timePanel = new CalendarTimePanel();
	private GridBagConstraints c2;
	private HashMap<Employee, List<Meeting>> meetingsByEmployee = new HashMap<>();
	private GridBagLayout containerLayout = new GridBagLayout();
	private HashMap<Integer, GridBagConstraints> constraints = new HashMap<>();
	private CalendarPanel thisRef;
	private PropertyChangeSupport pcs;
	
	public CalendarPanel(ClientModelImpl model) {
		setLayout(new GridBagLayout());
		this.model = model;
		this.thisRef = this;
		this.pcs = new PropertyChangeSupport(this);
		model.addPropertyChangeListener(this);
		
		init();
	}
	
	private void init() {
		GridBagConstraints c = new GridBagConstraints();
		c2 = new GridBagConstraints();
		
		Employee myEmployee = model.getMapEmployees().get(model.getUsername());
		empsToShow.add(myEmployee);
		System.out.println(myEmployee);
		meetingsByEmployee.put(myEmployee, model.getMeetingsByEmployee(myEmployee));
		
		calendarContainer.setLayout(containerLayout);
		c.gridx = 0; c.gridy = 0; c.gridheight = 1; c.gridwidth = 1;
		calendarContainer.add(timePanel, c);
		
		Date[] allDaysOfWeek = getAllDaysOfWeek(dayInWeek);
		int counter = 1;
		for (Date day : allDaysOfWeek) {
			Map<Employee, List<Meeting>> mapEmpMeets = new HashMap<>();
			for (Employee emp : meetingsByEmployee.keySet()) {
				List<Meeting> empMeetingsThisDay = getMeetingsOnDate(model.getMeetingsByEmployee(emp), day);
				mapEmpMeets.put(emp, empMeetingsThisDay);
			}

			CalendarDayPanel dayPanel = new CalendarDayPanel(day, mapEmpMeets);
			c.gridx += 1; c.gridy = 0; c.gridheight = 1; c.gridwidth = 1;
			calendarContainer.add(dayPanel, c);
			constraints.put(counter, containerLayout.getConstraints(dayPanel));
			counter++;
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
		
		refreshDays();
		
	}
	
	// TODO Fikse bug
	public void changeWeekForward() {
		System.out.println("Inside changeWeekForward");
		long currentMs = dayInWeek.getTime();
		long newTimeMs = currentMs + (7*24*60*60*1000);
		dayInWeek = new Date(newTimeMs);
		
		refreshDays();
	}
	
	public void setEmployeesToShow(List<Employee> emps) {
		this.empsToShow = emps;
		meetingsByEmployee.clear();
		for (Employee emp : empsToShow) {
			List<Meeting> meetingsForEmp = model.getMeetingsByEmployee(emp);
			meetingsByEmployee.put(emp, meetingsForEmp);
		}
		
		refreshDays();
	}
	
	private void refreshDays() {

		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0; c.gridy = 0; c.gridheight = 1; c.gridwidth = 1;
		Date[] allDaysOfWeek = getAllDaysOfWeek(dayInWeek);
		int counter = 1;
		for (Date day : allDaysOfWeek) {
			Map<Employee, List<Meeting>> mapEmpMeets = new HashMap<>();
			for (Employee emp : meetingsByEmployee.keySet()) {
				List<Meeting> empMeetingsThisDay = getMeetingsOnDate(model.getMeetingsByEmployee(emp), day);
                System.out.println(day + "\n " + empMeetingsThisDay);
                mapEmpMeets.put(emp, empMeetingsThisDay);
			}
			
			System.out.println(counter);
			CalendarDayPanel dayPanel = new CalendarDayPanel(day, mapEmpMeets);
			calendarContainer.add(dayPanel, constraints.get(counter), counter);
			System.out.println(constraints.get(counter).gridx);
			counter++;
		}
		
		calendarContainer.revalidate();
		calendarContainer.repaint();
		repaint();
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
			
			int startHour = meeting.getMeetingTime().getHours();
			int startMinute = meeting.getMeetingTime().getMinutes();
			int duration = meeting.getDuration();
			
			// Sjekkar at m�tet er mellom 8 og 20:
			if (startHour<8 || startHour>20) return;
			
			// Kj�rer sjekk om det blir overlapp
			numColumns = calculateMaxOverlap();
			colWidth = DAY_COLUMN_MAX_WIDTH/numColumns;
			// Fjernar alle avtalePanel fr� hovudpanelet
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
				meetPan.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				meetPan.addMouseListener(thisRef);
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

	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		// Modellen har endra seg
        System.out.println("SYNC RECEIVED");
        refreshDays();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		CalendarMeetingPanel meetPan = (CalendarMeetingPanel) e.getComponent();
		Meeting clickedMeet = meetPan.getMeeting();
		boolean owner = false;
		if (clickedMeet.getMeetingOwner().getUsername().equals(model.getUsername())) owner = true;
		
		String eventName = owner ? "EDIT_MEETING" : "SHOW_MEETING"; 
		System.out.println("Clicked on " + clickedMeet.getMeetingID());
		pcs.firePropertyChange(eventName, null, clickedMeet);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}
	
}
