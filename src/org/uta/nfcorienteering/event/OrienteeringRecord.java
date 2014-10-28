package org.uta.nfcorienteering.event;

import java.io.Serializable;
import java.util.ArrayList;

public class OrienteeringRecord implements Serializable {
	private String nickname = "";
	private ArrayList<Punch> punches = null;
	
	
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
	
}
