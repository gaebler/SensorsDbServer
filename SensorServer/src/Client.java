import java.io.*;
import java.net.*;
import java.util.Scanner;

/**
 * @author Guilherme Gaebler
 * 
 */
public class Client {
	private static int port = 1234;
	private Socket clientSocket;
	private OutputStream out;

	public Client(String host, int port) throws IOException {
		this.clientSocket = new Socket(host, port);
		this.out = this.clientSocket.getOutputStream();
	}

	public static void main(String[] args) throws IOException,
			ClassNotFoundException {

		System.out.println("Enter server IP:");
		Scanner sc = new Scanner(System.in);
		Client client = new Client(sc.nextLine(), Client.port);

		String msg = "0;1024;0;1024";

		client.sendMsg(msg);

		// System.out.println(client.receiveMsg());

		client.endConection();

	}

	private void endConection() {
		try {
			this.clientSocket.close();
			System.out.println("Cliente fechado!");
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void sendMsg(String msg) {
		try {
			ObjectOutputStream objOut = new ObjectOutputStream(this.out);
			objOut.writeObject(msg);
			objOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}