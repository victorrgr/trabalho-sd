package br.edu.ies.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.edu.ies.model.CommObject;
import br.edu.ies.model.Operation;

public class Utils {
	public static final ObjectMapper MAPPER = new ObjectMapper();
	public static final CommObject CONFIRM_OBJECT = new CommObject(Operation.CONNECTED, "Connected", null);
}
