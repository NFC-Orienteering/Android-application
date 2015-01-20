package org.uta.nfcorienteering.test;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.uta.nfcorienteering.event.OrienteeringEvent;
import org.uta.nfcorienteering.event.OrienteeringRecord;
import org.uta.nfcorienteering.event.Punch;
import org.uta.nfcorienteering.event.Track;
import org.uta.nfcorienteering.http.JsonBuilder;

import android.test.AndroidTestCase;
import android.util.Log;

public class TestJsonBuilder extends AndroidTestCase {
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
		p1 = new Punch(32, "123", 100, 100);
		p2 = new Punch(21, "231", 150, 50);
		p3 = new Punch(43, "221",  210, 60);
		punches.add(p1);
		punches.add(p2);
		punches.add(p3);

		track.setParentEvent(event);

		orienteeringRecord.setPunches(punches);
		orienteeringRecord.setNickname("Nick Name");

		track.setTrackNumber(2123);

		event.setRecord(orienteeringRecord);

		JsonBuilder builder = new JsonBuilder();
		String result = builder.recordToJson(track);

		try {
			jsonObject = new JSONObject(result);
		} catch (JSONException e) {
			e.printStackTrace();
		}

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

	public Object tryGetJsonValue(String name) {
		Object object = null;
		try {
			object = jsonObject.get(name);
		} catch (Exception e) {
			Log.e("TestJsonBuilder", "Unable to retrieve JsonValue");
		}

		return object;
	}
}
