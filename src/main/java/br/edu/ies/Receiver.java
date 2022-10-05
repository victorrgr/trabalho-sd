package br.edu.ies;

import br.edu.ies.component.Client;
import br.edu.ies.component.MessageReceiver;
import lombok.Data;

@Data
public class Receiver {
	private MessageReceiver messageReceiver;
	private Client client;
	
	public Receiver(MessageReceiver messageReceiver, Client client) {
		super();
		this.messageReceiver = messageReceiver;
		this.client = client;
	}
	
}
