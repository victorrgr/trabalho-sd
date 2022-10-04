package br.edu.ies;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.edu.ies.component.Client;

public class ClientServerTest {
	
	private List<Client> connections;
	private ServerHostTest host;
	
	@Before
	public void serverSetup() {
		System.out.println("SETUP");
		connections = new LinkedList<>();
		host = new ServerHostTest(1234);
		host.start();
	}
	
	@After
	public void serverCleanup() {
		System.out.println("CLEANUP");
		for (var conn : connections) {
			try {
				conn.closeConnection();
			} catch (Exception e) {}
		}
//		server.close();
//		host.close();
	}
	
	@Test
	public void simultaneousConnections() throws IOException, InterruptedException {
		for (int i = 0; i < 10; i++) {
			Client client = new Client(String.valueOf(i), "Client-" + i);
			client.establishConnection("localhost", 1234);
			System.out.println(client.getName());
			connections.add(client);
		}
		
		Thread.sleep(1000);
		
		System.out.println("Client connections -> " + connections.size());
		System.out.println("Server connections -> " + host.getServer().getConnections().size());
		
//		while (true) {
//			if (host.getServer().getConnections().size() >= 10) {
//				Logger.logAssertion();
//				assertEquals(connections.size(), host.getServer().getConnections().size());
//				break;
//			}
//		}
		
		assertEquals(connections.size(), host.getServer().getConnections().size());
	}
	
//	@Test
//	public void sendMessage() throws IOException, InterruptedException {
//		Client client = new Client("1", "Client-1");
//		client.establishConnection("localhost", 1234);
//		
//		String expectedMessage = "Send Message Test";
//		CommObject comm = new CommObject(Operation.SEND_MESSAGE, expectedMessage);
//		client.sendMessage(comm);
//		
//		while (true) {
//			var messages = server.getChat().getMessages();
//			if (messages.size() >= 1) {
//				var message = messages.get(0);
//				Logger.logAssertion();
//				assertEquals(expectedMessage, message.getContent());
//				assertEquals(client.getId(), message.getSender().getId());
//				break;
//			}
//		}
//	}
	
//	@Test
//	public void chatCommunication() throws IOException, InterruptedException {
//		Client client = new Client("1", "Client-1");
//		client.establishConnection("localhost", 1234);
//		
//		Client client2 = new Client("2", "Client-2");
//		client2.establishConnection("localhost", 1234);
//		
//		String expectedMessage = "Hello, how are you?";
//		CommObject comm = new CommObject(Operation.SEND_MESSAGE, expectedMessage);
//		client.sendMessage(comm);
//		
//		String expectedMessage2 = "I'm alright";
//		CommObject comm2 = new CommObject(Operation.SEND_MESSAGE, expectedMessage2);
//		client2.sendMessage(comm2);
//		
//		Thread.sleep(3000);
//		
//		boolean found = false;
//		boolean found2 = false;
//		for (var message : server.getChat().getMessages()) {
//			found = message.getContent().equals(expectedMessage);
//			found2 = message.getContent().equals(expectedMessage2);
//		}
//		
//		Logger.logAssertion();
//		assertEquals(true, found);
//		assertEquals(true, found2);
//	}
//	
//	@Test
//	public void messageNotification() {
//		
//	}

}
