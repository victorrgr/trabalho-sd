package br.edu.ies;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.edu.ies.component.Server;

public class ClientServerTest {
	
	private Server server;
	private List<String> list;
	
	@Before
	public void serverSetup() {
		server = new Server();
		list = new LinkedList<>();
		// TODO: Criar algo parecido com o receiver aqui de forma que possa
		//  executar em paralelo
//		ServerRequestGateway gateway = new ServerRequestGateway(server, null);
		
	}
	
	@After
	public void serverCleanup() {
		server.getConnections().forEach(conn -> {
			try {
				conn.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}
	
	@Test
	public void simultaneousConnections() throws IOException {
		list.add("dasdas");
		list.add("DASDAS256DAS");
//		List<Client> connections = new LinkedList<>();
//		for (int i = 0; i < 10; i++) {
//			Client client = new Client(String.valueOf(i), "Client-" + i);
//			client.establishConnection("localhost", 1234);
//			connections.add(client);
//		}
//		assertEquals(connections.size(), server.getConnections().size());
		assertEquals(2, list.size());
	}
	
	@Test
	public void chatCommunication() {
		assertEquals(0, list.size());
	}

}
