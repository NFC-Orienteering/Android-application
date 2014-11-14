package org.uta.nfcorienteering.service;

import org.uta.nfcorienteering.utility.Stopwatch;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class TimerService extends Service {

	private Stopwatch stopwatch = null;
	private StopwatchBinder binder = new StopwatchBinder();

	@Override
	public void onCreate() {
		super.onCreate();
		stopwatch = new Stopwatch();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
			stopwatch.start();
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}

	public class StopwatchBinder extends Binder {
		public void resetStopwatch() {
			stopwatch.stop();
			stopwatch.start();
		}

		public String readTimeMillis() {
			return stopwatch.readTimeMillis();
		}
	}

	@Override
	public void onDestroy() {
		stopSelf();
		stopwatch.stop();
		super.onDestroy();
	}
}
