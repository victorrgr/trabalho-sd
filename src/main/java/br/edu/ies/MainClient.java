package br.edu.ies;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import br.edu.ies.component.Client;
import br.edu.ies.component.Message;
import br.edu.ies.component.Operation;
import br.edu.ies.util.Logger;

public class MainClient {
    private static Long id = 0L;

    public static void main(String[] args) {
        Logger.logClient("[CLIENT] Started");
//        List<Client> clients = new LinkedList<>();
        for (int i = 0; i < 1; i++) {
            Client client = new Client(id++, "Client-" + id);
            try (Socket socket = client.establishConnection("localhost", 1234)) {
                Logger.logClient("Connection established -> " + client);
                sendMessage(new Message(Operation.WRITE, "{ \"message\": \"teste\" }"), socket);
                sendMessage(new Message(Operation.READ, "{ \"message\": \"teste2\" }"), socket);

                Thread.sleep(6000);

                sendMessage(new Message(Operation.DELETE, "{ \"message\": \"teste2\" }"), socket);

//              clients.add(client);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        
        // TODO: Criar várias janelas, uma para cada cliente
    }

    private static void sendMessage(Message message, Socket socket) throws IOException {
        OutputStream outputStream = socket.getOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(message);
        Logger.logClient("Message Sent -> " + message);
    }
}