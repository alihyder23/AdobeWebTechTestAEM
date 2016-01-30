//
//  Test.java
//  Test class to verify I can create multiple threads all at once on the same server
//  Test creates 10 sockets and connects them to port 1111
//      then reads out the input stream and prints the port its on and the content on the server.
//      The content should match the content written in WorkerThread.java run() function
//
//  Created by Ali Hyder on 1/27/16.
//
package WebTechTestAEM;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Test {

    public static void main(String args[]) throws IOException {
        final String host = "localhost";
        final int portNumber = 1111;
        System.out.println("Creating socket to '" + host + "' on port " + portNumber);

        for(int i=0; i<10; i++){
            Socket socket = new Socket(host, portNumber);
            readSocket(socket);
        }

    }
    public static void readSocket(Socket socket) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        System.out.println("Socket localPort: " + socket.getLocalPort() + "; server says: " + br.readLine());
    }
}