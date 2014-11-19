package org.uta.nfcorienteering.test;

import java.util.ArrayList;

import junit.framework.TestCase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.uta.nfcorienteering.event.OrienteeringEvent;
import org.uta.nfcorienteering.event.OrienteeringRecord;
import org.uta.nfcorienteering.event.Punch;
import org.uta.nfcorienteering.event.Track;
import org.uta.nfcorienteering.http.JsonBuilder;

public class TestJsonBuilder extends TestCase {
	/*
	 * Example JSON:
	 * 
	 * { "track_id": 2123, "nick_name": "Nick Name", "results": { "32": 00,
	 * "21": 20000, "43": 74223, } }
	 */

	private JSONObject jsonObject = null;
	private JSONArray array = null;

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		OrienteeringEvent event = new OrienteeringEvent();
		OrienteeringRecord orienteeringRecord = new OrienteeringRecord();
		Track track = new Track();
		ArrayList<Punch> punches = new ArrayList<Punch>();

		Punch p1, p2, p3;
		p1 = new Punch(32,"00");
		p2 = new Punch(21,"20000");
		p3 = new Punch(43,"74223");
		punches.add(p1);
		punches.add(p2);
		punches.add(p3);

		orienteeringRecord.setPunches(punches);
		orienteeringRecord.setNickname("Nick Name");

		track.setTrackNumber(2123);

		//event.setSelectedTrack(track);
		event.setRecord(orienteeringRecord);

		JsonBuilder builder = new JsonBuilder();
		String result = builder.recordToJson(event);

		try {
			jsonObject = new JSONObject(result);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void testGetTrackID() {
		int trackID = -1;
		trackID = (Integer) tryGetJsonValue("track_id");
		assertEquals(2123, trackID);
	}

	public void testGetNickname() {
		String nickName = "";
		nickName = (String) tryGetJsonValue("nick_name");
		assertEquals("Nick Name", nickName);
	}

	public void testGetResults() {
		String results = "";
		array = (JSONArray) tryGetJsonValue("results");
		results = array.toString();
		assertFalse("".equals(results));
	}
	
	public void testCheckResults(){
		array = (JSONArray) tryGetJsonValue("results");
		
		String[] punchesValue = {"32:00","21:20000","43:74223"};
		String str = "";
		try {
			for (int i = 0; i < punchesValue.length; i++) {
				String punch = punchesValue[i];
				str = array.getString(i);
				
				assertEquals(punch, str);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public Object tryGetJsonValue(String name) {
		Object object = null;
		try {
			object = jsonObject.get(name);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return object;
	}

}
