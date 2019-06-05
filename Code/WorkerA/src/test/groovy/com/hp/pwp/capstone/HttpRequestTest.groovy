package com.hp.pwp.capstone
import groovy.mock.interceptor.MockFor
import org.junit.Test

public class HttpRequestTest extends GroovyTestCase {
    @Test
    void testBadURLGet() {
      HttpRequest test = new HttpRequest();
      String badURL = "This Is a Bad URL"
      String badURLGET = test.get(badURL);
      assert(badURLGET == "error: unable to open connection with " + badURL);
    }
    @Test 
    void testBadURLPOST() {
      HttpRequest test = new HttpRequest();
      String badURL = "This Is a Bad URL"
      String badURLGET = test.post(badURL, badURL);
      assert(badURLGET == "error: unable to open connection with " + badURL);
    }
	 @Test
	 void testValidGET(){
		 HttpRequest test = new HttpRequest();
		 String url = "http://postman-echo.com/get"
		 String response = test.get(url);
		 assert(response != "error: unable to open connection with " + url)
	 }
	 @Test
	 void testBadResponseCode(){
		 HttpRequest test = new HttpRequest();
		 String url = "https://postman-echo.com/status/500";
		 String response = test.get(url);
		 assert(response == "error: 500");
	 }
	 @Test
	 void testGoodPOSTResponse(){
		 HttpRequest test = new HttpRequest();
		 String url="https://postman-echo.com/post";
		 String data = "test string";
		 String response = test.post(url,data);
		 assert(!response.startsWith("error:"));
	 }
}
