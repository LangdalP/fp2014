package gui;

import javax.swing.*;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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
		Calendar cal = new GregorianCalendar();
		cal.setTime(inDate);
		int dateHours = cal.get(Calendar.HOUR_OF_DAY);
		int dateMinutes = cal.get(Calendar.MINUTE);
		return new GuiTimeOfDay(dateHours, dateMinutes);
	}

    public static Date getDate(Date date, JComboBox<GuiTimeOfDay> dropDownTime){
        if (dropDownTime == null) return date;
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        GuiTimeOfDay t = (GuiTimeOfDay) dropDownTime.getSelectedItem();
        cal = new GregorianCalendar(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), t.getHours(), t.getMinutes());
        return cal.getTime();
    }

    public static Date getAlarmTime(Date meetingTime, JComboBox<GuiTimeOfDay> alarmTimeDropdown) {
        int hours = 0, minutes = 30;
        if (alarmTimeDropdown != null){
             GuiTimeOfDay t = (GuiTimeOfDay) alarmTimeDropdown.getSelectedItem();
            hours = t.getHours();
             minutes = t.getMinutes();
        }
        Calendar cal = new GregorianCalendar();
        cal.setTime(meetingTime);
        Calendar ret = cal;
        ret.set(Calendar.HOUR, cal.get(Calendar.HOUR - hours));
        ret.set(Calendar.MINUTE, cal.get(Calendar.MINUTE - minutes));
        return ret.getTime();
    }
}
