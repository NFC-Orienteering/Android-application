package org.uta.nfcorienteering.http;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.uta.nfcorienteering.event.OrienteeringEvent;
import org.uta.nfcorienteering.event.Track;

public class JsonResolver {
	public static OrienteeringEvent resloveExampleJson(String json) {
		OrienteeringEvent event = new OrienteeringEvent();
		int id = 0;
		String name = "";
		String startingTime="";
		try {
			JSONArray jsonArray= new JSONArray(json);
			JSONObject jsonObject = jsonArray.getJSONObject(0);
			
			id = jsonObject.getInt("id");
			name = jsonObject.getString("name");
			startingTime = jsonObject.getString("start_date");
		} catch (JSONException e) {
			e.printStackTrace();
			return event;
		}

		event.setEventID(id);
		event.setEventName(name);
		event.setStartingTime(startingTime);

		return event;
	}
	
	public static Track resolveTrackJson(String json){
		Track track = new Track();
		int id = 0;
		String name = "";
		String distance = "";
		
		try {
			JSONObject jsonObject = new JSONObject(json);
			
			id = jsonObject.getInt("id");
			name = jsonObject.getString("name");
			distance = jsonObject.getString("distance");
		} catch (JSONException e) {
			e.printStackTrace();
			return track;
		}
		
		track.setTrackName(name);
		track.setTrackNumber(id);
		track.setDistance(distance);
		return track;
	}

}
