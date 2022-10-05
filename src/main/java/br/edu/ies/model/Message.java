package br.edu.ies.model;

import br.edu.ies.component.Client;
import lombok.Data;

/**
 * Class that represents the message the users send to the server
 * and receive from it.
 */
@Data
public class Message {
    private String content;
    private Client sender;

    public Message() {}

    public Message(String content, Client sender) {
        this.content = content;
        this.sender = sender;
    }

    /**
     * Prints in a formatted way what a specific user
     * said in the chat
     */
    public void print() {
        String output = sender.getName() + " Says: \n" + content;
        System.out.println(output + "\n");
    }
    
    /**
     * Prints in a formatted way that a specific user
     * is leaving the chat
     */
    public void printLeave() {
        String output = sender.getName() + content;
        System.out.println(output);
    }

}
