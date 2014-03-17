package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import protocol.*;

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
    private boolean loggedIn = false;
    private static String username = "";
	
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
                TransferType transferType = transObj.getTransferType();
                MessageType messageType = transObj.getMsgType();

                if (messageType == MessageType.REQUEST && transferType == TransferType.LOGIN){
                    loggedIn = RequestHandler.handleLogin(transObj);
                    System.out.println("From server: Client logged in: " + loggedIn);
                    objOutput.writeObject(new TransferObject(MessageType.RESPONSE, TransferType.LOGIN, loggedIn));
                }

                boolean success = false;
                try {
                    if (loggedIn){
                        if (messageType == messageType.REQUEST && transferType == TransferType.INIT_MODEL){
                            RequestHandler.handleInit(transObj, username);
                        }
                        else RequestHandler.handleRequest(transObj, objOutput);
                    }
                    else System.out.println("Client not authorized yet!");
                    success = true;
                } catch(IOException ioexception){
                    //SUCCESS FORBLIR FALSE.
                    ioexception.printStackTrace();
                }

                if (objOutput == null) {
                    stop();
                    throw new IOException("ObjectOutputStream is null, Socket probably closed!");
                } else {
                    //@todo send ok/failed response back based on success.

                }
            } catch (IOException e) {
                // Har lukka workeren
                if(isStopped()) {
                    System.out.println("ClientWorker stopped!") ;
                    return;
                }
                // Klienten kobla fr�
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
