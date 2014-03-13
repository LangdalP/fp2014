package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.omg.CORBA.PUBLIC_MEMBER;

public class LoginPanel extends JPanel {
	
	private GridBagLayout layout = new GridBagLayout();
	private JTextField usernameField;
	private JPasswordField passwordField;
	
	public LoginPanel() {
		setLayout(layout);
		
		init();
	}
	
	private void init() {
		GridBagConstraints c = new GridBagConstraints();
		
		JLabel usernameLabel = new JLabel("Angi brukernavn: ");
		c.gridx = 0; c.gridy = 0; c.gridwidth = 1; c.gridheight = 1;
		add(usernameLabel, c);
		usernameField = new JTextField(20);
		c.gridx = 0; c.gridy = 1; c.gridwidth = 1; c.gridheight = 1;
		add(usernameField, c);
		
		JLabel passwordLabel = new JLabel("Angi passord: ");
		c.gridx = 0; c.gridy = 2; c.gridwidth = 1; c.gridheight = 1;
		add(passwordLabel, c);
		passwordField = new JPasswordField(20);
		c.gridx = 0; c.gridy = 3; c.gridwidth = 1; c.gridheight = 1;
		add(passwordField, c);
		
		JButton loginButton = new JButton("Logg inn");
		c.gridx = 0; c.gridy = 4; c.gridwidth = 1; c.gridheight = 1;
		add(loginButton, c);
		
		setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
		
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Test av innlogging");
		LoginPanel panel = new LoginPanel();
		frame.setContentPane(panel);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationByPlatform(true);
		frame.pack();
		frame.setVisible(true);
	}

}
