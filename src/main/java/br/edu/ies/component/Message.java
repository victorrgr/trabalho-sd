package br.edu.ies.component;

import lombok.Data;

@Data
public class Message {
    private String content;
    private Client sender;

    public Message() {}

    public Message(String content, Client sender) {
        this.content = content;
        this.sender = sender;
    }

    public void print() {
        String output = sender.getName() + " Says: \n" + content;
        System.out.println(output);
    }

}
