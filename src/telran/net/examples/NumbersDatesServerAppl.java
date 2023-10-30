package telran.net.examples;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import telran.net.TcpServer;

public class NumbersDatesServerAppl {
	static final int PORT = 5000;

	public static void main(String[] args) throws Exception {
		TcpServer tcpServer = new TcpServer(PORT, new NumbersDatesProtocol());
		tcpServer.run();

	}

	
}