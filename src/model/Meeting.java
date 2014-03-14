package model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Meeting implements Serializable{
	
	private static final long serialVersionUID = 5308232820435917628L;
	
	private String meetingID;
	private Date meetingTime;
	private int duration; //#minute
	private String description;
	private String meetingLocation;
	private Employee meetingOwner;
	private List<Attendee> attendees;
	private int guestAmount = 0;
	private MeetingRoom meetingRoom;
	private String meetingRoomName;
	private boolean meetingRoomBooked = false;
	private Date lastChanged = new Date();

    public Meeting(String meetingID) {
        this.meetingID = meetingID;
    }

    public Meeting(String meetingID, Date meetingTime, int duration,
			String description, String meetingLocation, Employee meetingOwner,
			List<Attendee> attendees, int guestAmount, MeetingRoom meetingRoom) {
		this.meetingID = meetingID;
		this.meetingTime = meetingTime;
		this.duration = duration;
		this.description = description;
		this.meetingLocation = meetingLocation;
		this.meetingOwner = meetingOwner;
		this.attendees = attendees;
		this.guestAmount = guestAmount;
		this.meetingRoom = meetingRoom;
		this.meetingRoomName = meetingRoom.getName();
		if(meetingRoom != null){
			meetingRoomBooked = true;
		}
	}

	public Date getMeetingTime() {
		return meetingTime;
	}

	public void setMeetingTime(Date meetingTime) {
		this.meetingTime = meetingTime;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMeetngLocation() {
		return meetingLocation;
	}

	public void setMeetingLocation(String meetingLocation) {
		this.meetingLocation = meetingLocation;
	}

	public int getGuestAmount() {
		return guestAmount;
	}

	public void setGuestAmount(int guestAmount) {
		this.guestAmount = guestAmount;
	}

	public MeetingRoom getMeetingRoom() {
		return meetingRoom;
	}

	public void setMeetingRoom(MeetingRoom meetingRoom) {
		this.meetingRoom = meetingRoom;
		meetingRoomBooked = true;
	}
	
	public void setMeetingRoomBooked(boolean booked){
		if(booked == false){
			meetingRoomBooked = false;
			meetingRoom = null;
		}
	}
	
	public boolean getMeetingRoomBooked(){
		return meetingRoomBooked;
	}

	public String getMeetingID() {
		return meetingID;
	}

	public Employee getMeetingOwner() {
		return meetingOwner;
	}
	
	public void setMeetingOwner(Employee emp) {
		this.meetingOwner = meetingOwner;
	}

	public List<Attendee> getAttendees() {
		return attendees;
	}
	
	public void addAttendee(Attendee attendee){
		attendees.add(attendee);
	}
	
	public void removeAttendee(Attendee attendee){
		attendees.remove(attendee);
	}

	public Date getLastChanged() {
		return lastChanged;
	}

	public void setLastChanged(Date lastChanged) {
		this.lastChanged = lastChanged;
	}


    @Override
    public String toString() {
        return "Meeting{" +
                "meetingID='" + meetingID + '\'' +
                ", meetingTime=" + meetingTime +
                ", duration=" + duration +
                ", description='" + description + '\'' +
                ", meetingLocation='" + meetingLocation + '\'' +
                ", meetingOwner=" + meetingOwner +
                ", attendees=" + attendees +
                ", guestAmount=" + guestAmount +
                ", meetingRoom=" + meetingRoom +
                ", meetingRoomBooked=" + meetingRoomBooked +
                ", lastChanged=" + lastChanged +
                '}';
    }
}
