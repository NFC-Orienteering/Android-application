package org.uta.nfcorienteering.activity;

import org.uta.nfcorienteering.R;

import android.app.Activity;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

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
	
		Intent intent = new Intent(this, ReadTrackTagActivity.class);
		startActivity(intent);
		
	}
	
	public void onClick(View v){
		switch (v.getId()) {
		case R.id.history_btn:
			onHistoryButtonClick(v);
			break;

		default:
			break;
		}
	}
	
	private void onHistoryButtonClick(View v){
		Intent intent = new Intent(MainActivity.this,ResultHistoryActivity.class);
		startActivity(intent);
	}

}
