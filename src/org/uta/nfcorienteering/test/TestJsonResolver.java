package org.uta.nfcorienteering.test;

import junit.framework.TestCase;

import org.uta.nfcorienteering.event.Track;
import org.uta.nfcorienteering.http.HttpRequest;
import org.uta.nfcorienteering.http.JsonResolver;
import org.uta.nfcorienteering.http.UrlGenerator;

public class TestJsonResolver extends TestCase {
	
	
/*	public void testResolveExampleJson(){
		String json = HttpRequest.tryHttpGet(UrlGenerator.exampleJsonUrl());
		OrienteeringEvent event = JsonResolver.resloveExampleJson(json);
		assertEquals("Orienting event", event.getEventName());
		assertEquals(1, event.getEventID());
		assertEquals("2014-10-12", event.getStartingTime());
	}
	*/
	public void  testResolveTrack(){
		String url = UrlGenerator.trackUrl(1, 1);
		String json = HttpRequest.tryHttpGet(url);
		Track track = JsonResolver.resolveTrackJson(json);
		
		assertEquals(1, track.getTrackNumber());
		assertEquals("5 Km.", track.getDistance());
		assertEquals("Track 1", track.getTrackName());
	}
	
}
