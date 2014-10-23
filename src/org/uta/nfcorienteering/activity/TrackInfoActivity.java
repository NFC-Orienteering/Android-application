package org.uta.nfcorienteering.activity;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.uta.nfcorienteering.R;
import org.uta.nfcorienteering.event.OrienteeringEvent;
import org.uta.nfcorienteering.event.Track;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


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

		
		/*
		 * The (possible) image of the track's map should be set up here. You have to possibly set the image's width and height
		 * here manually so that it scales properly on the screen.
		 */
		
		/*Bitmap mapImage_val;
		mapImage = (ImageView)findViewById(R.id.mapImage);
		URL mapUrl;
		try {
			mapUrl = new URL(track.getMapUrl());
			mapImage_val = BitmapFactory.decodeStream(mapUrl.openConnection().getInputStream());
			mapImage.setImageBitmap(mapImage_val);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		
		
		
	}
	
	//OnClick-method for selectThisTrack -button. This launches the ActiveOrienteeringEvent-activity.
	public void startOrienteeringActivity(View v) {
		
		Intent intent = new Intent(this, ActiveOrienteeringEventActivity.class);
		startActivity(intent);
	}
	
	//OnClick-method for selectOtherTrack -button. This goes back to the ReadTrackTag -activity.
	//although the back-button of the phone does the same thing also.
	public void readOtherTag(View v) {
		
		Intent intent = new Intent(this, ReadTrackTagActivity.class);
		startActivity(intent);
	}


}
