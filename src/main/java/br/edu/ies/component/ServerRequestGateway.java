package br.edu.ies.component;

import br.edu.ies.util.Logger;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.IOException;
import java.net.Socket;

@Data
@EqualsAndHashCode(callSuper = false)
public class ServerRequestGateway extends Thread {
    private final Server server;
    private Socket client;

    public ServerRequestGateway(Server server, Socket client) {
        this.server = server;
        this.client = client;
    }

    @Override
    public void run() {
        Logger.logGateway("Connection established -> " + client);
        var messageListener = new MessageListener(client, server.getChat());
        messageListener.subcribe();
        try {
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
