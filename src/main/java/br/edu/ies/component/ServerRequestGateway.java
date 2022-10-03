package br.edu.ies.component;

import br.edu.ies.util.Logger;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.IOException;
import java.net.Socket;

// TODO: Talvez chamar somente de ServerGateway e abstrair uma forma de lidar 
//  com a conexão para enviar mensagens e receber mensagens pela mesma classe
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
        try {
            var messageListener = new MessageListener(client, server.getChat());
            messageListener.subcribe();
            this.server.addConnection(client);
            this.server.handleConnection(client);
            client.close();
            messageListener.unsubcribe();
            this.server.removeConnection(client);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        Logger.logServer("Connection closed -> " + client);
    }

}
