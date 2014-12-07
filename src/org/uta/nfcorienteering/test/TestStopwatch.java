package org.uta.nfcorienteering.test;

import junit.framework.TestCase;

import org.uta.nfcorienteering.utility.Stopwatch;

import android.util.Log;

public class TestStopwatch extends TestCase {
	
	private Stopwatch stopwatch = new Stopwatch();
	
	public void testReadTime() {
		stopwatch.start();
		
		checkTime(100,100);
		checkTime(400,500);
		checkTime(500,1000);
		checkTime(500,1500);
		checkTime(1000,2500);
		
		stopwatch.stop();
	}
	
	private void checkTime(int delayMillis, int expectedTime){
		delay(delayMillis);
		String time = stopwatch.readTimeMillis();
		assertEquals(""+expectedTime, time);
	}
	
	
	private void delay(int millis){
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			Log.i("TestStopwatch", "Interrupted");
		}
	}
}
