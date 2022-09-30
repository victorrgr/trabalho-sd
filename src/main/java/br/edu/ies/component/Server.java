package br.edu.ies.component;

import br.edu.ies.util.Logger;
import lombok.Data;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

@Data
public class Server {
    private List<Socket> connections;

    public Server() {
        this.connections = new LinkedList<>();
    }

    /**
     * Handles the connection and processing of requested tasks
     * from the client socket
     * 
     * @param socket connection to be handled
     * @throws IOException if the client socket connection is not available anymore
     * @throws ClassNotFoundException if the message cannot be propertly translated
     */
    public void handleConnection(Socket socket) throws IOException, ClassNotFoundException {
        while (socket.isConnected()) {
            try {
                InputStream inputStream = socket.getInputStream();
                while (inputStream.available() != 0) {
                    System.out.println("Inputstream Available: " + inputStream.available());
                    ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                    System.out.println("Inputstream Available: " + inputStream.available());

                    System.out.println("Teste");
                    System.out.println("Available: " + objectInputStream.available());

                    //TODO: Possivelmente criar um objeto genérico para passar os objetos
                    //TODO: Possivelmente também usar JSON para comunicar as informações
                    //TODO: No objeto para comunicar utilizar um campo para determinar a operação desejada pelo usuário
                    Message message = (Message) objectInputStream.readObject();
                    Logger.logProcess("Message Received -> " + message);
                }

            } catch (Exception e) {
                Logger.logServer("EOFExeption");
            }
        }
        Logger.logServer("Connection closed -> " + socket);
    }

    private static void showState(Socket socket) {
        System.out.println("Closed: " + socket.isClosed());
        System.out.println("Connected: " + socket.isConnected());
        System.out.println("Bound: " + socket.isBound());
    }

    /**
     * Adds a new connection to the servers attributes
     * 
     * @param client to be added to the list of connections
     */
	public void addConnection(Socket client) {
		this.connections.add(client);
	}
    
}
