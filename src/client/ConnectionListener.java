package client;

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
        private final ClientMain clientMain;
	
	public ConnectionListener(Socket clientSocket, ClientMain clientMain) {
		this.clientSocket = clientSocket;
                this.clientMain = clientMain;
		
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
				
                                /*
                                 *  RESPONSE HANDLER
                                 */
                                //handleRespons.handle(incomingObj);
				
                                // Antek at det er login response fr� server
				if (incomingObj.getMsgType() == MessageType.RESPONSE && incomingObj.getRespType() == ResponseType.LOGIN_OK) {
                                    clientMain.setLoggedin(true); 
                                    System.out.println("Login successful!");
				}
                                if (incomingObj.getMsgType() == MessageType.RESPONSE && incomingObj.getRespType() == ResponseType.LOGIN_FAILED) {
                                    clientMain.setLoggedin(false); 
                                    System.out.println("Login unsuccessful");
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
				// Skal forh�pentligvis ikkje skje
				e.printStackTrace();
			}
		}
                //end while
	}
	
	private synchronized boolean isStopped() {
        return isStopped;
    }
	
	public synchronized void stop(){
        isStopped = true;
    }
	
}
