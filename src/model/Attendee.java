package model;

import java.io.Serializable;
import java.util.Date;

public class Attendee implements Serializable{
	
	private static final long serialVersionUID = 1134253360391058889L;
	
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

	public void setHasResponded(boolean hasResponded) {
		this.hasResponded = hasResponded;
	}

	public void setAttendeeStatus(boolean attendeeStatus) {
		this.attendeeStatus = attendeeStatus;
	}

	public void setLastNotification(Date lastNotification) {
		this.lastNotification = lastNotification;
	}

	public void setHasAlarm(boolean hasAlarm) {
		this.hasAlarm = hasAlarm;
	}

	public void setAlarmTime(Date alarmTime) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Attendee)) return false;

        Attendee attendee = (Attendee) o;
        if (!employee.getName().equals(attendee.employee.getName())) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return employee.hashCode();
    }

    @Override
    public String toString() {
        return "Attendee{" +
                "employee=" + employee +
                ", hasResponded=" + hasResponded +
                ", attendeeStatus=" + attendeeStatus +
                ", lastNotification=" + lastNotification +
                ", hasAlarm=" + hasAlarm +
                ", alarmTime=" + alarmTime +
                '}';
    }
}
