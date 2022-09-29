package br.edu.ies;

import br.edu.ies.component.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MainServer {
    public static void main(String[] args) {
        System.out.println("[SERVER]");
        Server server = new Server();
        try (ServerSocket serverSocket = new ServerSocket(1234)) {
            while (true) { // TODO: Validar uma forma melhor de fazer esse loop
                Socket client = serverSocket.accept();
                System.out.println("[SERVER] Connection established");
                server.getConnections().add(client);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
