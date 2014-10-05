package com.example.nfcorienteering;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	Button startEventButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Set the startEvent -button.
		startEventButton = (Button)findViewById(R.id.start_button);

	}
	
	//On-Click method for StartEventButton. This launches the ReadTrackTag -activity.
	public void selectTrackNFC(View v){
	
		Intent intent = new Intent(this, ReadTrackTag.class);
		startActivity(intent);
		
	}

}
