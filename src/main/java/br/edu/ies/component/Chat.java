package br.edu.ies.component;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
public class Chat {
    private List<Message> messages;
    private List<MessageListener> listeners;

    public Chat() {
        this.messages = new LinkedList<>();
        this.listeners = new LinkedList<>();
    }

    public void addMessage(Message message) {
        this.listeners.forEach(listener -> listener.notify(message));
        messages.add(message);
    }

    public void addListener(MessageListener listener) {
        listeners.add(listener);
    }

	public void removeListener(MessageListener messageListener) {
		listeners.remove(messageListener);
	}
}
