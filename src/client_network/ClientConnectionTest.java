package client_network;

public class ClientConnectionTest {
	
	public static final String serverIP = "localhost";
	public static final int serverPort = 54545;
	
	public static void main(String[] args) {
		
		ClientConnection clientConn = new ClientConnection(serverIP, serverPort);
		ConnectionListener listener = new ConnectionListener(clientConn.getConnectionSocket());
		
		Thread listenThread = new Thread(listener);
		listenThread.start();
		
		clientConn.sendString("liten beskjed!");
		
		try {
			Thread.sleep(5*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		clientConn.close();
		
	}

}
