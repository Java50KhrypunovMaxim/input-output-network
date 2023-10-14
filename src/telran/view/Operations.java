package telran.view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.function.Consumer;

public class Operations {
    public static void main(String[] args) {
    	
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
            Item.of("Date after a given number of days", Operations::addDate),
            Item.of("Date before a given number of days", Operations::subtractDate),
            Item.of("Days beetwen two dates", Operations::amountOfDays),
            Item.exit()
        };
    }
    
    private static Item[] getItemsForOperationsWithNumbers() {
        return new Item[] {
            Item.of("Add", Operations::addItem),
            Item.of("Subtract", Operations::subtractItem),
            Item.of("Multiply", Operations::multiplyItem),
            Item.of("Divide", Operations::divideItem),
            Item.exit()
        };
    }

    static void addItem(InputOutput io) {
        double[] operands = getOperands(io);
        io.writeObjectLine(operands[0] + operands[1]);
    }

    private static double[] getOperands(InputOutput io) {
        return new double[] {
            io.readDouble("Enter first number", "Wrong number"),
            io.readDouble("Enter second number", "Wrong number"),
        };
    }
    
   
    static void subtractItem(InputOutput io) {
        double[] operands = getOperands(io);
        io.writeObjectLine(operands[0] - operands[1]);
    }

    static void multiplyItem(InputOutput io) {
        double[] operands = getOperands(io);
        io.writeObjectLine(operands[0] * operands[1]);
    }

    static void divideItem(InputOutput io) {
        double[] operands = getOperands(io);
        io.writeObjectLine(operands[0] / operands[1]);
    }
    static void addDate(InputOutput io) {
    	LocalDate startDate = io.readIsoDate("Enter the start date (yyyy-MM-dd):", "Wrong date");
	    int amountOfDays = io.readInt("Enter the number of days:", "Wrong number");
	    LocalDate endDate = startDate.plusDays(amountOfDays);
	    io.writeObjectLine("End date: " + endDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }
    static void subtractDate (InputOutput io) {
    	LocalDate startDate = io.readIsoDate("Enter the start date (yyyy-MM-dd):", "Wrong date");
	    int amountOfDays = io.readInt("Enter the number of days:", "Wrong number");
	    LocalDate endDate = startDate.minusDays(amountOfDays);
	    io.writeObjectLine("End date: " + endDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }
    static void amountOfDays(InputOutput io) {
        LocalDate startDate = io.readIsoDate("Enter the start date (yyyy-MM-dd):", "Wrong date");
        LocalDate endDate = io.readIsoDate("Enter the end date (yyyy-MM-dd):", "Wrong date");
        long amountOfDays = ChronoUnit.DAYS.between(startDate, endDate);
        io.writeObjectLine("Amount of days: " + amountOfDays);
    }
    
}
