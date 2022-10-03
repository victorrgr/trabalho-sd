package br.edu.ies.component;

import br.edu.ies.util.Logger;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ClientRequestGateway extends Thread {
    private final Client client;

    public ClientRequestGateway(Client client) {
        this.client = client;
    }

    @Override
    public void run() {
        Logger.logGateway("Listening for Responses");

    }
}
