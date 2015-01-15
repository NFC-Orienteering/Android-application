package org.uta.nfcorienteering.test;

import java.util.List;

import junit.framework.TestCase;

import org.uta.nfcorienteering.event.Checkpoint;
import org.uta.nfcorienteering.event.OrienteeringEvent;
import org.uta.nfcorienteering.event.Track;
import org.uta.nfcorienteering.http.HttpRequest;
import org.uta.nfcorienteering.http.JsonResolver;
import org.uta.nfcorienteering.http.UrlGenerator;

public class TestJsonResolver extends TestCase {

	private Track track = null;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		String url = UrlGenerator.trackUrl(1);
		String json = HttpRequest.tryHttpGet(url);
		track = JsonResolver.resolveTrackJson(json);
	}
	
	public void testResolveTrack() {
		List<Checkpoint> checkpoints = track.getCheckpoints();

		assertEquals(1, track.getTrackNumber());
		assertEquals("5 km", track.getDistance());
		assertEquals("Intermediate", track.getTrackName());

		assertCheckPoint(checkpoints.get(0), 4, "15");
		assertCheckPoint(checkpoints.get(1), 3, "14");
		assertCheckPoint(checkpoints.get(2), 2, "13");
		assertCheckPoint(checkpoints.get(3), 1, "12");
		
		assertEquals("http://nfc-orienteering.sis.uta.fi/system/tracks/images/000/000/001/original/Hellenberge.jpg?1416325614", track.getMapUrl());
	}

	private void assertCheckPoint(Checkpoint checkpoint, int number,
			String tagID) {
		assertEquals(number, checkpoint.getCheckpointNumber());
		assertEquals(tagID, checkpoint.getRfidTag());
	}
	
	public void testResolveEvent(){
		OrienteeringEvent event = track.getParentEvent();
		
		assertEquals(1, event.getEventID());
		assertEquals("Orienteering Tampere",event.getEventName());
		assertEquals("Tampere",event.getLocation());
		assertEquals("2014-11-18 12:00:00", event.getStartingTime());
		assertEquals("2015-11-18 12:00:00", event.getEndingTime());
	}

}
