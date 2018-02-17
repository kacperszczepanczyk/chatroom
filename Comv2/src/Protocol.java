import java.time.*;

public class Protocol {

	public MessagesStorage messagesStorage;

	Protocol() {
		messagesStorage = new MessagesStorage();
	}

	public void saveMessage(String name, String message) {
		messagesStorage.putStringOnTopOfTheList(formatMessage(name, message));
		Broadcaster.setIsNewMessage(true);
		System.out.println("[" + getCurrentTime() + "] New message from \"" + name + "\" stored: \"" + message + "\"");
	}

	private String formatMessage(String name, String message) {
		return "[" + getCurrentTime() + "] " + name + ": " + message;
	}
	
	public void saveServerMessage(String message){
		messagesStorage.putStringOnTopOfTheList("[" + getCurrentTime() + "] " + message);
		Broadcaster.setIsNewMessage(true);
		System.out.println("[" + getCurrentTime() + "] New server messeage stored: " + message);
	}

	private String getCurrentTime() {
		LocalTime time = LocalTime.now();
		return time.toString().substring(0, 8);
	}

	public String getAllStoredMessages() {		
		return messagesStorage.mergeStoredMessagesIntoString();
	}
	
	public String getLatestMessage(){
		return messagesStorage.getFirstStringFromTheList();
	}
}