package org.uta.nfcorienteering.activity;

import com.example.nfcorienteering.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class ReadTrackTagActivity extends Activity {

	Button nextButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_read_track_tag);
		
		nextButton = (Button) findViewById(R.id.readTagButton);
		
		/*
		 *Here should be implemented the method call for setting the  
		 * NFC-reader on and waiting for the tag to be read. After the tag has
		 * been successfully read the application should move to TrackInfo -activity.
		 * The information of the track have to be transmitted to TrackInfo -activity
		 * via the intent. Should be figured out later when the NFC-reading works.
		 */
		
		
	}

	public void showTrackInfo(View v){
		
		Intent intent = new Intent(this, TrackInfoActivity.class);
		startActivity(intent);
		
		
	}



}
