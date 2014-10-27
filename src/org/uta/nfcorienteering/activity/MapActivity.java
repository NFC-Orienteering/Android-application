package org.uta.nfcorienteering.activity;

import org.uta.nfcorienteering.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout.LayoutParams;

import com.polites.android.GestureImageView;

public class MapActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);

		GestureImageView view = new GestureImageView(this);
		view.setImageResource(R.drawable.ic_launcher);
		view.setLayoutParams(params);

		ViewGroup layout = (ViewGroup) findViewById(R.id.gesture_image_view);

		layout.addView(view);
	}
}
