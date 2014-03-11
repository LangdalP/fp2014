package model;

import java.util.Date;

public class Attendee {
	
	private Employee employee;
	private boolean hasResponded;
	private boolean attendeeStatus;
	private Date lastNotification;
	private boolean hasAlarm;
	private Date alarmTime;
	
	
	
	public Attendee(Employee employee, boolean hasResponded,
			boolean attendeeStatus, Date lastNotification, boolean hasAlarm,
			Date alarmTime) {
		this.employee = employee;
		this.hasResponded = hasResponded;
		this.attendeeStatus = attendeeStatus;
		this.lastNotification = lastNotification;
		this.hasAlarm = hasAlarm;
		this.alarmTime = alarmTime;
	}

	public Employee getEmployee(){
		return employee;
	}
	
	public boolean getHasResponded(){
		return hasResponded;
	}
	
	public boolean getAttendeeStatus(){
		return attendeeStatus;
	}
	
	public Date getLastNotification(){
		return lastNotification;
	}
	
	public boolean getHasAlarm(){
		return hasAlarm;
	}
	
	public Date getAlarmTime(){
		return alarmTime;
	}

}
