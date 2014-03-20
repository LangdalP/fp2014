package gui;

import java.util.Date;

import com.sun.xml.internal.ws.util.StringUtils;

public class GuiTimeOfDay {
	
	private int hours;
	private int minutes;
	
	public GuiTimeOfDay(int hours, int minutes) {
		this.hours = hours;
		this.minutes = minutes;
	}
	
	public int getHours() {
		return hours;
	}
	
	public int getMinutes() {
		return minutes;
	}
	
	@Override
	public String toString() {
		String strHours = Integer.toString(hours);
		String strMinutes = Integer.toString(minutes);
		
		// Fyller med ekstra 0 f�r einsifra tal, f.eks 9 blir 09 og 0 blir 00;
		return ("00" + strHours).substring(strHours.length()) + ": " + ("00" + strMinutes).substring(strMinutes.length());
	}
	
	public static GuiTimeOfDay[] getTimesOfDayArray() {
		GuiTimeOfDay[] times = new GuiTimeOfDay[24];
		
		int counter = 0;
		for (int i=8; i<20; i++) {
			for (int j=0; j<60; j+=30) {
				times[counter] = new GuiTimeOfDay(i, j);
				counter++;
			}
		}
		
		return times;
	}
	
	public static GuiTimeOfDay[] getDurationTimesArray() {
		GuiTimeOfDay[] times = new GuiTimeOfDay[16];
		
		int counter = 0;
		for (int i=0; i<8; i++) {
			for (int j=0; j<60; j+=30) {
				if (i == 0 && j==0) continue;
				times[counter] = new GuiTimeOfDay(i, j);
				
				counter++;
			}
		}
		times[15] = new GuiTimeOfDay(8, 00);
		
		return times;
	}
	
	public static GuiTimeOfDay[] getAlarmTimesArray() {
		GuiTimeOfDay[] alarmTimes = new GuiTimeOfDay[96];
		
		int counter = 0;
		for (int i=0; i<24; i++) {
			for (int j=0; j<60; j+=15) {
				alarmTimes[counter] = new GuiTimeOfDay(i, j);
				counter++;
			}
		}
		
		return alarmTimes;
	}
	
	public static void main(String[] args) {
		GuiTimeOfDay[] times = getTimesOfDayArray();
		
		for (GuiTimeOfDay time : times) {
			System.out.println(time);
		}
		System.out.println("---");
		
		GuiTimeOfDay[] durTimes = getDurationTimesArray();
		
		for (GuiTimeOfDay time : durTimes) {
			System.out.println(time);
		}
		System.out.println("---");

		
		GuiTimeOfDay[] alarmTimes = getAlarmTimesArray();
		
		for (GuiTimeOfDay time : alarmTimes) {
			System.out.println(time);
		}
	}
	
	public static GuiTimeOfDay getGuiTimeOfDayFromDate(Date inDate) {
		int dateHours = inDate.getHours();
		int dateMinutes = inDate.getMinutes();
		return new GuiTimeOfDay(dateHours, dateMinutes);
	}

}
