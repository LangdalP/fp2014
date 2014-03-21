package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.ListCellRenderer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.Employee;
import model.Meeting;
import client.ClientModelImpl;

public class HomePanel extends JPanel {

	private GridBagLayout layout = new GridBagLayout();
	private ClientModelImpl model;
	private JList<Employee> addEmpList;
	private DefaultListModel<Employee> nameListModel;
    private PropertyChangeSupport pcs;

    public static Dimension dim = new Dimension(1000, 250);


	public HomePanel(ClientModelImpl model){
        pcs = new PropertyChangeSupport(this);
		this.model = model;
		setLayout(layout);
        setPreferredSize(dim);

		init();
	}

	public void init(){
		GridBagConstraints c = new GridBagConstraints();
		Insets insets = new Insets(0, 0, 0, 0);
		setLayout(new GridBagLayout());


		JButton opprett = new JButton("Opprett avtale");
        opprett.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pcs.firePropertyChange(GuiMain.NEW_MEETING, null, new Meeting(UUID.randomUUID().toString()));
            }
        });
		c.gridheight = 2;
		c.gridwidth = 1; 
		c.gridx = 0;
		c.gridy = 0;
		add(opprett,c);

		JToggleButton avslaatte = new JToggleButton("Avslåtte avtaler");
		c.gridheight = 2;
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy=2;

		add(avslaatte,c);

		JLabel addEmpLabel = new JLabel("Legg til deltakere");
		c.gridx = 1;
		c.gridy = 0;
		c.gridheight = 1;
		c.gridwidth = 3;
		add(addEmpLabel, c);

		nameListModel = new DefaultListModel<>();

		for (String key : model.getMapEmployees().keySet()) {
			
			nameListModel.addElement(model.getMapEmployees().get(key));

		}

		addEmpList = new JList<Employee>(nameListModel);
		addEmpList.setFixedCellWidth(200);
		addEmpList.setVisibleRowCount(5);
		addEmpList.addListSelectionListener(new SelectedEmpsListener());
		addEmpList.setCellRenderer(new EmployeeCellHome());
//		addEmpList.setSelectedValue(model.getUsername(), true);
		
		JScrollPane addEmpListScroller = new JScrollPane(addEmpList);

		c.gridx = 1;
		c.gridy = 1;
		c.gridheight = 3;
		c.gridwidth = 3;
		add(addEmpListScroller, c); 


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

    public void addPropertyChangeListener(PropertyChangeListener listener){
        pcs.addPropertyChangeListener(listener);
    }
    
    private class SelectedEmpsListener implements ListSelectionListener {
    	
    	private boolean firstClick = true;
    	
		@Override
		public void valueChanged(ListSelectionEvent e) {
			if (firstClick) {
				List<Employee> emps = addEmpList.getSelectedValuesList();
				firstClick = !firstClick;
				pcs.firePropertyChange(GuiMain.SET_EMPLOYEES_TO_SHOW, null, emps);
			} else {
				firstClick = !firstClick;
			}
		}
    	
    }


private class EmployeeCellHome implements ListCellRenderer {

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