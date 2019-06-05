package com.hp.pwp.capstone

public class HttpRequest {
  public static String get(String iURL) {
    try {
      // Create a new URL object from the given input URL and open a connection with it.
      def get = new URL(iURL)
        .openConnection();

      // Get the response code from the given url
      def getRC = get.getResponseCode();
      println(getRC); // Print ResponseCode.
      
      // If the ResponseCode is 200, get response and return.
      if( getRC.equals(200)) {
        return get.getInputStream().getText();
      }
      // Otherwise return the response code recieved.
      else {
        return "error: " + Integer.toString(getRC);
      }
    }
    catch (Exception ex) {
      // If the connection fails return an error message.
      return "error: unable to open connection with " + iURL;
    }
  }

  public static String post(String iURL, String message) {
    try {
      // Create a new URL object from the given input URL and open a connection with it.
      def post = new URL(iURL)
        .openConnection();

      // Configure URL object for HTTP post.
      post.setRequestMethod("POST")
      post.setDoOutput(true)
      post.setRequestProperty("Content-Type", "application/json")

      // Submit post request with given message.
      post.getOutputStream()
        .write(message.getBytes("UTF-8"));
      def postRC = post.getResponseCode();
      println(postRC);
      if(postRC.equals(200)) {
        return post.getInputStream().getText();
      }
      else {
        return "error: " + Integer.toString(postRC);
      }
    }

    catch (Exception ex) { 
      // If the connection fails
      return "error: unable to open connection with " + iURL;
    }
  }
}
