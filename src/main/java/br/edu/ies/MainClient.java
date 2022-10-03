package br.edu.ies;

import java.io.IOException;
import java.util.Scanner;
import java.util.UUID;

import br.edu.ies.component.Client;
import br.edu.ies.component.ClientReceiverGateway;
import br.edu.ies.component.CommObject;
import br.edu.ies.component.Operation;
import br.edu.ies.util.Logger;

public class MainClient {
    private static final String HOST = "localhost";
    private static final Integer PORT = 1234;

    public static void main(String[] args) throws IOException {
        Logger.logClient("Started");

        Scanner scanner = new Scanner(System.in);
        Logger.logClient("Type your name");
        String name = scanner.nextLine();
        Client client = new Client(UUID.randomUUID().toString(), name);
        client.establishConnection(HOST, PORT);

        ClientReceiverGateway receiver = new ClientReceiverGateway(client);
        receiver.start();
        
        Logger.separator();
        Logger.logClient("Recebendo mensagens do servidor");
        Logger.separator();
        client.retrieveMessages();

        boolean stop = false;
        while (!stop) {
            String message = scanner.nextLine();
            client.sendMessage(new CommObject(Operation.SEND_MESSAGE, message));
        }
        
        scanner.close();

//        oldClient(scanner);

        Logger.logClient("Closed");

        // TODO: Criar várias janelas, uma para cada cliente, usar threads para os clientes
        //  poderem utilizar as coisas em paralelo
        // TODO: Fazer uma única classe Main que cria um Servidor e permite que dê pra criar
        //  clientes
    }

//    private static void oldClient(Client client, Scanner scanner) throws IOException {
//        boolean stop = false;
//        while (!stop) {
//            // TODO: Criar uma forma de receber mensagens do chat de forma "asíncrona"
//            Logger.logClient("Operations:");
//            System.out.println(Arrays.toString(Operation.values()));
//
//            Logger.logClient("Type the operation:");
//            String operation = scanner.nextLine();
//
//            // TODO: Encontrar uma forma melhor de lidar com os inputs
//            if (Operation.CLOSE_CONNECTION.name().equals(operation)) {
//                client.closeConnection();
//                stop = true;
//            } else if (Operation.SEND_MESSAGE.name().equals(operation)) {
//                Logger.logClient("Type message:");
//                String message = scanner.nextLine();
//                client.sendMessage(new CommObject(Operation.SEND_MESSAGE, message));
//            } else {
//                Logger.logClient("Invalid operation");
//            }
//            ClientGateway gateway = new ClientGateway();
//
//        }
//    }

//    private static void testConnection() {
//        Logger.logClient("Started");
//        for (int i = 0; i < 1; i++) {
//            Client client = new Client(id++, "Client-" + id);
//            try (Socket socket = client.establishConnection("localhost", 1234)) {
//                Logger.logClient("Connection established -> " + client);
//
//                client.sendMessage(new CommObject(Operation.SEND_MESSAGE, "Mensagem teste 1"));
//                client.sendMessage(new CommObject(Operation.SEND_MESSAGE, "Mensagem teste 2"));
//
//                Thread.sleep(6000);
//
//                client.sendMessage(new CommObject(Operation.SEND_MESSAGE, "Mensagem teste 3"));
//                Thread.sleep(6000);
//                Thread.sleep(6000);
//
//                client.sendMessage(new CommObject(Operation.CLOSE_CONNECTION, "Mensagem teste 4 Fechamento"));
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }
//        Logger.logClient("Closed");
//    }
    
}