package br.edu.ies.component;

import br.edu.ies.util.Utils;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Data;

// TODO: Encontrar um nome melhorar para colocar nesse objeto

/**
 * Object used to communicate between sockets
 */
@Data
public class CommObject {
	private Operation operation;
	private String content;
	private Client client;

	public CommObject() {}

	public CommObject(Operation operation) {
		this.operation = operation;
	}

	public CommObject(Operation operation, Object content) {
		this.operation = operation;
		try {
			this.content = Utils.MAPPER.writeValueAsString(content);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	public CommObject(Operation operation, Object content, Client client) {
		this.operation = operation;
		try {
			this.content = Utils.MAPPER.writeValueAsString(content);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
		this.client = client;
	}
}
