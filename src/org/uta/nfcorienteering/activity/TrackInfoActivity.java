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

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class TrackInfoActivity extends Activity {

	Track track;
	OrienteeringEvent event;
	
	//Declare the used UI -components.
	TextView eventName;
	ImageButton selectOtherTrack;
	ImageButton selectThisTrack;
	
	TextView trackDistance;
	TextView trackDifficulty;
	TextView trackAvailableFrom;
	TextView trackAvailableTo;
	
	/*Declare the ImageView -element which is placed inside the LinearLayout-component
	*the UI. Track's map could be placed as a smaller scaled image inside this
	*in the track preview so that the user could determine if the track is suitable for him/her.
	**/
	ImageView mapImage;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_track_info);
		
		
		//set the buttons.
		selectOtherTrack = (ImageButton)findViewById(R.id.selectOtherButton);
		selectThisTrack = (ImageButton)findViewById(R.id.selectThisButton);
		
		event = (OrienteeringEvent)getIntent().getSerializableExtra("TRACK_INFO");
		track = event.getSelectedTrack();
		
		//Here should be all the info relating the track.
		eventName = (TextView)findViewById(R.id.eventNameText);
		eventName.setText(event.getEventName());
		
		trackDistance = (TextView)findViewById(R.id.trackLengthText);
		trackDistance.setText(track.getDistance());
		
		trackDifficulty = (TextView)findViewById(R.id.trackDifficultyText);
		
		trackAvailableFrom = (TextView)findViewById(R.id.availableFromText);
		trackAvailableFrom.setText(event.getStartingTime());
		
		trackAvailableTo = (TextView)findViewById(R.id.availableToText);

		
		//Set up the image in AsyncTask.
		mapImage = (ImageView)findViewById(R.id.mapImage);
		new MapImageDownloader().execute();
		

		
		
		
		
	}
	
	//OnClick-method for selectThisTrack -button. This launches the ActiveOrienteeringEvent-activity.
	public void startOrienteeringActivity(View v) {
		
		Intent intent = new Intent(this, ActiveOrienteeringEventActivity.class);
		intent.putExtra("TRACK_INFO",(Serializable)event);
		startActivity(intent);
	}
	
	//OnClick-method for selectOtherTrack -button. This goes back to the ReadTrackTag -activity.
	//although the back-button of the phone does the same thing also.
	public void readOtherTag(View v) {
		
		Intent intent = new Intent(this, ReadTrackTagActivity.class);
		startActivity(intent);
	}

	class MapImageDownloader extends AsyncTask<String, Void, Bitmap> {

		@Override
		protected Bitmap doInBackground(String... params) {

			URL mapUrl;
			try {
				mapUrl = new URL(UrlGenerator.mapUrl(track.getMapUrl()));
				return (BitmapFactory.decodeStream(mapUrl.openConnection().getInputStream()));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(Bitmap image) {
			if(image != null){
				mapImage.setImageBitmap(image);
			}
		
			
		}

	}

	
	
}
