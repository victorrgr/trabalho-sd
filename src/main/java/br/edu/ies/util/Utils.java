package br.edu.ies.util;

import java.io.IOException;
import java.io.InputStream;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.edu.ies.model.CommObject;
import br.edu.ies.model.Operation;

public class Utils {
	public static final ObjectMapper MAPPER = new ObjectMapper();
	public static final CommObject CONFIRM_OBJECT = new CommObject(Operation.CONNECTED, "Connected", null);
	
	public static byte[] readContent(InputStream inputStream) throws IOException {
		while (inputStream.available() == 0);
        var packetData = new byte[inputStream.read()];
        
        while (inputStream.available() < packetData.length);
        inputStream.read(packetData,0,packetData.length);
        
        return packetData;
	}
}
