package org.uta.nfcorienteering.event;

import java.io.Serializable;

public class Punch implements Serializable {
	private int checkpointNumber = 0;
	private String totalTimestamp = "";
	private String splitTime = "";

	public Punch() {
		;
	}

	public Punch(int checkpointNumber, String timestamp) {
		this.checkpointNumber = checkpointNumber;
		this.totalTimestamp = timestamp;
	}

	public int getCheckpointNumber() {
		return checkpointNumber;
	}

	public void setCheckpointNumber(int checkpointNumber) {
		this.checkpointNumber = checkpointNumber;
	}

	public String getTotalTimestamp() {
		return totalTimestamp;
	}

	public void setTotalTimestamp(String timestamp) {
		this.totalTimestamp = timestamp;
	}
	
	public String getSplitTime() {
		return splitTime;
	}
	
	public void setSplitTime(String splitTime) {
		this.splitTime = splitTime;
	}

	@Override
	public String toString() {
		return checkpointNumber + ":" + totalTimestamp;
	}
}
