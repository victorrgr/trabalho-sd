package br.edu.ies.component;

import lombok.Data;

import java.io.IOException;
import java.net.Socket;

@Data
public class Client {
    private Long id;
    private String name;
    private String host;

    public Client(Long id, String name, String host) {
        this.id = id;
        this.name = name;
        this.host = host;
    }

    /**
     * Establishes connection with remote socket in the specified remote port
     *
     * @param port remote port
     * @return connected Socket
     * @throws IOException if the connection cannot be aquired
     */
    public Socket establishConnection(Integer port) throws IOException {
        return new Socket(this.host, port);
    }


}
