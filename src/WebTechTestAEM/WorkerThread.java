//
//  WorkerThread.java
//  Thread class
//
//  Created by Ali Hyder on 1/27/16.
//
package WebTechTestAEM;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.net.Socket;


public class WorkerThread implements Runnable{

    protected Socket clientSocket = null;
    protected String content   = null;

    public WorkerThread(Socket clientSocket, String message) {
        this.clientSocket = clientSocket;
        this.content   = message;
    }

    public void run() {
        try {
            InputStream input  = clientSocket.getInputStream();
            OutputStream output = clientSocket.getOutputStream();
            long time = System.currentTimeMillis();
            output.write(("HTTP/1.1 200 OK\n\n" + this.content).getBytes());
            output.close();
            input.close();
            Thread.sleep(60);
            System.out.println("Request made: " + time);
        } catch (IOException | InterruptedException e) {
            System.err.println("Error writing content.");
        }
    }
}