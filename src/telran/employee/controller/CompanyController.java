package telran.employee.controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import telran.employee.DepartmentSalary;
import telran.employee.Employee;
import telran.employee.SalaryDistribution;
import telran.employee.service.Company;
import telran.view.InputOutput;
import telran.view.Item;

public class CompanyController {
private static final long MIN_ID = 100000;
private static final long MAX_ID = 999999;
private static final String[] DEPARTMENTS = {"QA", "Development", "Audit", "Accounting", "Management"};
private static final int MIN_SALARY = 7000;
private static final int MAX_SALARY = 50000;
private static final int MIN_YEAR = 1950;
private static final int MAX_YEAR = 2001;
private static Company company;
	public static ArrayList<Item> getItems(Company company) {
		CompanyController.company = company;
		List<Item> itemsList = getItemsList();
		ArrayList<Item> res = new ArrayList<>(itemsList);
		return res;
	}
	private static List<Item> getItemsList() {
		
		return List.of(
				Item.of("Hire new Employee", CompanyController::addEmployee),
				Item.of("Fire  Employee", CompanyController::removeEmployee),
				Item.of("Display data of Employee", CompanyController::getEmployee),
				Item.of("Display data of all Employees", CompanyController::getEmployees),
				Item.of("Distribution of salary by departments", CompanyController::getDepartmentSalaryDistribution),
				Item.of("Salary distribution per interval", CompanyController::getSalaryDistribution),
				Item.of("Display data of Employees in department", CompanyController::getEmployeesByDepartment),
				Item.of("Display data of Employees by salary", CompanyController::getEmployeesBySalary),
				Item.of("Display data of Employees by age", CompanyController::getEmployeesByAge),
				Item.of("Update salary", CompanyController::updateSalary),
				Item.of("Update department", CompanyController::updateDepartment)
				);
	}
	static void addEmployee(InputOutput io) {
		long id = io.readLong("Enter employee identity", "Wrong identity", MIN_ID, MAX_ID);
		String name = io.readString("Enter name", "Wrong name", str -> str.matches("[A-Z][a-z]{2,}"));
		String department = io.readString("Enter department " + Arrays.deepToString(DEPARTMENTS), "Wrong department", new HashSet<String>(List.of(DEPARTMENTS)));
		int salary = io.readInt("Enter Salary", "Wrong salary", MIN_SALARY, MAX_SALARY);
		LocalDate birthDate = io.readIsoDate("Enter birtdate in ISO format", "Wrong birthdate",
		LocalDate.of(MIN_YEAR,1, 1), LocalDate.of(MAX_YEAR,12, 31));
		Employee empl = new Employee(id, name, department, salary, birthDate);
		boolean res = company.addEmployeer(empl);
		io.writeLine(res ? "Employee has been added" : "Employee already exists");
	}
static void removeEmployee(InputOutput io) {
	long id = io.readLong("Enter employee identity", "Wrong identity", MIN_ID, MAX_ID);
	company.removeEmployeer(id);
	System.out.println("employee was removed successfully");
	}
static void getEmployee(InputOutput io) {
	long id = io.readLong("Enter employee identity", "Wrong identity", MIN_ID, MAX_ID);
	Employee emp = company.getEmployeer(id);
	System.out.println(emp);
}
static void getEmployees(InputOutput io) {
	List<Employee> employees = company.getEmployees();
	employees.forEach(System.out::println);
}
static void getDepartmentSalaryDistribution(InputOutput io) {
	System.out.println(company.getDepartmentSalaryDistribution());
}
static void getSalaryDistribution(InputOutput io) {
	int interval = io.readInt("Enter interval", "Wrong identity", 500, 10000);
	System.out.println(company.getSalaryDistribution(interval));
	System.out.println("Operation was successfully");
	
}
static void getEmployeesByDepartment(InputOutput io) {
	String department = io.readString("Enter department " + Arrays.deepToString(DEPARTMENTS), "Wrong department", 
			new HashSet<String>(List.of(DEPARTMENTS)));
	System.out.println(company.getEmployeesByDepartment(department));
	System.out.println("Operation was successfully");
}
static void getEmployeesBySalary(InputOutput io) {
	int salaryFrom = io.readInt("Enter whrom which Salary", "Wrong salary", MIN_SALARY, MAX_SALARY);
	int salaryUntil = io.readInt("Enter until which Salary", "Wrong salary", MIN_SALARY, MAX_SALARY);
	System.out.println(company.getEmployeesBySalary(salaryFrom, salaryUntil));
	System.out.println("Operation was successfully");
}
static void getEmployeesByAge(InputOutput io) {
	int agefrom = io.readInt("Enter from which age", "Wrong identity", 18, 70);
	int ageUntil = io.readInt("Enter until which age", "Wrong identity", 18, 70);
	System.out.println(company.getEmployeesByAge(agefrom, ageUntil));
	System.out.println("Operation was successfully");
}
static void updateSalary(InputOutput io) {
	long id = io.readLong("Enter employee identity", "Wrong identity", MIN_ID, MAX_ID);
	int salary = io.readInt("Enter new salary", "Wrong salary", MIN_SALARY, MAX_SALARY);
	company.updateSalary(id, salary);
	System.out.println("salary was changed successfully");
	System.out.println("new salary is " + salary);
	
}
static void updateDepartment(InputOutput io) {
	long id = io.readLong("Enter employee identity", "Wrong identity", MIN_ID, MAX_ID);
	String department = io.readString("Enter department " + Arrays.deepToString(DEPARTMENTS), 
	"Wrong department", new HashSet<String>(List.of(DEPARTMENTS)));
	company.updateDepartment(id, department);
	System.out.println("Department was changed successfully");
	System.out.println("New department is " + department);
}

}



