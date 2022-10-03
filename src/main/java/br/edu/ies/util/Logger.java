package br.edu.ies.util;

public class Logger {
	private static final String PROCESS = "PROCESS";
	private static final String SERVER = "SERVER";
	private static final String CLIENT = "CLIENT";
	private static final String GATEWAY = "GATEWAY";
	private static final String SEPARATOR = "=============";

	public static void logProcess(String message) {
		message = "["+ PROCESS +"] " + message;
 		System.out.println(message);
	}
	
	public static void logServer(String message) {
		message = "["+ SERVER +"] " + message;
		System.out.println(message);
	}
	
	public static void logClient(String message) {
		message = "["+ CLIENT +"] " + message;
		System.out.println(message);
	}

    public static void logGateway(String message) {
		message = "["+ GATEWAY +"] " + message;
		System.out.println(message);
    }

	public static void separator() {
		System.out.println(SEPARATOR);
	}
}
