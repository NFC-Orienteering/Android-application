package org.uta.nfcorienteering.utility;

import java.util.Timer;
import java.util.TimerTask;

public class Stopwatch {
	private int timerReadingMillis = 0;
	private int tickMillis = 50;

	private Timer timer = null;
	private TimerTask timerTask = null;

	public Stopwatch() {
		initTimer();
	}

	public void initTimer() {
		timer = new Timer();
		timerTask = new TimerTask() {

			@Override
			public void run() {
				timerReadingMillis += tickMillis;
			}
		};
	}

	public void start() {
		timerReadingMillis = 0;
		timer.schedule(timerTask, 0, tickMillis);
	}

	public String readTimeMillis() {
		return "" + timerReadingMillis;
	}

	public String readTimerSeconds() {
		return "" + (timerReadingMillis / 1000);
	}

	public void stop() {
		timerTask.cancel();
		timerTask = null;
		timer.cancel();
		
		//System.gc();
	}

}
