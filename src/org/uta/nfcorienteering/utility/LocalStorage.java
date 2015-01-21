package org.uta.nfcorienteering.utility;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.List;

import org.uta.nfcorienteering.event.Track;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Base64;
import android.util.Log;

public class LocalStorage {

	private static final String TAG = "LocalStorage";
	private static final String ORIENTEERING_HISTORY_KEY = "result_history";
	
	private Context context;
	private static SharedPreferences sp;

	public LocalStorage(Context context) {
		this.context = context;
	}

	
	public void saveOrienteeringHistory(List<Track> history) {
		saveToSharedPreference(ORIENTEERING_HISTORY_KEY, history);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Track> readOrienteeringHistory() {
		return (List<Track>) readFromSharedPreference(ORIENTEERING_HISTORY_KEY);
	}
	

	public void removeOrienteeringTrackFromHistory(Track track) {
		List<Track> tracks = readOrienteeringHistory();
		tracks.remove(track);
		saveOrienteeringHistory(tracks);
	}
	
	
	private void getSharedPreference() {
		if (sp == null) {
			sp = context.getSharedPreferences(ORIENTEERING_HISTORY_KEY, 0);
		}
	}

	
	private void saveToSharedPreference(String name, Object value) {
		getSharedPreference();
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(value);
			
			String base64 = new String(Base64.encodeToString(
					baos.toByteArray(), Base64.DEFAULT));
			
			Editor editor = sp.edit();
			editor.putString(name, base64);
			editor.commit();
		} catch (IOException e) {
			Log.i(TAG, "" + e);
		}
	}

	private Object readFromSharedPreference(String key) {
		getSharedPreference();
		
		String szBase64 = sp.getString(key, "");
		Object object = null;
		
		if (szBase64 == null) {
			return object;
		}

		byte[] base64 = Base64.decode(szBase64, Base64.DEFAULT);
		ByteArrayInputStream bais = new ByteArrayInputStream(base64);
		try {
			ObjectInputStream bis = new ObjectInputStream(bais);
			try {
				object = bis.readObject();
			} catch (ClassNotFoundException e) {
				Log.i(TAG, "" + e);
			}
		} catch (StreamCorruptedException e) {
			Log.i(TAG, "" + e);
		} catch (IOException e) {
			Log.i(TAG, "" + e);
		}

		return object;
	}
}
