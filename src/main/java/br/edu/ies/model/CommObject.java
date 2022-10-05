package br.edu.ies.model;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.edu.ies.component.Client;
import br.edu.ies.util.Utils;
import lombok.Data;

/**
 * Common Object used to enable ease communication between
 * client and server sockets. It will be serialized as JSON.
 * With the operation enum as one of its properties, it enables
 * a way of knowing which action is to be taken in the server
 * or client side.
 */
@Data
public class CommObject {
	private Operation operation;
	private String content;
	private Client client;

	public CommObject() {}

	public CommObject(Operation operation, Client client) {
		this.operation = operation;
		this.client = client;
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
