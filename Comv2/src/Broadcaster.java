import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Broadcaster implements Runnable {

	static boolean isNewMessage;
	private Thread thread;
	private Protocol protocol;
	private List<ConnectedUser> users;
	static int userCount;

	Broadcaster(Protocol protocol) {
		users = new LinkedList<ConnectedUser>();
		this.protocol = protocol;
		userCount = 0;
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(3);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			try {
				if (isNewMessage) {
					sendToConnectedUsers(getLatestMessage());
					isNewMessage = false;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void start() {
		if (thread == null) {
			thread = new Thread(this, "Broadcaster");
			thread.start();
			System.out.println("Broadcaster started.");
		}
	}
	
	private String getLatestMessage(){
		return protocol.getLatestMessage();
	}

	public void sendToConnectedUsers(String message) throws IOException {
		for (int i = 0; i < userCount; i++) {
			users.get(i).getPrintWriter().println(message);
		}
	}

	public boolean isAlreadyInChat(String name){
		for (int i = 0; i < userCount; i++) {
			if (users.get(i).getName().equals(name)){
				return true;
			}
		}
		return false;
	}
	
	public void addUser(ConnectedUser user) {
		users.add(user);
		userCount++;
		protocol.saveServerMessage(user.getName() + " has joined the chatroom.");
	}

	public static boolean isNewMessage() {
		return isNewMessage;
	}

	public static void setIsNewMessage(boolean isNewMessage) {
		Broadcaster.isNewMessage = isNewMessage;
	}

}
