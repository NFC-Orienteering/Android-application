package org.uta.nfcorienteering.activity;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.uta.nfcorienteering.event.OrienteeringEvent;
import org.uta.nfcorienteering.http.JsonBuilder;
import org.uta.nfcorienteering.http.JsonResolver;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

public class EventHistoryActivity extends Activity {
	private static SharedPreferences eventHistory = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	private void writeHistory(ArrayList<OrienteeringEvent> events) {
		SharedPreferences localHistory = getSharedPreferences("localHistory", 0);

		JsonBuilder builder = new JsonBuilder();
		ArrayList<JSONObject> jsonObjects = new ArrayList<JSONObject>();

		for (OrienteeringEvent orienteeringEvent : events) {
			JSONObject jsonObject = builder
					.recordToJsonObject(orienteeringEvent);
			jsonObjects.add(jsonObject);
		}

		JSONArray array = builder.jsonObjectsToArray(jsonObjects);
		String result = array.toString();

		SharedPreferences.Editor editor = localHistory.edit();
		editor.putString("orienteeringRecords", result);
		editor.commit();
	}

	

	private ArrayList<OrienteeringEvent> readHistory() {
		JsonResolver resolver = new JsonResolver();
		SharedPreferences localHistory = getSharedPreferences("localHistory", 0);

		String history = localHistory.getString("localHistory", "");
		if ("".equals(history)) {
			return null;
		}
		
		return resolver.jsonArrayToList(history);

	}

}
