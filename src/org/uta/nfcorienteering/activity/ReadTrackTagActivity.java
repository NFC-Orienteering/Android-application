package org.uta.nfcorienteering.activity;

import org.uta.nfcorienteering.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


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
	

	public void showTrackInfo(View v){
		
		Intent intent = new Intent(this, TrackInfoActivity.class);
		startActivity(intent);
			
	}


	@Override
	public void postNfcRead(String result) {
		tagId.setText(result);
		
	}
	
}
	