/*
 * TCP client waits tries to connect on a tcp port
 *
 */
import java.util.Scanner;;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.EOFException;
import java.net.Socket;
import java.net.InetAddress;

public class TCPClient {
	private static final Scanner read = new Scanner(System.in);
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private Socket connection;
	private String server; //server IP address as cmdline arg

	public TCPClient(String server) { 
		this.server = server;
	}

	//start client and communicate with server
	public void runClient() {

		try {
			connectToServer();
			getStreams();
			processConnection();

		} catch (EOFException eofException) {
			System.out.printf("\nClient terminated connection");

		} catch (IOException ioException) {
			ioException.printStackTrace();

		}  finally {
			closeConnection();
		}
	}

	private void connectToServer() throws IOException{
		System.out.printf("\nconnecting to <SERVER> @ %s ...", server);

		connection = new Socket(InetAddress.getByName( server ), 12345);

		System.out.printf("\nconnected to :: %s",
			connection.getInetAddress().getHostName());

	}

	//get references to connection's I/O stream objects
	private void getStreams() throws IOException{
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush();

		input = new ObjectInputStream(connection.getInputStream());
	}

	private void processConnection() throws IOException{
		//get message from server

		String msg = "";

		try {

			while (true) {
			msg = read.nextLine();
			output.writeObject(msg);
			output.flush();

			if(msg.equals("."))
				break;

			System.out.println( (String)input.readObject() );

			}
			

		} catch (ClassNotFoundException classNotFoundException) {
			classNotFoundException.printStackTrace();
		}
		
	}

	private void closeConnection() {
		try {
			output.close();
			input.close();
			connection.close();
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}

	//run client
	public static void main(String[] args) {
		TCPClient client;

		if(args.length != 0) { //no cmdline arg
			client = new TCPClient(args[0]);

		} else
			client = new TCPClient( "127.0.0.1"); //localhost, loopback ip

		client.runClient();
	}
}