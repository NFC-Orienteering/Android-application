package org.uta.nfcorienteering.activity;

import org.uta.nfcorienteering.R;
import org.uta.nfcorienteering.event.OrienteeringEvent;
import org.uta.nfcorienteering.event.Track;
import org.uta.nfcorienteering.event.Punch;
import org.uta.nfcorienteering.utility.DataInstance;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint.Style;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.opengl.Visibility;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class TrackResultsActivity extends Activity {

	ImageButton uploadButton, noUploadButton;
	OrienteeringEvent event;
	private Track track;

	TextView eventNameText, trackDistanceText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_track_results);

		uploadButton = (ImageButton) findViewById(R.id.uploadButton);
		noUploadButton = (ImageButton) findViewById(R.id.noUploadButton);
		eventNameText = (TextView) findViewById(R.id.eventNameText);
		trackDistanceText = (TextView) findViewById(R.id.trackLengthText);

		initView();
		
		eventNameText.setText(event.getEventName());
		trackDistanceText.setText(track.getDistance());

		if (event.getRecord().getPunches() == null) {
			Toast.makeText(this, "No orienteering record was found",
					Toast.LENGTH_LONG).show();
		} else {
			setResultTable(event);
		}
	}
	
	private void initView(){
		Bundle data = getIntent().getExtras();
		if (data.getBoolean("isFromResultActivity")) {
			initDataFromIntent(data);
			hideUploadUI();
		}else {
			initDataFromGlobal();
		}
	}
	
	private void hideUploadUI(){
		noUploadButton.setVisibility(View.INVISIBLE);
		uploadButton.setVisibility(View.INVISIBLE);
		
		findViewById(R.id.uploadInfoText).setVisibility(View.INVISIBLE);
		
	}
	
	private void initDataFromIntent(Bundle data){
		track = (Track) data.get("track");
		event = track.getParentEvent();
	}
	
	private void initDataFromGlobal(){
		
		event = DataInstance.getInstace().getEvent();
		track = DataInstance.getInstace().getTrack();
	}
	
	@Override
	public void onBackPressed()
	{

	}

	public void uploadResults(View v) {
		Intent intent = new Intent(this, UploadResultsActivity.class);
		startActivity(intent);
		finish();
	}

	public void backToStartMenu(View v) {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		finish();
	}

	public void setResultTable(OrienteeringEvent event) {

		int controlPointCount = track.getCheckpoints().size();
		int taggedPointsCount = 0;

		TableLayout tableLayout = (TableLayout) findViewById(R.id.resultTable);

		TableLayout.LayoutParams params = new TableLayout.LayoutParams(
				TableLayout.LayoutParams.FILL_PARENT,
				TableLayout.LayoutParams.WRAP_CONTENT);

		params.setMargins(5, 10, 5, 10);

		ShapeDrawable border = new ShapeDrawable(new RectShape());
		border.getPaint().setStyle(Style.STROKE);
		border.getPaint().setColor(Color.GREEN);

		for (int i = 1; i < controlPointCount; i++) {
			TableRow tableRow = new TableRow(this);
			TextView rowNumber = new TextView(this);
			TextView controlPointNumber = new TextView(this);
			TextView controlPointTime = new TextView(this);

			rowNumber.setPadding(15, 15, 15, 15);
			controlPointNumber.setPadding(15, 15, 15, 15);
			controlPointTime.setPadding(15, 15, 15, 15);

			rowNumber.setText(String.valueOf(i) + ".");
			controlPointNumber.setText("Point "
					+ track.getCheckpoints().get(i).getCheckpointNumber());

			if(event.getRecord().getPunches().get(i).getTotalTimestampMillis() == 0){
				controlPointTime.setText("Not tagged");
			}
			else {
				controlPointTime.setText(Punch.convertMillisToHMmSs(event.getRecord().getPunches().get(i).getTotalTimestampMillis()));
				taggedPointsCount++;
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
		controlPointsGot.setText(String.valueOf(taggedPointsCount) + " / "
				+ String.valueOf(controlPointCount - 1));
		totalTime.setText(Punch.convertMillisToHMmSs(event.getRecord().getPunches()
				.get(event.getRecord().getPunches().size() - 1).getTotalTimestampMillis()));

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
