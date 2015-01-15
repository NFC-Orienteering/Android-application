package org.uta.nfcorienteering.event;

import java.io.Serializable;
import java.util.List;

public class Track implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7545882639858903218L;

	private int trackNumber = 0;
	private String trackName = "";
	private String distance = "";
	private List<Checkpoint> checkpoints = null;
	private int currentCheckPoint = 0;
	private String mapUrl = "";
	private String description = "";
	private OrienteeringEvent parentEvent = null;

	public boolean checkComplete() {
		OrienteeringRecord record = this.parentEvent.getRecord();
		List<Punch> punches = record.getPunches();
		if (checkpoints == null
			||punches == null) {
			return false;
		}

		int length = checkpoints.size();
		for (int i = 0; i < length; i++) {
			Checkpoint checkpoint = checkpoints.get(i);
			Punch punch = punches.get(i);
			if (punch == null) {
				return false;
			}

			if (checkpoint.getCheckpointNumber() != punch.getCheckpointNumber()
					|| punch.getSplitTimeMillis() == 0) {
				return false;
			}
		}
		return true;
	}

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

	public List<Checkpoint> getCheckpoints() {
		return checkpoints;
	}

	public void setCheckpoints(List<Checkpoint> checkpoints) {
		this.checkpoints = checkpoints;
	}

	public String getMapUrl() {
		return mapUrl;
	}

	public void setMapUrl(String mapUrl) {
		this.mapUrl = mapUrl;
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

	public int newCheckPointReached(String tagid) {

		if (checkpoints == null) {
			throw new NullPointerException();
		}

		for (int i = 0; i < checkpoints.size(); i++) {

			if (checkpoints.get(i).getRfidTag().equals(tagid)) {
				return i;
			}
		}
		return -1;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public OrienteeringEvent getParentEvent() {
		return parentEvent;
	}

	public void setParentEvent(OrienteeringEvent parentEvent) {
		this.parentEvent = parentEvent;
	}

}
