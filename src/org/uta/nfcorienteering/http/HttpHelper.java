package org.uta.nfcorienteering.http;

import org.uta.nfcorienteering.event.OrienteeringEvent;
import org.uta.nfcorienteering.event.OrienteeringRecord;

public class HttpHelper {

	public OrienteeringEvent getTrack(int eventNumber, int trackNumber){
		String url = UrlGenerator.trackUrl(eventNumber, trackNumber);
		String response = HttpRequest.tryHttpGet(url);
		
		OrienteeringEvent event = JsonResolver.resloveExampleJson(response);
		return event;
	}
	
	public boolean pushRecord(OrienteeringRecord record){
		return false;
	}
}
