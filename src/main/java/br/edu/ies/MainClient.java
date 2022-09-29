package br.edu.ies;

import br.edu.ies.component.Client;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class MainClient {
    private static Long id = 0L;

    public static void main(String[] args) {
        System.out.println("[CLIENT]");
        List<Client> clients = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            Client client = new Client(id++, "Client-" + id);
            try (Socket socket = client.establishConnection("localhost", 1234)) {
                System.out.println("[CLIENT] Connection established -> " + client);
                // TOOD: Validar como fazer o envio de mensagens
//                PrintStream printStream = new PrintStream(socket.getOutputStream());
                clients.add(client);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println(clients);

    }
}