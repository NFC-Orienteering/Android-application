package org.uta.nfcorienteering.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

public class HttpRequest {

	public static String tryHttpGet(String url) {
		String webPage = "";

		try {
			webPage = readContentFromGet(url);
		} catch (Exception e) {
			e.printStackTrace();
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
				e.printStackTrace();
				retry = true;
			} catch (UnknownHostException e) {
				e.printStackTrace();
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
			URLConnection conn = url.openConnection();

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

		} catch (Exception e) {
			e.printStackTrace();
			result = "";
		}

		return result;
	}
}
