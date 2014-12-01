package org.uta.nfcorienteering.http;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.uta.nfcorienteering.event.OrienteeringRecord;
import org.uta.nfcorienteering.event.Punch;
import org.uta.nfcorienteering.event.Track;


public class JsonBuilder {

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
		JSONArray jsonArray = new JSONArray();
		
		
		for (Punch punch : punches) {
			JSONObject punchJson = punchObjectToJson(punch);
			jsonArray.put(punchJson);
		}
		
		try {
			jsonObject.put("nickname", nickName);
			jsonObject.put("total_time", totalTime);
			jsonObject.put("complete", completed);
			jsonObject.put("control_points", jsonArray);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return jsonObject;
	}
	
	private JSONObject punchObjectToJson(Punch punch){
		JSONObject jsonObject = new JSONObject();
		
		try {
			jsonObject.put("tag_id", punch.getCheckpointNumber());
			jsonObject.put("time", punch.getSplitTimeMillis());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return jsonObject;
	}
}
