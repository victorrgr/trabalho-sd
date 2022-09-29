package br.edu.ies;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

import br.edu.ies.component.Client;
import br.edu.ies.util.Logger;

public class MainClient {
    private static Long id = 0L;

    public static void main(String[] args) {
        Logger.logClient("[CLIENT] Started");
        List<Client> clients = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            Client client = new Client(id++, "Client-" + id);
            try (Socket socket = client.establishConnection("localhost", 1234)) {
                Logger.logClient("Connection established -> " + client);
                
                OutputStream outputStream = socket.getOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                objectOutputStream.writeObject(client);
                
                clients.add(client);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        // TODO: Criar várias janelas, uma para cada cliente
    }
}