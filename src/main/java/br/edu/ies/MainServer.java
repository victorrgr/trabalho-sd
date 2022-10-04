package br.edu.ies;

import br.edu.ies.component.Server;
import br.edu.ies.component.ServerReceiverGateway;
import br.edu.ies.util.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Main execution class for the server
 */
public class MainServer {
	
    public static void main(String[] args) {
    	Logger.logServer("Started");
        Server server = new Server();
        int port = 1234;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
        	Logger.logServer("Listening at port [" + port + "]");
            while (!serverSocket.isClosed()) {
                Socket client = serverSocket.accept();
                var gateway = new ServerReceiverGateway(server, client);
                gateway.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Logger.logServer("Closed");
    }
    
}
