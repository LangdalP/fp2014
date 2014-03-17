package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GuiMain extends JFrame {
	
	public GuiMain() {
		setContentPane(new JPanel());
		
		pack();
	}
	
	public void loginDataEntered(String username, String password) {
		System.out.println("Login data entered: " + username + " : " + password);
	}
	
}
