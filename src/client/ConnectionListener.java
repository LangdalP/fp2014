package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import model.Employee;
import protocol.MessageType;
import protocol.TransferType;
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

                /*  RESPONSE HANDLER  */
                //handleRespons.handle(incomingObj);
				MessageType msgType = incomingObj.getMsgType();
                TransferType transferType = incomingObj.getTransferType();
                System.out.println("CL " + msgType + "\t" + transferType + incomingObj.getObject(0));
                if (msgType == MessageType.REQUEST) continue;

                switch(transferType){
                     case LOGIN: {
                         Boolean success = (Boolean) incomingObj.getObject(0);
                         clientMain.setLoggedin(success);
                         if (success) System.out.println("Login successful!");
                         else System.out.println("Login unsuccessful");
                         break;
                     }
//                     case GET_EMPLOYEES:{
//                         List<Employee>
//                     }

                }

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
