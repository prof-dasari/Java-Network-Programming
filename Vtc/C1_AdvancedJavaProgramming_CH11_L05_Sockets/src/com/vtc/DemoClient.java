package com.vtc;

import java.io.PrintWriter;
import java.net.Socket;

public class DemoClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try
		{
			System.out.println("Starting Client:");
			Socket socket = new Socket("localhost",55555);
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream(),true);
			printWriter.println("Hello from Client");
			printWriter.println("Connected, Yes!");
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}

}
