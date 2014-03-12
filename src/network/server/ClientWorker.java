package network.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import protocol.MessageType;
import protocol.RequestType;
import protocol.ResponseType;
import protocol.TransferObject;

/**
 * Created with IntelliJ IDEA.
 * User: Christoffer Buvik
 * Date: 07.03.14
 * Time: 14:08
 * To change this template use File | Settings | File Templates.
 */
public class ClientWorker implements Runnable {
	
	private Socket serverSocket = null;
	private ObjectInputStream objInput;
	private ObjectOutputStream objOutput;
	
	private boolean isStopped = true;
	private Thread runningThread = null;
	
    public ClientWorker(Socket givenSocket) {
    	this.serverSocket = givenSocket;
    	
    	try {
    		objInput = new ObjectInputStream(serverSocket.getInputStream());
			objOutput = new ObjectOutputStream(serverSocket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    public void run() {
    	isStopped = false;
    	synchronized(this){
    		this.runningThread = Thread.currentThread();
    	}
		while (!isStopped) {
			TransferObject transObj;
			try {
				
				System.out.println("Trying to read object from client!");
				transObj = (TransferObject) objInput.readObject();
				if (transObj == null) {
					stop();
					throw new IOException("Incoming object is null, socket probably closed!");
				}
				
				// Antek at det er login som kjem inn
				String username = (String) transObj.getObject(0);
				String password = (String) transObj.getObject(1);
				
				System.out.println("Got login from: " + username + " pw: " + password );
				TransferObject responseObj = new TransferObject(MessageType.RESPONSE, RequestType.NOT_A_REQUEST, ResponseType.LOGIN_OK);
				
				// Skal til å sende svar tilbake
				if (objOutput == null) {
					stop();
					throw new IOException("ObjectOutputStream is null, Socket probably closed!");
				} else {					
					objOutput.writeObject(responseObj);
				}
			} catch (IOException e) {
				// Har lukka workeren
				if(isStopped()) {
                    System.out.println("ClientWorker stopped!") ;
                    return;
				}
				// Klienten kobla frå
				else {
                	System.out.println("Client disconnected!");
                	stop();
                	closeSocket();
                }
			} catch (ClassNotFoundException e) {
				// Skal helst ikkje skje
				e.printStackTrace();
			}
		}
    }

    private synchronized boolean isStopped() {
        return isStopped;
    }
    
    private synchronized void closeSocket() {
    	try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
    public synchronized void stop(){
        this.isStopped = true;
    }
    
    public Socket getSocket() {
    	return serverSocket;
    }
}
