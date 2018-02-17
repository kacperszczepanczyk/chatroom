import java.util.LinkedList;
import java.util.List;

public class MessagesStorage {
	private List<String> messages;

	MessagesStorage() {
		messages = new LinkedList<String>();
	}

	public void putStringOnTopOfTheList(String message) {
		((LinkedList<String>) messages).addFirst(message);
	}

	public String getFirstStringFromTheList() {
		return ((LinkedList<String>) messages).getFirst();
	}

	public String mergeStoredMessagesIntoString() {
		String result = "";
		for (int i = 0; i < messages.size(); i++) {
			result = result + "[" + messages.get(i) + "]";
		}
		return result;
	}

	public List<String> getMessages() {
		return messages;
	}

}
