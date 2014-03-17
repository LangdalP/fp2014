package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import client.ClientMain;

public class LoginPanel extends JPanel {
	
	private GridBagLayout layout = new GridBagLayout();
	private JTextField usernameField;
	private JPasswordField passwordField;
	private GuiMain parent;
	
	public LoginPanel() {
		this.parent = null;
		setLayout(layout);
		
		init();
	}
	
	public LoginPanel(GuiMain parent) {
		this.parent = parent;
		setLayout(layout);
		
		init();
	}
	
	private void init() {
		GridBagConstraints c = new GridBagConstraints();
		
		JLabel usernameLabel = new JLabel("Angi brukernavn: ");
		c.gridx = 0; c.gridy = 0; c.gridwidth = 1; c.gridheight = 1;
		add(usernameLabel, c);
		usernameField = new JTextField(20);
		usernameField.setText("peder.langdal@gmail.com");
		c.gridx = 0; c.gridy = 1; c.gridwidth = 1; c.gridheight = 1;
		add(usernameField, c);
		
		JLabel passwordLabel = new JLabel("Angi passord: ");
		c.gridx = 0; c.gridy = 2; c.gridwidth = 1; c.gridheight = 1;
		add(passwordLabel, c);
		passwordField = new JPasswordField(20);
		c.gridx = 0; c.gridy = 3; c.gridwidth = 1; c.gridheight = 1;
		add(passwordField, c);
		
		JButton loginButton = new JButton("Logg inn");
		Action loginAction = new LoginAction("Logg inn", parent);
		loginButton.setAction(loginAction);
		c.gridx = 0; c.gridy = 4; c.gridwidth = 1; c.gridheight = 1;
		add(loginButton, c);
		
		setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
		
	}
	
	private class LoginAction extends AbstractAction {
		
		private GuiMain gui;
		
		public LoginAction(String name, GuiMain parent) {
			super();
	        putValue(Action.NAME, name);
			this.gui = parent;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			String username = usernameField.getText();
			String password = new String(passwordField.getPassword());
			
			gui.loginDataEntered(username, password);
		}
		
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
