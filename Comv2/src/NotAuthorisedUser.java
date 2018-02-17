import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
//import java.io.PrintWriter;
import java.net.*;

public class NotAuthorisedUser implements Runnable {

	private Thread thread;
	private Socket socket;
	private BufferedReader fromUser;
	private PrintWriter toUser;
	private Broadcaster broadcaster;
	private Protocol protocol;
	private Authenticator authenticator;
	private String name;

	NotAuthorisedUser(Socket clientSocket, Protocol protocol, Broadcaster broadcaster) {
		this.socket = clientSocket;
		this.protocol = protocol;
		this.broadcaster = broadcaster;
		createInputOutputToUser();
	}

	@Override
	public void run() {
		try {
			while (true) {
				authenticator = new Authenticator(fromUser.readLine());
				if(authenticator.authoriseUser() && !isAlreadyInChat(authenticator.extractUserName())){
					toUser.println("AUTHORISED");
					addUserToChat(authenticator.extractUserName(), socket);
					stopThread();
					break;
				}
				else{
					toUser.println("NOT AUTHORISED");
				}
			}
		} catch (IOException e) {
			System.out.println("Exception caught when trying to communicate with user named: " + socket.toString());
			System.out.println(e.getMessage());
		}
	}

	public void start() {
		if (thread == null) {
			thread = new Thread(this, socket.toString());
			thread.start();
			System.out.println("Started notAuthorisedUser: " + socket.toString());
		}
	}
	
	 private void stopThread() {
	        thread.interrupt();
	        thread = null;
	    }
	
	private void createInputOutputToUser() {		
		try {
			toUser = new PrintWriter(socket.getOutputStream(), true);
			fromUser = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}		 		
	}
	
	private void addUserToChat(String name, Socket clientSocket) {
		ConnectedUser newUser = new ConnectedUser(clientSocket, protocol, name);
		broadcaster.addUser(newUser);
		newUser.start();
	}
	
	private boolean isAlreadyInChat(String name){
		return broadcaster.isAlreadyInChat(authenticator.extractUserName());
	}

	public String getName() {
		return name;		
	}
	
	public PrintWriter getUserPrintWriter() {
		return toUser;
	}

}
