package org.uta.nfcorienteering.test;

import junit.framework.TestCase;

import org.uta.nfcorienteering.http.HttpRequest;
import org.uta.nfcorienteering.http.UrlGenerator;

public class TestHttpRequet extends TestCase{
	String getUrl = "http://www.google.com";
		
	public void testHttpGet(){
		String content = "";
		content = HttpRequest.tryHttpGet(getUrl);
		assertFalse((content == ""));
	}
	
	public void testHttpPost(){
		String postUrl = UrlGenerator.uploadResultUrl("123");
		String postContent = "123";
		String result = "";
		
		result = HttpRequest.tryHttpPost(postUrl, postContent);
		
		assertFalse((result == ""));
	}
}
