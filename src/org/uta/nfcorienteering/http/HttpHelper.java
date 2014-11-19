package org.uta.nfcorienteering.http;

import org.uta.nfcorienteering.event.OrienteeringRecord;
import org.uta.nfcorienteering.event.Track;
import org.uta.nfcorienteering.utility.DataInstance;

public class HttpHelper {

	public static void getTrackAndParentEvent(int trackNumber) {
		String url = UrlGenerator.trackUrl(trackNumber);
		String response = HttpRequest.tryHttpGet(url);

		Track track = JsonResolver.resolveTrackJson(response);

		DataInstance dataInstance = DataInstance.getInstace();
		dataInstance.setTrack(track);
		dataInstance.setEvent(track.getParentEvent());
	}

	public boolean pushRecord(OrienteeringRecord record) {
		return false;
	}
}
