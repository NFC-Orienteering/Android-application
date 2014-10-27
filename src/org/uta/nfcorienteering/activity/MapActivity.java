package org.uta.nfcorienteering.activity;

import org.uta.nfcorienteering.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.LinearLayout.LayoutParams;

import com.polites.android.GestureImageView;

public class MapActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// initGestureImageView();
	}

	/*
	 * This is another way of setting GestureImageView from athor's tutorial
	 */
	@SuppressWarnings("unused")
	private void initGestureImageView() {
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);

		GestureImageView view = new GestureImageView(this);
		view.setImageResource(R.drawable.ic_launcher);
		view.setLayoutParams(params);

		// ViewGroup layout = (ViewGroup) findViewById(R.id.layout);
		// layout.addView(view);
	}

	private void setImageDrable(Drawable drawable) {
		GestureImageView imageView = (GestureImageView) findViewById(R.id.gesture_image_view);
		imageView.setImageDrawable(drawable);
	}

	private void setImageBitmap(Bitmap bitmap) {
		GestureImageView imageView = (GestureImageView) findViewById(R.id.gesture_image_view);
		imageView.setImageBitmap(bitmap);
	}
}
