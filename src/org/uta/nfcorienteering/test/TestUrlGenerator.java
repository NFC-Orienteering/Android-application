package org.uta.nfcorienteering.test;

import org.uta.nfcorienteering.http.UrlGenerator;

import junit.framework.TestCase;

public class TestUrlGenerator extends TestCase {

	public void testEventListURL() {
		assertEquals(
				"http://ec2-54-69-118-107.us-west-2.compute.amazonaws.com/events.json",
				UrlGenerator.eventListURL());
	}

	public void testEventURL() {
		assertEquals(
				"http://ec2-54-69-118-107.us-west-2.compute.amazonaws.com/events/1.json",
				UrlGenerator.eventURL(1));
	}

	public void testTrackListUrl() {
		assertEquals(
				"http://ec2-54-69-118-107.us-west-2.compute.amazonaws.com/events/1/tracks.json",
				UrlGenerator.trackListURL(1));
	}

	public void testTrackUrl() {
		assertEquals(
				"http://ec2-54-69-118-107.us-west-2.compute.amazonaws.com/events/1/tracks/1.json",
				UrlGenerator.trackUrl(1, 1));

	}
}
