public class ClientBuilder {

	public static void main(String[] args) {
		Client client = new Client("192.168.0.4", 5000);
		client.createInputOutputToServer();
		client.initializeGUI();
		client.initializeClientListener();
		client.initializeServerListener();
	}

}
