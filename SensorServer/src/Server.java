import java.io.*;
import java.net.*;

/**
 * @author Guilherme Gaebler
 * 
 */
public class Server {

	public int port = 1234;
	public ServerSocket listenSocket;

	public Server() throws IOException {
		this.listenSocket = new ServerSocket(this.port);
	}

	public ServerSocket getlistenSocket() {
		return listenSocket;
	}
	
	@SuppressWarnings("unused")
	public static void main(String args[]) throws IOException {
		Server server = new Server();
		while (true) {
			Socket client = server.getlistenSocket().accept();
			System.out.println("Novo cliente conectado...");
			ServerConnection c = new ServerConnection(client);
		}
	}
}
