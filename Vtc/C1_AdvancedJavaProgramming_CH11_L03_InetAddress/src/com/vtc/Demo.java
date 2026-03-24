package com.vtc;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Demo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			InetAddress Address = InetAddress.getLocalHost();
			Address = InetAddress.getByName("google.com");
			System.out.println(Address);
			InetAddress SW[] = InetAddress.getAllByName("www.nba.com");
			for (int i = 0; i < SW.length; i++)
				System.out.println(SW[i]);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

	}

}
