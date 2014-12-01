package org.uta.nfcorienteering.http;

public class UrlGenerator {

	static final String domain = "http://nfc-orienteering.sis.uta.fi/api/v1";
	static final String event = "events";
	static final String track = "tracks";
	static final String result = "results";
	static final String search = "search";
	static final String postfix = ".json";

	public static String eventListURL() {
		return domain + "/" + event + postfix;
	}

	public static String eventURL(int eventNum) {
		return domain + "/" + event + "/" + eventNum;
	}

	public static String trackListURL(int eventNum) {
		return domain + "/" + event + "/" + eventNum + "/" + track;

	}

	public static String trackUrl(int trackID) {
		return domain + "/" + track + "/" + trackID;
	}

	public static String uploadResultUrl(String trackNumber) {
		return domain + "/" + track + "/" + trackNumber + "/" + result;
	}

	public static String searchTrackUrl(String params) {
		return domain + "/" + track + "/" + search + "/" + params;
	}

}
