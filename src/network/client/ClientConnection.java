package network.client;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import protocol.TransferObject;

public class ClientConnection {
	
	private Socket clientSocket;
	private ObjectOutputStream objOutput;
	
	
	public ClientConnection(String ip, int port) {
		
		try {
			clientSocket = new Socket(ip, port);
			objOutput = new ObjectOutputStream(clientSocket.getOutputStream());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public Socket getConnectionSocket() {
		return clientSocket;
	}
	
	public void sendTransferObject(TransferObject transObj) {
		
		try {
			objOutput.writeObject(transObj);
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
