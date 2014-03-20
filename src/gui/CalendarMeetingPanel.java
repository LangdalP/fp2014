package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import model.Meeting;

public class CalendarMeetingPanel extends JPanel {
	

	private static final int VERT_PX_PER_HOUR = 30;
	
	private Meeting meet = null;
	private int duration = 0;
	private int heightPx = 0;
	private int widthPx = 80;
	
	public CalendarMeetingPanel(Meeting meeting, int panelWidth) {
		this.meet = meeting;
		this.duration = meeting.getDuration();
		widthPx = panelWidth;
		
		heightPx = (duration/60)*VERT_PX_PER_HOUR;
		if ((duration%60) > 0) {
			heightPx += VERT_PX_PER_HOUR/2;
		}
		
		setPreferredSize(new Dimension(widthPx, heightPx));
		setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		setToolTipText("<html><h2>" + meet.getDescription() + "</h2></html>");
		
	}
	
	public int getWidth() {
		return widthPx;
	}
	
	public int getHeight() {
		return heightPx;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(Color.decode("#6495ED"));
		g.fillRect(0, 0, widthPx, heightPx);
	}
}
