package br.edu.ies.component;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;

import br.edu.ies.model.CommObject;
import br.edu.ies.model.Message;
import br.edu.ies.model.Operation;
import br.edu.ies.util.Utils;

/**
 * Class that receives messages from other users 
 * connected to the server
 */
public class MessageReceiver {

	/**
	 * Handles data received from the server
	 * 
	 * @param client
	 */
	public void handleConnection(Client client) {
		try (Scanner scanner = new Scanner(client.getSocket().getInputStream())) {
            while (scanner.hasNextLine()) {
                CommObject comm = Utils.MAPPER.readValue(scanner.nextLine(), CommObject.class);
                if (comm.getOperation() == Operation.RECEIVE_MESSAGE) {
                    Message message =  Utils.MAPPER.readValue(comm.getContent(), Message.class);
                    client.getChat().addMessage(message);
                    if (!message.getSender().getId().equals(client.getId()))
                    	message.print();
                    
                } else if (comm.getOperation() == Operation.RECEIVE_LEAVE_MESSAGE) {
                	Message message =  Utils.MAPPER.readValue(comm.getContent(), Message.class);
                	client.getChat().addMessage(message);
                	if (!message.getSender().getId().equals(client.getId()))
                    	message.printLeave();
                	
                } else if (comm.getOperation() == Operation.RECEIVE_MESSAGES) {
                    List<Message> messages = Utils.MAPPER.readValue(comm.getContent(), new TypeReference<>(){});
                    client.getChat().addMessages(messages);
                    messages.forEach(Message::print);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
	}
	
}
