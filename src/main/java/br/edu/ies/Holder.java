package br.edu.ies;

import br.edu.ies.component.Client;
import br.edu.ies.component.MessageReceiver;
import lombok.Data;

@Data
public class Holder {
	private MessageReceiver messageReceiver;
	private Client client;
	
	public Holder(MessageReceiver messageReceiver, Client client) {
		super();
		this.messageReceiver = messageReceiver;
		this.client = client;
	}
	
}
