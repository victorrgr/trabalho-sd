package br.edu.ies.component;

import br.edu.ies.util.Logger;
import br.edu.ies.util.Utils;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;


public class MessageListener {
    private Socket client;
    private Chat chat;

    public MessageListener(Socket client, Chat chat) {
        this.client = client;
        this.chat = chat;
    }

    public void subcribe() {
        chat.addListener(this);
    }

    public void unsubcribe() {
    	chat.removeListener(this);
    }
    
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

}
