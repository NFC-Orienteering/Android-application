package org.uta.nfcorienteering.event;

import java.io.Serializable;
import java.util.List;

public class OrienteeringRecord implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8382206012435372165L;

	private String nickname = "";
	private String finishDate = "";
	private List<Punch> punches = null;
	private boolean recordComplete = false;

	public OrienteeringRecord(){
		
	}
	
	public long getTotalTime(){
		long maxTime = 0;
		if (punches == null) {
			return maxTime;
		}
		
		for (Punch punch : punches) {
			long current = punch.getTotalTimestampMillis();
			if ( current > maxTime) {
				maxTime = current;
			}
		}
		
		return maxTime;
	}
	
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public List<Punch> getPunches() {
		return punches;
	}

	public void setPunches(List<Punch> punches) {
		this.punches = punches;
	}

	public String getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(String finishDate) {
		this.finishDate = finishDate;
	}
	
	public boolean getRecordComplete() {
		return recordComplete;
	}
	
	public void setRecordComplete(boolean recordComplete) {
		this.recordComplete = recordComplete;
	}

}
