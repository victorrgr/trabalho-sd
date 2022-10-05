package br.edu.ies.component;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

import br.edu.ies.model.Chat;
import br.edu.ies.model.CommObject;
import br.edu.ies.model.Message;
import br.edu.ies.model.Operation;
import br.edu.ies.util.Logger;
import br.edu.ies.util.Utils;
import lombok.Data;
import lombok.ToString;

/**
 * Class that represents a listener which will listen 
 * for messages in a chat and then notify to the user it
 * has been assigned for
 */
@Data
public class MessageListener {
    private Socket client;
    @ToString.Exclude
    private Chat chat;

    public MessageListener(Socket client, Chat chat) {
        this.client = client;
        this.chat = chat;
    }

    /**
     * Subscribes (that is, listens to) to the chat passed to the constructor
     */
    public void subcribe() {
        chat.addListener(this);
    }

    /**
     * Unsubscribes from the previous assigned chat
     */
    public void unsubcribe() {
    	chat.removeListener(this);
    }
    
    /**
     * Notify a message to the assigned user
     * @param message to be notified
     */
    public void notify(Message message) {
        try {
            CommObject comm = new CommObject(Operation.RECEIVE_MESSAGE, message);
            var outputStream = client.getOutputStream();
            var printStream = new PrintStream(outputStream);
            printStream.println(Utils.MAPPER.writeValueAsString(comm));
            Logger.logClient("Message Notified -> " + message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Notift a leave message to the assigned user
     * @param message to be notified
     */
    public void notifyLeave(Message message) {
        try {
            CommObject comm = new CommObject(Operation.RECEIVE_LEAVE_MESSAGE, message);
            var outputStream = client.getOutputStream();
            var printStream = new PrintStream(outputStream);
            printStream.println(Utils.MAPPER.writeValueAsString(comm));
            Logger.logClient("Message Notified -> " + message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
