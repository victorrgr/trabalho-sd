package br.edu.ies.component;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

import br.edu.ies.util.Logger;
import lombok.Data;

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
    	InputStream inputStream = socket.getInputStream();
    	ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
    	
    	//TODO: Possivelmente criar um objeto genérico para passar os objetos
    	//TODO: Possivelmente também usar JSON para comunicar as informações
    	//TODO: No objeto para comunicar utilizar um campo para determinar a operação desejada pelo usuário
    	Client client = (Client) objectInputStream.readObject();
    	Logger.logProcess("Object Received: " + client);
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
