package org.uta.nfcorienteering.event;

import java.io.Serializable;
import java.util.ArrayList;

public class Track implements Serializable{
	private int trackNumber = 0;
	private String trackName = "";
	private String distance = "";
	private ArrayList<Checkpoint> checkpoints = null;
	private int currentCheckPoint = 0;
	private String MapUrl = "";
	private String description = "";

	public int getTrackNumber() {
		return trackNumber;
	}

	public void setTrackNumber(int trackNumber) {
		this.trackNumber = trackNumber;
	}

	public String getTrackName() {
		return trackName;
	}

	public void setTrackName(String trackName) {
		this.trackName = trackName;
	}

	public ArrayList<Checkpoint> getCheckpoints() {
		return checkpoints;
	}

	public void setCheckpoints(ArrayList<Checkpoint> checkpoints) {
		this.checkpoints = checkpoints;
	}

	public String getMapUrl() {
		return MapUrl;
	}

	public void setMapUrl(String mapUrl) {
		MapUrl = mapUrl;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getCurrentCheckPoint() {
		return currentCheckPoint;
	}
	
	public void setCurrentCheckPoint(int currentCheckPoint) {
		this.currentCheckPoint = currentCheckPoint;
	}
	
	

	public boolean newCheckPointReached(String tagid) {

		// this.tagid = tag_id;checking the current tag+id whether it is correct
		// or not if it is then
		// we will move to the next otherwise it will pop up with a msg. that
		// the tag id is incorrect.

		if (checkpoints == null) 
		{
			throw new NullPointerException();
		}

		if (checkpoints.get(currentCheckPoint).getRfidTag() == tagid)
		{
			
			return true;

		} else 
		
		{
			
			return false;
		}

	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}
}

