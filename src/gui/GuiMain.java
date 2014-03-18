package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import model.impl.ModelImpl;
import client.ClientMain;

public class GuiMain extends JFrame {
	
	private boolean loggedIn = false;
	private JPanel contentPanel = new JPanel();
	private JPanel upperPanel;
	private JPanel calendarPanel;
	private GridBagLayout layout = new GridBagLayout();
	
	private ModelImpl model;
	
	public GuiMain() {
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
	
	public void showMainPanel(ModelImpl model) {
		this.model = model;
		// Må sette upperPanel til "Hjem" og calendarPanel til kalender
		setContentPane(contentPanel);
		
		upperPanel = new JPanel();		// Skal vere "Hjem"
		calendarPanel = new JPanel();	// Skal vere Kalendervisning
		GridBagConstraints c = new GridBagConstraints();
		
		NewMeetingPanel newMeetingPanel = new NewMeetingPanel(this.model);
		
		c.gridx = 0; c.gridy = 0; c.gridwidth = 1; c.gridheight = 1;
		contentPanel.add(newMeetingPanel, c);
		c.gridx = 0; c.gridy = 1; c.gridwidth = 1; c.gridheight = 1;
		contentPanel.add(calendarPanel, c);
		
		pack();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationByPlatform(true);
		setTitle("CalendarPro 2.3");
		setVisible(true);
		
	}
	
	public void loginDataEntered(String username, String password) {
		System.out.println("Entered username: " + username + " and password: " + password);
		boolean success = ClientMain.validateLogin(username, password);
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
	
}
