package gui;

import gui.MeetingPanels.EditPanel;
import gui.MeetingPanels.InfoMeetingPanel;
import gui.MeetingPanels.MeetingModel;
import gui.MeetingPanels.NewMeetingPanel;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.Employee;
import model.Meeting;
import client.ClientMain;
import client.ClientModelImpl;

public class GuiMain extends JFrame implements PropertyChangeListener {
	
	private boolean loggedIn = false;
	private JPanel contentPanel = new JPanel();
	private HomePanel hPanel;
	private CalendarPanel calendarPanel;
	private GridBagLayout layout = new GridBagLayout();
	private GridBagConstraints topConstraint;
	private GridBagConstraints bottomConstraint;
	
	private ClientModelImpl model;
	
	public GuiMain() {
        Dimension size = new Dimension(700,700);
        contentPanel.setPreferredSize(size);
        contentPanel.setMinimumSize(size);
        contentPanel.setMinimumSize(size);
        contentPanel.setAutoscrolls(true);
		setContentPane(contentPanel);
		addWindowListener(new WindowClosedListener());
		
		pack();
	}
	
	public void showLogin() {
		LoginPanel loginPanel = new LoginPanel(this);
		setContentPane(loginPanel);
		pack();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationByPlatform(true);
		setTitle("Logg inn");
		setVisible(true);
		
		while (getLoggedIn() == false) {
			while (ClientMain.getLoggedin() == null) {
				// wait
				
			}
			if (ClientMain.getLoggedin() == true) {
				setLoggedIn(true);
			} else {
				setLoggedIn(false);
			}
		}
		setVisible(false);
	}
	
	public void showMainPanel(ClientModelImpl model) {
		this.model = model;
		// Mï¿½ sette upperPanel til "Hjem" og calendarPanel til kalender
		setContentPane(contentPanel);
		//contentPanel.setLayout(layout);
		
		hPanel = new HomePanel(model);		// Skal vere "Hjem"
        hPanel.addPropertyChangeListener(this);
		calendarPanel = new CalendarPanel(model);
		calendarPanel.addPropertyChangeListener(this);
		
		GridBagConstraints c = new GridBagConstraints();
		//c.gridx = 0; c.gridy = 0; c.gridwidth = 1; c.gridheight = 1;
		contentPanel.add(hPanel);
		//c.gridx = 0; c.gridy = 1; c.gridwidth = 1; c.gridheight = 1;
		contentPanel.add(calendarPanel);
		
		// Teste fleire ansatte
		List<Employee> empsShow = new ArrayList<>();
		empsShow.add(model.getMapEmployees().get("peder.langdal@gmail.com"));
		empsShow.add(model.getMapEmployees().get("hegelborge@gmail.com"));
		
		calendarPanel.setEmployeesToShow(empsShow);
		
		setLocation(0,0);
		pack();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setLocationByPlatform(true);
		setTitle("CalendarPro 2.3 Logged in as: " + model.getUsername());
		setVisible(true);
		
	}
	
	public void loginDataEntered(String username, String password) {
		System.out.println("Entered username: " + username + " and password: " + password);
		boolean success = ClientMain.validateLogin(username, password);
		if (!success) {
			JOptionPane.showMessageDialog(null, "Feil brukernavn eller passord");
		}
		setLoggedIn(success);
	}
	
	private synchronized boolean getLoggedIn() {
		return loggedIn;
	}
	
	private synchronized void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}
	
	
	private void showAlarm(Meeting meeting){
		Toolkit.getDefaultToolkit().beep();
//		if(meeting.getMeetngLocation() == null) {
//			JOptionPane.showMessageDialog(null, "Alarm for møte: " + meeting.getDescription() + ", møterom: " + meeting.getMeetingRoom().getName() + " kl: " + meeting.getMeetingTime().toString());		
//		}
//		else{
			JOptionPane.showMessageDialog(null, "Alarm for møte: " + meeting.getDescription() + ", sted: " + meeting.getMeetngLocation() + " kl: " + meeting.getMeetingTime().toString());
//		}
	}
	
	private class WindowClosedListener implements WindowListener {
		
		public WindowClosedListener() {
			//
		}

		@Override
		public void windowActivated(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowClosed(WindowEvent e) {
			if (ClientMain.getLoggedin() == true) {
				ClientMain.sendLogout();
			}
			ClientMain.closeConnection();
			ClientMain.shutdownClient();
		}

		@Override
		public void windowClosing(WindowEvent e) {
            System.out.println("window closing");
            if (ClientMain.getLoggedin() == true) {
                ClientMain.sendLogout();
            }
            ClientMain.closeConnection();
            ClientMain.shutdownClient();
        }

		@Override
		public void windowDeactivated(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowDeiconified(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowIconified(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowOpened(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}
	}

    public static String SHOW_HOME = "SHOW_HOME";
    public static String SHOW_MEETING = "SHOW_MEETING";
    public static String EDIT_MEETING = "EDIT_MEETING";
    public static String NEW_MEETING = "NEW_MEETING";
    public static String SET_EMPLOYEES_TO_SHOW = "SET_EMPLOYEES_TO_SHOW";

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		System.out.println(evt.getPropertyName());
		if (evt.getPropertyName().equals(EDIT_MEETING)) {
			Meeting meet = (Meeting) evt.getNewValue();
			showAlarm(meet);
			EditPanel editPanel = new EditPanel(model, new MeetingModel(meet));
            editPanel.addPropertyChangeListener(this);
			contentPanel.add(editPanel, 0);
			contentPanel.remove(1);
		} else if (evt.getPropertyName().equals(SHOW_MEETING)) {
			Meeting meet = (Meeting) evt.getNewValue();
			InfoMeetingPanel infoPanel = new InfoMeetingPanel(model, new MeetingModel(meet));
            infoPanel.addPropertyChangeListener(this);
			contentPanel.add(infoPanel, 0);
			contentPanel.remove(1);
		} else if (evt.getPropertyName().equals(SHOW_HOME)){
            HomePanel hp = new HomePanel(model);
            hp.addPropertyChangeListener(this);
            contentPanel.add(hp, 0);
            contentPanel.remove(1);
            System.out.println("SHOW HOME");
        }
        else if (evt.getPropertyName().equals(NEW_MEETING)){
        	Meeting meet = (Meeting) evt.getNewValue();
            NewMeetingPanel panel = new NewMeetingPanel(model, new MeetingModel(meet));
            panel.addPropertyChangeListener(this);
            contentPanel.add(panel, 0);
            contentPanel.remove(1);
        } else if (evt.getPropertyName().equals(SET_EMPLOYEES_TO_SHOW)) {
        	List<Employee> empsToShow = (List<Employee>) evt.getNewValue();
        	calendarPanel.setEmployeesToShow(empsToShow);
        }

		contentPanel.revalidate();
		contentPanel.repaint();
	}
	
}
