package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;

import client.ClientModelImpl;
import model.Attendee;
import model.Employee;
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
	protected JTextArea descText;
    protected JXDatePicker datePicker;
    protected JComboBox<GuiTimeOfDay> startTimeDropdown;
    protected JComboBox<GuiTimeOfDay> durationDropdown;
    protected JRadioButton participateYesButton;
    protected JRadioButton participateNoButton;
	protected JRadioButton alarmYesButton;
	protected JRadioButton alarmNoButton;
	protected JComboBox<GuiTimeOfDay> alarmTimeDropdown;
	
	// Verdi-felt på høgresida
	protected JList<Employee> addEmpList;
    protected JTextField extraField;
    protected JRadioButton roomRadioButton;
    protected JRadioButton locationRadioButton;
    protected JComboBox<String> roomsDropdown;
    protected JTextField locationTextField;

    protected ClientModelImpl model;
    protected DefaultComboBoxModel<String> roomsComboBoxModel;
    private String[] rooms;

    final String defaultText = "[Velg ett annet sted:]";

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
        startTimeDropdown.addActionListener(updateAvailableRooms);
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
        durationDropdown.addActionListener(updateAvailableRooms);
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
        participateYesButton.addActionListener(updateAvailableRooms);
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

        DefaultListModel<Employee> nameListModel = new DefaultListModel<>();

        for (String key : model.getMapEmployees().keySet()) {
            if (key.equals(model.getUsername())) continue;
            nameListModel.addElement(model.getMapEmployees().get(key));
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
        addEmpList = new JList<Employee>(nameListModel);
        addEmpList.setFixedCellWidth(200);
        JScrollPane addEmpListScroller = new JScrollPane(addEmpList);
        addEmpList.setCellRenderer(new EmployeeCellRenderer());

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

        locationTextField = new JTextField(defaultText, 15);
        locationTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                locationTextField.setEditable(true);
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
                locationTextField.setEditable(false);
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
          // Overrides i InfoMeetingPanel
        c.gridx = 0;
        c.gridy = 8;
        c.gridheight = 1;
        c.gridwidth = 1;
        rp.add(getLeftButton() , c);

      
        c.gridx = 1;
        c.gridy = 8;
        c.gridheight = 1;
        c.gridwidth = 2;
        rp.add(getRightButton(), c);

        cl.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        cl.insets = new Insets(0, 50, 0, 0);
        add(rp, cl);

    }
    
    public JButton getLeftButton(){
        JButton cancelButton =  new JButton("Avbryt");
        cancelButton.setAction(new CancelAction("Avbryt"));
        return cancelButton;
    	
    }
    
    public JButton getRightButton(){
    	
  	   JButton saveButton = new JButton("Lagre");
       saveButton.setAction(new NewMeetingAction("Lagre"));
       return saveButton;
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
        Date returnDate = datePicker.getDate();
		returnDate.setHours(selectedTime.getHours());
		returnDate.setMinutes(selectedTime.getMinutes());
		return returnDate;
	}

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("Fire RECEIVED");
        if (evt.getPropertyName().equals(ClientModelImpl.ROOMS)) {
            System.out.println("Rooms updated!");
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

    private class CancelAction extends AbstractAction{

        private CancelAction(String tittel) {
            putValue(AbstractAction.NAME, tittel);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            for (Meeting m : model.getMapFutureMeetings().values()){
                System.out.println(model.getUsername() + "\t" + m.getDescription());
            }
        }
    }

    //SAVE BUTTON
    private class NewMeetingAction extends AbstractAction {
		
		public NewMeetingAction(String tittel) {
			putValue(AbstractAction.NAME, tittel);

		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
            Meeting meeting = new Meeting(UUID.randomUUID().toString());

            String selectedRoom = (String) roomsDropdown.getSelectedItem();
            if (locationRadioButton.isSelected())  {
                String meetingLocation = locationTextField.getText();
                if (meetingLocation.isEmpty() || meetingLocation.equals(defaultText)){
                    //@todo varsle bruker, må sette navn på eksternt møterom.
                }
                else meeting.setMeetingLocation(meetingLocation);
            }
            if (roomRadioButton.isSelected()) {
                //antar at validering av ledig moterom er gjort.
                meeting.setMeetingRoom(model.getMapMeetingRoomAvailable().get(roomsDropdown.getSelectedItem()));
            }
			meeting.setDescription(descText.getText());

            GuiTimeOfDay meetingStart = (GuiTimeOfDay) startTimeDropdown.getSelectedItem();
			meeting.setMeetingTime(computeDateFromDateAndTimeOfDay(meetingStart));
			GuiTimeOfDay time = (GuiTimeOfDay) durationDropdown.getSelectedItem();
            meeting.setDuration(time.getHours() * 60 + time.getMinutes());

            List<Employee> emps = addEmpList.getSelectedValuesList();
            for (Employee emp : emps){
                System.out.println(model.getMapEmployees());
                meeting.addAttendee(new Attendee(emp, false, false, new Date(), false, null));
            }

            //LEGG TIL BRUKER.
            boolean hasResponded = (participateYesButton.isSelected() || participateNoButton.isSelected()) ? true : false;
            Boolean attendeeStatus = false;
            if (participateYesButton.isSelected()) attendeeStatus = true;
            if (participateNoButton.isSelected()) attendeeStatus = false;
            boolean hasAlarm = alarmYesButton.isSelected() ? true : false;
            GuiTimeOfDay timeAlarm  = (GuiTimeOfDay) alarmTimeDropdown.getSelectedItem();

            Long minusAlarmTime = meeting.getMeetingTime().getTime() - new Long(timeAlarm.getHours() * 60 + timeAlarm.getMinutes()) * 60 * 1000;
            Calendar cal = new GregorianCalendar();
            cal.setTimeInMillis(minusAlarmTime);

            Employee userEmp = model.getMapEmployees().get(model.getUsername());
            Attendee userAttendee = new Attendee(userEmp, hasResponded, attendeeStatus, new Date(), hasAlarm, cal.getTime());
            meeting.addAttendee(userAttendee);

            //set eksterne deltagere.
			meeting.setGuestAmount(Integer.parseInt(extraField.getText()));
            int antAttendee = meeting.getGuestAmount() + meeting.getAttendees().size();

//            System.out.println(meeting);
            ClientMain.sendTransferObject(new TransferObject(MessageType.REQUEST, TransferType.ADD_MEETING, meeting));


//            System.out.println("nr futureMeetings: " + model.getMapFutureMeetings().size());
//            for (Meeting m : model.getMapFutureMeetings().values()){
//                System.out.println(m.getMeetingOwner().getUsername() + "\t"+ m.getMeetingTime());
//            }

        }
		
	}
	
	private class LocationRadioButtonListener implements ActionListener {
		public LocationRadioButtonListener() {
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


    public class EmployeeCellRenderer2 implements ListCellRenderer{

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            return null;
        }
    }

    public class EmployeeCellRenderer implements ListCellRenderer {
        protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

        public Component getListCellRendererComponent(JList list, Object value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            Font theFont = null;
            Color theForeground = null;
            String theText = null;

            JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index,
                    isSelected, cellHasFocus);

            Employee emp = (Employee) value;
            renderer.setText(emp.getName());
            renderer.setFont(theFont);
            return renderer;
        }
    }

}
