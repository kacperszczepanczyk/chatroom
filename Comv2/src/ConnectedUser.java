import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

public class ConnectedUser implements Runnable {

	private Thread thread;
	private Socket socket;
	private BufferedReader fromUser;
	private PrintWriter toUser;
	private Protocol protocol;
	private String name;

	ConnectedUser(Socket clientSocket, Protocol protocol, String name) {
		this.socket = clientSocket;
		this.protocol = protocol;
		this.name = name;
		createInputOutputToUser();
	}

	@Override
	public void run() {
		try {
			while (true) {
				protocol.saveMessage(name, fromUser.readLine());
			}
		} catch (IOException e) {
			System.out.println("Exception caught when trying to communicate with user named: " + name + ", socket name: " + socket.toString());
		}
	}

	public void start() {
		if (thread == null) {
			thread = new Thread(this, socket.toString());
			thread.start();
			System.out.println("Started connection for: " + name);
		}
	}
	
	private void createInputOutputToUser() {		
		try {
			toUser = new PrintWriter(socket.getOutputStream(), true);
			fromUser = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}		 		
	}

	public String getName() {
		return name;		
	}
	
	public PrintWriter getPrintWriter() {
		return toUser;
	}

}
