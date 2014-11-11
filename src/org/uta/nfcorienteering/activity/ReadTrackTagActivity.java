package org.uta.nfcorienteering.activity;

import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;

import org.uta.nfcorienteering.R;
import org.uta.nfcorienteering.event.OrienteeringEvent;
import org.uta.nfcorienteering.event.Track;
import org.uta.nfcorienteering.http.HttpRequest;
import org.uta.nfcorienteering.http.JsonResolver;
import org.uta.nfcorienteering.http.UrlGenerator;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class ReadTrackTagActivity extends BaseNfcActivity {

	
	final Context context = this;
	TextView tagId;
	Button nextButton;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_read_track_tag);
		
		nextButton = (Button) findViewById(R.id.readTagButton);
		tagId = (TextView)findViewById(R.id.tagId);
		

		
	}
	
	//This method is here only temporarily just to move to the next Activity via Next-button.
	public void showTrackInfo(View v){
		
		//Intent intent = new Intent(this, TrackInfoActivity.class);
		//startActivity(intent);
		postNfcRead("button");
			
	}


	@Override
	public void postNfcRead(String result) {
		tagId.setText(result);
		String trackId = result;	
		boolean trackIdFound = true;
		
		Vibrator v = (Vibrator) this.context.getSystemService(Context.VIBRATOR_SERVICE);
		 // Vibrate for 500 milliseconds
		 v.vibrate(500);
		
		//Received trackID, result, should be queried to server if there is such event available.
		if (trackIdFound){
			Toast.makeText(this, "Track found, downloading track data. Please wait", Toast.LENGTH_LONG).show();
			nextButton.setEnabled(false);
			new EventDataDownloader().execute(trackId);
		}
		else {
			Toast.makeText(this, "Track could not be found with the ID read from the Tag. Please try reading " +
		             "another Tag.", Toast.LENGTH_LONG).show();			
		}
	}
	
	public  void startTrackInfoActivity(OrienteeringEvent event){
			
		Intent intent = new Intent(this, TrackInfoActivity.class);
		intent.putExtra("TRACK_INFO",(Serializable)event);
		startActivity(intent);

		
	}
	
	class EventDataDownloader extends AsyncTask<String, Void, OrienteeringEvent> {

		@Override
		protected OrienteeringEvent doInBackground(String... params) {

			String eventUrl = UrlGenerator.exampleJsonUrl();
			String eventJson = HttpRequest.tryHttpGet(eventUrl);
			OrienteeringEvent event = JsonResolver.resloveExampleJson(eventJson);
			
			String trackUrl = UrlGenerator.trackUrl(1, 1);
			String trackJson = HttpRequest.tryHttpGet(trackUrl);
			Track track = JsonResolver.resolveTrackJson(trackJson);
			
			event.setSelectedTrack(track);

			return event;
		}
		
		@Override
		protected void onPostExecute(OrienteeringEvent event) {
			startTrackInfoActivity(event);
		}

	}
	
}
	

	