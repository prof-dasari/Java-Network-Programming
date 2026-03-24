package com.vtc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class DemoServer {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Wait for client to connect on 55555
		try {
			System.out.println("Starting Server:");
			ServerSocket serverSocket = new ServerSocket(55555);
			Socket clientSocket = serverSocket.accept();
			// Create a reader
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(clientSocket.getInputStream()));
			// Get the client message
			String inputLine;
			while ((inputLine = bufferedReader.readLine()) != null)
				System.out.println("Server Message:"+inputLine);
		} catch (IOException e) {
			System.out.println(e);
		}
	}
}
