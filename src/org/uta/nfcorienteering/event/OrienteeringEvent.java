package org.uta.nfcorienteering.event;

public class OrienteeringEvent {
	private int eventNumber = 0;
	private String eventName = "";
	private String startingTime = "";
	private String map;
	private int trackAmount = 0;
	private Track selectedTrack = null;
	private OrienteeringRecord record;

	public int getEventNumber() {
		return eventNumber;
	}

	public void setEventNumber(int eventNumber) {
		this.eventNumber = eventNumber;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getMap() {
		return map;
	}

	public void setMap(String map) {
		this.map = map;
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
