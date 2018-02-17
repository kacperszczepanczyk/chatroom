import java.io.*;

public class ServerListener implements Runnable {

	private Thread thread;
	private BufferedReader fromServer;
	private ClientGUI userGUI;
	private String toUserMessage;
	private boolean isAuthorised;

	ServerListener(BufferedReader fromServer, ClientGUI userGUI) {
		this.fromServer = fromServer;
		this.userGUI = userGUI;
	}

	private String getMessageFromServer() {
		try {
			return fromServer.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private void addMessageToDisplayArea(String message) {
		userGUI.addJDisplayText(message);
	}

	private void scrollJDisplayAreaDown() {
		userGUI.scrollJDisplayAreaDown();
	}

	@Override
	public void run() {
		while (true) {
			toUserMessage = getMessageFromServer();
			if (toUserMessage != null) {
				if(toUserMessage.equals("AUTHORISED") && isAuthorised == false){
					userGUI.enableChat(true);
					userGUI.enableAuthenticator(false);
					isAuthorised = true;
				}
				addMessageToDisplayArea(toUserMessage);
				scrollJDisplayAreaDown();
			}
		}
	}

	public void start() {
		System.out.println("Starting ServerListener named: " + this.toString());
		if (thread == null) {
			thread = new Thread(this, this.toString());
			thread.start();
		}
	}

}
