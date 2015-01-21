package org.uta.nfcorienteering.event;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.uta.nfcorienteering.utility.AppConfig;

public class OrienteeringRecord implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8382206012435372165L;

	private Date finishDate;
	private String nickname = "";
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
		if(null != finishDate) {
			SimpleDateFormat sdf = 
					new SimpleDateFormat(AppConfig.DATE_FORMAT, Locale.US);
			return sdf.format(finishDate);
		}
		return "";
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}
	
	public boolean getRecordComplete() {
		return recordComplete;
	}
	
	public void setRecordComplete(boolean recordComplete) {
		this.recordComplete = recordComplete;
	}

}
