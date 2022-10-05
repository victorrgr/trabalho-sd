package br.edu.ies;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import br.edu.ies.component.Server;
import br.edu.ies.component.ServerReceiverGateway;
import br.edu.ies.util.Logger;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ServerHostTest extends Thread {
	
	private Server server;
	private ServerSocket serverSocket;
	private Integer port;

	public ServerHostTest(Integer port) {
		this.server = new Server();
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
                var gateway = new ServerReceiverGateway(server, client);
                gateway.start();
            }
        } catch (IOException e) {
        	e.printStackTrace();
        }
        if (serverSocket != null && !serverSocket.isClosed()) {
			try {
				serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        Logger.logServer("Closed");
	}

	public void close() {
		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
