package br.edu.ies;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.edu.ies.component.Client;
import br.edu.ies.util.Logger;

public class ClientServerTest {
	
	private List<Client> connections;
	private ServerHostTest host;
	
	@Before
	public void serverSetup() {
		System.out.println("STARTUP");
		host = new ServerHostTest(1234);
		host.start();
		connections = new LinkedList<>();
	}
	
	@After
	public void serverCleanup() {
		System.out.println("CLEANUP");
		host.getServer().close();
		for (var conn : connections) {
			try { conn.closeConnection(); } 
			catch (Exception e) { }
		}
		host.close();
		host.interrupt();
	}
	
//	@Test
//	public void simultaneousConnections() throws IOException, InterruptedException {
//		for (int i = 0; i < 10; i++) {
//			Client client = new Client(String.valueOf(i), "Client-" + i);
//			client.establishConnection("localhost", 1234);
//			System.out.println(client.getName());
//			connections.add(client);
//		}
//		
//		System.out.println("Client connections -> " + connections.size());
//		System.out.println("Server connections -> " + host.getServer().getConnections().size());
//		
//		assertEquals(connections.size(), host.getServer().getConnections().size());
//	}
	
//	@Test
//	public void sendMessage() throws IOException, InterruptedException {
//		Client client = new Client("1", "Client-1");
//		client.establishConnection("localhost", 1234);
//		
//		String expectedMessage = "Send Message Test";
//		client.sendMessage(expectedMessage);
//		
//		var messages = host.getServer().getChat().getMessages();
//		if (!messages.isEmpty()) {
//			var message = messages.get(0);
//			Logger.logAssertion();
//			assertEquals(expectedMessage, message.getContent());
//			assertEquals(client.getId(), message.getSender().getId());
//		}
//	}
	
	@Test
	public void chatCommunication() throws IOException, InterruptedException {
		Client client = new Client("1", "Client-1");
		client.establishConnection("localhost", 1234);
		Client client2 = new Client("2", "Client-2");
		client2.establishConnection("localhost", 1234);
		
		String expectedMessage = "Hello, how are you?";
		client.sendMessage(expectedMessage);
		String expectedMessage2 = "I'm alright";
		client2.sendMessage(expectedMessage2);
		
		var messages = host.getServer().getChat().getMessages();
		
		System.out.println(messages);
		
		boolean found = false;
		for (var message : messages)
			if (message.getContent().equals(expectedMessage))
				found = true;
		boolean found2 = false;
		for (var message : messages)
			if (message.getContent().equals(expectedMessage2))
				found2 = true;
		
		System.out.println("FOUND " + found);
		System.out.println("FOUND2 " + found2);
		
		Logger.logAssertion();
		assertEquals(true, found);
		assertEquals(true, found2);
	}
//	
//	@Test
//	public void messageNotification() {
//		
//	}

}
