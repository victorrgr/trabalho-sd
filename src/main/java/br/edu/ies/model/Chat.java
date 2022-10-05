package br.edu.ies.model;

import java.util.LinkedList;
import java.util.List;

import br.edu.ies.component.MessageListener;
import lombok.Data;

/**
 * Class that holds all messages users send to the server. It
 * also holds the references to the listeners so that it can fire
 * notify messages to other users that a message was sent to the server
 * 
 * @author victorrgr
 */
@Data
public class Chat {
    private List<Message> messages;
    private List<MessageListener> listeners;

    public Chat() {
        this.messages = new LinkedList<>();
        this.listeners = new LinkedList<>();
    }

    /**
     * Adds a message to the chat
     * 
     * @param message
     */
    public void addMessage(Message message) {
        this.listeners.forEach(listener -> listener.notify(message));
        messages.add(message);
    }
    
    /**
     * Adds a list of messages to the chat
     * 
     * @param messages
     */
    public void addMessages(List<Message> messages) {
    	this.messages.addAll(messages);
    }
    
    /**
     * Adds a leave message to the chat, that is a user is leaving
     * the chat and so other users know this fact
     * @param message
     */
    public void addLeaveMessage(Message message) {
        this.listeners.forEach(listener -> listener.notifyLeave(message));
        messages.add(message);
    }

    /**
     * Adds a listener to this chat, it will listen and fire
     * messages whenever the methods addMessage() or addLeaveMessage()
     * are called
     * 
     * @param listener
     */
    public void addListener(MessageListener listener) {
        listeners.add(listener);
    }

    /**
     * Removes a listener whenever a connection with a client was closed
     * @param messageListener
     */
	public void removeListener(MessageListener messageListener) {
		listeners.remove(messageListener);
	}

}
