package br.edu.ies.component;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;

import lombok.Data;

@Data
public class Client implements Serializable {
	private static final long serialVersionUID = 3109940289114519368L;
	private Long id;
    private String name;
    private transient Socket socket;

    public Client(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Establishes connection with remote socket in the specified remote port
     *
     * @param port remote port
     * @return connected Socket
     * @throws IOException if the connection cannot be aquired
     */
    public Socket establishConnection(String host, Integer port) throws IOException {
        this.socket = new Socket(host, port);
        return socket;
    }

}
