package com.vtc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Demo {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {

		 // hypertext transfer protocol
	    isProtocolSupported("http://www.vtc.com");
	    // secure http
	    isProtocolSupported("https://www.amazon.com/exec/obidos/order2/");
	    // file transfer protocol
	    isProtocolSupported("ftp://metalab.unc.edu/pub/languages/java/javafaq/");
	    // telnet
	    isProtocolSupported("telnet://dibner.poly.edu/");
	    // local file access
	    isProtocolSupported("file:///etc/passwd");
	    // gopher
	    isProtocolSupported("gopher://gopher.anc.org.za/");
	    // Lightweight Directory Access Protocol
	    
	    
	    URL url = new URL("http://www.vtc.com/");
	    URLConnection vtcURLConnection = url.openConnection();
	    vtcURLConnection.connect();
	    
		BufferedReader in = new BufferedReader(new InputStreamReader(
				vtcURLConnection.getInputStream()));
		String inputLine;
		while ((inputLine = in.readLine()) != null)
			System.out.println(inputLine);
		in.close();

	}
	
	private static void isProtocolSupported(String url) {
	    try {
	      URL u = new URL(url);
	      System.out.println(u.getProtocol( ) + " is supported");
	    }
	    catch (MalformedURLException ex) {
	      String protocol = url.substring(0, url.indexOf(':'));
	      System.out.println(protocol + " is not supported");
	    }
	  }

}
