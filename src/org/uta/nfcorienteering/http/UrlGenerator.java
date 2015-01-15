package org.uta.nfcorienteering.http;

public class UrlGenerator {

	static final String DOMAIN = "http://nfc-orienteering.sis.uta.fi/api/v1";
	static final String EVENT = "events";
	static final String TRACK = "tracks";
	static final String RESULT = "result";
	static final String SEARCH = "search";
	static final String POSTFIX = ".json";

	public static String eventListURL() {
		return DOMAIN + "/" + EVENT + POSTFIX;
	}

	public static String eventURL(int eventNum) {
		return DOMAIN + "/" + EVENT + "/" + eventNum;
	}

	public static String trackListURL(int eventNum) {
		return DOMAIN + "/" + EVENT + "/" + eventNum + "/" + TRACK;

	}

	public static String trackUrl(int trackID) {
		return DOMAIN + "/" + TRACK + "/" + trackID;
	}

	public static String uploadResultUrl(String trackNumber) {
		return DOMAIN + "/" + TRACK + "/" + trackNumber + "/" + RESULT;
	}

	public static String searchTrackUrl(String params) {
		return DOMAIN + "/" + TRACK + "/" + SEARCH + "/" + params;
	}

}
