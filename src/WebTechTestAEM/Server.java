//
//  Server.java
//  Multithreaded Web Server with thread pooling implemented in Java
//
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
	}

	@Override
	public void run() {
		try {
			server = new ServerSocket(port);
			threadPool = Executors.newFixedThreadPool(10);
		} catch (IOException e) {
			System.err.println("Can't listen on " + port);
			System.exit(1);
		}
		
		System.out.println("Server running on port " + port + ".");

		while (!Thread.interrupted()) {
			try {
				threadPool.execute(new WorkerThread(server.accept(), "Ya Gunners Ya"));
			} catch (IOException e) {
				System.err.println("Cannot accept client.");
			}
		}
		close();
	}

	public void close() {
		try {
			server.close();
		} catch (IOException e) {
			System.err.println("Error closing server.");
		}

		threadPool.shutdown();

		try {
			if (!threadPool.awaitTermination(10, TimeUnit.SECONDS))
				threadPool.shutdownNow();
		} catch (InterruptedException e) {
            System.err.println("Error terminating thread pool.");
        }
	}

}
