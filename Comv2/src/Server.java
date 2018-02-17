import java.net.*;
import java.io.*;

public class Server implements Runnable {

	private Thread t;
	private String serverName;
	private int portNumber;
	private Protocol protocol;
	private Broadcaster broadcaster;

	Server(int portNumber, String serverName) {
		this.serverName = serverName;
		this.portNumber = portNumber;
		protocol = new Protocol();
		initializeBroadcaster();
		System.out.println("Creating " + serverName);
	}

	@Override
	public void run() {
		System.out.println("Running Server");
		try (ServerSocket serverSocket = new ServerSocket(portNumber);) {
			
			while (true){
				Socket clientSocket = serverSocket.accept();
				if (clientSocket != null){
					NotAuthorisedUser newNotAuthorisedUser = new NotAuthorisedUser(clientSocket, protocol, broadcaster);
					newNotAuthorisedUser.start();
				}
			}
		} catch (IOException e) {
			System.out.println(
					"Exception caught when trying to listen on port " + portNumber + " or listening for a connection");
			System.out.println(e.getMessage());
		}
	}

	private void initializeBroadcaster() {
		broadcaster = new Broadcaster(protocol);
		broadcaster.start();
	}

	public void start() {
		System.out.println("Starting server named: " + serverName);
		if (t == null) {
			t = new Thread(this, serverName);
			t.start();
		}
	}
}
