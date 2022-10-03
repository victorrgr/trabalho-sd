package br.edu.ies;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.edu.ies.component.Client;
import br.edu.ies.component.CommObject;
import br.edu.ies.component.Operation;
import br.edu.ies.component.Server;
import br.edu.ies.component.ServerHost;
import br.edu.ies.util.Logger;

public class ClientServerTest {
	
	private Server server;
	private ServerHost host;
	
	@Before
	public void serverSetup() {
		server = new Server();
		host = new ServerHost(server, 1234);
		host.start();
	}
	
	@After
	public void serverCleanup() throws IOException {
		host.close();
		server.close();
	}
	
	@Test
	public void simultaneousConnections() throws IOException, InterruptedException {
		List<Client> connections = new LinkedList<>();
		for (int i = 0; i < 10; i++) {
			Client client = new Client(String.valueOf(i), "Client-" + i);
			client.establishConnection("localhost", 1234);
			connections.add(client);
		}
		Thread.sleep(1000);
		
		Logger.logAssertion();
		assertEquals(connections.size(), server.getConnections().size());
	}
	
	@Test
	public void sendMessage() throws IOException, InterruptedException {
		Client client = new Client("1", "Client-1");
		client.establishConnection("localhost", 1234);
		
		String expectedMessage = "Send Message Test";
		CommObject comm = new CommObject(Operation.SEND_MESSAGE, expectedMessage);
		client.sendMessage(comm);
		
		Thread.sleep(3000);
		var message = server.getChat().getMessages().get(0);
		
		Logger.logAssertion();
		assertEquals(expectedMessage, message.getContent());
		assertEquals(client.getId(), message.getSender().getId());
	}
	
	@Test
	public void chatCommunication() throws IOException, InterruptedException {
		Client client = new Client("1", "Client-1");
		client.establishConnection("localhost", 1234);
		
		Client client2 = new Client("2", "Client-2");
		client2.establishConnection("localhost", 1234);
		
		String expectedMessage = "Hello, how are you?";
		CommObject comm = new CommObject(Operation.SEND_MESSAGE, expectedMessage);
		client.sendMessage(comm);
		
		String expectedMessage2 = "I'm alright";
		CommObject comm2 = new CommObject(Operation.SEND_MESSAGE, expectedMessage2);
		client2.sendMessage(comm2);
		
		Thread.sleep(3000);
		
		boolean found = false;
		boolean found2 = false;
		for (var message : server.getChat().getMessages()) {
			found = message.getContent().equals(expectedMessage);
			found2 = message.getContent().equals(expectedMessage2);
		}
		
		Logger.logAssertion();
		assertEquals(true, found);
		assertEquals(true, found2);
	}
	
	@Test
	public void messageNotification() {
		
	}

}
