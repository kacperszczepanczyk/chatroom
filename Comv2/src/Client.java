
import java.io.*;
import java.net.*;

public class Client {
	
	private String hostIP;
	private int portNumber;
	private ClientGUI userGUI;
	private ServerListener serverReader;
	private ClientListener clientReader;
	private PrintWriter toServer;
	private BufferedReader fromServer;
	private Socket socket;
	
	Client(String hostIP, int portNumber){
		this.hostIP = hostIP;
		this.portNumber = portNumber;
	}
	
	void createInputOutputToServer(){
		try {
			socket = new Socket(InetAddress.getByName(hostIP), portNumber);				
			toServer = new PrintWriter(socket.getOutputStream(), true);		 		
			fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	public void initializeGUI(){
		userGUI = new ClientGUI();
		userGUI.start();
	}
	
	public void initializeServerListener(){
		serverReader = new ServerListener(fromServer, userGUI);
		serverReader.start();
	}
	
	public void initializeClientListener(){
		clientReader = new ClientListener(toServer, userGUI);
		clientReader.start();
	}
	
}