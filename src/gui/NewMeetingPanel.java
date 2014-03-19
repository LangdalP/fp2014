package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
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
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;

import client.ClientModelImpl;
import model.Meeting;
import model.MeetingRoom;

import org.jdesktop.swingx.JXDatePicker;

import protocol.MessageType;
import protocol.TransferObject;
import protocol.TransferType;
import client.ClientMain;

public class NewMeetingPanel extends JPanel implements PropertyChangeListener {
	
	private GridBagLayout layout = new GridBagLayout();
	
	// Verdi-felt på venstresida
	private JTextArea descText;
	private JXDatePicker datePicker;
	private JComboBox<GuiTimeOfDay> startTimeDropdown;
	private JComboBox<GuiTimeOfDay> durationDropdown;
	private JRadioButton participateYesButton;
	private JRadioButton participateNoButton;
	private JRadioButton alarmYesButton;
	private JRadioButton alarmNoButton;
	private JComboBox<GuiTimeOfDay> alarmTimeDropdown;
	
	// Verdi-felt på høgresida
	private JList<String> addEmpList;
	private JTextField extraField;
	private JRadioButton roomRadioButton;
	private JRadioButton locationRadioButton;
	private JComboBox<String> roomsDropdown;
	private JTextField locationTextField;
	
	private ClientModelImpl model;
    private DefaultComboBoxModel<String> roomsComboBoxModel;
    private String[] rooms;

    public NewMeetingPanel(ClientModelImpl model) {
		this.model = model;
		this.model.addPropertyChangeListener(this);
		setLayout(layout);
		init();
	}

    private void init() {

        JPanel lp = new JPanel();
        lp.setLayout(new GridBagLayout());

        // Testkode
        GuiTimeOfDay[] startTimeArray = GuiTimeOfDay.getTimesOfDayArray();
        GuiTimeOfDay[] durationTimeArray = GuiTimeOfDay.getDurationTimesArray();
        GuiTimeOfDay[] alarmTimeArray = GuiTimeOfDay.getAlarmTimesArray();

        DefaultComboBoxModel<GuiTimeOfDay> startTimeComboBoxModel = new DefaultComboBoxModel<>(startTimeArray);
        DefaultComboBoxModel<GuiTimeOfDay> durationComboBoxModel = new DefaultComboBoxModel<>(durationTimeArray);
        DefaultComboBoxModel<GuiTimeOfDay> alarmTimeComboBoxModel = new DefaultComboBoxModel<>(alarmTimeArray);

        // Testkode slutt
        GridBagConstraints c = new GridBagConstraints();
        Insets emptyInsets = new Insets(0, 0, 0, 0);

        // Beskrivelse
        JLabel descLabel = new JLabel("Beskrivelse: ");
        c.gridx = 0;
        c.gridy = 0;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.insets = new Insets(10, 10, 0, 0);
        lp.add(descLabel, c);
        descText = new JTextArea(3, 14);
        descText.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        c.gridx = 1;
        c.gridy = 0;
        c.gridheight = 1;
        c.gridwidth = 4;
        lp.add(descText, c);

        // Dato
        JLabel dateLabel = new JLabel("Dato: ");
        c.gridx = 0;
        c.gridy = 1;
        c.gridheight = 1;
        c.gridwidth = 1;
        lp.add(dateLabel, c);
        datePicker = new JXDatePicker(new Date());
        datePicker.setDate(new Date());
        c.gridx = 1;
        c.gridy = 1;
        c.gridheight = 1;
        c.gridwidth = 4;
        lp.add(datePicker, c);

        // Starttid
        JLabel startLabel = new JLabel("Starttid: ");
        c.gridx = 0;
        c.gridy = 2;
        c.gridheight = 1;
        c.gridwidth = 1;
        lp.add(startLabel, c);
        startTimeDropdown = new JComboBox<>(startTimeComboBoxModel);
        c.gridx = 1;
        c.gridy = 2;
        c.gridheight = 1;
        c.gridwidth = 4;
        startTimeDropdown.addActionListener(updateAvailableRooms);
        lp.add(startTimeDropdown, c);

        // Varigheit
        JLabel durationLabel = new JLabel("Varighet: ");
        c.gridx = 0;
        c.gridy = 3;
        c.gridheight = 1;
        c.gridwidth = 1;
        lp.add(durationLabel, c);
        durationDropdown = new JComboBox<>(durationComboBoxModel);
        c.gridx = 1;
        c.gridy = 3;
        c.gridheight = 1;
        c.gridwidth = 4;
        lp.add(durationDropdown, c);

        // Deltakelse
        JLabel participateLabel = new JLabel("Delta på møte: ");
        c.gridx = 0;
        c.gridy = 4;
        c.gridheight = 1;
        c.gridwidth = 1;
        lp.add(participateLabel, c);
        participateYesButton = new JRadioButton("Ja");
        c.gridx = 1;
        c.gridy = 4;
        c.gridheight = 1;
        c.gridwidth = 2;
        lp.add(participateYesButton, c);
        participateNoButton = new JRadioButton("Nei");
        c.gridx = 3;
        c.gridy = 4;
        c.gridheight = 1;
        c.gridwidth = 2;
        lp.add(participateNoButton, c);

        ButtonGroup participateGroup = new ButtonGroup();
        participateGroup.add(participateYesButton);
        participateGroup.add(participateNoButton);

        // Alarm
        JLabel alarmLabel = new JLabel("Alarm før møte: ");
        c.gridx = 0;
        c.gridy = 5;
        c.gridheight = 1;
        c.gridwidth = 1;
        lp.add(alarmLabel, c);
        alarmYesButton = new JRadioButton("På");
        c.gridx = 1;
        c.gridy = 5;
        c.gridheight = 1;
        c.gridwidth = 1;
        lp.add(alarmYesButton, c);
        alarmNoButton = new JRadioButton("Av");
        c.gridx = 2;
        c.gridy = 5;
        c.gridheight = 1;
        c.gridwidth = 1;
        lp.add(alarmNoButton, c);

        ButtonGroup alarmGroup = new ButtonGroup();
        alarmGroup.add(alarmYesButton);
        alarmGroup.add(alarmNoButton);

        alarmTimeDropdown = new JComboBox<>(alarmTimeComboBoxModel);
        c.gridx = 3;
        c.gridy = 5;
        c.gridheight = 1;
        c.gridwidth = 1;
        lp.add(alarmTimeDropdown, c);

        GridBagConstraints cl = new GridBagConstraints();
        cl.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        add(lp, cl);
        // SLUTT VENSTRESIDE

        // START HØGRESIDE

        DefaultListModel<String> nameListModel = new DefaultListModel<>();

        for (String key : model.getMapEmployees().keySet()) {
            if (key.equals(model.getUsername())) continue;
            nameListModel.addElement(model.getMapEmployees().get(key).getName());
        }




        // Testkode slutt
        JPanel rp = new JPanel();
        rp.setLayout(new GridBagLayout());

        c = new GridBagConstraints();

        // Legg til deltakere
        JLabel addEmpLabel = new JLabel("Legg til deltakere");
        c.gridx = 0;
        c.gridy = 0;
        c.gridheight = 1;
        c.gridwidth = 3;
        rp.add(addEmpLabel, c);
        addEmpList = new JList<>(nameListModel);
        addEmpList.setFixedCellWidth(200);
        JScrollPane addEmpListScroller = new JScrollPane(addEmpList);
        c.gridx = 0;
        c.gridy = 1;
        c.gridheight = 2;
        c.gridwidth = 3;
        rp.add(addEmpListScroller, c);

        JLabel extraLabel = new JLabel("Ekstra deltakere: ");
        c.gridx = 0;
        c.gridy = 3;
        c.gridheight = 1;
        c.gridwidth = 1;
        rp.add(extraLabel, c);
        extraField = new JTextField(2);
        extraField.setText("0");
        PlainDocument doc = (PlainDocument) extraField.getDocument();
        doc.setDocumentFilter(new MyIntFilter());
        extraField.setColumns(2);
        extraField.addActionListener(updateAvailableRooms);
        extraField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        c.gridx = 1;
        c.gridy = 3;
        c.gridheight = 1;
        c.gridwidth = 2;
        rp.add(extraField, c);

        // Velg rom/sted
        LocationRadioButtonListener roomOrLocationListener = new LocationRadioButtonListener();

//
        roomRadioButton = new JRadioButton();
        roomRadioButton.addActionListener(roomOrLocationListener);
        c.gridx = 0;
        c.gridy = 5;
        c.gridheight = 1;
        c.gridwidth = 1;
        rp.add(roomRadioButton, c);
        roomsComboBoxModel = new DefaultComboBoxModel<>(getRooms(true));
        roomsDropdown = new JComboBox<>(roomsComboBoxModel);
        c.gridx = 1;
        c.gridy = 5;
        c.gridheight = 1;
        c.gridwidth = 2;
        Dimension dim = new Dimension(175, 20);
        roomsDropdown.setPreferredSize(dim);
        roomsDropdown.setMaximumSize(dim);
        roomsDropdown.setMinimumSize(dim);
        roomsDropdown.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                roomRadioButton.setSelected(true);
                locationRadioButton.setSelected(false);
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });
        rp.add(roomsDropdown, c);


        locationRadioButton = new JRadioButton();
        locationRadioButton.addActionListener(roomOrLocationListener);
        c.gridx = 0;
        c.gridy = 7;
        c.gridheight = 1;
        c.gridwidth = 1;
        rp.add(locationRadioButton, c);
        final String defaultText = "[Velg ett annet sted:]";
        locationTextField = new JTextField(defaultText, 15);
        locationTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                locationRadioButton.setSelected(true);
                roomRadioButton.setSelected(false);
                if (locationTextField.getText().equals(defaultText)){
                    locationTextField.setText("");
                };

            }
            @Override
            public void focusLost(FocusEvent e) {
                if (locationTextField.getText().isEmpty()){
                    locationTextField.setText(defaultText);
                }
            }
        });
        c.gridx = 1;
        c.gridy = 7;
        c.gridheight = 1;
        c.gridwidth = 2;
        rp.add(locationTextField, c);

        ButtonGroup roomLocationGroup = new ButtonGroup();
        roomLocationGroup.add(roomRadioButton);
        roomLocationGroup.add(locationRadioButton);

        // Knappar
        JButton cancelButton = new JButton("Avbryt");
        c.gridx = 0;
        c.gridy = 8;
        c.gridheight = 1;
        c.gridwidth = 1;
        rp.add(cancelButton, c);

        JButton saveButton = new JButton("Lagre");
        saveButton.setAction(new NewMeetingAction("Lagre"));
        c.gridx = 1;
        c.gridy = 8;
        c.gridheight = 1;
        c.gridwidth = 2;
        rp.add(saveButton, c);

        cl.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        cl.insets = new Insets(0, 50, 0, 0);
        add(rp, cl);

    }

    private ActionListener updateAvailableRooms = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            int antAttendees = 0;
            if (participateYesButton.isSelected()) antAttendees++;
            antAttendees += addEmpList.getSelectedValuesList().size();
            antAttendees += Integer.parseInt(extraField.getText());

            System.out.println(antAttendees);
            String meetingRoomName = roomsDropdown.getSelectedItem().toString();
            Date meetingtime =computeDateFromDateAndTimeOfDay((GuiTimeOfDay) startTimeDropdown.getSelectedItem());
            GuiTimeOfDay guiTime = (GuiTimeOfDay) durationDropdown.getSelectedItem();
            int duration = guiTime.getHours() * 60 + guiTime.getMinutes();
            MeetingRoom mr = model.getMapMeetingRoom().get(meetingRoomName);
            ClientMain.sendTransferObject(new TransferObject(MessageType.REQUEST, TransferType.GET_AVAILABLE_MEETING_ROOMS, mr, meetingtime, duration, antAttendees));
        }
    };

	
	private Date computeDateFromDateAndTimeOfDay(GuiTimeOfDay selectedTime) {
        System.out.println(datePicker);
        Date returnDate = datePicker.getDate();
		returnDate.setHours(selectedTime.getHours());
		returnDate.setMinutes(selectedTime.getMinutes());

		return returnDate; 
	}

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(ClientModelImpl.ROOMS)) {
            roomsComboBoxModel = new DefaultComboBoxModel<>(getRooms(false));
            roomsDropdown.setModel(roomsComboBoxModel);

        }
    }

    public String[] getRooms(boolean init) {
        List<MeetingRoom> rooms = null;
        if (init) rooms = new ArrayList<>(model.getMapMeetingRoom().values());
        else rooms = new ArrayList<>(model.getMapMeetingRoomAvailable().values());
        String[] roomsArr = new String[rooms.size()+1];
        roomsArr[0] = "Velg rom";
        for (int i = 1; i < rooms.size(); i++){
            roomsArr[i] = rooms.get(i).getName();
        }
        return roomsArr;
    }

    private class NewMeetingAction extends AbstractAction {
		
		public NewMeetingAction(String tittel) {
			putValue(AbstractAction.NAME, tittel);

		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
            String selectedRoom = (String) roomsDropdown.getSelectedItem();
            if (selectedRoom.contains("Velg rom"))  {
                //@todo popupbeskjed om å velge rom.
                return;
            }
            System.out.println("action");
            Meeting meeting = new Meeting(UUID.randomUUID().toString());
			meeting.setDescription(descText.getText());
			meeting.setMeetingTime(new Date());
			GuiTimeOfDay time = (GuiTimeOfDay) durationDropdown.getSelectedItem();
            meeting.setDuration(time.getHours() * 60 + time.getMinutes());
            meeting.setMeetingRoom(model.getMapMeetingRoomAvailable().get(roomsDropdown.getSelectedItem()));

			// todo: Legg til deltakere
			meeting.setGuestAmount(Integer.parseInt(extraField.getText()));
            int antAttendee = meeting.getGuestAmount() + meeting.getAttendees().size();
            ClientMain.sendTransferObject(new TransferObject(MessageType.REQUEST, TransferType.GET_AVAILABLE_MEETING_ROOMS, meeting.getMeetingRoom(), meeting.getMeetingTime(), meeting.getDuration(), antAttendee));

            for (String name : model.getMapMeetingRoomAvailable().keySet()){
                System.out.println(model.getMapMeetingRoomAvailable().get(name));
            }
//            ClientMain.sendTransferObject(new TransferObject(MessageType.REQUEST, TransferType.ADD_MEETING, meeting));

        }
		
	}
	
	private class LocationRadioButtonListener implements ActionListener {
		
		public LocationRadioButtonListener() {
			//
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (roomRadioButton.isSelected()) {
				locationTextField.setEditable(false);
				roomsDropdown.setEnabled(true);
			} else if (locationRadioButton.isSelected()) {
				roomsDropdown.setEnabled(false);
				locationTextField.setEditable(true);
			}
		}
		
		
		
	}
	
	public static void main(String[] args) {
		
		JFrame frame = new JFrame("Test av opprett avtale");
		NewMeetingPanel panel = new NewMeetingPanel(null);
		frame.setContentPane(panel);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationByPlatform(true);
		frame.pack();
		frame.setVisible(true);
		
	}
	
	private class MyIntFilter extends DocumentFilter {
		   @Override
		   public void insertString(FilterBypass fb, int offset, String string,
		         AttributeSet attr) throws BadLocationException {

		      Document doc = fb.getDocument();
		      StringBuilder sb = new StringBuilder();
		      sb.append(doc.getText(0, doc.getLength()));
		      sb.insert(offset, string);

		      if (test(sb.toString())) {
		         super.insertString(fb, offset, string, attr);
		      } else {
		         // warn the user and don't allow the insert
		      }
		   }

		   private boolean test(String text) {
		      try {
		    	 if (text.isEmpty()) {
		    		 return true;
		    	 }
		         Integer.parseInt(text);
		         return true;
		      } catch (NumberFormatException e) {
		         return false;
		      }
		   }

		   @Override
		   public void replace(FilterBypass fb, int offset, int length, String text,
		         AttributeSet attrs) throws BadLocationException {

		      Document doc = fb.getDocument();
		      StringBuilder sb = new StringBuilder();
		      sb.append(doc.getText(0, doc.getLength()));
		      sb.replace(offset, offset + length, text);

		      if (test(sb.toString())) {
		         super.replace(fb, offset, length, text, attrs);
		      } else {
		         // warn the user and don't allow the insert
		      }

		   }

		   @Override
		   public void remove(FilterBypass fb, int offset, int length)
		         throws BadLocationException {
		      Document doc = fb.getDocument();
		      StringBuilder sb = new StringBuilder();
		      sb.append(doc.getText(0, doc.getLength()));
		      sb.delete(offset, offset + length);

		      if (test(sb.toString())) {
		         super.remove(fb, offset, length);
		      } else {
		         // warn the user and don't allow the insert
		      }

		   }
		}
}
