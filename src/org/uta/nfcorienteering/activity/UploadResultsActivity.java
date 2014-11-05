package org.uta.nfcorienteering.activity;

import org.uta.nfcorienteering.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UploadResultsActivity extends Activity {

	EditText nicknameTextField;
	Button uploadResultsButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_upload_results);

		nicknameTextField = (EditText) findViewById(R.id.nicknameTextField);
		uploadResultsButton = (Button) findViewById(R.id.uploadButton);

	}

	public void uploadResults(View v) {

		Toast.makeText(this, "Results uploaded with nickname " + nicknameTextField.getText() + ".", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		finish();
		
		
		/*
		 * In this method the process of uploading the results to webserver
		 * should be implemented. After that, return back to MainActivity.class
		 */
	}
}
