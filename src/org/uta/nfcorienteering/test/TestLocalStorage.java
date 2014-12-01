package org.uta.nfcorienteering.test;

import java.util.ArrayList;

import org.uta.nfcorienteering.utility.LocalStorage;

import android.test.AndroidTestCase;

public class TestLocalStorage extends AndroidTestCase {

	LocalStorage localStorage;

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		localStorage = new LocalStorage(getContext());

		localStorage.saveToSharedPreference("test", getTestData());
	}

	public void TestLocalStorage() {
		ArrayList<String> strings = (ArrayList<String>) localStorage
				.readFromSharedPreference("test");

		ArrayList<String> data = getTestData();
		for (int i = 0; i < data.size(); i++) {
			assertEquals(strings.get(i), data.get(i));
		}

	}

	private ArrayList<String> getTestData() {
		String s1 = "string1";
		String s2 = "stirng2";

		ArrayList<String> strings = new ArrayList<String>();
		strings.add(s1);
		strings.add(s2);

		return strings;
	}

}
