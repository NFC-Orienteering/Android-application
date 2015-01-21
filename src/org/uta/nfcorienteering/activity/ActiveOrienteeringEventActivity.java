package org.uta.nfcorienteering.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.uta.nfcorienteering.R;
import org.uta.nfcorienteering.event.OrienteeringEvent;
import org.uta.nfcorienteering.event.OrienteeringRecord;
import org.uta.nfcorienteering.event.Punch;
import org.uta.nfcorienteering.event.Track;
import org.uta.nfcorienteering.service.TimerService;
import org.uta.nfcorienteering.service.TimerService.StopwatchBinder;
import org.uta.nfcorienteering.utility.AppConfig;
import org.uta.nfcorienteering.utility.DataInstance;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Vibrator;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.iutinvg.compass.Compass;
import com.polites.android.GestureImageView;


public class ActiveOrienteeringEventActivity extends BaseNfcActivity  {
	
	private static final int PADDING_LEFT = 5;
	private static final int PADDING_TOP = 5;
	private static final int PADDING_RIGHT = 5;
	private static final int PADDING_BOTTOM = 5;
	
	private Compass compass =  null;
	boolean compassHidden;
	boolean compassLarge;
	
	Button nextButton;
	ImageView compassImage;
	
	//Object which includes event information and where track record should be saved
	OrienteeringEvent event;
	//Object to store the record of the track
	OrienteeringRecord record = new OrienteeringRecord();
	//Object to store the punches of control points
	List<Punch> punches = new ArrayList<Punch>();
	
	GestureImageView gestureMapImage;
	
	private Track track = null;
	
	StopwatchBinder stopwatch;
	boolean mBound = false;
	Intent timerServiceIntent = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_active_orienteering_event);
		
		compassHidden = false;
		compassLarge = false;

		nextButton = (Button)findViewById(R.id.activeEventNextButton);
		if(!AppConfig.IS_REAL_DEVICE) {
			nextButton.setVisibility(Button.VISIBLE);
		}
				
		compassImage = (ImageView)findViewById(R.id.compassImageView);
		compassImage.setOnClickListener(new OnClickListener() {
			public void onClick(View v){
				if(!compassLarge) {
					if(!compassHidden){
						compassImage.setImageResource(R.drawable.compass_big);
						compassImage.setPadding(0, 15, 0, 0);
						compassLarge = true;
					}
				}else {
					if(!compassHidden){
						compassImage.setImageResource(R.drawable.compass_small);
						compassImage.setPadding(0,0,0,0);
						compassLarge = false;
					}
				}
				
			}
		});

		event = DataInstance.getInstace().getEvent();
		track = DataInstance.getInstace().getTrack();
		
		setTitle(event.getEventName() + " " + track.getDistance());
		
		event.setRecord(record);
		
		gestureMapImage = (GestureImageView) findViewById(R.id.gesture_image_view);
		gestureMapImage.setImageBitmap(DataInstance.getInstace().getMapImage());
		
		for(int i = 0; i < track.getCheckpoints().size(); i++) {
			Punch controlPoint = new Punch();
			
			controlPoint.setCheckpointNumber(track.getCheckpoints().get(i).getCheckpointNumber());
			controlPoint.setTagId(track.getCheckpoints().get(i).getRfidTag());
			controlPoint.setSplitTimeMillis(0);
			controlPoint.setTotalTimestampMillis(0);
			punches.add(controlPoint);
		}
		
		setCompass();
	    setImageSize();
	}

	public void setCompass() {
		if(compass == null) {
			compass = new Compass(this);
			compass.arrowView = compassImage;
		}
	
	}

	
	public boolean onCreateOptionsMenu(Menu menu){
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item){
		boolean ret;
		if(item.getItemId() == R.id.menu_settings){
			showResultDialog();
			ret = true;
		}
		if(item.getItemId() == R.id.menu_quit) {
			
			AlertDialog.Builder builder = new AlertDialog.Builder(this);

			builder.setTitle("Quit active event");
			builder.setMessage("Do you want to quit the event and return to main menu?"
					+ " Event data will not be saved.");

			builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
			builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					returnToMainMenu();
				}

			});
	
			AlertDialog alert = builder.create();
			alert.show();
		
			return true;
			
		}
		if(item.getItemId() == R.id.hide_compass){
			if(compassHidden){
				if(compassLarge){
					compassImage.setImageResource(R.drawable.compass_big);
				
				} else {
					compassImage.setImageResource(R.drawable.compass_small);
				}
				compassImage.setVisibility(View.VISIBLE);
				compassHidden = false;
				item.setTitle("Hide Compass");
				
			} else {
				compassImage.setImageResource(0);
				compassImage.setVisibility(View.INVISIBLE);
				compassHidden = true;
				item.setTitle("Unhide Compass");
			}
			ret = true;
		} else {
			ret = super.onOptionsItemSelected(item);
		}
		return ret;
	}
	
	public void returnToMainMenu() {
		
		if(timerServiceIntent != null){
			stopService(timerServiceIntent);
		}
		Toast.makeText(this, "Active event stopped.",
				Toast.LENGTH_LONG).show();
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		finish();
	}
	
	@Override
	public void onBackPressed(){
		/*
		 * Method is empty because we don't want the back button
		 * to do anything in this activity.
		 */

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
		
		final Dialog dialog = new Dialog(this);
		dialog.setContentView(R.layout.result_dialog);
		dialog.setTitle("Standings");
		
		Button dialogButton = (Button) dialog.findViewById(R.id.dialogOk_button);
		dialogButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		
		//SET THE TABLE OF RESULTS
		int controlPointCount = track.getCheckpoints().size();
		
	    TableLayout tableLayout = (TableLayout)dialog.findViewById(R.id.eventResultTable);
	    TableLayout.LayoutParams params = new TableLayout.LayoutParams(TableLayout.
	    		LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
	    
	    params.setMargins(5,10,5,10);
	    
	    for(int i = 1; i < controlPointCount; i++) {
	    	TableRow tableRow = new TableRow(this);

	    	TextView rowNumber = new TextView(this);
			TextView controlPointNumber = new TextView(this);
			TextView controlPointTime = new TextView(this);
			
			rowNumber.setPadding(PADDING_LEFT, PADDING_TOP, PADDING_RIGHT, PADDING_BOTTOM);
			controlPointNumber.setPadding(PADDING_LEFT, PADDING_TOP, PADDING_RIGHT, PADDING_BOTTOM);
			controlPointTime.setPadding(PADDING_LEFT, PADDING_TOP, PADDING_RIGHT, PADDING_BOTTOM);
			
			rowNumber.setText(String.valueOf(i+1) + ".");
			controlPointNumber.setText("Point " + String.valueOf(track.getCheckpoints().get(i).getCheckpointNumber()));
			
			if(i < track.getCurrentCheckPoint()){
				if(punches.get(i).getTotalTimestampMillis() == 0){
					controlPointTime.setText("Not tagged");
				} else{
					controlPointTime.setText(Punch.convertMillisToHMmSs(punches.get(i).getTotalTimestampMillis()));
				}
			}
	
			rowNumber.setTextColor(Color.BLACK);
			rowNumber.setGravity(Gravity.CENTER);
			controlPointNumber.setTextColor(Color.BLACK);
			controlPointNumber.setGravity(Gravity.CENTER);
			controlPointTime.setTextColor(Color.BLACK);
			controlPointTime.setGravity(Gravity.CENTER);

			tableRow.addView(rowNumber);
			tableRow.addView(controlPointNumber);
			tableRow.addView(controlPointTime);
			tableLayout.addView(tableRow);
	    
	    }
	    dialog.show();
	}
		
		

	@Override
	public void postNfcRead(String result) {
		
		 Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
		 v.vibrate(500);

		int checkpointIndex = track.newCheckPointReached(result);
		
		if(checkpointIndex >= 0) {
			
			boolean tagRead = false;
			if(punches.get(checkpointIndex).getTotalTimestampMillis() > 0) {
				Toast.makeText(this, "Tag has been already read!", Toast.LENGTH_LONG).show();
				tagRead = true;
			}
			
			//The tag that has been correctly read belongs to a control point
			if(checkpointIndex > 0 && !tagRead &&
					checkpointIndex < track.getCheckpoints().size() - 1 && track.getCurrentCheckPoint() > 0){

				Toast.makeText(this, "Control point tag " + result +" read successfully!", Toast.LENGTH_LONG).show();
				int lastTaggedIndex = 0;
				
				for(int i = 1; i < checkpointIndex; i++) {
					if(punches.get(i).getTotalTimestampMillis() > 0){
						lastTaggedIndex = i;
					}
				}
				
				long totalTimeMillis = Integer.parseInt(stopwatch.readTimeMillis());
				punches.get(checkpointIndex).setTotalTimestampMillis(totalTimeMillis);
				punches.get(checkpointIndex).setSplitTimeMillis(getSplitTimeMillis(punches.get(lastTaggedIndex)
						.getTotalTimestampMillis(), totalTimeMillis));
				
				for(int i = checkpointIndex + 1; i < track.getCheckpoints().size() - 1; i++) {
					if(punches.get(i).getTotalTimestampMillis() > 0){
						punches.get(i).setSplitTimeMillis(0);
						punches.get(i).setTotalTimestampMillis(0);
					}
				}
				
				track.setCurrentCheckPoint(checkpointIndex + 1);
				
			}
			//The tag that has been correctly read is a starting tag
			else if(checkpointIndex == 0 && track.getCurrentCheckPoint() == 0){
				
				//Starting tag was checked
				Toast.makeText(this, "Starting tag read! Have fun!", Toast.LENGTH_LONG).show();
				
				//call timer service
				timerServiceIntent = new Intent(this, TimerService.class);
				bindService(timerServiceIntent, mConnection, BIND_AUTO_CREATE);
				startService(timerServiceIntent);
					
				track.setCurrentCheckPoint(track.getCurrentCheckPoint() + 1);
				
			//The tag that has been correctly read is a finish tag 
			}else if(checkpointIndex == track.getCheckpoints().size() - 1 && track.getCurrentCheckPoint() > 0){

				int lastTaggedIndex = 0;
				for(int i = 1; i < track.getCheckpoints().size()-1; i++) {
					
					if(punches.get(i).getTotalTimestampMillis() > 0){
						lastTaggedIndex = i;
					}
				}
				
				long totalTimeMillis = Integer.parseInt(stopwatch.readTimeMillis());
				punches.get(checkpointIndex).setTotalTimestampMillis(totalTimeMillis);
				punches.get(checkpointIndex).setSplitTimeMillis(getSplitTimeMillis(punches.get(lastTaggedIndex)
						.getTotalTimestampMillis(), totalTimeMillis));
				
				boolean trackComplete = true;
				for(int i = 1; i < track.getCheckpoints().size(); i++) {
					if(punches.get(i).getTotalTimestampMillis() == 0) {
						trackComplete = false;
					}
				}
				
				track.setCurrentCheckPoint(track.getCurrentCheckPoint() + 1);
				trackFinished(trackComplete);
				
			} else if(checkpointIndex > 0 && track.getCurrentCheckPoint() == 0){
				Toast.makeText(this, "Invalid starting tag read. Please read the starting tag to start.", Toast.LENGTH_LONG).show();
				
			} else if(checkpointIndex == 0 && track.getCurrentCheckPoint() > 0) {
				Toast.makeText(this, "Starting tag has already been read!", Toast.LENGTH_LONG).show();
			}
			
		} else {
			
			if(track.getCurrentCheckPoint() > 0){
				Toast.makeText(this, "Wrong control point tag was read", Toast.LENGTH_LONG).show();
				
			} else if(track.getCurrentCheckPoint()  == 0){
				Toast.makeText(this, "Invalid starting tag read. Please read the starting tag to start.", Toast.LENGTH_LONG).show();
			}
		}			
	}
	
	private ServiceConnection mConnection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName name,
				IBinder service) {
			
			stopwatch = (StopwatchBinder) service;
			mBound = true;	
		}
		@Override
		public void onServiceDisconnected(ComponentName name) {
			mBound = false;
			stopwatch.resetStopwatch();
			stopwatch = null;
		}	
	};
	
	public void trackFinished(boolean allControlPointsTagged) {
		
		stopService(timerServiceIntent);
		
		if(allControlPointsTagged) {
			Toast.makeText(this, "Track finished successfully!", Toast.LENGTH_LONG).show();
		
		} else {
			Toast.makeText(this,  "Track finished but some control points are missing!", Toast.LENGTH_LONG).show();
		}
		
		record.setPunches(punches);
		record.setRecordComplete(allControlPointsTagged);
		Intent intent = new Intent(this, TrackResultsActivity.class);
		intent.putExtra("EVENT_RECORD", (Serializable)event);
		startActivity(intent);
		finish();
		
	}
	public void trackFinished(View v){
		if(timerServiceIntent != null) {
			stopService(timerServiceIntent);
		}
		Intent intent = new Intent(this, TrackResultsActivity.class);
		intent.putExtra("EVENT_RECORD", (Serializable)event);
		startActivity(intent);
		finish();
	}
	
	public long getSplitTimeMillis (long lastTimestamp, long totalTimeMillis) {
		return totalTimeMillis - lastTimestamp;
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
	    super.onConfigurationChanged(newConfig);
	    setImageSize();    	
	}
	
	private void setImageSize() {
    	GestureImageView img = (GestureImageView) findViewById(R.id.gesture_image_view);
    	
    	Display display = getWindowManager().getDefaultDisplay();
    	Point point = new Point();
    	display.getSize(point);    	
  	
    	img.getLayoutParams().width = point.x;
    	img.getLayoutParams().height = point.y;
    	img.reset();
	}
}
