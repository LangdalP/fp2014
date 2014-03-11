package client_network;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientConnection {
	
	private Socket clientSocket;
	private DataOutputStream outToServer;
	
	
	public ClientConnection(String ip, int port) {
		
		try {
			clientSocket = new Socket(ip, port);
			outToServer = new DataOutputStream(clientSocket.getOutputStream());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public Socket getConnectionSocket() {
		return clientSocket;
	}
	
	public void sendString(String msg) {
		
		try {
			outToServer.writeBytes(msg + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void close() {
		try {
			clientSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
