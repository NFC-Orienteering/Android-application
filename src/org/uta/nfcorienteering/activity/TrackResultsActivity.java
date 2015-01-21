package org.uta.nfcorienteering.activity;

import java.util.ArrayList;
import java.util.List;

import org.uta.nfcorienteering.R;
import org.uta.nfcorienteering.event.OrienteeringEvent;
import org.uta.nfcorienteering.event.Punch;
import org.uta.nfcorienteering.event.Track;
import org.uta.nfcorienteering.utility.DataInstance;
import org.uta.nfcorienteering.utility.LocalStorage;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint.Style;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class TrackResultsActivity extends Activity {

	private static final int PADDING_LEFT = 15;
	private static final int PADDING_TOP = 15;
	private static final int PADDING_RIGHT = 15;
	private static final int PADDING_BOTTOM = 15;
	private static final int PADDING_TOTAL_LEFT = 15;
	private static final int PADDING_TOTAL_TOP = 15;
	private static final int PADDING_TOTAL_RIGHT = 15;
	private static final int PADDING_TOTAL_BOTTOM = 15;
	
	Button uploadButton, noUploadButton;
	OrienteeringEvent event;
	private Track track;
	private boolean histroyMode = false;

	TextView eventNameText, trackDistanceText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_track_results);

		uploadButton = (Button) findViewById(R.id.uploadButton);
		noUploadButton = (Button) findViewById(R.id.noUploadButton);
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
		if (data.getBoolean("isFromHistroytActivity")) {
			initDataFromIntent(data);
			hideUploadUI();
			histroyMode = true;
		}else {
			initDataFromGlobal();
			storeResultToLocalHistroy();
			
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
	
	
	private void storeResultToLocalHistroy(){
		LocalStorage localStorage = new LocalStorage(this);
		Object data = localStorage.readFromSharedPreference();
		List<Track> histroy = (List<Track>) data;
		if (histroy == null) {
			histroy = new ArrayList<Track>();
		}
		histroy.add(track);
		localStorage.saveToSharedPreference("result_history", histroy);
	}
	@Override
	public void onBackPressed(){
		if (histroyMode) {
			super.onBackPressed();
		}
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

	@SuppressWarnings("deprecation")
	public void setResultTable(OrienteeringEvent event) {

		int controlPointCount = track.getCheckpoints().size();
		int taggedPointsCount = 0;

		TableLayout tableLayout = (TableLayout) findViewById(R.id.resultTable);

		TableLayout.LayoutParams params = new TableLayout.LayoutParams(
				TableLayout.LayoutParams.MATCH_PARENT,
				TableLayout.LayoutParams.WRAP_CONTENT);

		params.setMargins(5, 10, 5, 10);

		ShapeDrawable border = new ShapeDrawable(new RectShape());
		border.getPaint().setStyle(Style.STROKE);
		border.getPaint().setColor(getResources().getColor(R.color.tableBorderColor));

		for (int i = 1; i < controlPointCount; i++) {
			TableRow tableRow = new TableRow(this);
			TextView rowNumber = new TextView(this);
			TextView controlPointNumber = new TextView(this);
			TextView controlPointTime = new TextView(this);

			rowNumber.setPadding(PADDING_LEFT, PADDING_TOP, PADDING_RIGHT, PADDING_BOTTOM);
			controlPointNumber.setPadding(PADDING_LEFT, PADDING_TOP, PADDING_RIGHT, PADDING_BOTTOM);
			controlPointTime.setPadding(PADDING_LEFT, PADDING_TOP, PADDING_RIGHT, PADDING_BOTTOM);


			rowNumber.setText(String.valueOf(i) + ".");
			controlPointNumber.setText("Point "
					+ track.getCheckpoints().get(i).getCheckpointNumber());

			if(event.getRecord().getPunches().get(i).getTotalTimestampMillis() == 0){
				controlPointTime.setText("Not tagged");
			}else {
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

		total.setPadding(PADDING_TOTAL_LEFT, PADDING_TOTAL_TOP, PADDING_TOTAL_RIGHT, PADDING_TOTAL_BOTTOM);
		controlPointsGot.setPadding(PADDING_TOTAL_LEFT, PADDING_TOTAL_TOP, PADDING_TOTAL_RIGHT, PADDING_TOTAL_BOTTOM);
		totalTime.setPadding(PADDING_TOTAL_LEFT, PADDING_TOTAL_TOP, PADDING_TOTAL_RIGHT, PADDING_TOTAL_BOTTOM);

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
