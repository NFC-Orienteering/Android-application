package org.uta.nfcorienteering.utility;

import org.uta.nfcorienteering.event.OrienteeringEvent;
import org.uta.nfcorienteering.event.OrienteeringRecord;
import org.uta.nfcorienteering.event.Track;

import android.graphics.Bitmap;

public class DataInstance {
	private static DataInstance instance = null;

	private OrienteeringEvent event = null;
	private Track track = null;
	private OrienteeringRecord record = null;
	private Bitmap mapImage;

	private DataInstance() {

	}

	public static DataInstance getInstace() {
		if (instance == null) {
			instance = new DataInstance();
		}

		return instance;
	}

	public OrienteeringEvent getEvent() {
		return event;
	}

	public void setEvent(OrienteeringEvent event) {
		this.event = event;
	}

	public Track getTrack() {
		return track;
	}

	public void setTrack(Track track) {
		this.track = track;
	}

	public OrienteeringRecord getRecord() {
		return record;
	}

	public void setRecord(OrienteeringRecord record) {
		this.record = record;
	}
	
	public Bitmap getMapImage() {
		return mapImage;
	}
	
	public void setMapImage(Bitmap mapImage) {
		if(this.mapImage != null){
			this.mapImage.recycle();
		}
		this.mapImage = mapImage;
	}
}
