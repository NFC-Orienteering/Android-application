package org.uta.nfcorienteering.utility;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Base64;
import android.util.Log;

public class LocalStorage {

	private static final String TAG = "LocalStorage";
	
	Context context;
	static SharedPreferences sp;

	public LocalStorage(Context context) {
		this.context = context;
	}

	private void getSharedPreference() {
		if (sp == null) {
			sp = context.getSharedPreferences("reuslt_history", 0);
		}
	}

	public void saveToSharedPreference(String name, Object value) {
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

	public Object readFromSharedPreference(String name) {
		getSharedPreference();
		
		String szBase64 = sp.getString(name, "");
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
