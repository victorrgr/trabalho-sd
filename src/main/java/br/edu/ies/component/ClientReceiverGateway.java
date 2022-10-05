package br.edu.ies.component;

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
        var messageReceiver = new MessageReceiver();
        messageReceiver.handleConnection(client);
    }
    
}
