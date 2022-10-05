package br.edu.ies;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import br.edu.ies.component.Client;
import br.edu.ies.component.MessageListener;
import br.edu.ies.component.MessageReceiver;
import br.edu.ies.component.Server;
import br.edu.ies.util.Logger;

public class MainTest {
	private static final String HOST = "localhost";
    private static final Integer PORT = 1234;

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		Logger.logClient("Started");

		Server server = new Server();
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
        	var clientConnections = new ArrayList<Client>();
        	var serverClientConnections = new ArrayList<Socket>();
        	
        	for (int i = 0; i < 10; i++) {
    			Client client = new Client(String.valueOf(i), "Client-" + i);
    			client.establishConnection(HOST, PORT);
    			clientConnections.add(client);
    			
    			System.out.println("Client's Name -> " + client.getName());
    			Socket serverClient = serverSocket.accept();
    			serverClientConnections.add(serverClient);
    		}
        	
        	var receivers = new ArrayList<Receiver>();
        	
        	for (int i = 0; i < 10; i++) {
        		var client = clientConnections.get(i);
        		client.sendMessage("Test");
    			
        		var serverClient = serverClientConnections.get(i);
        		
    			var listener = new MessageListener(serverClient, server.getChat());
    			listener.subcribe();
    			server.handleConnectionSync(serverClient);
				
    			var messageReceiver = new MessageReceiver();
    			Receiver receiver = new Receiver(messageReceiver, client);
    			receivers.add(receiver);
    			for (var x : receivers) {
    				x.getMessageReceiver().handleConnectionSync(x.getClient());
    			}
    			
				System.out.println("Client's Chat -> " + client.getChat());
        	}
			
			System.out.println("Server -> " + server);
			
		}
        
//        ClientReceiverGateway receiver = new ClientReceiverGateway(client);
//        receiver.start();
//        
//        client.retrieveMessages();
//        
//		client.sendMessage("Test");
//        
//        scanner.close();
//        client.closeConnection();
//        receiver.interrupt();

        Logger.logClient("Closed");
	}
	
}
