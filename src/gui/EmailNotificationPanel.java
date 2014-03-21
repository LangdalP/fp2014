package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class EmailNotificationPanel extends JDialog {
	
	private static final String GMAIL_USERNAME = "avtale.notifikasjon@gmail.com";
	private static final String GMAIL_PASSWORD = "fellespro14";
	
	private Properties props;
	private Session session;
	
	private JTextField emailField;
	private JTextField subjField;
	private JTextArea emailTextArea;
	private JList emailList;
	private DefaultListModel<String> emailModel;
	
	public EmailNotificationPanel() {
		props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		
		session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(GMAIL_USERNAME, GMAIL_PASSWORD);
					}
				});
		
		initGui();
	}
	
	private void initGui() {
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		JLabel emailLabel = new JLabel("Legg til epost");
		c.gridx = 0; c.gridy = 0; c.gridwidth = 1; c.gridheight = 1; c.fill = GridBagConstraints.BOTH;
		add(emailLabel, c);
		
		emailField = new JTextField(20);
		emailField.setText("");
		emailField.setColumns(20);
		c.gridx = 0; c.gridy = 1;
		add(emailField, c);
		
		JButton addButton = new JButton("+");
		addButton.setActionCommand("ADD_EMAIL");
		addButton.addActionListener(new AddButtonListener());
		c.gridx = 1;
		add(addButton, c);
		
		emailModel = new DefaultListModel<>();
		
		emailList = new JList<>(emailModel);
		JScrollPane emailScroller = new JScrollPane(emailList);
		
		c.gridx = 3; c.gridy = 0; c.gridwidth = 3; c.gridheight = 3;
		add(emailScroller, c);
		
		AbstractAction sendToAllAction = new AbstractAction() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				for (int i = 0; i<emailModel.getSize(); i++) {
					String addr = emailModel.getElementAt(i);
					sendEmail(addr, subjField.getText(), emailTextArea.getText());
					dispose();
				}
			}
		};
		sendToAllAction.putValue(Action.NAME, "Send eposter");
		
		JButton sendEmailsButton = new JButton(sendToAllAction);
		c.gridx = 0; c.gridy = 4; c.gridwidth = 1; c.gridheight = 1;
		add(sendEmailsButton, c);
		
		JLabel subjLabel = new JLabel("Emne");
		c.gridx = 6; c.gridy = 0;
		add(subjLabel, c);
		
		subjField = new JTextField(10);
		c.gridx = 6; c.gridy = 1;
		add(subjField, c);
		
		JLabel emailTextLabel = new JLabel("Epost-tekst");
		c.gridx = 7; c.gridy = 0;
		add(emailTextLabel, c);
		
		emailTextArea = new JTextArea();
		emailTextArea.setColumns(8);
		emailTextArea.setRows(3);
		c.gridx = 7; c.gridy = 1; c.gridwidth = 1; c.gridheight = 1;
		emailTextArea.setPreferredSize(new Dimension(200, 100));
		add(emailTextArea, c);
		
		pack();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
	}
	
	private void sendEmail(String recip, String subj, String msg) {
		try {
			 
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(GMAIL_USERNAME));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(recip));
			message.setSubject(subj);
			message.setText(msg);
 
			Transport.send(message);
 
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	
	private class AddButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			emailModel.addElement(emailField.getText());
			emailField.setText("");
		}
		
		
	}

	public static void main(String[] args) {
		;
		JFrame testFrame = new JFrame();
		
		JButton startButton = new JButton(new AbstractAction() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				EmailNotificationPanel testPanel = new EmailNotificationPanel();
				testPanel.setVisible(true);
			}
		});
		
		testFrame.getContentPane().add(startButton);
		
		testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		testFrame.pack();
		testFrame.setVisible(true);
		
	}


}
