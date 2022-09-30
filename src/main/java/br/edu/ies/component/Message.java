package br.edu.ies.component;

import lombok.Data;

import java.io.Serializable;

@Data
public class Message implements Serializable {
	private Operation operation;
	private String json;

	public Message(Operation operation, String json) {
		this.operation = operation;
		this.json = json;
	}
}
