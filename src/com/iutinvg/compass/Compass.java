package com.iutinvg.compass;

import org.uta.nfcorienteering.activity.ActiveOrienteeringEventActivity;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class Compass implements SensorEventListener {
	private static final String TAG = "Compass";

	private SensorManager sensorManager;
	private Sensor gsensor;
	private Sensor msensor;
	private float[] mGravity = new float[3];
	private float[] mGeomagnetic = new float[3];
	private float azimuth = 0f;
	private float currectAzimuth = 0;
	private Context context;
	private float orientation[] = new float[3];

	// compass arrow to rotate
	public ImageView arrowView = null;

	public Compass(Context context) {
		this.context = context;
		sensorManager = (SensorManager) context
				.getSystemService(Context.SENSOR_SERVICE);
		gsensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		msensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
	}

	public void start() {
		sensorManager.registerListener(this, gsensor,
				SensorManager.SENSOR_DELAY_GAME);
		sensorManager.registerListener(this, msensor,
				SensorManager.SENSOR_DELAY_GAME);
	}

	public void stop() {
		sensorManager.unregisterListener(this);
	}

	private void adjustArrow() {
		if (arrowView == null) {
			Log.i(TAG, "arrow view is not set");
			return;
		}

		//Log.i(TAG, "will set rotation from " + currectAzimuth + " to "
	    //	+ azimuth);

		Animation an = new RotateAnimation(-currectAzimuth, -azimuth,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		currectAzimuth = azimuth;

		an.setDuration(500);
		an.setRepeatCount(0);
		an.setFillAfter(true);

		arrowView.startAnimation(an);
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		final float alpha = 0.97f;

		synchronized (this) {
			if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

				mGravity[0] = alpha * mGravity[0] + (1 - alpha)
						* event.values[0];
				mGravity[1] = alpha * mGravity[1] + (1 - alpha)
						* event.values[1];
				mGravity[2] = alpha * mGravity[2] + (1 - alpha)
						* event.values[2];

				// mGravity = event.values;

				// Log.e(TAG, Float.toString(mGravity[0]));
			}

			if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
				// mGeomagnetic = event.values;

				mGeomagnetic[0] = alpha * mGeomagnetic[0] + (1 - alpha)
						* event.values[0];
				mGeomagnetic[1] = alpha * mGeomagnetic[1] + (1 - alpha)
						* event.values[1];
				mGeomagnetic[2] = alpha * mGeomagnetic[2] + (1 - alpha)
						* event.values[2];
				// Log.e(TAG, Float.toString(event.values[0]));

			}

			float R[] = new float[9];
			float I[] = new float[9];
			boolean success = SensorManager.getRotationMatrix(R, I, mGravity,
					mGeomagnetic);
			if (success) {
				
				int axisX = 0;
				int axisY = 0;
				
				 Display display;         
				 display = ((Activity) context).getWindow().getWindowManager().getDefaultDisplay();
		         
				 int mScreenRotation = display.getRotation();
				 
				 SensorManager.getOrientation(R, orientation);
		         
				switch (mScreenRotation) {
			    case Surface.ROTATION_0:
			    	axisX = SensorManager.AXIS_X;
			        axisY = SensorManager.AXIS_Y;
			    	//fixRotation0(orientation);
			        break;

			    case Surface.ROTATION_90:
			    	axisX = SensorManager.AXIS_Y;
			        axisY = SensorManager.AXIS_MINUS_X;
			    	//fixRotation90(orientation);
			        break;

			    case Surface.ROTATION_180:
			    	axisX = SensorManager.AXIS_MINUS_X;
			        axisY = SensorManager.AXIS_MINUS_Y;
			    	//fixRotation180(orientation);
			        break;

			    case Surface.ROTATION_270:
			    	axisX = SensorManager.AXIS_MINUS_Y;
			        axisY = SensorManager.AXIS_X;
			    	//fixRotation270(orientation);
			        break;

			    default:
			        break;
			}
				boolean remapped = SensorManager.remapCoordinateSystem(R, axisX, axisY, R);
				
				
				// Log.d(TAG, "azimuth (rad): " + azimuth);
				azimuth = (float) Math.toDegrees(orientation[0]); // orientation
				azimuth = (azimuth + 360) % 360;
				// Log.d(TAG, "azimuth (deg): " + azimuth);
				adjustArrow();
			}
		}
	}
	public static final void fixRotation0(float[] orientation) {//azimuth, pitch, roll
		orientation[1] = -orientation[1];// pitch = -pitch
	}
	public static final void fixRotation90(float[] orientation) {//azimuth, pitch, roll
		orientation[0] += Math.PI/2f; // offset
		float tmpOldPitch = orientation[1];
		orientation[1] = -orientation[2]; //pitch = -roll
		orientation[2] = -tmpOldPitch; // roll = -pitch
	}
	public static final void fixRotation180(float[] orientation) {//azimuth, pitch, roll
		orientation[0] = (float) (orientation[0] > 0f ? (orientation[0] - Math.PI): (orientation[0] + Math.PI));// offset
		orientation[2] = -orientation[2];// roll = -roll
	}
	public static final void fixRotation270(float[] orientation) {//azimuth, pitch, roll
		orientation[0] -= Math.PI/2;// offset
		float tmpOldPitch = orientation[1];
		orientation[1] = orientation[2]; //pitch = roll
		orientation[2] = tmpOldPitch; // roll = pitch
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}
}