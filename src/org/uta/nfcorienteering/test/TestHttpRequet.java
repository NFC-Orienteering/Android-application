package org.uta.nfcorienteering.test;

import junit.framework.TestCase;

import org.uta.nfcorienteering.http.HttpRequest;

public class TestHttpRequet extends TestCase{
	String url = "http://www.google.com";
		
	public void testHttpGet(){
		String content = "";
		content = HttpRequest.tryHttpGet(url);
		assertFalse((content == ""));
	}
}
