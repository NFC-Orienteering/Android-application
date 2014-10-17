package org.uta.nfcorienteering.activity;

import org.uta.nfcorienteering.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint.Style;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


public class ActiveOrienteeringEventActivity extends BaseNfcActivity {

	Button nextButton;
	final Context context = this;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_active_orienteering_event);
		
		nextButton = (Button)findViewById(R.id.activeEventNextButton);

		
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
		else {
			ret = super.onOptionsItemSelected(item);
		}
		return ret;
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
		int controlPointCount = 6;
		
	    TableLayout tableLayout = (TableLayout)dialog.findViewById(R.id.eventResultTable);
	    TableLayout.LayoutParams params = new TableLayout.LayoutParams(TableLayout.
	    		LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
	    
	    params.setMargins(5,10,5,10);
	    
	    ShapeDrawable border = new ShapeDrawable(new RectShape());
		border.getPaint().setStyle(Style.STROKE);
		border.getPaint().setColor(Color.GREEN);
	    
	    
	    for(int i = 0; i < controlPointCount; i++) {
	    	TableRow tableRow = new TableRow(this);

	    	TextView rowNumber = new TextView(this);
			TextView controlPointNumber = new TextView(this);
			TextView controlPointTime = new TextView(this);
			
			rowNumber.setPadding(5, 5, 5, 5);
			controlPointNumber.setPadding(5, 5, 5, 5);
			controlPointTime.setPadding(5, 5, 5, 5);
			
			rowNumber.setText(String.valueOf(i+1) + ".");
			controlPointNumber.setText("Point " + String.valueOf(Math.floor(Math.random() * 16)));
			controlPointTime.setText(String.valueOf(Math.floor(Math.random()*14)) + " : " + String.valueOf(Math.floor(Math.random()* 59)));
	    	
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
	    dialog.show();
	
	
	}
		
		
	public void trackFinished(View v){
		
		Intent intent = new Intent(this, TrackResultsActivity.class);
		startActivity(intent);
	}

	@Override
	public void postNfcRead(String result) {
		// TODO Auto-generated method stub
		
	}
}
