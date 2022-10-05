package br.edu.ies.component;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.edu.ies.model.Chat;
import br.edu.ies.model.CommObject;
import br.edu.ies.model.Message;
import br.edu.ies.model.Operation;
import br.edu.ies.util.Utils;
import lombok.Data;

/**
 * Class that represents the user which can send messages to 
 * a chat in a remote server using a socket
 */
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

    public Client() {
    	this.chat = new Chat();
    }

    public Client(String id, String name) {
        this.id = id;
        this.name = name;
        this.connected = Boolean.FALSE;
        this.chat = new Chat();
    }

    public void confirmServerReceive() throws IOException {
    	this.socket.getInputStream().read();
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
//        confirmServerReceive();
        this.connected = Boolean.TRUE;
    }

    /**
     * Closes the connection with the remote server socket
     * @throws IOException if the connection is no longer available
     */
    public void closeConnection() throws IOException {
        if (Boolean.FALSE.equals(this.connected))
            throw new IllegalStateException("Not connected");
        this.connected = Boolean.FALSE;
        this.socket.close();
    }

    /**
     * Sends a leave message to the server
     *
     * @param commObject object to transfer information
     * @throws IOException if the connection is no longer available
     */
    public void sendLeave() throws IOException {
        if (Boolean.FALSE.equals(this.connected))
            throw new IllegalStateException("Not connected");
        Message message = new Message(null, this);
        CommObject commObject = new CommObject(Operation.SEND_LEAVE_MESSAGE, message, this);
        var outputStream = socket.getOutputStream();
        var printStream = new PrintStream(outputStream);
        System.out.println("You left the chat");
        printStream.println(Utils.MAPPER.writeValueAsString(commObject));
    }
    
    /**
     * Sends a message to the server
     *
     * @param commObject object to transfer information
     * @throws IOException if the connection is no longer available
     */
    public void sendMessage(String content) throws IOException {
        if (Boolean.FALSE.equals(this.connected))
            throw new IllegalStateException("Not connected");
        Message message = new Message(content, this);
        CommObject commObject = new CommObject(Operation.SEND_MESSAGE, message, this);
        var outputStream = socket.getOutputStream();
        var printStream = new PrintStream(outputStream);
        message.print();
        printStream.println(Utils.MAPPER.writeValueAsString(commObject));
//        confirmServerReceive();
    }

    /**
     * Send message to the server to retrieve all messages that has
     * been sent to it
     *
     * @throws IOException if the connection is no longer available
     */
    public void retrieveMessages() throws IOException {
        if (Boolean.FALSE.equals(this.connected))
            throw new IllegalStateException("Not connected");
        CommObject commObject = new CommObject(Operation.RETRIEVE_MESSAGES, this);
        var outputStream = socket.getOutputStream();
        var printStream = new PrintStream(outputStream);
        printStream.println(Utils.MAPPER.writeValueAsString(commObject));
    }
}
