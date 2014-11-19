package org.uta.nfcorienteering.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.uta.nfcorienteering.R;
import org.uta.nfcorienteering.event.Checkpoint;
import org.uta.nfcorienteering.event.OrienteeringEvent;
import org.uta.nfcorienteering.event.OrienteeringRecord;
import org.uta.nfcorienteering.event.Punch;
import org.uta.nfcorienteering.service.TimerService;
import org.uta.nfcorienteering.service.TimerService.StopwatchBinder;
import org.uta.nfcorienteering.utility.Stopwatch;

import com.iutinvg.compass.Compass;

import android.app.Activity;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.Paint.Style;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.IBinder;
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
	
	StopwatchBinder stopwatch;
	boolean mBound = false;
	
	Intent timerServiceIntent;
	
	

	
	
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
		
		compass = new Compass(this);
		compass.arrowView = compassImage;

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
		 
		 System.out.println(result);
		
		
		boolean correctTagRead = event.getSelectedTrack().newCheckPointReached(result);
		
		if(correctTagRead) {
			
			//The tag that has been correctly read belongs to a control point
			if(event.getSelectedTrack().getCurrentCheckPoint() > 0 && 
					event.getSelectedTrack().getCurrentCheckPoint() < event.getSelectedTrack().getCheckpoints().size() - 1){
				
				Toast.makeText(this, "Control point tag " + result +" read successfully!", Toast.LENGTH_LONG).show();
				Punch controlPoint = new Punch();
				controlPoint.setCheckpointNumber(event.getSelectedTrack().getCheckpoints().get(event.getSelectedTrack().getCurrentCheckPoint()).getCheckpointNumber());
				
				long totalTimeSeconds = TimeUnit.MILLISECONDS.toSeconds(Integer.parseInt(stopwatch.readTimeMillis()));
				String currentTimeStamp = convertSecondsToHMmSs(totalTimeSeconds);
				
				controlPoint.setTimestamp(currentTimeStamp);
				punches.add(controlPoint);
				
				event.getSelectedTrack().setCurrentCheckPoint(event.getSelectedTrack().getCurrentCheckPoint() + 1);
				
				
			}
			//The tag that has been correctly read is a starting tag
			else if(event.getSelectedTrack().getCurrentCheckPoint()  == 0){
				//Starting tag was checked
				Toast.makeText(this, "Starting tag read! Have fun!", Toast.LENGTH_LONG).show();
				
				//call timer service
				timerServiceIntent = new Intent(this, TimerService.class);
				bindService(timerServiceIntent, mConnection, BIND_AUTO_CREATE);
				startService(timerServiceIntent);
					
				event.getSelectedTrack().setCurrentCheckPoint(event.getSelectedTrack().getCurrentCheckPoint() + 1);
				Punch startingPoint = new Punch(0, "00:00:00");
				punches.add(startingPoint);
			}
			
			//The tag that has been correctly read is a finish tag and the other control points have been read correctly.
			else if(event.getSelectedTrack().getCurrentCheckPoint() == event.getSelectedTrack().getCheckpoints().size() - 1){

				//Finish tag was read and the other control points were read correctly.
				/*long totalTimeMillis = Integer.parseInt(stopwatch.readTimeMillis());
				String lastTimeStamp = punches.get(event.getSelectedTrack().getCurrentCheckPoint() -1).getTimestamp();
				String currentTimeStamp = getSplitTimeString(lastTimeStamp, totalTimeMillis);
				*/
				
				long totalTimeSeconds = TimeUnit.MILLISECONDS.toSeconds(Integer.parseInt(stopwatch.readTimeMillis()));
				String finalTimeStamp = convertSecondsToHMmSs(totalTimeSeconds);
				
				Punch controlPoint = new Punch();
				controlPoint.setCheckpointNumber(event.getSelectedTrack().getCheckpoints().get(event.getSelectedTrack().getCurrentCheckPoint()).getCheckpointNumber());
				controlPoint.setTimestamp(finalTimeStamp);
				punches.add(controlPoint);
				
				event.getSelectedTrack().setCurrentCheckPoint(event.getSelectedTrack().getCurrentCheckPoint() + 1);
				trackFinished(true);
			}
			
		}
		//The next correct tag was not read so have to do something to inform the orienteer 
		//depending on the situation.
		else {
			
			if(event.getSelectedTrack().getCurrentCheckPoint() > 0){
				
				//Check if finish point tag was read too early
				if(event.getSelectedTrack().getCheckpoints().get(event.getSelectedTrack().getCheckpoints().size()-1).getRfidTag().equals(result)){
					
					long totalTimeSeconds = TimeUnit.MILLISECONDS.toSeconds(Integer.parseInt(stopwatch.readTimeMillis()));
					String finalTimeStamp = convertSecondsToHMmSs(totalTimeSeconds);
					
					Punch controlPoint = new Punch();
					controlPoint.setTimestamp(finalTimeStamp);
					controlPoint.setCheckpointNumber(event.getSelectedTrack().getCheckpoints().get(event.getSelectedTrack().getCheckpoints().size()-1).getCheckpointNumber());
					punches.add(controlPoint);
					
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
		
		//stop timer
		stopService(timerServiceIntent);
		
		if(allControlPointsTagged)
			Toast.makeText(this, "Track finished successfully!", Toast.LENGTH_LONG).show();
		
		else
			Toast.makeText(this,  "Track finished but some control points are missing!", Toast.LENGTH_LONG).show();
		
		
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
	
	public String getSplitTimeString (String lastTimestamp, long totalTimeMillis) {
		
		long lastTimeStampMillis = 0;
		long currentTimeStampMillis = 0;
		
		String[] timeSplit = lastTimestamp.split(":");
		long[] intTimes = new long[timeSplit.length];
		
		for(int i = 0; i < timeSplit.length; i++) {
			intTimes[i] = Integer.parseInt(timeSplit[i]);
		}
		
		if(intTimes.length == 3){
			lastTimeStampMillis += TimeUnit.HOURS.toMillis(intTimes[0]);
			lastTimeStampMillis += TimeUnit.MINUTES.toMillis(intTimes[1]);
			lastTimeStampMillis += TimeUnit.SECONDS.toMillis(intTimes[2]);
		}
		else {
			lastTimeStampMillis += TimeUnit.MINUTES.toMillis(intTimes[0]);
			lastTimeStampMillis += TimeUnit.SECONDS.toMillis(intTimes[1]);
		}
		
		currentTimeStampMillis = totalTimeMillis - lastTimeStampMillis;
		long currentTimeStampSeconds = TimeUnit.MILLISECONDS.toSeconds(currentTimeStampMillis);
		String currentTimeStamp = convertSecondsToHMmSs(currentTimeStampSeconds);

		return currentTimeStamp;
	}
	
	public static String convertSecondsToHMmSs(long seconds) {
	    long s = seconds % 60;
	    long m = (seconds / 60) % 60;
	    long h = (seconds / (60 * 60)) % 24;
	    return String.format("%d:%02d:%02d", h,m,s);
	}
}
