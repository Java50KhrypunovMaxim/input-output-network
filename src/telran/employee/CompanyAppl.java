package telran.employee;

import java.util.ArrayList;

import telran.employee.controller.CompanyController;
import telran.employee.service.Company;
import telran.employee.service.CopmanyImplemention;
import telran.view.ConsoleInputOutput;
import telran.view.InputOutput;
import telran.view.Item;
import telran.view.Menu;

public class CompanyAppl {
private static final String DEFAULT_FILE_NAME = "C:/Documents/Save copmany.txt";
public static void main(String[] args) throws ClassNotFoundException {
	InputOutput io = new ConsoleInputOutput();
	Company company = new CopmanyImplemention();
	String fileName = args.length > 0 ? args[0] : DEFAULT_FILE_NAME;
	company.restore(fileName );
	ArrayList<Item> items = CompanyController.getItems(company);
	items.add(Item.of("Exit & Save", io1 -> company.save(fileName), true));
	Menu menu = new Menu("Company Application", items );
	menu .perform(io);

}

}
