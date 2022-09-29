package br.edu.ies;

import br.edu.ies.component.Client;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

public class MainClient {
    private static Long id = 0L;

    public static void main(String[] args) {
//        List<Client> clients = new LinkedList<>();
//        for (int i = 0; i < 10; i++) {
//            clients.add(new Client(id++, "Randy", "localhost"));
//        }
//        System.out.println(clients);
        System.out.println("[CLIENT]");
        Client client = new Client(id++, "Victor", "localhost");

        try (Socket socket = client.establishConnection(1234)) {
            PrintStream printStream = new PrintStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}