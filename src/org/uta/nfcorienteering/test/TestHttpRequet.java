package org.uta.nfcorienteering.test;

import java.util.ArrayList;

import junit.framework.TestCase;

import org.uta.nfcorienteering.event.OrienteeringEvent;
import org.uta.nfcorienteering.event.OrienteeringRecord;
import org.uta.nfcorienteering.event.Punch;
import org.uta.nfcorienteering.event.Track;
import org.uta.nfcorienteering.http.HttpRequest;
import org.uta.nfcorienteering.http.JsonBuilder;
import org.uta.nfcorienteering.http.UrlGenerator;

public class TestHttpRequet extends TestCase {
	String getUrl = "http://www.google.com";

	public void testHttpGet() {
		String content = "";
		content = HttpRequest.tryHttpGet(getUrl);
		assertFalse((content == ""));
	}

	public void testHttpPostNormal() {
		String postUrl = UrlGenerator.uploadResultUrl("3");
		String postContent = "";
		String result = "";
		
		Track track = initData();
		JsonBuilder builder = new JsonBuilder();
		postContent = builder.recordToJson(track);
		
		result = HttpRequest.tryHttpPost(postUrl, postContent);
		
		assertTrue((result == "201 created"));
	}

	private Track initData() {
		Track track = new Track();
		OrienteeringRecord record = new OrienteeringRecord();
		ArrayList<Punch> punches = new ArrayList<Punch>();
		OrienteeringEvent event = new OrienteeringEvent();
		
		Punch p1, p2;
		p1 = new Punch(1, 100, 100);
		p2 = new Punch(2, 200, 100);
		
		punches.add(p1);
		punches.add(p2);
		
		record.setPunches(punches);
		record.setNickname("nick name");
		
		event.setRecord(record);
		track.setParentEvent(event);
		
		
		return track;

	}
}
