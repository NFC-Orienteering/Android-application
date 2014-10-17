package org.uta.nfcorienteering.activity;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import org.uta.nfcorienteering.R;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentFilter.MalformedMimeTypeException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class ReadTrackTagActivity extends BaseNfcActivity {

	TextView tagId;
	Button nextButton;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_read_track_tag);
		
		nextButton = (Button) findViewById(R.id.readTagButton);
		tagId = (TextView)findViewById(R.id.tagId);
		

		
	}
	

	public void showTrackInfo(View v){
		
		Intent intent = new Intent(this, TrackInfoActivity.class);
		startActivity(intent);
			
	}


	@Override
	public void postNfcRead(String result) {
		tagId.setText(result);
		
	}
	
}
	