package org.uta.nfcorienteering.test;

import org.uta.nfcorienteering.http.UrlGenerator;

import junit.framework.TestCase;

public class TestUrlGenerator extends TestCase {
	
	public void testEventListURL(){
		assertEquals("example.org/events", UrlGenerator.eventListURL());
	}
	
	public void testTrackUrl(){
		assertEquals("example.org/events/1/tracks/1", UrlGenerator.trackUrl(1, 1));
	}
}
