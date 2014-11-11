package org.uta.nfcorienteering.http;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.uta.nfcorienteering.event.Checkpoint;
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
		String mapUrl = "";
		ArrayList<Checkpoint> controlPoints = new ArrayList<Checkpoint>();
		
		try {
			JSONObject jsonObject = new JSONObject(json);
			
			JSONArray controlPointsJson = jsonObject.getJSONArray("control_points");
			for(int i = 0; i < controlPointsJson.length(); i++) {
				JSONObject c = controlPointsJson.getJSONObject(i);
				
				int trackNumber = Integer.parseInt(c.getString("id"));
				String tagId = c.getString("tag_id");
				
				Checkpoint cp = new Checkpoint(trackNumber, tagId);
				controlPoints.add(cp);
				
			}
			
			mapUrl = jsonObject.getString("image");
			id = jsonObject.getInt("id");
			name = jsonObject.getString("name");
			distance = jsonObject.getString("distance");
		} catch (JSONException e) {
			e.printStackTrace();
			return track;
		}
		
		track.setMapUrl(mapUrl);
		track.setTrackName(name);
		track.setTrackNumber(id);
		track.setDistance(distance);
		track.setCheckpoints(controlPoints);
		return track;
	}

}
