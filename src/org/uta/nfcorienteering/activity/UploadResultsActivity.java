package org.uta.nfcorienteering.activity;

import org.uta.nfcorienteering.R;
import org.uta.nfcorienteering.event.OrienteeringEvent;
import org.uta.nfcorienteering.event.Punch;
import org.uta.nfcorienteering.event.Track;
import org.uta.nfcorienteering.http.HttpRequest;
import org.uta.nfcorienteering.http.JsonBuilder;
import org.uta.nfcorienteering.http.UrlGenerator;
import org.uta.nfcorienteering.utility.DataInstance;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class UploadResultsActivity extends Activity {

	final static int STATE_NICKNAME = 1;
	final static int STATE_UPLOAD = 2;
	final static int STATE_FINISH = 3;
	final static int PADDING_LEFT = 40;
	final static int PADDING_TOP = 15;
	final static int PADDING_RIGHT = 10;
	final static int PADDING_BOTTOM = 15;

	OrienteeringEvent event;
	private Track track;

	TextView addNameText, nicknameText, uploadText, finishText;
	TextView uploadingResultsText;
	EditText nicknameTextField;
	Button nextButton, cancelButton;
	ProgressBar progressBarUpload;
	String nickname;
	int progressState;
	ImageView okIconSmall;

	TableLayout trackUploadTable;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_upload_results);

		nicknameTextField = (EditText) findViewById(R.id.nicknameTextField);
		nextButton = (Button) findViewById(R.id.nextResultsButton);
		cancelButton = (Button) findViewById(R.id.cancelResultsButton);
		progressBarUpload = (ProgressBar) findViewById(R.id.progressBarResults);
		nicknameText = (TextView) findViewById(R.id.nicknameText);
		uploadText = (TextView) findViewById(R.id.trackUploadText);
		finishText = (TextView) findViewById(R.id.uploadText);
		addNameText = (TextView) findViewById(R.id.addNameText);
		uploadingResultsText = (TextView) findViewById(R.id.uploadingResultsText);
		trackUploadTable = (TableLayout) findViewById(R.id.trackUploadTable);

		nicknameText.setTextColor(Color.GREEN);
		uploadText.setTextColor(Color.BLACK);
		finishText.setTextColor(Color.BLACK);

		event = DataInstance.getInstace().getEvent();
		track = DataInstance.getInstace().getTrack();

		progressState = STATE_NICKNAME;
	}

	@Override
	public void onBackPressed() {
		/*
		 * Back-button is disabled.
		 */
	}

	public void uploadProgressNext(View v) {

		if (progressState == STATE_NICKNAME) {
			nickname = nicknameTextField.getText().toString();
			nicknameTextField.setVisibility(View.INVISIBLE);
			addNameText.setVisibility(View.INVISIBLE);
			uploadText.setTextColor(Color.GREEN);
			progressState = STATE_UPLOAD;

			nextButton.setText("Upload");

			progressBarUpload.setProgress(20);

			TableRow tableRow = new TableRow(this);
			TextView trackName = new TextView(this);
			TextView totalTimestamp = new TextView(this);

			trackName.setPadding(PADDING_LEFT, PADDING_TOP, PADDING_RIGHT, PADDING_BOTTOM);
			totalTimestamp.setPadding(10, 15, 10, 15);

			trackName
					.setText(event.getEventName() + "  " + track.getDistance());

			if (event.getRecord().getPunches() != null) {

				if (event.getRecord().getPunches()
						.get(event.getRecord().getPunches().size() - 1)
						.getTotalTimestampMillis() > 0) {

					totalTimestamp.setText(Punch.convertMillisToHMmSs(event
							.getRecord().getPunches()
							.get(event.getRecord().getPunches().size() - 1)
							.getTotalTimestampMillis()));
				}
			} else {
				totalTimestamp.setText("01:24:45");
			}

			trackName.setTextSize(16);
			totalTimestamp.setTextSize(16);

			okIconSmall = new ImageView(this);
			okIconSmall.setBackgroundResource(R.drawable.ok_icon_small);
			tableRow.addView(okIconSmall);

			tableRow.addView(trackName);
			tableRow.addView(totalTimestamp);
			tableRow.setGravity(Gravity.CENTER);
			trackUploadTable.addView(tableRow);

		} else if (progressState == STATE_UPLOAD) {
			// HERE should be implemented the POST-method.
			finishText.setTextColor(Color.GREEN);

			trackUploadTable.removeAllViews();
			progressBarUpload.setProgress(100);

			TextView uploadFeedback1 = new TextView(this);
			TextView uploadFeedback2 = new TextView(this);
			TextView uploadFeedback3 = new TextView(this);

			uploadFeedback1.setText("Results");
			uploadFeedback2.setText("are");
			uploadFeedback3.setText("uploaded");

			uploadFeedback1.setTextSize(25);
			uploadFeedback2.setTextSize(25);
			uploadFeedback3.setTextSize(25);

			uploadFeedback1.setPadding(PADDING_LEFT, PADDING_TOP, PADDING_RIGHT, PADDING_BOTTOM);
			uploadFeedback2.setPadding(PADDING_LEFT, PADDING_TOP, PADDING_RIGHT, PADDING_BOTTOM);
			uploadFeedback3.setPadding(PADDING_LEFT, PADDING_TOP, PADDING_RIGHT, PADDING_BOTTOM);

			uploadFeedback1.setGravity(Gravity.CENTER);
			uploadFeedback2.setGravity(Gravity.CENTER);
			uploadFeedback3.setGravity(Gravity.CENTER);

			TableRow row1 = new TableRow(this);
			row1.addView(uploadFeedback1);
			TableRow row2 = new TableRow(this);
			row2.addView(uploadFeedback2);
			TableRow row3 = new TableRow(this);
			row3.addView(uploadFeedback3);

			row1.setGravity(Gravity.CENTER);
			row2.setGravity(Gravity.CENTER);
			row3.setGravity(Gravity.CENTER);

			ImageView okIcon = new ImageView(this);
			okIcon.setBackgroundResource(R.drawable.ok_icon);
			okIcon.setPadding(40, 15, 10, 15);
			TableRow row4 = new TableRow(this);
			row4.addView(okIcon);
			row4.setGravity(Gravity.CENTER);

			trackUploadTable.addView(row1);
			trackUploadTable.addView(row2);
			trackUploadTable.addView(row3);
			trackUploadTable.addView(row4);

			// HTTP post can not be run on the main thread
			new UploadResultsTask().execute(track);

			nextButton.setText("OK");
			progressState = STATE_FINISH;
			
		}else if (progressState == STATE_FINISH) {
			uploadResults(nextButton);
		}
	}

	public void uploadProgressCancel(View v) {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle("Upload results");
		builder.setMessage("Do you want to cancel uploading your results and return to"
				+ " main menu?");

		builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				// Return to main menu
				returnToMainMenu();
			}

		});
		builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// Do nothing
				dialog.dismiss();
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}

	public void returnToMainMenu() {

		Toast.makeText(this, "Orienteering results not uploaded",
				Toast.LENGTH_LONG).show();
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		finish();
	}

	public void uploadResults(View v) {

		Toast.makeText(
				this,
				"Results uploaded with nickname " + nicknameTextField.getText()
						+ ".", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		finish();
	}

	private class UploadResultsTask extends AsyncTask<Track, Long, Boolean> {
		@Override
		protected Boolean doInBackground(Track... tracks) {
			if (null != tracks && tracks.length > 0) {
				JsonBuilder builder = new JsonBuilder();
				HttpRequest.tryHttpPost(
						UrlGenerator.uploadResultUrl(""
								+ tracks[0].getTrackNumber()),
						builder.recordToJson(track));
				return true;
			}
			return false;
		}
	}

	public void onClick(View v) {
		Log.d("upload reusult activity", "clicked");
		((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
				.hideSoftInputFromWindow(UploadResultsActivity.this
						.getCurrentFocus().getWindowToken(),
						InputMethodManager.HIDE_NOT_ALWAYS);
	}
}
