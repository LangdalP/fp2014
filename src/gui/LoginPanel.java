package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

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
		usernameField = new JPasswordField(10);
		
		JLabel passwordLabel = new JLabel("Angi passord: ");
		passwordField = new JPasswordField(10);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
