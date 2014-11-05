package org.uta.nfcorienteering.event;

public class Checkpoint {
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
