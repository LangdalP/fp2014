package server;

import model.CalendarModel;
import protocol.RequestHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * Created with IntelliJ IDEA.
 * User: Christoffer Buvik
 * Date: 07.03.14
 * Time: 14:07
 * To change this template use File | Settings | File Templates.
 */
public class ServerMain {
    private CalendarModel model;
    private RequestHandler requestHandler;
	
	public static final int SERVER_PORT = 54545;

    public ServerMain() {
        model = new ServerModelImpl();
        requestHandler = new RequestHandler(model);
    }


    public static void main(String[] args) {
	}

    public void startServer(){
		// Lagar multitråda server og startar på eigen tråd
		MultiThreadedServer server = new MultiThreadedServer(SERVER_PORT);
		new Thread(server).start();

		// Tek input frå konsoll, slik at admin kan kalle /stop for å lukke connections
		BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
		String inLine;
		try {
			while (!(inLine = consoleReader.readLine()).equals("/stop")) {
				//
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		server.stop();
		System.out.println("Stopped server");


    }
}
