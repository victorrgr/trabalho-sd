package br.edu.ies.component;

import lombok.Data;

import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

@Data
public class Server {
    private List<Socket> connections;

    public Server() {
        this.connections = new LinkedList<>();
    }
}
