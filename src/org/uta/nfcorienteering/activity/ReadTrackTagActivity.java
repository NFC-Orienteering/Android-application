package org.uta.nfcorienteering.activity;

import java.io.Serializable;

import org.uta.nfcorienteering.R;
import org.uta.nfcorienteering.event.OrienteeringEvent;
import org.uta.nfcorienteering.event.Track;
import org.uta.nfcorienteering.http.HttpRequest;
import org.uta.nfcorienteering.http.JsonResolver;
import org.uta.nfcorienteering.http.UrlGenerator;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class ReadTrackTagActivity extends BaseNfcActivity {

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
		
		Intent intent = new Intent(this, TrackInfoActivity.class);
		startActivity(intent);

			
	}


	@Override
	public void postNfcRead(String result) {
		tagId.setText(result);
		String trackId = result;	
		boolean trackIdFound = false;
		
		//Received trackID, result, should be queried to server if there is such event available.
		if (trackIdFound){
			new EventDataDownloader().execute(trackId);
		}
		else {
			Toast.makeText(this, "Track could not be found with the ID read from the Tag. Please try reading " +
		             "another Tag.", Toast.LENGTH_LONG).show();			
		}
	}
	
	public  void startTrackInfoActivity(OrienteeringEvent event){
			
		Intent intent = new Intent(this, TrackInfoActivity.class);
		startActivity(intent);

		
	}
	
	class EventDataDownloader extends AsyncTask<String, Void, OrienteeringEvent> {

		@Override
		protected OrienteeringEvent doInBackground(String... params) {


			return null;
		}
		
		@Override
		protected void onPostExecute(OrienteeringEvent event) {
			startTrackInfoActivity(event);
		}

	}
	
}
	

	