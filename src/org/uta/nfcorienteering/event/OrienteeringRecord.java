package org.uta.nfcorienteering.event;

import java.io.Serializable;
import java.util.ArrayList;

public class OrienteeringRecord implements Serializable {
	private String username = "";
	private ArrayList<Punch> punches = null;
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public ArrayList<Punch> getPunches() {
		return punches;
	}
	public void setPunches(ArrayList<Punch> punches) {
		this.punches = punches;
	}
	
}
