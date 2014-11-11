package org.uta.nfcorienteering.event;

import java.io.Serializable;

public class Checkpoint implements Serializable{
	private int checkpointNumber = 0;
	private String rfidTag = "";
	
	public Checkpoint(int cpNumber, String tagId) {
		checkpointNumber = cpNumber;
		rfidTag = tagId;
	}

	public int getCheckpointNumber() {
		return checkpointNumber;
	}

	public void setCheckpointNumber(int checkpointNumber) {
		this.checkpointNumber = checkpointNumber;
	}

	public String getRfidTag() {
		return rfidTag;
	}

	public void setRfidTag(String rfidTag) {
		this.rfidTag = rfidTag;
	}

}
