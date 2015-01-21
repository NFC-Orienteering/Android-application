package org.uta.nfcorienteering.http;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.uta.nfcorienteering.event.Checkpoint;
import org.uta.nfcorienteering.event.OrienteeringEvent;
import org.uta.nfcorienteering.event.Track;

import android.util.Log;

public class JsonResolver {
	
	private static final String TAG = "JsonResolver";
	
	public static OrienteeringEvent resloveExampleJson(String json) {
		OrienteeringEvent event = new OrienteeringEvent();
		int id = 0;
		String name = "";
		String startingTime = "";
		try {
			JSONArray jsonArray = new JSONArray(json);
			JSONObject jsonObject = jsonArray.getJSONObject(0);

			id = jsonObject.getInt("id");
			name = jsonObject.getString("name");
			startingTime = jsonObject.getString("start_date");
		} catch (JSONException e) {
			Log.e(TAG, "" + e);
			return event;
		}

		event.setEventID(id);
		event.setEventName(name);
		event.setStartingTime(startingTime);

		return event;
	}


	public static JSONObject StringToJsonObject(String json) {
		JSONObject trackJson = null;
		try {
			trackJson = new JSONObject(json);
		} catch (Exception e) {
			Log.e("Json resolver", "create json object from string failed");
		}
		return trackJson;
	}

	public static Track resolveTrackJson(String trackJson) {
		Track track = new Track();
		int id = 0;
		String name = "";
		String distance = "";
		String mapUrl = "";
		OrienteeringEvent event = null;

		List<Checkpoint> controlPoints = new ArrayList<Checkpoint>();

		try {
			JSONObject jsonObject = new JSONObject(trackJson);

			JSONArray controlPointsJson = jsonObject
					.getJSONArray("control_points");
			for (int i = 0; i < controlPointsJson.length(); i++) {
				JSONObject controlPointJson = controlPointsJson
						.getJSONObject(i);

				int controlPointNumber = Integer.parseInt(controlPointJson
						.getString("id"));
				String tagId = controlPointJson.getString("tag_id");

				Checkpoint cp = new Checkpoint(controlPointNumber, tagId);
				controlPoints.add(cp);
			}

			mapUrl = jsonObject.getString("image");
			id = jsonObject.getInt("id");
			name = jsonObject.getString("name");
			distance = jsonObject.getString("distance");
			
			JSONObject eventJson =jsonObject.getJSONObject("event");
			event = resolveEventJson(eventJson);

		} catch (JSONException e) {
			Log.i(TAG, "" + e);
			return track;
		}

		track.setTrackName(name);
		track.setTrackNumber(id);
		track.setDistance(distance);
		track.setCheckpoints(controlPoints);
		track.setParentEvent(event);

		if (!mapUrl.contains("http")) {
			mapUrl = "http://nfc-orienteering.sis.uta.fi" + mapUrl;
		}
		track.setMapUrl(mapUrl);

		return track;
	}

	private static OrienteeringEvent resolveEventJson(JSONObject eventJson) {
		OrienteeringEvent event = new OrienteeringEvent();

		int eventID = -1;
		String name = "";
		String location = "";
		String startDate = "";
		String endDate = "";

		try {
			eventID = eventJson.getInt("id");
			name = eventJson.getString("name");
			location = eventJson.getString("location");
			startDate = resolveDateAndTime(eventJson.getString("start_date"));
			endDate = resolveDateAndTime(eventJson.getString("end_date"));
		} catch (JSONException e) {
			Log.e(TAG, "" + e);
		}

		event.setEventID(eventID);
		event.setEventName(name);
		event.setLocation(location);
		event.setStartingTime(startDate);
		event.setEndingTime(endDate);

		return event;
	}

	private static String resolveDateAndTime(String dateAndTime) {
		String[] temp1 = dateAndTime.split("T");
		String[] temp2 = temp1[1].split("\\.");

		String date = temp1[0];
		String time = temp2[0];

		return date + " " + time;

	}
}
