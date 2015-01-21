package org.uta.nfcorienteering.test;

import java.util.ArrayList;
import java.util.List;

import org.uta.nfcorienteering.event.Track;
import org.uta.nfcorienteering.utility.LocalStorage;

import android.test.AndroidTestCase;

public class TestLocalStorage extends AndroidTestCase {


	public void TestReadOrienteeringHistory() {
		LocalStorage localStorage = new LocalStorage(getContext());
		localStorage.saveOrienteeringHistory(getTestData());
		
		List<Track> data = localStorage.readOrienteeringHistory();

		assertEquals(2, data.size());
		assertEquals("Track 1", data.get(0).getTrackName());
		assertEquals("Track 2", data.get(1).getTrackName());		
	}

	private List<Track> getTestData() {
		Track track1 = new Track();
		track1.setTrackName("Track 1");
		Track track2 = new Track();
		track2.setTrackName("Track 2");		

		List<Track> tracks = new ArrayList<Track>();
		tracks.add(track1);
		tracks.add(track2);

		return tracks;
	}

}
