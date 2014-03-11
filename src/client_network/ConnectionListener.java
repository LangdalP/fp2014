package client_network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ConnectionListener implements Runnable {
	
	private Socket clientSocket;
	private BufferedReader reader;
	private boolean listening = false;
	
	public ConnectionListener(Socket clientSocket) {
		this.clientSocket = clientSocket;
		
		try {
			reader = new BufferedReader(
					new InputStreamReader(this.clientSocket.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Done with ConnectionListener constructor");
	}

	@Override
	public void run() {
		listening = true;
		while (listening) {
			String incomingMsg;
			try {
				System.out.println("Trying to read incoming line from server!");
				incomingMsg = reader.readLine();
				
				System.out.println(incomingMsg);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
