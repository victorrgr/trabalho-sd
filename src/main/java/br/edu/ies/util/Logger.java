package br.edu.ies.util;

public class Logger {
	private static final String PROCESS = "PROCESS";
	private static final String SERVER = "SERVER";
	private static final String CLIENT = "CLIENT";
	
	public static void logProcess(String message) {
		message = String.format("[%s] " + message, PROCESS);
		System.out.println(message);
	}
	
	public static void logServer(String message) {
		message = String.format("[%s] " + message, SERVER);
		System.out.println(message);
	}
	
	public static void logClient(String message) {
		message = String.format("[%s] " + message, CLIENT);
		System.out.println(message);
	}
}
