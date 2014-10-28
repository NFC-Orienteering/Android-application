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
	private int trackAmount = 0;
	private Track selectedTrack = null;
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

	public Track getSelectedTrack() {
		return selectedTrack;
	}

	public void setSelectedTrack(Track selectedTrack) {
		this.selectedTrack = selectedTrack;
	}

	public OrienteeringRecord getRecord() {
		return record;
	}

	public void setRecord(OrienteeringRecord record) {
		this.record = record;
	}

}
