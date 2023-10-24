package telran.net.calculation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.time.LocalDate;

import telran.view.ConsoleInputOutput;
import telran.view.InputOutput;
import telran.view.Item;
import telran.view.Menu;
import telran.view.Operations;

public class Client {
	private static final String HOST = "localhost";
	private static final int PORT = 1000;
static BufferedReader reader;
static PrintStream writer;
	public static void main(String[] args) throws Exception{
		InputOutput io = new ConsoleInputOutput();
		Socket socket = new Socket(HOST, PORT);
		reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		writer = new PrintStream(socket.getOutputStream());
		 Item[] items = getItems();
	        Menu menu = new Menu("Operations", items);
	        menu.perform(new ConsoleInputOutput());
	    }

	    private static Item[] getItems() {
	        Item numberOperationsItem = Item.of("Number Operations", io -> {
	            Menu numberMenu = new Menu("Number Operations", getItemsForOperationsWithNumbers());
	            numberMenu.perform(io);
	        });
	        Item dateOperationsItem = Item.of("Date Operations", io -> {
	            Menu numberMenu = new Menu("Date Operations", getItemsForOperationsWithDates());
	            numberMenu.perform(io);
	        });
	        
	        return new Item[] {
	            numberOperationsItem,
	            dateOperationsItem,
	            Item.exit()
	        };
	    }
	    private static Item[] getItemsForOperationsWithDates() {
	        return new Item[] {
	            Item.of("Date after a given number of days", io1 -> runProtocolDateAfterBefore ("After", io1)),
	            Item.of("Date before a given number of days",io1 -> runProtocolDateAfterBefore ("Before", io1)),
	            Item.of("Days beetwen two dates", io1 -> runProtocolDateAfterBefore ("Between", io1)),
	            Item.exit()
	        };
	    }
	    
	    private static Item[] getItemsForOperationsWithNumbers() {
	        return new Item[] {
	            Item.of("Add", io1 -> runProtocol("Add", io1)),
	            Item.of("Subtract", io1 -> runProtocol("Subtract", io1)),
	            Item.of("Multiply", io1 -> runProtocol("Multiply", io1)),
	            Item.of("Divide", io1 -> runProtocol("Divide", io1)),
	            Item.exit()};
	    }
	  
	
	static void runProtocol(String type, InputOutput io) {
		
		double[] operands = getOperands(io);
		writer.printf("%s#%s#%s\n", type, operands[0], operands[1]);
		try {
			io.writeLine(reader.readLine());
		} catch (IOException e) {
			
		}
	}
	private static double[] getOperands(InputOutput io) {
	    return new double[] {
	        io.readDouble("Enter first number", "Wrong number"),
	        io.readDouble("Enter second number", "Wrong number"),
	    };
	}
static void runProtocolDateBetween (String type, InputOutput io) {
		
		LocalDate startDate = io.readIsoDate("Enter the start date (yyyy-MM-dd):", "Wrong date");
        LocalDate endDate = io.readIsoDate("Enter the end date (yyyy-MM-dd):", "Wrong date");
		writer.printf("%s#%s#%s\n", type, startDate, endDate);
		try {
			io.writeLine(reader.readLine());
		} catch (IOException e) {
			
		}
	}
static void runProtocolDateAfterBefore (String type, InputOutput io) {
	
	LocalDate startDate = io.readIsoDate("Enter the start date (yyyy-MM-dd):", "Wrong date");
	int amountOfDays = io.readInt("Enter the number of days:", "Wrong number");;
	writer.printf("%s#%s#%d\n", type, startDate.toString(), amountOfDays);
	try {
		io.writeLine(reader.readLine());
	} catch (IOException e) {
		
	}
}

	
}
