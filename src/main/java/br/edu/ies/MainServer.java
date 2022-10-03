package br.edu.ies;

import br.edu.ies.component.Server;
import br.edu.ies.component.ServerRequestGateway;
import br.edu.ies.util.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MainServer {
	
    public static void main(String[] args) {
    	Logger.logServer("Started");
        Server server = new Server();
        int port = 1234;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
        	Logger.logServer("Listening at port [" + port + "]");
            while (!serverSocket.isClosed()) {
                Socket client = serverSocket.accept();
                var gateway = new ServerRequestGateway(server, client);
                gateway.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Logger.logServer("Closed");
    }
    
}
