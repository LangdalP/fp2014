package model;

import java.util.List;

public class MeetingRoom {

	private String name;
	private int maxPeople;
	private List<Meeting> upcomingMeetings;
	
	public MeetingRoom(String name, int maxPeople,
			List<Meeting> upcomingMeetings) {
		this.name = name;
		this.maxPeople = maxPeople;
		this.upcomingMeetings = upcomingMeetings;
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
	
	
}
