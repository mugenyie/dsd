/*
 * TCP server that waits on a tcp port
 */

import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.EOFException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {

	private ObjectOutputStream output; //output stream to client
	private ObjectInputStream input; //input stream to client.
	private ServerSocket server;
	private Socket connection; //connection to client

	public void runServer() {

		//set up server to accept connections & process connections
		try {

			server = new ServerSocket(12345, 100);//register server to tcp port 12345

			while ( true ) {
				try {
					waitForConnection();
					getStreams(); //I/O streams
					processConnection();
				}
				catch ( EOFException eofException ) { 
					System.out.println("\nServer terminated connection");
				}
				finally { //finally executes whether an execption is thrown or not
					closeConnection();
				}

			}//while 
		} catch ( IOException ioException ) {
			ioException.printStackTrace(); 
		}
	}

	//wait for connections
	private void waitForConnection()throws IOException {
		System.out.printf("\nServer Waiting for connection...");

		connection = server.accept(); //wait for connection

		System.out.printf("\nConnected to <CLIENT> @ %s\n",
			connection.getInetAddress().getHostName());
	}

	//get object streams to send and receive serializables
	private void getStreams() throws IOException {
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush(); //flash buffer, send headers

		input = new ObjectInputStream(connection.getInputStream());
	}

	private void processConnection() throws IOException {

		String serverMsg = String.format("\nSERVER <SAYS> WELCOME");
		String frmCl;

		try {
			while (true) {

			frmCl = (String) input.readObject();

			if (frmCl.equals("."))
				break;

			System.out.println( frmCl );

			output.writeObject( serverMsg );

		}

		} catch (ClassNotFoundException classNotFoundException) {
			classNotFoundException.printStackTrace();
		}
		
		output.writeObject( serverMsg ); //send string object
		output.flush(); //flush output to client

	}

	private void closeConnection() {

		try {
			output.close(); //close output stream
			input.close(); //close input stream
			connection.close(); //close socket

		} catch ( IOException ioException) {
			ioException.printStackTrace();
		}
	}

	public static void main(String[] args) {
		TCPServer server = new TCPServer(); //create an instance of the server
		server.runServer();
	}
}