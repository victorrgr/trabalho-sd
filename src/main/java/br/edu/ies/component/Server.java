package br.edu.ies.component;

import br.edu.ies.util.Logger;
import br.edu.ies.util.Utils;
import lombok.Data;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

@Data
public class Server {
    private Chat chat;
    private List<Socket> connections;

    public Server() {
        this.connections = new LinkedList<>();
        this.chat = new Chat();
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
        Scanner scanner = new Scanner(socket.getInputStream());
        while (scanner.hasNextLine()) {
            CommObject comm = Utils.MAPPER.readValue(scanner.nextLine(), CommObject.class);
            Logger.logProcess("Request Received -> " + comm);

            if (comm.getOperation() == Operation.SEND_MESSAGE) {
                Message message = new Message((String) comm.getContent(), comm.getClient());
                Logger.logProcess("Send Message -> " + socket);
                chat.addMessage(message);
            } else if (comm.getOperation() == Operation.RETRIEVE_MESSAGES) {
                CommObject response = new CommObject(Operation.RECEIVE_MESSAGES, chat.getMessages());
                PrintStream printStream = new PrintStream(socket.getOutputStream());
                Logger.logProcess("Retrieve Messages -> " + socket);
                printStream.println(Utils.MAPPER.writeValueAsString(response));
            }

//            Logger.logServer("Chat messages: " + chat.getMessages());
        }

    }

//    private static void showState(Socket socket) {
//        System.out.println("Closed: " + socket.isClosed());
//        System.out.println("Connected: " + socket.isConnected());
//        System.out.println("Bound: " + socket.isBound());
//    }

    /**
     * Adds a new connection to the servers attributes
     * 
     * @param client to be added to the list of connections
     */
	public void addConnection(Socket client) {
		this.connections.add(client);
	}

	public void removeConnection(Socket client) {
		this.connections.remove(client);
	}
    
}
