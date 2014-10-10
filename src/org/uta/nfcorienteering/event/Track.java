package org.uta.nfcorienteering.event;

import java.util.ArrayList;

public class Track {
	private int trackNumber = 0;
	private String trackName = "";
	private ArrayList<Checkpoint> checkpoints = null;
	
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
}
