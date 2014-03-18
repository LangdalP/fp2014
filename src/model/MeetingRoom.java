package model;

import java.io.Serializable;
import java.util.List;

public class MeetingRoom implements Serializable{
	
	private static final long serialVersionUID = 2402190254633613704L;

	private String name;
	private int maxPeople;

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
	

    @Override
    public String toString() {
        return "MeetingRoom{" +
                "name='" + name + '\'' +
                ", maxPeople=" + maxPeople +
                '}';
    }
}
