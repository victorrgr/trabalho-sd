package br.edu.ies.component;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import br.edu.ies.util.Logger;

public class ServerHost extends Thread {
	
	private Server server;
	private ServerSocket serverSocket;
	private Integer port;

	public ServerHost(Server server, Integer port) {
		this.server = server;
		this.port = port;
	}

	@Override
	public void run() {
		Logger.logServer("Started");
        try {
        	serverSocket = new ServerSocket(port);
        	Logger.logServer("Listening at port [" + port + "]");
            while (!serverSocket.isClosed()) {
                Socket client = serverSocket.accept();
                var gateway = new ServerRequestGateway(server, client);
                gateway.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        	try {
				serverSocket.close();
			} catch (IOException e) {}
        }
        Logger.logServer("Closed");
	}

	public void close() throws IOException {
		serverSocket.close();
	}

}
