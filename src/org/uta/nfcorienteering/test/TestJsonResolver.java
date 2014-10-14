package org.uta.nfcorienteering.test;

import org.uta.nfcorienteering.event.OrienteeringEvent;
import org.uta.nfcorienteering.http.HttpRequest;
import org.uta.nfcorienteering.http.JsonResolver;
import org.uta.nfcorienteering.http.UrlGenerator;

import junit.framework.TestCase;

public class TestJsonResolver extends TestCase {
	
	public void testResloveTrack(){
		;
	}
	
	public void testResolveExampleJson(){
		String json = HttpRequest.tryHttpGet(UrlGenerator.exampleJsonUrl());
		OrienteeringEvent event = JsonResolver.resloveExampleJson(json);
		assertEquals("Orienting event", event.getEventName());
		assertEquals(1, event.getEventID());
		assertEquals("2014-10-12", event.getStartingTime());
	}
	
	
}
