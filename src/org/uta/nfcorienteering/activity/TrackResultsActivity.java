package org.uta.nfcorienteering.activity;

import java.util.Vector;

import com.example.nfcorienteering.R;

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
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


public class TrackResultsActivity extends Activity {

	Button uploadButton, noUploadButton;
	Vector <TextView> resultList = new Vector<TextView>();

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_track_results);
		
		uploadButton = (Button)findViewById(R.id.uploadButton);
		noUploadButton = (Button)findViewById(R.id.noUploadButton);
		
		setResultTable();
		
	}


	public void uploadResults(View v) {
		Intent intent = new Intent(this, UploadResultsActivity.class);
		startActivity(intent);
	}
	
	public void backToStartMenu(View v) {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}
	
	public void setResultTable(){
		
		int controlPointCount = 6;
		
		

	    TableLayout tableLayout = (TableLayout)findViewById(R.id.resultTable);
	
	    
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
	    TableRow tableRow = new TableRow(this);

		
		TextView total = new TextView(this);
		TextView controlPointsGot = new TextView(this);
		TextView totalTime = new TextView(this);
		
		total.setText("Total");
		controlPointsGot.setText("6 / 6");
		totalTime.setText("01:12:56");
		
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
