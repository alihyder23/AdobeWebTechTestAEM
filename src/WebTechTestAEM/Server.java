//
//  Server.java
//  Multithreaded Web Server with thread pooling implemented in Java
//	Implemented using example from https://github.com/ibogomolov/WebServer
//	All code is my own code, however it is heavily influenced by the content from the above url
//  Created by Ali Hyder on 1/27/16.
//
package WebTechTestAEM;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Server implements Runnable {

	private final int port;
	private ServerSocket server;
	private ExecutorService threadPool; //used ExecutorService class to create my thread pool


	public static void main(String[] args) {
		int port = 1111;
		new Thread(new Server(port)).start();
	}

	public Server(int port) {
		this.port = port;
	} //initialize Server object with port number

	@Override
	public void run() {
		try {
			server = new ServerSocket(port); //create new server on port
			threadPool = Executors.newFixedThreadPool(10); //create new threadpool of size 10
		} catch (IOException e) {
			System.err.println("Can't listen on " + port);
			System.exit(1);
		}
		
		System.out.println("Server running on port " + port + ".");

		while (!Thread.interrupted()) { //loop to implement threadpooling
			try {
				threadPool.execute(new WorkerThread(server.accept(), "Ya Gunners Ya"));
			} catch (IOException e) {
				System.err.println("Cannot accept client.");
			}
		}
		close(); //close server
	}

	public void close() {
		try {
			server.close(); //close server
		} catch (IOException e) {
			System.err.println("Error closing server.");
		}

		threadPool.shutdown(); //shutdown threadpool

		try { //if threadpool does not terminate, force shut down
			if (!threadPool.awaitTermination(10, TimeUnit.SECONDS))
				threadPool.shutdownNow();
		} catch (InterruptedException e) {
            System.err.println("Error terminating thread pool.");
        }
	}

}
