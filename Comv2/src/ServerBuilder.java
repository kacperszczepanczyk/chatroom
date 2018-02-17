
public class ServerBuilder {

	public static void main(String[] args) {
		int portNumber = 5000;
		Server server = new Server(portNumber, "server1");
	    server.start();
	}

}
