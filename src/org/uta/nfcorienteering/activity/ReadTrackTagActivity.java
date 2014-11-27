package org.uta.nfcorienteering.activity;

import java.io.Serializable;

import org.uta.nfcorienteering.R;
import org.uta.nfcorienteering.event.OrienteeringEvent;
import org.uta.nfcorienteering.http.HttpHelper;
import org.uta.nfcorienteering.utility.DataInstance;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
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

		postNfcRead("button");
			
	}


	@Override
	public void postNfcRead(String result) {
		tagId.setText(result);
		String infoTagId = result;	
		
		if(infoTagId.equals("button")){
			infoTagId = "12";
		}

		
		Vibrator v = (Vibrator) this.context.getSystemService(Context.VIBRATOR_SERVICE);
		 // Vibrate for 500 milliseconds
		 v.vibrate(500);
		
		//Received trackID, result, should be queried to server if there is such event available.
		nextButton.setEnabled(false);
		Toast.makeText(context, "Searching for track data. Please wait", Toast.LENGTH_LONG).show();
		new EventDataDownloader().execute(infoTagId);

	}
	
	public void searchTrackDataResults(OrienteeringEvent event) {
		if(event != null){
			Toast.makeText(context, "Track found!", Toast.LENGTH_SHORT).show();
			startTrackInfoActivity(event);
		}
		else {
			Toast.makeText(context, "Track could not be found with the ID read from the Tag. Please try reading " +
		             "another Tag.", Toast.LENGTH_LONG).show();
		}
	}
	
	public  void startTrackInfoActivity(OrienteeringEvent event){
		Toast.makeText(context, "Track found!", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(this, TrackInfoActivity.class);
		intent.putExtra("TRACK_INFO",(Serializable)event);
		startActivity(intent);

		
	}
	
	class EventDataDownloader extends AsyncTask<String, Void, OrienteeringEvent> {

		@Override
		protected OrienteeringEvent doInBackground(String... params) {
			
			System.out.println(params[0]);
			boolean trackFound = HttpHelper.getTrackAndParentEvent(params[0]);
			
			if(!trackFound){
				return null;
			}
			else {
				return DataInstance.getInstace().getEvent();
			}

		}
		
		@Override
		protected void onPostExecute(OrienteeringEvent event) {
			searchTrackDataResults(event);
		}

	}
	
}
	

	