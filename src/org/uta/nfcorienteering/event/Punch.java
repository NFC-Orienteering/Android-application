package org.uta.nfcorienteering.event;

import java.io.Serializable;

public class Punch implements Serializable {
	private int checkpointNumber = 0;
	private String timestamp = "";

	public Punch() {
		;
	}

	public Punch(int checkpointNumber, String timestamp) {
		this.checkpointNumber = checkpointNumber;
		this.timestamp = timestamp;
	}

	public int getCheckpointNumber() {
		return checkpointNumber;
	}

	public void setCheckpointNumber(int checkpointNumber) {
		this.checkpointNumber = checkpointNumber;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return checkpointNumber + ":" + timestamp;
	}
}
