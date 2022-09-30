package br.edu.ies;

import br.edu.ies.component.Server;
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
                Logger.logServer("Connection established -> " + client);
                server.handleConnection(client);
                server.addConnection(client);
            }
            Logger.logServer("Server Socket closed");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
