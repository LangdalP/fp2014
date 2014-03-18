package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import client.ClientModelImpl;

public class CalendarPanel extends JPanel {
	
	private GridBagLayout layout = new GridBagLayout();
	private ClientModelImpl model;
	
	public CalendarPanel(ClientModelImpl model) {
		setLayout(layout);
		this.model = model;
		
		init();
	}
	
	private void init() {
		GridBagConstraints c = new GridBagConstraints();
		
		CalendarTimePanel timePanel = new CalendarTimePanel();
		c.gridx = 0; c.gridy = 0; c.gridheight = 1; c.gridwidth = 1;
		add(timePanel, c);
	}
	
	
	private class CalendarTimePanel extends JPanel {
		
		public CalendarTimePanel() {
			
			setLayout(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();
			Border brdr = BorderFactory.createLineBorder(Color.BLACK, 2);
			
			JLabel timeText = new JLabel("  Klokkeslett");
			timeText.setBorder(brdr);
			timeText.setPreferredSize(new Dimension(80, 60));
			c.gridx = 0; c.gridy = 0; c.gridwidth = 1; c.gridwidth = 1;
			add(timeText, c);
			
			addTimeLabels(c, brdr);
		}
		
		private void addTimeLabels(GridBagConstraints c, Border brdr) {
			int hoursStart = 8;
			int hoursEnd = 20;
			
			
			for (int i=hoursStart; i<= hoursEnd; i++) {
				c.gridy = c.gridy + 1;
				
				String labelString = Integer.toString(i);
				JLabel timeLabel = new JLabel("  " + labelString + ": 00");
				timeLabel.setBorder(brdr);
				timeLabel.setPreferredSize(new Dimension(80, 40));
				add(timeLabel, c);
				
			}
			
		}
		
		
		
	}

	public static void main(String[] args) {
		
		CalendarPanel calPan = new CalendarPanel(null);
		
		JFrame frame = new JFrame("Topptekst");
		
		frame.setContentPane(calPan);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		
		
	}
}
