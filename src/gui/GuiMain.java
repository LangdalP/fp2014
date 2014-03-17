package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;

import client.ClientMain;

public class GuiMain extends JFrame {
	
	public GuiMain() {
		setContentPane(new JPanel());
		
		pack();
	}
	
	public void showLogin() {
		LoginPanel loginPanel = new LoginPanel(this);
		setContentPane(loginPanel);
		pack();
	}
	
	public void loginDataEntered(String username, String password) {
		System.out.println("Entered username: " + username + " and password: " + password);
		boolean success = ClientMain.validateLogin(username, password);
	}
	
}
