package org.uta.nfcorienteering.http;

import org.uta.nfcorienteering.utility.AppConfig;

public class UrlGenerator {

	static final String EVENT = "events";
	static final String TRACK = "tracks";
	static final String RESULT = "result";
	static final String SEARCH = "search";
	static final String POSTFIX = ".json";

	private UrlGenerator() {
		
	}
	
	public static String eventListURL() {
		return AppConfig.DOMAIN + "/" + EVENT + POSTFIX;
	}

	public static String eventURL(int eventNum) {
		return AppConfig.DOMAIN + "/" + EVENT + "/" + eventNum;
	}

	public static String trackListURL(int eventNum) {
		return AppConfig.DOMAIN + "/" + EVENT + "/" + eventNum + "/" + TRACK;

	}

	public static String trackUrl(int trackID) {
		return AppConfig.DOMAIN + "/" + TRACK + "/" + trackID;
	}

	public static String uploadResultUrl(String trackNumber) {
		return AppConfig.DOMAIN + "/" + TRACK + "/" + trackNumber + "/" + RESULT;
	}

	public static String searchTrackUrl(String params) {
		return AppConfig.DOMAIN + "/" + TRACK + "/" + SEARCH + "/" + params;
	}

}
