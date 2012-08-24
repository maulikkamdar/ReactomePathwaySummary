package org.reactome.summary.server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
/**
 * Google Summer of Code 2012 Project
 * Reactome Pathway Summary Visualization
 * @author maulik
 *
 */
/**
 * A utilities class providing methods to make GET and POST requests
 */
public class RESTRequest {

	/**
	 * Makes an HTTP GET Request to the specified URL
	 * @param urlStr URL to make the HTTP GET Request
	 * @return JSON response
	 * @throws IOException
	 */
	public static String httpGet(String urlStr) throws IOException {
		  URL url = new URL(urlStr);
		  HttpURLConnection conn =
		      (HttpURLConnection) url.openConnection();
		  conn.addRequestProperty("Accept", "application/json");
		  if (conn.getResponseCode() != 200) {
		    throw new IOException(conn.getResponseMessage());
		  }

		  // Buffer the result into a string
		  BufferedReader rd = new BufferedReader(
		      new InputStreamReader(conn.getInputStream()));
		  StringBuilder sb = new StringBuilder();
		  String line;
		  while ((line = rd.readLine()) != null) {
		    sb.append(line);
		  }
		  rd.close();

		  conn.disconnect();
		  return sb.toString();
	}
	
	/**
	 * Makes an HTTP POST Request to the url specified with the parameters provided
	 * @param urlStr URL to make the HTTP POST request
	 * @param paramName ArrayList containing name of the parameters
	 * @param paramVal ArrayList containing values of the parameters
	 * @return JSON Response
	 * @throws Exception
	 */
	public static String httpPost(String urlStr, String[] paramName,
			String[] paramVal) throws Exception {
			String boundary = Long.toHexString(System.currentTimeMillis()); 
			String charset = "UTF-8";

			String CRLF = "\r\n"; 
			  URL url = new URL(urlStr);
			  HttpURLConnection conn =
			      (HttpURLConnection) url.openConnection();
			  conn.setRequestMethod("POST");
			  conn.setDoOutput(true);
			  conn.setDoInput(true);
			  conn.setUseCaches(false);
			  conn.setAllowUserInteraction(false);
			  conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
			//  conn.addRequestProperty("Accept", "application/json");
			  // Create the form content
			  OutputStream out = conn.getOutputStream();
			  Writer writer = new OutputStreamWriter(out, "UTF-8");
			// Send normal param.
			    writer.append("--" + boundary).append(CRLF);
			    writer.append("Content-Disposition: form-data; name=\""+paramName[0]+"\"").append(CRLF);
		//	    writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
			    writer.append(CRLF);
			    writer.append(paramVal[0]).append(CRLF).flush();
			System.out.println(paramVal[0]);   
			  writer.close();
			  out.close();

			  if (conn.getResponseCode() != 200) {
			    throw new IOException(conn.getResponseMessage());
			  } 
			  
			  System.out.println(conn.getResponseCode());
			  
			  // Buffer the result into a string
			  BufferedReader rd = new BufferedReader(
			      new InputStreamReader(conn.getInputStream()));
			  StringBuilder sb = new StringBuilder();
			  String line;
			  while ((line = rd.readLine()) != null) {
				 System.out.println("tried" + line);
			    sb.append(line);
			  }
			  rd.close();

			  conn.disconnect();
			  return sb.toString();
			}
}
