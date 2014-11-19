package org.uta.nfcorienteering.event;

import java.io.Serializable;

public class OrienteeringEvent implements Serializable {
	/**
	 * Default serial id
	 */
	private static final long serialVersionUID = 1L;
	
	private int eventID = 0;
	private String eventName = "";
	private String startingTime = "";
	private String endingTime = "";
	private int trackAmount = 0;
	private String location = "";
	private OrienteeringRecord record;

	public int getEventID() {
		return eventID;
	}

	public void setEventID(int eventID) {
		this.eventID = eventID;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getStartingTime() {
		return startingTime;
	}

	public void setStartingTime(String startingTime) {
		this.startingTime = startingTime;
	}

	public int getTrackAmount() {
		return trackAmount;
	}

	public void setTrackAmount(int trackAmount) {
		this.trackAmount = trackAmount;
	}


	public OrienteeringRecord getRecord() {
		return record;
	}

	public void setRecord(OrienteeringRecord record) {
		this.record = record;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getEndingTime() {
		return endingTime;
	}

	public void setEndingTime(String endingTime) {
		this.endingTime = endingTime;
	}

}
