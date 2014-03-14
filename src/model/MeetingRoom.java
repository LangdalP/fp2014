package model;

import java.io.Serializable;
import java.util.List;

public class MeetingRoom implements Serializable{
	
	private static final long serialVersionUID = 2402190254633613704L;

	private String name;
	private int maxPeople;
	private List<Meeting> upcomingMeetings;
	
	public MeetingRoom(String name, int maxPeople,
			List<Meeting> upcomingMeetings) {
		this.name = name;
		this.maxPeople = maxPeople;
		this.upcomingMeetings = upcomingMeetings;
	}

	public MeetingRoom(String name, int maxPeople) {
		this.name = name;
		this.maxPeople = maxPeople;
	}
	
	public String getName() {
		return name;
	}

	public int getMaxPeople() {
		return maxPeople;
	}

	public List<Meeting> getUpcomingMeetings() {
		return upcomingMeetings;
	}
	
	public void addUpcomingMeetings(Meeting meeting){
		upcomingMeetings.add(meeting);
	}
	
	public void removeUpcomingMeetings(Meeting meeting){
		upcomingMeetings.remove(meeting);
	}

    @Override
    public String toString() {
        return "MeetingRoom{" +
                "name='" + name + '\'' +
                ", maxPeople=" + maxPeople +
                ", upcomingMeetings=" + upcomingMeetings +
                '}';
    }
}
