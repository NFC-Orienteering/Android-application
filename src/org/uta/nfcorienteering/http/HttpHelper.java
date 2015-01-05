package org.uta.nfcorienteering.http;

import org.uta.nfcorienteering.event.OrienteeringRecord;
import org.uta.nfcorienteering.event.Track;
import org.uta.nfcorienteering.utility.DataInstance;

public class HttpHelper {

	public static boolean getTrackAndParentEvent(String infoTagId) {
		
		String response = "";
		response = HttpRequest.tryHttpGet(UrlGenerator.searchTrackUrl(infoTagId));
		
		if("".equals(response)){
			return false;
			
		}else {
			Track track = JsonResolver.resolveTrackJson(response);

			DataInstance dataInstance = DataInstance.getInstace();
			dataInstance.setTrack(track);
			dataInstance.setEvent(track.getParentEvent());
			return true;	
		}
	}

	public boolean pushRecord(OrienteeringRecord record) {
		return false;
	}
	

}
