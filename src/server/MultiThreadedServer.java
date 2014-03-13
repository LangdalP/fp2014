package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MultiThreadedServer implements Runnable {
	
    private int serverPort;
    private ServerSocket serverSocket = null;
    private boolean isStopped = false;
    // Kanskje un�dvendig
    private Thread runningThread = null;
    
    // Liste med workers
    private List<ClientWorker> workers = new ArrayList<>();

    public MultiThreadedServer(int port){
        this.serverPort = port;
    }
    
    public void run(){
    	synchronized(this){
    		this.runningThread = Thread.currentThread();
    	}
        openServerSocket();
        while(! isStopped()){
            Socket acceptedSocket = null;
            try {
                acceptedSocket = serverSocket.accept();
            } catch (IOException e) {
                if(isStopped()) {
                    System.out.println("Server Stopped.") ;
                    return;
                }
                throw new RuntimeException(
                    "Error accepting client connection", e);
            }
            System.out.println("Accepting client");
            
            // Lagar ny ClientWorker og tr�d
            ClientWorker newWorker = new ClientWorker(acceptedSocket);
            workers.add(newWorker);
            
            new Thread(newWorker).start();
        }
        System.out.println("Server Stopped.") ;
    }


    private synchronized boolean isStopped() {
        return this.isStopped;
    }

    public synchronized void stop(){
    	// Stoppar lytting
    	for (ClientWorker worker : workers) {
    		worker.stop();
    	}
    	
    	// Lukker sockets
    	for (ClientWorker worker : workers) {
    		try {
				worker.getSocket().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    	
        this.isStopped = true;
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException("Error closing server", e);
        }
    }

    private void openServerSocket() {
        try {
            this.serverSocket = new ServerSocket(this.serverPort);
        } catch (IOException e) {
            throw new RuntimeException("Cannot open port 8080", e);
        }
    }
}
