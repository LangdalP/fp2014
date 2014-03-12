package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class NewMeetingPanel extends JPanel {
	
	private GridBagLayout layout = new GridBagLayout();
	private LeftPanel leftPanel;
	private RightPanel rightPanel;
	
	public NewMeetingPanel() {
		setLayout(layout);
		
		leftPanel = new LeftPanel();
		rightPanel = new RightPanel();
		
		init();
	}
	
	private void init() {
		// Testkode
		Integer[] testNums = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31};
		DefaultComboBoxModel<Integer> datoComboBoxModel = new DefaultComboBoxModel<>(testNums);
		DefaultComboBoxModel<Integer> startTimeComboBoxModel = new DefaultComboBoxModel<>(testNums);
		DefaultComboBoxModel<Integer> durationComboBoxModel = new DefaultComboBoxModel<>(testNums);
		DefaultComboBoxModel<Integer> alarmHoursComboBoxModel = new DefaultComboBoxModel<>(testNums);
		DefaultComboBoxModel<Integer> alarmMinutesComboBoxModel = new DefaultComboBoxModel<>(testNums);

		DefaultListModel<String> nameListModel = new DefaultListModel<>();
		nameListModel.addElement("Ivar Åsen");
		nameListModel.addElement("Knud Knudsen");
		nameListModel.addElement("Fred Perry");
		
		String[] testRooms = {"411", "414", "421", "424"};
		DefaultComboBoxModel<String> roomsComboBoxModel = new DefaultComboBoxModel<>(testRooms);
		
		
		// Testkode slutt
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 0; c.gridy = 0; c.gridwidth = 1; c.gridheight = 1;
		add(leftPanel, c);
		c.gridx = 1; c.gridy = 0; c.gridwidth = 1; c.gridheight = 1;
		add(rightPanel, c);
		
	}
	
	private class LeftPanel extends JPanel {
		private GridBagLayout layout = new GridBagLayout();
		
		public LeftPanel() {
			setLayout(layout);
			
			initLeftPanel();
		}

		private void initLeftPanel() {
			// Testkode
			Integer[] testNums = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31};
			DefaultComboBoxModel<Integer> datoComboBoxModel = new DefaultComboBoxModel<>(testNums);
			DefaultComboBoxModel<Integer> startTimeComboBoxModel = new DefaultComboBoxModel<>(testNums);
			DefaultComboBoxModel<Integer> durationComboBoxModel = new DefaultComboBoxModel<>(testNums);
			DefaultComboBoxModel<Integer> alarmHoursComboBoxModel = new DefaultComboBoxModel<>(testNums);
			DefaultComboBoxModel<Integer> alarmMinutesComboBoxModel = new DefaultComboBoxModel<>(testNums);
			
			// Testkode slutt
			GridBagConstraints c = new GridBagConstraints();
			
			// Beskrivelse
			JLabel descLabel = new JLabel("Beskrivelse: ");
			c.gridx = 0; c.gridy = 0; c.gridheight = 1; c.gridwidth = 1;
			add(descLabel, c);
			JTextArea descText = new JTextArea();
			c.gridx = 1; c.gridy = 0; c.gridheight = 1; c.gridwidth = 4;
			add(descText, c);
			
			// Dato
			JLabel dateLabel = new JLabel("Dato: ");
			c.gridx = 0; c.gridy = 1; c.gridheight = 1; c.gridwidth = 1;
			add(dateLabel, c);
			JComboBox<Integer> dateDropdown = new JComboBox<>(datoComboBoxModel);
			c.gridx = 1; c.gridy = 1; c.gridheight = 1; c.gridwidth = 4;
			add(dateDropdown, c);
			
			// Starttid
			JLabel startLabel = new JLabel("Starttid: ");
			c.gridx = 0; c.gridy = 2; c.gridheight = 1; c.gridwidth = 1;
			add(startLabel, c);
			JComboBox<Integer> startTimeDropdown = new JComboBox<>(startTimeComboBoxModel);
			c.gridx = 1; c.gridy = 2; c.gridheight = 1; c.gridwidth = 4;
			add(startTimeDropdown, c);
			
			// Varigheit
			JLabel durationLabel = new JLabel("Varighet: ");
			c.gridx = 0; c.gridy = 3; c.gridheight = 1; c.gridwidth = 1;
			add(durationLabel, c);
			JComboBox<Integer> durationDropdown = new JComboBox<>(durationComboBoxModel);
			c.gridx = 1; c.gridy = 3; c.gridheight = 1; c.gridwidth = 4;
			add(durationDropdown, c);
			
			// Deltakelse
			JLabel participateLabel = new JLabel("Delta på møte: ");
			c.gridx = 0; c.gridy = 4; c.gridheight = 1; c.gridwidth = 1;
			add(participateLabel, c);
			JRadioButton participateYesButton = new JRadioButton("Ja");
			c.gridx = 1; c.gridy = 4; c.gridheight = 1; c.gridwidth = 2;
			add(participateYesButton, c);
			JRadioButton participateNoButton = new JRadioButton("Nei");
			c.gridx = 3; c.gridy = 4; c.gridheight = 1; c.gridwidth = 2;
			add(participateNoButton, c);
			
			ButtonGroup participateGroup = new ButtonGroup();
			participateGroup.add(participateYesButton);
			participateGroup.add(participateNoButton);
			
			// Alarm
			JLabel alarmLabel = new JLabel("Alarm før møte: ");
			c.gridx = 0; c.gridy = 5; c.gridheight = 1; c.gridwidth = 1;
			add(alarmLabel, c);
			JRadioButton alarmYesButton = new JRadioButton("På");
			c.gridx = 1; c.gridy = 5; c.gridheight = 1; c.gridwidth = 1;
			add(alarmYesButton, c);
			JRadioButton alarmNoButton = new JRadioButton("Av");
			c.gridx = 2; c.gridy = 5; c.gridheight = 1; c.gridwidth = 1;
			add(alarmNoButton, c);
			
			ButtonGroup alarmGroup = new ButtonGroup();
			alarmGroup.add(alarmYesButton);
			alarmGroup.add(alarmNoButton);
			
			JComboBox<Integer> alarmHoursDropdown = new JComboBox<>(alarmHoursComboBoxModel);
			c.gridx = 3; c.gridy = 5; c.gridheight = 1; c.gridwidth = 1;
			add(alarmHoursDropdown, c);
			JComboBox<Integer> alarmMinutesDropdown = new JComboBox<>(alarmMinutesComboBoxModel);
			c.gridx = 4; c.gridy = 5; c.gridheight = 1; c.gridwidth = 1;
			add(alarmMinutesDropdown, c);
		}
		
	}
	
	private class RightPanel extends JPanel {
		private GridBagLayout layout = new GridBagLayout();
		
		public RightPanel() {
			setLayout(layout);
			
			initRightPanel();
		}

		private void initRightPanel() {
			// Testkode
			DefaultListModel<String> nameListModel = new DefaultListModel<>();
			nameListModel.addElement("Ivar Åsen");
			nameListModel.addElement("Knud Knudsen");
			nameListModel.addElement("Fred Perry");
			
			String[] testRooms = {"411", "414", "421", "424"};
			DefaultComboBoxModel<String> roomsComboBoxModel = new DefaultComboBoxModel<>(testRooms);
			
			
			// Testkode slutt
			GridBagConstraints c = new GridBagConstraints();
			
			// Legg til deltakere
			JLabel addEmpLabel = new JLabel("Legg til deltakere");
			c.gridx = 0; c.gridy = 0; c.gridheight = 1; c.gridwidth = 3;
			add(addEmpLabel, c);
			JList<String> addEmpList = new JList<>(nameListModel);
			addEmpList.setFixedCellWidth(200);
			JScrollPane addEmpListScroller = new JScrollPane(addEmpList);
			c.gridx = 0; c.gridy = 1; c.gridheight = 2; c.gridwidth = 3;
			add(addEmpListScroller, c);
			
			JLabel extraLabel = new JLabel("Ekstra deltakere: ");
			c.gridx = 0; c.gridy = 3; c.gridheight = 1; c.gridwidth = 1;
			add(extraLabel, c);
			JTextField extraField = new JTextField(4);
			c.gridx = 1; c.gridy = 3; c.gridheight = 1; c.gridwidth = 2;
			add(extraField, c);
			
			// Velg rom/sted
			JLabel chooseRoomLabel = new JLabel("Velg et tilgjengelig rom: ");
			c.gridx = 0; c.gridy = 4; c.gridheight = 1; c.gridwidth = 3;
			add(chooseRoomLabel, c);
			JRadioButton roomRadioButton = new JRadioButton();
			c.gridx = 0; c.gridy = 5; c.gridheight = 1; c.gridwidth = 1;
			add(roomRadioButton, c);
			JComboBox<String> roomsDropdown = new JComboBox<>(roomsComboBoxModel);
			c.gridx = 1; c.gridy = 5; c.gridheight = 1; c.gridwidth = 2;
			add(roomsDropdown, c);
			
			JLabel roomOrLocationLabel = new JLabel("eller velg annet sted: ");
			c.gridx = 0; c.gridy = 6; c.gridheight = 1; c.gridwidth = 3;
			add(roomOrLocationLabel, c);
			JRadioButton locationRadioButton = new JRadioButton();
			c.gridx = 0; c.gridy = 7; c.gridheight = 1; c.gridwidth = 1;
			add(locationRadioButton, c);
			JTextField locationTextField = new JTextField(15);
			c.gridx = 1; c.gridy = 7; c.gridheight = 1; c.gridwidth = 2;
			add(locationTextField, c);
			
			ButtonGroup roomLocationGroup = new ButtonGroup();
			roomLocationGroup.add(roomRadioButton);
			roomLocationGroup.add(locationRadioButton);
			
			// Knappar
			JButton cancelButton = new JButton("Avbryt");
			c.gridx = 0; c.gridy = 8; c.gridheight = 1; c.gridwidth = 1;
			add(cancelButton, c);

			JButton saveButton = new JButton("Lagre");
			c.gridx = 1; c.gridy = 8; c.gridheight = 1; c.gridwidth = 2;
			add(saveButton, c);
		}

	}
	public static void main(String[] args) {
		
		JFrame frame = new JFrame("Test av opprett avtale");
		NewMeetingPanel panel = new NewMeetingPanel();
		frame.setContentPane(panel);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationByPlatform(true);
		frame.pack();
		frame.setVisible(true);
		
	}
}
