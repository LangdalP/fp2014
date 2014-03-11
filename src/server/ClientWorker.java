package server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created with IntelliJ IDEA.
 * User: Christoffer Buvik
 * Date: 07.03.14
 * Time: 14:08
 * To change this template use File | Settings | File Templates.
 */
public class ClientWorker implements Runnable {
	
	private Socket serverSocket = null;
	private BufferedReader inputReader;
	private DataOutputStream outputPrinter;
	
	private boolean isStopped = true;
	private Thread runningThread = null;
	
    public ClientWorker(Socket givenSocket) {
    	this.serverSocket = givenSocket;
    	
    	try {
			this.inputReader = new BufferedReader(
					new InputStreamReader(this.serverSocket.getInputStream()));
			this.outputPrinter = new DataOutputStream(givenSocket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	System.out.println("Done with ClientWorker constructor");
    }

    public void run() {
    	isStopped = false;
    	synchronized(this){
    		this.runningThread = Thread.currentThread();
    	}
    	System.out.println("Starting to listen!");
		while (!isStopped) {
			String incomingMsg;
			String capitalizedMsg = "";
			try {
				
				System.out.println("Trying to read line from client!");
				incomingMsg = inputReader.readLine();
				if (incomingMsg == null) {
					stop();
					throw new IOException("Incoming message is null, socket probably closed!");
				}
				System.out.println(incomingMsg);
				capitalizedMsg = incomingMsg.toUpperCase();
				
				// Skal til å sende svar tilbake
				if (outputPrinter == null) {
					stop();
					throw new IOException("DataOutputStream is null, Socket probably closed!");
				} else {					
					outputPrinter.writeBytes(capitalizedMsg + "\n");
				}
			} catch (IOException e) {
				if(isStopped()) {
                    System.out.println("ClientWorker stopped!") ;
                    return;
                }
			}
		}
    }

    private synchronized boolean isStopped() {
        return isStopped;
    }
	
    public synchronized void stop(){
        this.isStopped = true;
    }
    
    public Socket getSocket() {
    	return serverSocket;
    }
}
