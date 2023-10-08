package telran.view;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import telran.employee.Employee;

class InputOutputTest {
	InputOutput io = new ConsoleInputOutput();

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testReadEmployeeBySeparateField() throws Exception {

		String idStr = io.readString("Enter employee ID");
		long id = InputOutput.readLong(idStr, "Invalid ID format", 100000000, 999999999);
		String name = io.readString("Enter employee name",
				"Name must contain more than one letter where the first one is a capital",
				input -> input.matches("[A-Z][a-zA-Z]+"));
		LocalDate birthDate = io.readDate("Enter employee birthdate in ISO format (yyyy-MM-dd)",
				"Birth Date must be in range [1960-12-31 - 2004-12-31]", LocalDate.of(1960, 12, 31),
				LocalDate.of(2004, 12, 31));
		String department = io.readString("Enter employee department",
				"Department must be one out of QA, Development, Audit, Accounting, Management", getHashSet());
		String salaryStr = io.readString("Enter employee salary");
		int salary = InputOutput.readInt(salaryStr, "Invalid salary format", 7000, 50000);

		Employee empl = new Employee(id, name, department, salary, birthDate);

		io.writeObjectLine("Employee Data:");
		io.writeObjectLine("ID: " + empl.id());
		io.writeObjectLine("Name: " + empl.name());
		io.writeObjectLine("Birth Date: " + empl.birthDate());
		io.writeObjectLine("Department: " + empl.department());
		io.writeObjectLine("Salary: " + empl.salary());
	}

	private HashSet<String> getHashSet() {
		HashSet<String> options = new HashSet<>();
		options.add("QA");
		options.add("Development");
		options.add("Audit");
		options.add("Accounting");
		options.add("Management");
		return options;
	}

	@Test
	void testSimpleArithmeticCalculator() {
	    String num1 = io.readString("Enter num1");
	    int number1 = InputOutput.readInt(num1, "Invalid number format", 0, 99999999);
	    String num2 = io.readString("Enter num2");
	    int number2 = InputOutput.readInt(num2, "Invalid number format", 0, 99999999);

	    char operator = io.readOperator("Enter operator", "Invalid operator");

	    int res = 0;
	    switch (operator) {
	        case '+':
	            res = number1 + number2;
	            break;
	        case '-':
	            res = number1 - number2;
	            break;
	        case '*':
	            res = number1 * number2;
	            break;
	        case '/':
	            if (number2 != 0) {
	                res = number1 / number2;
	            } else {
	                io.writeLine("Error: Division by zero");
	                return;
	            }
	            break;
	        default:
	            io.writeLine("Error: Invalid operator");
	            return;
	    }

	    io.writeObjectLine("Result: " + res);
	}
}
