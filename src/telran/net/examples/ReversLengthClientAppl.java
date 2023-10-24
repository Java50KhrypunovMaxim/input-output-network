package telran.net.examples;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import telran.view.ConsoleInputOutput;
import telran.view.InputOutput;
import telran.view.Item;
import telran.view.Menu;

public class ReversLengthClientAppl {
	private static final String HOST = "localhost";
	private static final int PORT = 5000;
static BufferedReader reader;
static PrintStream writer;
	public static void main(String[] args) throws Exception{
		InputOutput io = new ConsoleInputOutput();
		Socket socket = new Socket(HOST, PORT);
		reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		 writer = new PrintStream(socket.getOutputStream());
		Menu menu = new Menu("Reverse-Length-Client", Item.of("reverse", io1 -> runProtocol("reverse", io1))
		, Item.of("Subtract", io1 -> runProtocol("Subtract", io1)),
		Item.of("Exit", io1 -> {
			try {
				socket.close();
			} catch (IOException e) {
				
			}
		}, true));
		menu.perform(io);
	}
	
	static void runProtocol(String type, InputOutput io1) {
		String str = io1.readString("Enter any string");
		writer.printf("%s#%s\n", type, str);
		try {
			io1.writeLine(reader.readLine());
		} catch (IOException e) {
			
		}
	}
}
