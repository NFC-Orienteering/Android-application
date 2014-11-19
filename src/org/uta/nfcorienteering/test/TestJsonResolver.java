package org.uta.nfcorienteering.test;

import java.util.ArrayList;

import junit.framework.TestCase;

import org.uta.nfcorienteering.event.Checkpoint;
import org.uta.nfcorienteering.event.Track;
import org.uta.nfcorienteering.http.HttpRequest;
import org.uta.nfcorienteering.http.JsonResolver;
import org.uta.nfcorienteering.http.UrlGenerator;

public class TestJsonResolver extends TestCase {

	/*
	 * public void testResolveExampleJson(){ String json =
	 * HttpRequest.tryHttpGet(UrlGenerator.exampleJsonUrl()); OrienteeringEvent
	 * event = JsonResolver.resloveExampleJson(json);
	 * assertEquals("Orienting event", event.getEventName()); assertEquals(1,
	 * event.getEventID()); assertEquals("2014-10-12", event.getStartingTime());
	 * }
	 */
	public void testResolveTrack() {
		String url = UrlGenerator.trackUrl(1, 1);
		String json = HttpRequest.tryHttpGet(url);
		Track track = JsonResolver.resolveTrackJson(json);
		ArrayList<Checkpoint> checkpoints = track.getCheckpoints();

		assertEquals(1, track.getTrackNumber());
		assertEquals("5 km", track.getDistance());
		assertEquals("Intermediate", track.getTrackName());
		
		assertCheckPoint(checkpoints.get(0), 3, "4");
		assertCheckPoint(checkpoints.get(1), 2, "3");
		assertCheckPoint(checkpoints.get(2), 1, "2");
	}

	private void assertCheckPoint(Checkpoint checkpoint, int number,
			String tagID) {
		assertEquals(number, checkpoint.getCheckpointNumber());
		assertEquals(tagID, checkpoint.getRfidTag());
	}

}
