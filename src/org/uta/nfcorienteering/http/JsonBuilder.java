package org.uta.nfcorienteering.http;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.uta.nfcorienteering.event.OrienteeringEvent;
import org.uta.nfcorienteering.event.OrienteeringRecord;
import org.uta.nfcorienteering.event.Punch;
import org.uta.nfcorienteering.event.Track;

public class JsonBuilder {

	public String recordToJson(OrienteeringEvent event) {

		return recordToJsonObject(event).toString();
	}

	public JSONObject recordToJsonObject(OrienteeringEvent event) {
		Track track = event.getSelectedTrack();
		OrienteeringRecord record = event.getRecord();

		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		for (Punch punch : record.getPunches()) {
			jsonArray.put(punch.toString());
		}
		try {
			jsonObject.put("track_id", track.getTrackNumber());
			jsonObject.put("nick_name", record.getNickname());
			jsonObject.put("results", jsonArray);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return jsonObject;
	}

	public JSONArray jsonObjectsToArray(ArrayList<JSONObject> jsonObjects) {
		JSONArray array = new JSONArray();

		return array;

	}

}
