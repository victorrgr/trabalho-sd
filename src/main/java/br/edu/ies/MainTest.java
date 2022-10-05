package br.edu.ies;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

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
        	var listeners = new ArrayList<MessageListener>();
        	
        	for (int i = 0; i < 10; i++) {
    			Client client = new Client(String.valueOf(i), "Client-" + i);
    			client.establishConnection(HOST, PORT);
    			clientConnections.add(client);
    			
    			System.out.println("Client's Name -> " + client.getName());
    			System.out.println("Client's Local Port -> " + client.getSocket().getLocalPort());
    			Socket serverClient = serverSocket.accept();
    			var listener = new MessageListener(serverClient, server.getChat());
    			listener.subcribe();
    			listeners.add(listener);
    			serverClientConnections.add(serverClient);
    		}
        	
        	for (int i = 0; i < 10; i++) {
        		System.out.println("SEND MESSAGE");
        		
        		var client = clientConnections.get(i);
        		client.sendMessage("Test");
    			
        		System.out.println("SERVER LISTENER");
        		
        		var serverClient = serverClientConnections.get(i);
    			server.handleConnectionSync(serverClient);
				
    			System.out.println("MESSAGE RECEIVER");
    			
    			for (var cli : clientConnections) {
    				var messageReceiver = new MessageReceiver();
        			messageReceiver.handleConnectionSync(cli);
    			}
    			
    			System.out.println("END " + i);
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
