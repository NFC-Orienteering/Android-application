package org.uta.nfcorienteering.http;

public class UrlGenerator {

	static final String domain = "http://ec2-54-69-118-107.us-west-2.compute.amazonaws.com";
	static final String event = "events";
	static final String track = "tracks";
	static final String postfix = ".json";

	public static String eventListURL() {
		return domain + "/" + event + postfix;
	}

	public static String eventURL(int eventNum) {
		return domain + "/" + event + "/" + eventNum + postfix;
	}

	public static String trackListURL(int eventNum) {
		return domain + "/" + event + "/" + eventNum + "/" + track + postfix;

	}

	public static String trackUrl(int eventNumber, int trackNumber) {
		return domain + "/" + event + "/" + eventNumber + "/" + track + "/"
				+ trackNumber+postfix;
	}

	public static String exampleJsonUrl() {
		return "http://ec2-54-69-118-107.us-west-2.compute.amazonaws.com/events.json";
	}
}
