/*
 * udp client -- sends an ephemeral port number with each packet.
 * udp - adds very little to the underlying internet protocol.
 */

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.SocketException;
import java.net.InetAddress;

public class UDPClient {
	private DatagramSocket socket;

	public void runClient() {
		//create socket, send a packet to server <MAX_SIZE 6000 bytes>

		try {
			socket = new DatagramSocket();

			//convert message to byte array
			String pack = "GETINFO";

			byte[] data = pack.getBytes();

			DatagramPacket packet = new DatagramPacket( data, data.length, 
				InetAddress.getLocalHost(), 5000); //InetAddress.getByName("127.0.0.1")

			socket.send( packet );

			waitForPackets();

		} catch ( SocketException socketException ) { //failed to create socket
			socketException.printStackTrace();
			System.exit( 1 ); //kill client

		} catch (IOException ioException ) {
			ioException.printStackTrace();
		}
	}


	public void waitForPackets() throws IOException {

		//while ( true ) - in case there would subsquent communication

		byte[] data  = new byte[ 100 ];
		DatagramPacket serverMsg = new DatagramPacket( data, data.length );

		socket.receive( serverMsg );

		String msg = String.format(
			"Client Network Status:\n%s\n", 
			new String( serverMsg.getData(), 0, serverMsg.getLength()));

		System.out.println( msg );
	}

	public static void main( String[] args) {
		UDPClient client = new UDPClient();
		client.runClient();
	}
}