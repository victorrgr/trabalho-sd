package br.edu.ies.component;

import java.io.IOException;
import java.net.Socket;

import br.edu.ies.util.Logger;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Class that receives all the sockets messages request.
 * Runs in a separate Thread for each client connection
 * so that the server can handle multiple user connections
 * 
 * @author victorrgr
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ServerReceiverGateway extends Thread {
    private final Server server;
    private Socket client;

    public ServerReceiverGateway(Server server, Socket client) {
        this.server = server;
        this.client = client;
    }

    /**
     * Setup for the user connection, adds a listener for its messages
     * and calls the server method for it to handle the connections requests,
     * such as message sending and message retrieveng
     */
    @Override
    public void run() {
        Logger.logGateway("Connection established -> " + client);
        Logger.logGateway("Connection size -> " + server.getConnections().size());
        this.server.addConnection(client);
        var messageListener = new MessageListener(client, server.getChat());
        messageListener.subcribe();
        try {
//        	var confirm = Utils.CONFIRM_OBJECT;
//        	this.client.getOutputStream().write(Utils.MAPPER.writeValueAsBytes(confirm));
            this.server.handleConnection(client);
            client.close();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        messageListener.unsubcribe();
        this.server.removeConnection(client);
        Logger.logServer("Connection closed -> " + client);
    }

}
