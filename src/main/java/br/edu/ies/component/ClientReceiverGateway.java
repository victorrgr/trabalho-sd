package br.edu.ies.component;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import com.fasterxml.jackson.core.type.TypeReference;

import br.edu.ies.model.CommObject;
import br.edu.ies.model.Message;
import br.edu.ies.model.Operation;
import br.edu.ies.util.Utils;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Class that can run in paralell to the user interaction with the
 * chat, enabling the user to receive messages from other users
 * 
 * @author victorrgr
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ClientReceiverGateway extends Thread {
    private final Client client;

    public ClientReceiverGateway(Client client) {
        this.client = client;
    }

    /**
     * Waits communication from the server for it to take action.
     * Mainly prints messages from others users
     */
    @Override
    public void run() {
        try (Scanner scanner = new Scanner(client.getSocket().getInputStream())) {
            while (scanner.hasNextLine()) {
                CommObject comm = Utils.MAPPER.readValue(scanner.nextLine(), CommObject.class);
                if (comm.getOperation() == Operation.RECEIVE_MESSAGE) {
                    Message message =  Utils.MAPPER.readValue(comm.getContent(), Message.class);
                    if (!message.getSender().getId().equals(client.getId()))
                    	message.print();
                } else if (comm.getOperation() == Operation.RECEIVE_LEAVE_MESSAGE) {
                	Message message =  Utils.MAPPER.readValue(comm.getContent(), Message.class);
                	if (!message.getSender().getId().equals(client.getId()))
                    	message.printLeave();
                } else if (comm.getOperation() == Operation.RECEIVE_MESSAGES) {
                    List<Message> messages = Utils.MAPPER.readValue(comm.getContent(), new TypeReference<>(){});
                    messages.forEach(Message::print);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
