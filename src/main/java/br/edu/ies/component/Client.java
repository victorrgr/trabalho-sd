package br.edu.ies.component;

import br.edu.ies.util.Logger;
import br.edu.ies.util.Utils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

@Data
public class Client {
	private String id;
    private String name;
    @JsonIgnore
    private Socket socket;
    @JsonIgnore
    private Boolean connected;
    @JsonIgnore
    private Chat chat;

    public Client() {}

    public Client(String id, String name) {
        this.id = id;
        this.name = name;
        this.connected = Boolean.FALSE;
    }

    /**
     * Establishes connection with remote socket in the specified remote port
     *
     * @param host remote host
     * @param port remote port
     * @throws IOException if the connection cannot be aquired
     */
    public void establishConnection(String host, Integer port) throws IOException {
        this.socket = new Socket(host, port);
        this.connected = Boolean.TRUE;
    }

    public void closeConnection() throws IOException {
        if (Boolean.FALSE.equals(this.connected))
            throw new IllegalStateException("Not connected");
        this.connected = Boolean.FALSE;
        this.socket.close();
    }

    /**
     * Send message to the server
     *
     * @param commObject object to transfer information
     * @throws IOException if the connection is no longer available
     */
    public void sendMessage(CommObject commObject) throws IOException {
        if (Boolean.FALSE.equals(this.connected))
            throw new IllegalStateException("Not connected");
        commObject.setClient(this);
        var outputStream = socket.getOutputStream();
        var printStream = new PrintStream(outputStream);
        Message message = new Message(commObject.getContent(), this);
        message.print();
        printStream.println(Utils.MAPPER.writeValueAsString(commObject));
    }

    /**
     * Send message to the server to retrieve all messages that it has
     *
     * @throws IOException if the connection is no longer available
     */
    public void retrieveMessages() throws IOException {
        if (Boolean.FALSE.equals(this.connected))
            throw new IllegalStateException("Not connected");
        CommObject commObject = new CommObject(Operation.RETRIEVE_MESSAGES);
        commObject.setClient(this);
        var outputStream = socket.getOutputStream();
        var printStream = new PrintStream(outputStream);
        printStream.println(Utils.MAPPER.writeValueAsString(commObject));
        Logger.logClient("RETRIEVE_MESSAGES");
    }
}
