package network.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import protocol.MessageType;
import protocol.ResponseType;
import protocol.TransferObject;

public class ConnectionListener implements Runnable {
	
	private Socket clientSocket;
	private ObjectInputStream objInput;
	private boolean isStopped = true;
	
	public ConnectionListener(Socket clientSocket) {
		this.clientSocket = clientSocket;
		
		try {
			objInput = new ObjectInputStream(this.clientSocket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		isStopped = false;
		while (!isStopped) {
			TransferObject incomingObj;
			try {
				System.out.println("Trying to read incoming object from server!");
				incomingObj = (TransferObject) objInput.readObject();
				
				// Antek at det er login response frå server
				if (incomingObj.getMsgType() == MessageType.RESPONSE && incomingObj.getRespType() == ResponseType.LOGIN_OK) {
					System.out.println("Login successful!");
				}
				
				
			} catch (IOException e) {
				if(isStopped()) {
                    System.out.println("ClientListener stopped!") ;
                    return;
                } else {
                	System.out.println("Socket closed!");
                	return;
                }
			} catch (ClassNotFoundException e) {
				// Skal forhåpentligvis ikkje skje
				e.printStackTrace();
			}
		}
	}
	
	private synchronized boolean isStopped() {
        return isStopped;
    }
	
	public synchronized void stop(){
        this.isStopped = true;
    }
	
}
