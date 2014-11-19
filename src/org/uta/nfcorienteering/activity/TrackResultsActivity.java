package org.uta.nfcorienteering.activity;




import java.io.Serializable;

import org.uta.nfcorienteering.R;
import org.uta.nfcorienteering.event.OrienteeringEvent;
import org.uta.nfcorienteering.event.OrienteeringRecord;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint.Style;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


public class TrackResultsActivity extends Activity {

	ImageButton uploadButton, noUploadButton;
	OrienteeringEvent event;
	
	TextView eventNameText, trackDistanceText;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_track_results);
		
		uploadButton = (ImageButton)findViewById(R.id.uploadButton);
		noUploadButton = (ImageButton)findViewById(R.id.noUploadButton);
		eventNameText = (TextView)findViewById(R.id.eventNameText);
		trackDistanceText = (TextView)findViewById(R.id.trackLengthText);
		
		//Collect the records of the track from our intent.
		event = (OrienteeringEvent)getIntent().getSerializableExtra("EVENT_RECORD");
		eventNameText.setText(event.getEventName());
		trackDistanceText.setText(event.getSelectedTrack().getDistance());
		
		if(event.getRecord().getPunches() == null){
			Toast.makeText(this, "No orienteering record was found", Toast.LENGTH_LONG).show();
		}
		else{
			setResultTable(event);
		}
		
	}


	public void uploadResults(View v) {
		Intent intent = new Intent(this, UploadResultsActivity.class);
		intent.putExtra("TRACK_INFO",(Serializable)event);
		startActivity(intent);
		finish();
	}
	
	public void backToStartMenu(View v) {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		finish();
	}
	
	public void setResultTable(OrienteeringEvent event){
		
		int controlPointCount = event.getSelectedTrack().getCheckpoints().size();
		int taggedPointsCount = 0;
		
		boolean[] pointsTagged = new boolean[controlPointCount];
		for(int i = 1; i < controlPointCount; i++) {
			boolean tagFound = false;
			for(int j = 1; j < event.getRecord().getPunches().size(); j++) {
				if(event.getRecord().getPunches().get(j).getCheckpointNumber() == event.getSelectedTrack().getCheckpoints().get(i).getCheckpointNumber()){
					pointsTagged[i] = true;
					tagFound = true;
					taggedPointsCount++;
					break;
				}
			}
			if(!tagFound){
				pointsTagged[i] = false;
			}
			
		}
		

	    TableLayout tableLayout = (TableLayout)findViewById(R.id.resultTable);
	
	    
	    TableLayout.LayoutParams params = new TableLayout.LayoutParams(TableLayout.
	    		LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
	    
	    params.setMargins(5,10,5,10);
	    
	    ShapeDrawable border = new ShapeDrawable(new RectShape());
		border.getPaint().setStyle(Style.STROKE);
		border.getPaint().setColor(Color.GREEN);
		
	    
	    
	    for(int i = 1; i < controlPointCount; i++) {
	    	TableRow tableRow = new TableRow(this);
	    	TextView rowNumber = new TextView(this);
			TextView controlPointNumber = new TextView(this);
			TextView controlPointTime = new TextView(this);
			
			rowNumber.setPadding(15, 15, 15, 15);
			controlPointNumber.setPadding(15, 15, 15, 15);
			controlPointTime.setPadding(15, 15, 15, 15);
			
			rowNumber.setText(String.valueOf(i) + ".");
			controlPointNumber.setText("Point " + event.getSelectedTrack().getCheckpoints().get(i).getCheckpointNumber());
			
			//Check that corresponding controlPoints have been tagged during the track completion.
			if(pointsTagged[i]) {
				if(i >= event.getRecord().getPunches().size()){
					controlPointTime.setText(event.getRecord().getPunches().get(event.getRecord().getPunches().size()-1).getTimestamp());
				}
				else {
					controlPointTime.setText(event.getRecord().getPunches().get(i).getTimestamp());
				}
			}
			else {
				controlPointTime.setText("Not tagged");
			}
			
	    	
			rowNumber.setTextColor(Color.BLACK);
			rowNumber.setGravity(Gravity.CENTER);
			controlPointNumber.setTextColor(Color.BLACK);
			controlPointNumber.setGravity(Gravity.CENTER);
			controlPointTime.setTextColor(Color.BLACK);
			controlPointTime.setGravity(Gravity.CENTER);
			controlPointNumber.setBackgroundDrawable(border);
			rowNumber.setBackgroundDrawable(border);
			controlPointTime.setBackgroundDrawable(border);
	    
			tableRow.addView(rowNumber);
			tableRow.addView(controlPointNumber);
			tableRow.addView(controlPointTime);
			tableLayout.addView(tableRow);
			
			
	    
	    }
	    TableRow tableRow = new TableRow(this);

		TextView total = new TextView(this);
		TextView controlPointsGot = new TextView(this);
		TextView totalTime = new TextView(this);
		
		total.setText("Total");
		controlPointsGot.setText(String.valueOf(taggedPointsCount) + " / " + String.valueOf(controlPointCount-1));
		totalTime.setText(event.getRecord().getPunches().get(event.getRecord().getPunches().size()-1).getTimestamp());
		
		total.setPadding(5, 5, 5, 5);
		controlPointsGot.setPadding(5, 5, 5, 5);
		totalTime.setPadding(5, 5, 5, 5);
		
		total.setTextColor(Color.BLACK);
		total.setGravity(Gravity.CENTER);
		controlPointsGot.setTextColor(Color.BLACK);
		controlPointsGot.setGravity(Gravity.CENTER);
		totalTime.setTextColor(Color.BLACK);
		totalTime.setGravity(Gravity.CENTER);

		tableRow.addView(total);
		tableRow.addView(controlPointsGot);
		tableRow.addView(totalTime);
		tableLayout.addView(tableRow);
	}
	
}
