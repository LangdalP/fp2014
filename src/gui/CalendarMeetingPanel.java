package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

import model.Meeting;

public class CalendarMeetingPanel extends JPanel {
	

	private static final int VERT_PX_PER_HOUR = 30;
	
	private Meeting meet = null;
	private int duration = 0;
	private int heightPx = 0;
	private int widthPx = 80;
	
	public CalendarMeetingPanel(Meeting meeting) {
		this.meet = meeting;
		this.duration = meeting.getDuration();
		
		heightPx = (duration/60)*VERT_PX_PER_HOUR;
		if ((duration%60) > 0) {
			heightPx += VERT_PX_PER_HOUR/2;
		}
		
	}
	
	
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(Color.decode("#6495ED"));
		g.fillRoundRect(0, 0, widthPx, heightPx, 10, 10);
		
		g.setColor(Color.BLACK);
		Font f = g.getFont();
		g.setFont(f.deriveFont(8));
		g.drawString(meet.getDescription(), 30, 16);
	}
}
