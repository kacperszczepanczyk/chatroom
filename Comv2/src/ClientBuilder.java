public class ClientBuilder {

	public static void main(String[] args) {
		Client client = new Client("83.20.213.203", 5000);
		client.createInputOutputToServer();
		client.initializeGUI();
		client.initializeClientListener();
		client.initializeServerListener();
	}

}
