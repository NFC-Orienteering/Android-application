package org.uta.nfcorienteering.event;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

public class Punch implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7861937422589577083L;
	private int checkpointNumber = 0;
	private long totalTimestampMillis = 0;
	private long splitTimeMillis = 0;
	private String tagId = "";

	public Punch() {
		
	}

	public Punch(int checkpointNumber, String tagId, long timestamp, long splitTime) {
		this.checkpointNumber = checkpointNumber;
		this.tagId = tagId;
		this.totalTimestampMillis = timestamp;
		this.splitTimeMillis = splitTime;
	}

	public int getCheckpointNumber() {
		return checkpointNumber;
	}

	public void setCheckpointNumber(int checkpointNumber) {
		this.checkpointNumber = checkpointNumber;
	}

	public String getTagId() {
		return tagId;
	}
	
	public void setTagId(String tagId) {
		this.tagId = tagId;
	}
	
	public long getTotalTimestampMillis() {
		return totalTimestampMillis;
	}

	public void setTotalTimestampMillis(long timestampMillis) {
		this.totalTimestampMillis = timestampMillis;
	}
	
	public long getSplitTimeMillis() {
		return splitTimeMillis;
	}
	
	public void setSplitTimeMillis(long splitTimeMillis) {
		this.splitTimeMillis = splitTimeMillis;
	}
	
	public static String convertMillisToHMmSs(long millis) {
		long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);
	    long s = seconds % 60;
	    long m = (seconds / 60) % 60;
	    long h = (seconds / (60 * 60)) % 24;
	    return String.format("%d:%02d:%02d", h,m,s);
	}
}
