package org.uta.nfcorienteering.activity;


import org.uta.nfcorienteering.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class TrackInfoActivity extends Activity {

	//Declare the used UI -components.
	TextView eventName;
	Button selectOtherTrack;
	Button selectThisTrack;
	
	TextView trackLength;
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
		selectOtherTrack = (Button)findViewById(R.id.selectOtherButton);
		selectThisTrack = (Button)findViewById(R.id.selectThisButton);
		
		eventName = (TextView)findViewById(R.id.eventNameText);
		/* The event name should be set up here as the information has been received from web server.
		 * eventName.setText....
		 */
		
		trackLength = (TextView)findViewById(R.id.trackLengthText);
		trackDifficulty = (TextView)findViewById(R.id.trackDifficultyText);
		trackAvailableFrom = (TextView)findViewById(R.id.availableFromText);
		trackAvailableTo = (TextView)findViewById(R.id.availableToText);
		
		/*
		 * Information on these TextViews should be set up here as the information has been received from web server.
		 */
		
		mapImage = (ImageView)findViewById(R.id.mapImage);
		
		/*
		 * The (possible) image of the track's map should be set up here. You have to possibly set the image's width and height
		 * here manually so that it scales properly on the screen.
		 */
		
		
		
		
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
