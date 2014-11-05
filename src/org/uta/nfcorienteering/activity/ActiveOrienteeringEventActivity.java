package org.uta.nfcorienteering.activity;

import java.io.Serializable;
import java.util.ArrayList;

import org.uta.nfcorienteering.R;
import org.uta.nfcorienteering.event.Checkpoint;
import org.uta.nfcorienteering.event.OrienteeringEvent;
import org.uta.nfcorienteering.event.OrienteeringRecord;
import org.uta.nfcorienteering.event.Punch;

import com.iutinvg.compass.Compass;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint.Style;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


public class ActiveOrienteeringEventActivity extends BaseNfcActivity  {

	final Context context = this;
	final String FINISH_POINT = "default";
	
	private Compass compass;
	boolean compassHidden;
	boolean compassLarge;
	
	Button nextButton;

	ImageView compassImage;
	
	//Object which includes event information and where track record should be saved
	OrienteeringEvent event;
	//Object to store the record of the track
	OrienteeringRecord record = new OrienteeringRecord();
	//Object to store the punches of control points
	ArrayList<Punch> punches = new ArrayList<Punch>();

	

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_active_orienteering_event);
		
		compassHidden = false;
		compassLarge = false;
		nextButton = (Button)findViewById(R.id.activeEventNextButton);

		
		compassImage = (ImageView)findViewById(R.id.compassImageView);
		compassImage.setOnClickListener(new OnClickListener() {
			public void onClick(View v){
				if(!compassLarge) {
					compassImage.setImageResource(R.drawable.compass_big);
					compassImage.setPadding(0, 15, 0, 0);
					compassLarge = true;
				}
				else {
					compassImage.setImageResource(R.drawable.compass_small);
					compassImage.setPadding(0,0,0,0);
					compassLarge = false;
				}
				
			}
		});
		
		event = (OrienteeringEvent)getIntent().getSerializableExtra("TRACK_INFO");
		event.setRecord(record);
		
		ArrayList<Checkpoint> checkpoints = new ArrayList<Checkpoint>();
		
		compass = new Compass(this);
		
		compass.arrowView = compassImage;
		
		
		//Dummy values to checkpoints
		for(int i = 0; i < 5; i++) {
			
			Checkpoint cp = new Checkpoint(i, "ControlPoint" + i);
			checkpoints.add(cp);
		}
		event.getSelectedTrack().setCheckpoints(checkpoints);

	}

	public boolean onCreateOptionsMenu(Menu menu){
		getMenuInflater().inflate(R.menu.main, menu);
		
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item){
		boolean ret;
		if(item.getItemId() == R.id.menu_settings)
		{
			showResultDialog();
			ret = true;
		}
		if(item.getItemId() == R.id.menu_quit) {
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
			return true;
		}
		if(item.getItemId() == R.id.hide_compass){
			if(compassHidden){
				findViewById(R.id.compassImageView).setVisibility(View.VISIBLE);
				compassHidden = false;
				item.setTitle("Hide Compass");

			}
			else {
				findViewById(R.id.compassImageView).setVisibility(View.INVISIBLE);
				compassHidden = true;
				item.setTitle("Unhide Compass");
			}
			ret = true;
		}
		else {
			ret = super.onOptionsItemSelected(item);
		}
		return ret;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		compass.start();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		compass.start();

	}
	
	@Override
	protected void onPause() {
		super.onPause();
		compass.stop();

	}
	
	@Override
	protected void onStop() {
		super.onStop();
		compass.stop();
	}
	
	
	public void showResultDialog() {
		
		final Dialog dialog = new Dialog(context);
		dialog.setContentView(R.layout.result_dialog);
		dialog.setTitle("Standings");
		
		Button dialogButton = (Button) dialog.findViewById(R.id.dialogOk_button);
		dialogButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		
		//SET THE TABLE OF RESULTS
		int controlPointCount = event.getSelectedTrack().getCheckpoints().size();
		
	    TableLayout tableLayout = (TableLayout)dialog.findViewById(R.id.eventResultTable);
	    TableLayout.LayoutParams params = new TableLayout.LayoutParams(TableLayout.
	    		LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
	    
	    params.setMargins(5,10,5,10);
	    
	    /*ShapeDrawable border = new ShapeDrawable(new RectShape());
		border.getPaint().setStyle(Style.STROKE);
		border.getPaint().setColor(Color.GREEN);
	    */
	    
	    for(int i = 0; i < controlPointCount; i++) {
	    	TableRow tableRow = new TableRow(this);

	    	TextView rowNumber = new TextView(this);
			TextView controlPointNumber = new TextView(this);
			TextView controlPointTime = new TextView(this);
			
			rowNumber.setPadding(5, 5, 5, 5);
			controlPointNumber.setPadding(5, 5, 5, 5);
			controlPointTime.setPadding(5, 5, 5, 5);
			
			rowNumber.setText(String.valueOf(i+1) + ".");
			controlPointNumber.setText("Point " + String.valueOf(event.getSelectedTrack().getCheckpoints().get(i).getCheckpointNumber()));
			
			if(i < punches.size()){
				controlPointTime.setText(punches.get(i).getTimestamp());
			}
			rowNumber.setTextColor(Color.BLACK);
			rowNumber.setGravity(Gravity.CENTER);
			controlPointNumber.setTextColor(Color.BLACK);
			controlPointNumber.setGravity(Gravity.CENTER);
			controlPointTime.setTextColor(Color.BLACK);
			controlPointTime.setGravity(Gravity.CENTER);
			/*controlPointNumber.setBackgroundDrawable(border);
			rowNumber.setBackgroundDrawable(border);
			controlPointTime.setBackgroundDrawable(border);
	    	*/
			tableRow.addView(rowNumber);
			tableRow.addView(controlPointNumber);
			tableRow.addView(controlPointTime);
			
			
			tableLayout.addView(tableRow);
	    
	    }
	    dialog.show();
	
	
	}
		
		

	@Override
	public void postNfcRead(String result) {
		
		
		 Vibrator v = (Vibrator) this.context.getSystemService(Context.VIBRATOR_SERVICE);
		 // Vibrate for 500 milliseconds
		 v.vibrate(500);
		
		
		boolean correctTagRead = event.getSelectedTrack().newCheckPointReached(result);
		
		if(correctTagRead) {
			
			//The tag that has been correctly read belongs to a control point
			if(event.getSelectedTrack().getCurrentCheckPoint() > 0 && 
					event.getSelectedTrack().getCurrentCheckPoint() < event.getSelectedTrack().getCheckpoints().size() - 1){
				
				Toast.makeText(this, "Control point tag " + result +" read successfully!", Toast.LENGTH_LONG).show();
				Punch controlPoint = new Punch();
				controlPoint.setCheckpointNumber(event.getSelectedTrack().getCurrentCheckPoint());
				
				//Calculate the time it took for this control point by substracting the timestamp time from the
				//control point before this from the total time.
				
				punches.add(controlPoint);
				
				event.getSelectedTrack().setCurrentCheckPoint(event.getSelectedTrack().getCurrentCheckPoint() + 1);
				
				
			}
			//The tag that has been correctly read is a starting tag
			else if(event.getSelectedTrack().getCurrentCheckPoint()  == 0){
				//Starting tag was checked
				//call timer service
				event.getSelectedTrack().setCurrentCheckPoint(event.getSelectedTrack().getCurrentCheckPoint() + 1);
				Punch startingPoint = new Punch(0, "00:00");
				punches.add(startingPoint);
			}
			
			//The tag that has been correctly read is a finish tag and the other control points have been read correctly.
			else if(event.getSelectedTrack().getCurrentCheckPoint() == event.getSelectedTrack().getCheckpoints().size() - 1){
				//Finish tag was read and the other control points were read correctly.
				trackFinished(true);
			}
			
		}
		//The next correct tag was not read so have to do something to inform the orienteer 
		//depending on the situation.
		else {
			
			if(event.getSelectedTrack().getCurrentCheckPoint() > 0){
				
				//Check if finish point tag was read too early
				if(event.getSelectedTrack().getCheckpoints().get(event.getSelectedTrack().getCheckpoints().size()-1).equals(result)){
					trackFinished(false);
				}
				else {
					Toast.makeText(this, "Wrong control point tag was read", Toast.LENGTH_LONG).show();
				}
						
			}
			else if(event.getSelectedTrack().getCurrentCheckPoint()  == 0){
				
				Toast.makeText(this, "Invalid starting tag read. Please read the starting tag to start.", Toast.LENGTH_LONG).show();
				
			}

			
		}
			
	}
	
	public void trackFinished(boolean allControlPointsTagged) {
		
		event.getSelectedTrack().setCurrentCheckPoint(event.getSelectedTrack().getCurrentCheckPoint() + 1);
		Toast.makeText(this, "Track finished!", Toast.LENGTH_LONG).show();
		
		//stop timer
		
		//Set punches to record.
		record.setPunches(punches);
		
		Intent intent = new Intent(this, TrackResultsActivity.class);
		intent.putExtra("EVENT_RECORD", (Serializable)event);
		startActivity(intent);
		finish();
		
	}
	public void trackFinished(View v){
		
		Intent intent = new Intent(this, TrackResultsActivity.class);
		intent.putExtra("EVENT_RECORD", (Serializable)event);
		startActivity(intent);
	}
}
