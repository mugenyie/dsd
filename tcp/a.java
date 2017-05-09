import java.net.*;
 
class PortScanner {
   public static void main(String []args) {
      for (int port = 1; port <= 65535; port++) {
         try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress("localhost", port), 1000);
            System.out.println("Port " + port + " is open");
        } catch (Exception ex) {
          System.out.println("Port " + port + " is closed");
        } finally {
          socket.close(); // possibly throws null pointer ex
        }

      }
   }
}

// worker= new Socket();                  
//                 worker.connect(new InetSocketAddress(hostAddress,currentPort),timeout);
//                  for(int currentPort=startPort,i=0;currentPort&lt;=endPort;currentPort++)
//         {           
//             try
//             {               
//                 worker= new Socket(hostAddress,currentPort);                  
//                 b=true;                          
//             }
//             catch(java.io.IOException e)
//             {
//                 b=false;                
//             }
            
//             finally
//             {
//                 try
//                 {
//                     worker.close();
//                 }
//                 catch(java.io.IOException e){}      
//                 catch(NullPointerException e){}
//             }
            
//             if(b==true)
//             {
//                 openPorts[i]=currentPort;