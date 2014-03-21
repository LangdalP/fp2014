package gui;

import gui.MeetingPanels.NewMeetingPanel.EmployeeCellRenderer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.UUID;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.ListCellRenderer;

import model.Employee;

import client.ClientModelImpl;
import model.Meeting;

public class HomePanel extends JPanel implements PropertyChangeListener{

	private GridBagLayout layout = new GridBagLayout();
	private ClientModelImpl model;
	private JList<Employee> addEmpList;
    private PropertyChangeSupport pcs;

    public static Dimension dim = new Dimension(1000, 250);


	public HomePanel(ClientModelImpl model){
        pcs = new PropertyChangeSupport(this);
		this.model = model;
		this.model.addPropertyChangeListener(this);
		setLayout(layout);
        setPreferredSize(dim);

		init();
	}

	public void init(){
		GridBagConstraints c = new GridBagConstraints();
		Insets insets = new Insets(0, 0, 0, 0);
		setLayout(new GridBagLayout());


		JButton opprett = new JButton("Oppret avtale");
        opprett.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pcs.firePropertyChange(GuiMain.NEW_MEETING, null, new Meeting(UUID.randomUUID().toString()));
            }
        });
		c.gridheight = 1;
		c.gridwidth = 1; 
		c.gridx = 0;
		c.gridy = 0;
		add(opprett,c);

		JToggleButton avslaatte = new JToggleButton("Avsl�tte avtaler");
		c.gridheight = 1;
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy=1;

		add(avslaatte,c);

		JLabel addEmpLabel = new JLabel("Legg til deltakere");
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 1;
		c.gridwidth = 3;

		DefaultListModel<Employee> nameListModel = new DefaultListModel<>();

		for (String key : model.getMapEmployees().keySet()) {
			
			nameListModel.addElement(model.getMapEmployees().get(key));



			add(addEmpLabel, c);
			addEmpList = new JList<Employee>(nameListModel);
			addEmpList.setFixedCellWidth(200);
			addEmpList.setVisibleRowCount(5);
			JScrollPane addEmpListScroller = new JScrollPane(addEmpList);
			addEmpList.setCellRenderer(new EmployeeCellHome());

			c.gridx = 1;
			c.gridy = 0;
			c.gridheight = 4;
			c.gridwidth = 1;
			add(addEmpListScroller, c);
		} 


	}

	/**public static void main(String[] args) {

		JFrame frame = new JFrame("Hjemtest");
		HomePanel panel = new HomePanel();
		frame.setContentPane(panel);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationByPlatform(true);
		frame.pack();
		frame.setVisible(true);
		}
	 **/


	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub

	}


    public void addPropertyChangeListener(PropertyChangeListener listener){
        pcs.addPropertyChangeListener(listener);
    }


public class EmployeeCellHome implements ListCellRenderer {

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