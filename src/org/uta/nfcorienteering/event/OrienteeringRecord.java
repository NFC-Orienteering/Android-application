package org.uta.nfcorienteering.event;

import java.io.Serializable;
import java.util.ArrayList;

public class OrienteeringRecord implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8382206012435372165L;

	private String nickname = "";
	private String finishDate = "";
	private ArrayList<Punch> punches = null;
	private boolean recordComplete = false;

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public ArrayList<Punch> getPunches() {
		return punches;
	}

	public void setPunches(ArrayList<Punch> punches) {
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
