package org.uta.nfcorienteering.utility;

public class AppConfig {
	// set to false if the app is running on an emulator
	public static final boolean IS_REAL_DEVICE = true;

	// standard date format for the app (SimpleDateFormat)
	public static final String DATE_FORMAT = "dd.MM.yyyy HH:mm:ss";
	
	// defines the current server api
	public static final String API_PATH = "/api/v1";
	// defines the server URL
	public static final String SERVER_URL = "http://nfc-orienteering.sis.uta.fi";

	// just combines URL and api
	public static final String DOMAIN = SERVER_URL + API_PATH;
}
