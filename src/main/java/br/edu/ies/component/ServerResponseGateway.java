package br.edu.ies.component;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.net.Socket;

@Data
@EqualsAndHashCode(callSuper = false)
public class ServerResponseGateway extends Thread {
    private final Server server;
    private Socket client;

    public ServerResponseGateway(Server server, Socket client) {
        this.server = server;
        this.client = client;
    }

    @Override
    public void run() {

    }
}
