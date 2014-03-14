package server;

import model.impl.ModelImpl;
import db.ModelDbImpl;
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
    private ModelImpl model;
    private RequestHandler requestHandler;
    public static final int SERVER_PORT = 54545;

    public ServerMain() {
        model = new ModelImpl();
        
        ModelDbImpl modeldb = new ModelDbImpl(model);
        // Lastar modell frå db
        model.setEmployees(modeldb.getEmployees());
        model.setGroups(modeldb.getGroups());
        
        model.setFutureMeetings(modeldb.getAllMeetings());
        requestHandler = new RequestHandler(model);

        System.out.println("future meetings: " + model.getFutureMeetings());
    }


    public static void main(String[] args) {
        new ServerMain().startServer();
	}

    public void startServer(){
		// Lagar multitrï¿½da server og startar pï¿½ eigen trï¿½d
		MultiThreadedServer server = new MultiThreadedServer(SERVER_PORT);
		new Thread(server).start();

		// Tek input frï¿½ konsoll, slik at admin kan kalle /stop for ï¿½ lukke connections
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
