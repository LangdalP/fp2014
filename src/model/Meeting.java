package model;

import java.util.Date;
import java.util.List;

public class Meeting {
	private int meetingID;
	private Date meetingTime;
	private int duration; //#minute
	private String description;
	private String meetingLocation;
	private Employee meetingOwner;
	private List<Attendee> attendees;
	private int guestAmount = 0;
	private MeetingRoom meetingRoom;
	private boolean meetingRoomBooked = false;
	
	public Meeting(int meetingID, Date meetingTime, int duration,
			String description, String meetingLocation, Employee meetingOwner,
			List<Attendee> attendees, int guestAmount, MeetingRoom meetingRoom, boolean meetingRoomBooked) {
		this.meetingID = meetingID;
		this.meetingTime = meetingTime;
		this.duration = duration;
		this.description = description;
		this.meetingLocation = meetingLocation;
		this.meetingOwner = meetingOwner;
		this.attendees = attendees;
		this.guestAmount = guestAmount;
		this.meetingRoom = meetingRoom;
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

	public int getMeetingID() {
		return meetingID;
	}

	public Employee getMeetingOwner() {
		return meetingOwner;
	}

	public List<Attendee> getAttendee() {
		return attendees;
	}
	
	public void addAttendee(Attendee attendee){
		attendees.add(attendee);
	}
	
	public void removeAttendee(Attendee attendee){
		attendees.remove(attendee);
	}
	
	
	
	
}
