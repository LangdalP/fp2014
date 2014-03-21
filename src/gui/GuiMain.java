package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.Meeting;
import client.ClientMain;
import client.ClientModelImpl;

public class GuiMain extends JFrame implements PropertyChangeListener {
	
	private boolean loggedIn = false;
	private JPanel contentPanel = new JPanel();
	private HomePanel hPanel;
	private JPanel calendarPanel;
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
		// M� sette upperPanel til "Hjem" og calendarPanel til kalender
		setContentPane(contentPanel);
		//contentPanel.setLayout(layout);
		
		hPanel = new HomePanel(model);		// Skal vere "Hjem"
		calendarPanel = new CalendarPanel(model);
		calendarPanel.addPropertyChangeListener(this);
		
		GridBagConstraints c = new GridBagConstraints();
		
//        Meeting meeting = model.getMapFutureMeetings().get("mote3");
//		NewMeetingPanel panel = new NewMeetingPanel(this.model, meeting);

		//c.gridx = 0; c.gridy = 0; c.gridwidth = 1; c.gridheight = 1;
		contentPanel.add(hPanel);
		//c.gridx = 0; c.gridy = 1; c.gridwidth = 1; c.gridheight = 1;
		contentPanel.add(calendarPanel);
		
		setLocation(0,0);
		pack();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setLocationByPlatform(true);
		setTitle("CalendarPro 2.3");
		setVisible(true);
		
	}
	
	public void loginDataEntered(String username, String password) {
		System.out.println("Entered username: " + username + " and password: " + password);
		boolean success = ClientMain.validateLogin(username, password);
		if (!success) {
			JOptionPane.showMessageDialog(null, "Innloggingen var feil");
		}
		setLoggedIn(success);
	}
	
	private synchronized boolean getLoggedIn() {
		return loggedIn;
	}
	
	private synchronized void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
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
			// TODO Auto-generated method stub
			
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
	
	public void drawHomePanel() {
		contentPanel.add(hPanel, 0);
		contentPanel.remove(1);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		Meeting meet = (Meeting) evt.getNewValue();
		System.out.println(evt.getPropertyName());
		if (evt.getPropertyName().equals("EDIT_MEETING")) {
			NewMeetingPanel editPanel = new NewMeetingPanel(model, meet);
			contentPanel.add(editPanel, 0);
			contentPanel.remove(1);
		} else if (evt.getPropertyName().equals("SHOW_MEETING")) {
			InfoMeetingPanel infoPanel = new InfoMeetingPanel(model, meet);
			contentPanel.add(infoPanel, 0);
			contentPanel.remove(1);
		}
		contentPanel.revalidate();
		contentPanel.repaint();
	}
	
}
