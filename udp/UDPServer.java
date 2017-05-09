/*
 * Packet based transmission with UDP.
 * Server receives a packet from the client and sends it
 * back to the client.
 */

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.SocketException;

public class UDPServer {
	private DatagramSocket socket; //connectionless
	private int count = 0;

	//creates a datagramsocket for sending and receiving packets
	public void runServer() {

		try {
			socket = new DatagramSocket( 5000 ); //at port 5000

			//wait for packets from clients
			while( true ) {

				byte[] data = new byte[ 100 ]; //byte array
				DatagramPacket clientMsg = new DatagramPacket( data, data.length );
				socket.receive( clientMsg );//wait to receive a packet

				// String msg = String.format(
				// "Packet from : %s\n@port : %s\n%s", clientMsg.getAddress(),
				// clientMsg.getPort(), new String( clientMsg.getData(), 0,
				// clientMsg.getLength() ) );

				System.out.printf("client connected : count %2d\n",
				count++); //print received msg

				//resend packet
				sendPacketToClient( clientMsg );
			}

		} catch ( SocketException socketEXception ) { //can't bind to port
			socketEXception.printStackTrace();
			System.exit( 1 ); //kill server
		} catch ( IOException ioException ) { //error while waiting
			ioException.printStackTrace();
		}
	}

	public void sendPacketToClient( DatagramPacket recievedPacket ) throws IOException {
		//create a packet to send
		String msg = String.format("Your port %s\nYour Ip %s\n",
			recievedPacket.getPort(), recievedPacket.getAddress());

			  byte[] array = msg.getBytes();

		DatagramPacket packet = new DatagramPacket( array,
			array.length, recievedPacket.getAddress(),
			recievedPacket.getPort());

		socket.send( packet );
	}

	public static void main( String[] args ) {
		UDPServer server = new UDPServer();
		server.runServer();
	}
}
