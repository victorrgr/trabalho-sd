package br.edu.ies.component;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ClientSenderGateway extends Thread {

	private Client client;

	public ClientSenderGateway(Client client) {
		this.client = client;
	}

	@Override
	public void run() {
		// TODO: Lidar com requisição de mensagens do usuário
	}
	
}
