package telran.net.examples;

import java.io.IOException;

import telran.net.TcpClientHandler;
import telran.view.ConsoleInputOutput;
import telran.view.InputOutput;
import telran.view.Item;
import telran.view.Menu;

public class NumbersDatesTcpClientAppl {
private static final String HOST = "localhost";
private static final int PORT = 5000;

	public static void main(String[] args) throws Exception {
		InputOutput io = new ConsoleInputOutput();
		TcpClientHandler handler = new TcpClientHandler(HOST, PORT);
		Menu menu = new Menu("Operations", getItems(handler));

		menu.perform(io);

	}

	private static Item[] getItems(TcpClientHandler handler) {
		Item items[] = {
			NumbersOperationsMenu.getNumberOperationsItem( "Number Operations", handler),
			DatesOperationsMenu.getDateOperationsItem("Date Operations", handler),
			Item.of("Exit", io -> {
				try {
					handler.close();
				} catch (IOException e) {
					throw new RuntimeException(e.getMessage());
				}
			}, true)
		};
		return items;
	}

}