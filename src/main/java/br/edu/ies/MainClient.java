package br.edu.ies;

import java.io.IOException;
import java.net.SocketException;
import java.util.Scanner;
import java.util.UUID;

import br.edu.ies.component.Client;
import br.edu.ies.component.ClientReceiverGateway;
import br.edu.ies.util.Logger;

/**
 * Main execution class for the client
 */
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
        Logger.logClient("Receiving messages from the server");
        Logger.logClient("Type CLOSE_CONNECTION to exit the Chat");
        Logger.logClient("You can start chatting...");
        Logger.separator();
        client.retrieveMessages();
        
        boolean stop = false;
		try {
			while (!stop) {

				String message = scanner.nextLine();
				if (message.equals("CLOSE_CONNECTION")) {
					client.sendLeave();
					break;
				}
				client.sendMessage(message);
			}
		} catch (SocketException e) {
			Logger.logClient("Server connection lost");
		}
        
        scanner.close();
        client.closeConnection();
        receiver.interrupt();

        Logger.logClient("Closed");
    }

}