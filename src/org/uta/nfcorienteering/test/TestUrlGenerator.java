package org.uta.nfcorienteering.test;

import junit.framework.TestCase;

import org.uta.nfcorienteering.http.UrlGenerator;

public class TestUrlGenerator extends TestCase {

	public void testEventURL() {
		assertEquals(
				"http://nfc-orienteering.sis.uta.fi/api/v1/events/1",
				UrlGenerator.eventURL(1));
	}

	public void testTrackUrl() {
		assertEquals(
				"http://nfc-orienteering.sis.uta.fi/api/v1/tracks/1",
				UrlGenerator.trackUrl(1));
	}

	public void testUploadResultUrl() {
		assertEquals(
				"http://nfc-orienteering.sis.uta.fi/api/v1/tracks/results",
				UrlGenerator.uploadResultUrl());

	}
}
