//
//  WorkerThread.java
//  Thread class
//
//  Created by Ali Hyder on 1/27/16.
//  Implemented following tutorial from http://tutorials.jenkov.com/java-multithreaded-servers/thread-pooled-server.html
//	All code is my own code, however it is heavily influenced by the content from the above url
//
package WebTechTestAEM;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.net.Socket;


public class WorkerThread implements Runnable{

    protected Socket clientSocket = null;
    protected String content   = null;

    public WorkerThread(Socket clientSocket, String message) { // initialize WorkerThread object with socket and message
        this.clientSocket = clientSocket;
        this.content   = message;
    }

    public void run() {
        try {
            InputStream input  = clientSocket.getInputStream();
            OutputStream output = clientSocket.getOutputStream();
            long time = System.currentTimeMillis();
            output.write(("HTTP/1.1 200 OK\n\n" + this.content).getBytes()); //write content to socket
            output.close();
            input.close();
            Thread.sleep(60); //have the thread sleep for 60 seconds to make sure that other socket connections being made in Test happen while this thread is busy
            System.out.println("Request made: " + time);
        } catch (IOException | InterruptedException e) {
            System.err.println("Error writing content.");
        }
    }
}