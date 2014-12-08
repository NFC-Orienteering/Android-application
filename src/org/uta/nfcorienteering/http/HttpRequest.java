package org.uta.nfcorienteering.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;

import android.util.Log;

public class HttpRequest {

	private static final String TAG = "Http-request";
	
	public static String tryHttpGet(String url) {
		String webPage = "";

		try {
			webPage = readContentFromGet(url);
		} catch (Exception e) {
			Log.i(TAG, "" + e);
		}

		return webPage;
	}

	private static String readContentFromGet(String szURL) throws IOException {
		HttpURLConnection connection = null;
		String result = "";
		BufferedReader reader = null;

		boolean retry = true;
		while (retry) {
			try {
				URL url = new URL(szURL);
				connection = (HttpURLConnection) url.openConnection();
				connection.setConnectTimeout(5000);
				connection.setReadTimeout(5000);

				connection.connect();
				reader = new BufferedReader(new InputStreamReader(
						connection.getInputStream()));

				if (connection.getResponseCode() != 200) {
					continue;
				}

				String lines;
				while ((lines = reader.readLine()) != null) {
					result += lines + "\n";
				}
				retry = false;

			} catch (SocketTimeoutException e) {
				Log.e(TAG, "" + e);
				retry = true;
			} catch (UnknownHostException e) {
				Log.i(TAG, "" + e);
				retry = true;
			}
		}
		reader.close();
		connection.disconnect();

		return result;
	}

	public static String tryHttpPost(String szUrl, String content) {
		String result = "";
		try {
			String urlParameters = content;
			URL url = new URL(szUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.addRequestProperty("Content-Type", "application/json");
			conn.setDoOutput(true);

			OutputStreamWriter writer = new OutputStreamWriter(
					conn.getOutputStream());

			writer.write(urlParameters);
			writer.flush();

			String line;
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));

			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
			writer.close();
			reader.close();
			
			if ("".equals(result) && conn.getResponseCode() == 201){
				result = "201 "+ conn.getResponseMessage();
			}
		} catch (Exception e) {
			Log.i(TAG, "" + e);
			result = "";
		}

		return result;
	}
}
