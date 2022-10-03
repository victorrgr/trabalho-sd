package br.edu.ies.component;

import br.edu.ies.util.Logger;
import br.edu.ies.util.Utils;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

@Data
@EqualsAndHashCode(callSuper = false)
public class ClientReceiverGateway extends Thread {
    private final Client client;

    public ClientReceiverGateway(Client client) {
        this.client = client;
    }

    @Override
    public void run() {
        Logger.logGateway("Listening to messages -> " + client);
        try (Scanner scanner = new Scanner(client.getSocket().getInputStream())) {
            while (scanner.hasNextLine()) {
                CommObject comm = Utils.MAPPER.readValue(scanner.nextLine(), CommObject.class);
                // TODO: Melhorar a nomenclatura uma vez que algumas comunicações não serão
                //  respostas
//                Logger.logProcess("Response Received -> " + comm);

                if (comm.getOperation() == Operation.RECEIVE_MESSAGE) {
                    Message message =  Utils.MAPPER.readValue(comm.getContent(), Message.class);
                    Logger.logProcess("Receive Message -> " + message);
                    if (!message.getSender().getId().equals(client.getId()))
                        message.print();
                } else if (comm.getOperation() == Operation.RECEIVE_MESSAGES) {
                    List<Message> messages = Utils.MAPPER.readValue(comm.getContent(), new TypeReference<>(){});
                    Logger.logProcess("Receive Messages -> " + messages);
                    messages.forEach(Message::print);
                }

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Logger.logGateway("Listener terminated -> " + client);
    }
}
