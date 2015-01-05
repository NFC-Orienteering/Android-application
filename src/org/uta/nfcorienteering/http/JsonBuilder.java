package org.uta.nfcorienteering.http;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.uta.nfcorienteering.event.OrienteeringRecord;
import org.uta.nfcorienteering.event.Punch;
import org.uta.nfcorienteering.event.Track;

import android.util.Log;

public class JsonBuilder {

	private static final String TAG = "JsonBuilder";

	public String recordToJson(Track track) {

		return recordToJsonObject(track).toString();
	}

	public JSONObject recordToJsonObject(Track track) {
		OrienteeringRecord record = track.getParentEvent().getRecord();
		ArrayList<Punch> punches = record.getPunches();

		String nickName = record.getNickname();
		long totalTime = record.getTotalTime();
		boolean completed = track.checkComplete();

		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray = punchesToJsonArray(punches);
		
		try {
			jsonObject.put("nickname", nickName);
			jsonObject.put("total_time", totalTime);
			jsonObject.put("complete", completed);
			jsonObject.put("control_points", jsonArray);
		} catch (JSONException e) {
			Log.e(TAG, "" + e);
		}

		return jsonObject;
	}

	private JSONArray punchesToJsonArray(List<Punch> punches) {
		JSONArray jsonArray = new JSONArray();
		if (punches == null
			||punches.isEmpty()) {
			return jsonArray;
		}

		for (Punch punch : punches) {
			JSONObject punchJson = punchObjectToJson(punch);
			jsonArray.put(punchJson);
		}
		
		return jsonArray;
	}

	private JSONObject punchObjectToJson(Punch punch) {
		JSONObject jsonObject = new JSONObject();

		try {
			jsonObject.put("tag_id", punch.getCheckpointNumber());
			jsonObject.put("time", punch.getSplitTimeMillis());
		} catch (Exception e) {
			Log.e(TAG, "" + e);
		}

		return jsonObject;
	}
}
