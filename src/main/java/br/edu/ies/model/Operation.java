package br.edu.ies.model;

/**
 * Class that defines operations that can be taken 
 * by the client or server
 */
public enum Operation {
	CONNECTED,
    SEND_MESSAGE,
    RECEIVE_MESSAGE,
    RETRIEVE_MESSAGES,
    RECEIVE_MESSAGES, 
    SEND_LEAVE_MESSAGE, 
    RECEIVE_LEAVE_MESSAGE;
	
	private Operation() {
	}
}
