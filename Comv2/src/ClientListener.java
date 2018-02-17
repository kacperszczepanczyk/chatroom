import java.io.PrintWriter;

public class ClientListener implements Runnable {

	private Thread thread;
	private PrintWriter toServer;
	private ClientGUI userGUI;

	ClientListener(PrintWriter toServer, ClientGUI userGUI) {
		this.toServer = toServer;
		this.userGUI = userGUI;
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (isMessageReady()) {
				sendMessageToServer(getMessageFromUser());
				cleanWritingArea();
			}
			if (isUserNameAndPasswordReady()) {
				sendMessageToServer(getUserNameAndPassword());
				cleanChatArea();
			}
		}
	}
	
	private boolean isMessageReady(){
		if (userGUI.isMessageReady)
			return true;
		else
			return false;
	}
	
	private boolean isUserNameAndPasswordReady(){
		if (userGUI.isUserPasswordReady)
			return true;
		else
			return false;
	}
	
	private String getUserNameAndPassword(){
		return userGUI.getUserNameAndPassword();
	}

	private String getMessageFromUser() {
		return userGUI.getUserText();
	}

	private void sendMessageToServer(String message) {
		toServer.println(message);
		userGUI.isMessageReady = false;
		userGUI.isUserPasswordReady = false;
	}

	private void cleanWritingArea() {
		userGUI.setUserText("");
	}
	
	private void cleanChatArea(){
		userGUI.setJDisplayText("");
	}

	public void start() {
		System.out.println("Starting ClientListener named: " + this.toString());
		if (thread == null) {
			thread = new Thread(this, this.toString());
			thread.start();
		}
	}

}