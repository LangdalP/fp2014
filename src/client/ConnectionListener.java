package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import model.Employee;
import model.impl.ModelImpl;
import protocol.MessageType;
import protocol.TransferType;
import protocol.TransferObject;

public class ConnectionListener implements Runnable {
	
	private Socket clientSocket;
	private ObjectInputStream objInput;
	private boolean isStopped = true;
    private ClientMain clientMain;
    private ResponseHandler responseHandler;

	public ConnectionListener(Socket clientSocket, ClientMain clientMain) {
		this.clientSocket = clientSocket;
                this.clientMain = clientMain;
		
		try {
			objInput = new ObjectInputStream(this.clientSocket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
        responseHandler = new ResponseHandler();
	}

	@Override
	public void run() {
		isStopped = false;
		while (!isStopped) {
			TransferObject incomingObj;
			try {
				System.out.println("Trying to read incoming object from server!");
				incomingObj = (TransferObject) objInput.readObject();

				MessageType msgType = incomingObj.getMsgType();
                TransferType transferType = incomingObj.getTransferType();
                if (msgType == MessageType.REQUEST) continue;
                
                System.out.println("Is response");
                
                if (transferType == TransferType.LOGIN) {
                	Boolean success = (Boolean) incomingObj.getObject(0);
                	ClientMain.setLoggedin(success);
                	if (success) {
                		System.out.println("Logged in!");
                	}
                } else if (transferType == TransferType.INIT_MODEL){
                	System.out.println("Inside init model");
                    ModelImpl model = responseHandler.handleInit(incomingObj);
                    clientMain.setModel(model);
                } else responseHandler.handleResponse(incomingObj);

			} catch (IOException e) {
                e.printStackTrace();
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
