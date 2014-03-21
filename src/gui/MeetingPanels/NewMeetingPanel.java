package gui.MeetingPanels;

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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;

import client.ClientModelImpl;
import gui.EmailNotificationPanel;
import gui.GuiMain;
import gui.GuiTimeOfDay;
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
    private final Employee employee;
    protected MeetingModel mModel; //m�te for panelet.

    protected PropertyChangeSupport pcs;
	private GridBagLayout layout = new GridBagLayout();
	
	// Verdi-felt p� venstresida
	protected JTextArea descText;
    protected JXDatePicker datePicker;
    protected JComboBox<GuiTimeOfDay> startTimeDropdown;
    protected JComboBox<GuiTimeOfDay> durationDropdown;
    protected JRadioButton participateYesButton;
    protected JRadioButton participateNoButton;
	protected JRadioButton alarmYesButton;
	protected JRadioButton alarmNoButton;
	protected JComboBox<GuiTimeOfDay> alarmTimeDropdown;
	
	// Verdi-felt p� h�gresida
	protected JList<Employee> addEmpList;
    protected JTextField extraField;
    protected JRadioButton roomRadioButton;
    protected JRadioButton locationRadioButton;
    protected JComboBox<String> roomsDropdown;
    protected JTextField locationTextField;
    protected JButton sendEmailButton;
    
    protected JPanel lp;
    protected JPanel rp;

    protected ClientModelImpl model;
    protected DefaultComboBoxModel<String> roomsComboBoxModel;
    private String[] rooms;

 //		knapper

//    protected JButton lb = getLeftButton();
//    protected JButton rb = getRightButton();

    final String defaultText = "[Velg ett annet sted:]";

    public NewMeetingPanel(ClientModelImpl model, MeetingModel mModel) {
		this.model = model;
        this.mModel = mModel;
        mModel.setUsername(model.getUsername());
        this.employee = model.getMapEmployees().get(model.getUsername());
		this.model.addPropertyChangeListener(this);
        pcs = new PropertyChangeSupport(this);
		setLayout(layout);
        mModel.addPropertyChangeListener(this);
		init();
	}

    
    private void init() {

        lp = new JPanel();
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
        descText.setText(mModel.getDescription());
        ;
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
        datePicker = new JXDatePicker(mModel.getMeetingTime());
        datePicker.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date date = GuiTimeOfDay.getDate(datePicker.getDate(), startTimeDropdown);
                mModel.setMeetingTime(date);

            }
        });
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
        GuiTimeOfDay g = GuiTimeOfDay.getGuiTimeOfDayFromDate(mModel.getMeetingTime());
        startTimeDropdown.setEditable(true);
        startTimeDropdown.setSelectedItem(g);
        startTimeDropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mModel.setMeetingTime(GuiTimeOfDay.getDate(mModel.getMeetingTime(), startTimeDropdown));
            }
        });

        c.gridx = 1;
        c.gridy = 2;
        c.gridheight = 1;
        c.gridwidth = 4;
//        startTimeDropdown.addActionListener(actionUpdateAvailableRooms);
        lp.add(startTimeDropdown, c);

        // Varigheit
        JLabel durationLabel = new JLabel("Varighet: ");
        c.gridx = 0;
        c.gridy = 3;
        c.gridheight = 1;
        c.gridwidth = 1;
        lp.add(durationLabel, c);
        durationDropdown = new JComboBox<>(durationComboBoxModel);
        System.out.println("fadg" + g.getHours());
        int hours = mModel.getDuration() / 60;
        int minutes = mModel.getDuration() - (hours * 60);
        durationDropdown.setEditable(true);
        durationDropdown.setSelectedItem(new GuiTimeOfDay(hours, minutes));
        durationDropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GuiTimeOfDay t = (GuiTimeOfDay) durationDropdown.getSelectedItem();
                mModel.setDuration(t.getHours() * 60 + t.getMinutes());
            }
        });
        c.gridx = 1;
        c.gridy = 3;
        c.gridheight = 1;
        c.gridwidth = 4;
        lp.add(durationDropdown, c);

        // Deltakelse
        JLabel participateLabel = new JLabel("Delta p� m�te: ");
        c.gridx = 0;
        c.gridy = 4;
        c.gridheight = 1;
        c.gridwidth = 1;
        lp.add(participateLabel, c);
        participateYesButton = new JRadioButton("Ja");
        participateYesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mModel.getUserAttende().setAttendeeStatus(true);
            }
        });
        c.gridx = 1;
        c.gridy = 4;
        c.gridheight = 1;
        c.gridwidth = 2;
        lp.add(participateYesButton, c);
        participateNoButton = new JRadioButton("Nei");
        participateNoButton.setEnabled(false);
        participateNoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mModel.getUserAttende().setAttendeeStatus(false);
            }
        });
        c.gridx = 3;
        c.gridy = 4;
        c.gridheight = 1;
        c.gridwidth = 2;
        lp.add(participateNoButton, c);

        ButtonGroup participateGroup = new ButtonGroup();
        participateGroup.add(participateYesButton);
        participateGroup.add(participateNoButton);


        // Alarm
        JLabel alarmLabel = new JLabel("Alarm f�r m�te: ");
        c.gridx = 0;
        c.gridy = 5;
        c.gridheight = 1;
        c.gridwidth = 1;
        lp.add(alarmLabel, c);
        alarmYesButton = new JRadioButton("P�");

        alarmYesButton.setEnabled(mModel.hasAlarm());
        alarmYesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mModel.getUserAttende().setHasAlarm(true);
            }
        });
        c.gridx = 1;
        c.gridy = 5;
        c.gridheight = 1;
        c.gridwidth = 1;
        lp.add(alarmYesButton, c);
        alarmNoButton = new JRadioButton("Av");
        alarmNoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mModel.getUserAttende().setHasAlarm(false);
            }
        });
        alarmNoButton.setEnabled(mModel.hasAlarm());
        c.gridx = 2;
        c.gridy = 5;
        c.gridheight = 1;
        c.gridwidth = 1;
        lp.add(alarmNoButton, c);

        ButtonGroup alarmGroup = new ButtonGroup();
        alarmGroup.add(alarmYesButton);
        alarmGroup.add(alarmNoButton);


        alarmTimeDropdown = new JComboBox<>(alarmTimeComboBoxModel);
        alarmTimeDropdown.setEnabled(false);
        alarmTimeDropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date alarmTime = GuiTimeOfDay.getAlarmTime(mModel.getMeetingTime(), alarmTimeDropdown);
                mModel.getUserAttende().setAlarmTime(alarmTime);
            }
        });
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

        // START H�GRESIDE

        DefaultListModel<Employee> nameListModel = getEmployeeDefaultListModel();




        // Testkode slutt
        rp = new JPanel();
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
        addEmpList.setVisibleRowCount(5);
        JScrollPane addEmpListScroller = new JScrollPane(addEmpList);
        addEmpList.setCellRenderer(new EmployeeCellRenderer());
        addEmpList.addListSelectionListener(listEmployeeSelectionListener());


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
        extraField.addActionListener(actionUpdateAvailableRooms);
        c.gridx = 1;
        c.gridy = 3;
        c.gridheight = 1;
        c.gridwidth = 1;
        rp.add(extraField, c);
        
        AbstractAction openEmailBoxAction = new AbstractAction("Send epost") {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				EmailNotificationPanel emailPanel = new EmailNotificationPanel();
				emailPanel.setVisible(true);
			}
		};
		
		sendEmailButton = new JButton(openEmailBoxAction);
		c.gridx = 2;
		c.gridy = 3;
		c.gridheight = 1;
		c.gridwidth = 1;
		rp.add(sendEmailButton, c);

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
        c.gridwidth = 1;

        rp.add(getRightButton(), c);

        cl.gridx = 1;
        cl.gridy = 0;
        cl.gridwidth = 1;
        cl.gridheight = 1;
        cl.insets = new Insets(0, 50, 0, 0);
        add(rp, cl);

    }

    protected DefaultListModel<Employee> getEmployeeDefaultListModel() {
        DefaultListModel<Employee> nameListModel = new DefaultListModel<>();
        for (String key : model.getMapEmployees().keySet()) {
            nameListModel.addElement(model.getMapEmployees().get(key));
        }
        return nameListModel;
    }

    public JButton getLeftButton(){
        JButton cancelButton =  new JButton("Avbryt");
//        cancelButton.setAction(new CancelAction("Avbryt"));
        cancelButton.setAction(new AbstractAction("Avbryt") {
            @Override
            public void actionPerformed(ActionEvent e) {
                pcs.firePropertyChange(GuiMain.SHOW_HOME, null, null);
            }
        });
        return cancelButton;
    	
    }
    
    public JButton getRightButton(){
    	
  	   JButton saveButton = new JButton("Lagre");
       saveButton.setAction(new AbstractAction("Lagre") {

           @Override
           public void actionPerformed(ActionEvent e) {
               System.out.println("Send Meeting to server. ");
               mModel.setDescription(descText.getText());
               mModel.setMeetingTime(GuiTimeOfDay.getDate(mModel.getMeetingTime(), startTimeDropdown));
               
               if (locationRadioButton.isSelected()) {
            	   mModel.setMeetingLocation(locationTextField.getText());
               }
               
               if (roomRadioButton.isSelected()) {
            	   String roomName = (String) roomsComboBoxModel.getSelectedItem();
            	   System.out.println(roomName);
            	   mModel.setMeetingRoom(model.getMapMeetingRoom().get(roomName));
               }
               
               System.out.println("size: " + mModel.getMapAttendees().size());
               Meeting meeting = mModel.meeting();
               meeting.setMeetingOwner(model.getMapEmployees().get(model.getUsername()));
               model.addMeeting(meeting);
               ClientMain.sendTransferObject(new TransferObject(MessageType.REQUEST, TransferType.ADD_MEETING, meeting));
               System.out.println(meeting);
               pcs.firePropertyChange(GuiMain.SHOW_HOME, null, null);
           }
       });
       return saveButton;
    }

    public JButton getHomeButton() {
        JButton button =  new JButton("Hjem");
        button.setAction(new AbstractAction("Hjem") {
            @Override
            public void actionPerformed(ActionEvent e) {
                pcs.firePropertyChange(GuiMain.SHOW_HOME, null, null);
            }
        });
        return button;
    }




    public ListSelectionListener listEmployeeSelectionListener(){
        return new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                boolean userAttending = false;
                for (Employee emp : addEmpList.getSelectedValuesList()){
                    mModel.addAttendee(new Attendee(emp, false, false, new Date(), false, null));
                    if (emp.getUsername().equals(model.getUsername())){
                        userAttending = true;
                    }
                }
                //enable deltager related ting.
                alarmTimeDropdown.setEnabled(userAttending);
                alarmYesButton.setEnabled(userAttending);
                alarmNoButton.setEnabled(userAttending);
                participateYesButton.setEnabled(userAttending);
                participateNoButton.setEnabled(userAttending);
                if (participateYesButton.isSelected()){
                    boolean hasReponded = false;
//                    participateYesButton.isSelected() hasReponded = true;

                    boolean attendeeStatus = participateYesButton.isSelected() ? true: false;
                    boolean hasAlarm = alarmYesButton.isSelected() ? true : false;
                    GuiTimeOfDay alarmTime = (GuiTimeOfDay) alarmTimeDropdown.getSelectedItem();
                    Calendar alarmCal = new GregorianCalendar(mModel.getMeetingTime().getYear(), alarmTime.getHours(), alarmTime.getMinutes());
                    mModel.addAttendee(new Attendee(employee, hasReponded, attendeeStatus, new Date(), hasAlarm, alarmCal.getTime()));


                }
        }
        };
    }



    private ActionListener actionUpdateAvailableRooms = new ActionListener() {
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

//		JFrame frame = new JFrame("Test av opprett avtale");
//		NewMeetingPanel panel = new NewMeetingPanel(null);
//		frame.setContentPane(panel);
//
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setLocationByPlatform(true);
//		frame.pack();
//		frame.setVisible(true);

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

            Attendee att = mModel.getMapAttendees().get(emp.getUsername());
            String isGoing = "";
            String format = "%-30s %-5s";
            String text = null;
            if (att != null){
                if (att.getHasResponded() && att.getAttendeeStatus()) isGoing = "Ja";
                if (att.getHasResponded() && !att.getAttendeeStatus()) isGoing = "Nei";
                if (!att.getHasResponded()) isGoing = "Ikke svart";
                text = String.format(format, emp.getName(), isGoing);
            }
            if (text == null){
                text = String.format(format, emp.getName(), "LEGG TIL");
            }
            renderer.setText(text);
            renderer.setFont(theFont);
            return renderer;
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("Fire RECEIVED");
        String EV = evt.getPropertyName();
        if (EV.equals(ClientModelImpl.ROOMS)) {
            System.out.println("Rooms updated!");
            roomsComboBoxModel = new DefaultComboBoxModel<>(getRooms(false));
            roomsDropdown.setModel(roomsComboBoxModel);

        }
        if (EV.equals("NR_ATT")){
//            System.out.println(mModel.getn);
        }


    }


    public void addPropertyChangeListener(PropertyChangeListener listener){
        pcs.addPropertyChangeListener(listener);
    }
}
