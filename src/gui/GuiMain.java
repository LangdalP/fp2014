package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

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
		
		pack();
	}
	
	public void showLogin() {
		LoginPanel loginPanel = new LoginPanel(this);
		setContentPane(loginPanel);
		pack();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationByPlatform(true);
		setVisible(true);
		
		while (getLoggedIn() != true) {
			// wait
		}
		setVisible(false);
	}
	
	public void showMainPanel(ModelImpl model) {
		this.model = model;
		// Må sette upperPanel til "Hjem" og calendarPanel til kalender
		setContentPane(contentPanel);
		upperPanel = new JPanel();
		calendarPanel = new JPanel();
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 0; c.gridy = 0; c.gridwidth = 1; c.gridheight = 1;
		contentPanel.add(upperPanel, c);
		c.gridx = 0; c.gridy = 1; c.gridwidth = 1; c.gridheight = 1;
		contentPanel.add(calendarPanel, c);
		
		pack();
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

	
}
