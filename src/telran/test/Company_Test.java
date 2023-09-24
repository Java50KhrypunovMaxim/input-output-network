package telran.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import telran.employee.DepartmentSalary;
import telran.employee.Employee;
import telran.employee.SalaryDistribution;
import telran.employee.service.Company;
import telran.employee.service.CopmanyImplemention;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class Company_Test {
//TODO
	final static String TEST_FILE_NAME = "test.data";
	Employee empl1 = new Employee(111222, "MAX", "Back End", 19000, LocalDate.of(1989,2,17));
	Employee empl2 = new Employee(111333, "TOLIK","Back End", 18000, LocalDate.of(1990, 3, 14));
	Employee empl3 = new Employee(111444, "Irina", "Design", 14000, LocalDate.of(1991,5,17));
	Employee empl4 = new Employee(111555, "Nadya", "Design", 9000, LocalDate.of(1994,8,27));
	Employee empl5 = new Employee(111666, "Valera", "Front end", 17000, LocalDate.of(1985,12,7));
	Employee[] employees = {empl1, empl2, empl3, empl4, empl5};
	Company company;

	@BeforeEach
	void setUp() throws Exception {
		company = new CopmanyImplemention();
		for(Employee empl: employees) {
			company.addEmployeer(empl);
		}
	}

	@Test
	void testAddEmployee() {
		assertTrue(company.addEmployeer(new Employee(111777, "Sasha", "Front end", 17000, LocalDate.of(1989,5,5))));
		assertFalse(company.addEmployeer(new Employee(111777, "Sasha", "Front end", 17000, LocalDate.of(1989,5,5))));
	}

	@Test
	void testRemoveEmployee() {
		company.removeEmployeer(111222);
		assertTrue(company.addEmployeer(new Employee(111222, "MAX", "Back End", 20000, LocalDate.of(1989,2,17))));
		
	}

	@Test
	void testGetEmployee() {
		assertEquals(empl1, company.getEmployeer(111222));
	}

	@Test
	void testGetEmployees() {
		List<Employee> expectedEmployees = Arrays.asList(employees);
	    List<Employee> actualEmployees = company.getEmployees();
	    actualEmployees.sort(Comparator.comparingLong(Employee::id));
	    assertEquals(expectedEmployees.size(), actualEmployees.size());
	    for (int i = 0; i < expectedEmployees.size(); i++) {
	        assertEquals(expectedEmployees.get(i), actualEmployees.get(i));
	    }
	}
	
	@Test
	@Order(2)
	void testRestore() throws Exception {
		Company restoreCompany =  new CopmanyImplemention();
		restoreCompany.restore(TEST_FILE_NAME);
		assertEquals(empl1, restoreCompany.getEmployeer(111222));
		assertEquals(empl2, restoreCompany.getEmployeer(111333));	
		List<Employee> expectedEmployees = Arrays.asList(employees);
	    List<Employee> actualEmployees = restoreCompany.getEmployees();
	    actualEmployees.sort(Comparator.comparingLong(Employee::id));
	    assertEquals(expectedEmployees.size(), actualEmployees.size());
	    for (int i = 0; i < expectedEmployees.size(); i++) {
	        assertEquals(expectedEmployees.get(i), actualEmployees.get(i));
	    }
	}
	@Test
	@Order(1)
	void testSave() {
		company.save(TEST_FILE_NAME);
	}
	@Test
	void testGetDepartmentSalaryDistribution() {
		DepartmentSalary ex1 = new DepartmentSalary("Back End", 18500);
		DepartmentSalary ex2 = new DepartmentSalary("Design", 11500);
		DepartmentSalary ex3 = new DepartmentSalary("Front end", 17000);
		List<DepartmentSalary> expected = List.of(ex2,ex3,ex1);
		List<DepartmentSalary> actual = company.getDepartmentSalaryDistribution();
		assertIterableEquals(expected, actual);
	}
	@Test
	void testGetSalaryDistribution() {
		SalaryDistribution sd1 = new SalaryDistribution(0, 10000, 1);
		SalaryDistribution sd2 = new SalaryDistribution(10000, 20000, 4);
		List<SalaryDistribution> expected = List.of(sd1,sd2);
		List<SalaryDistribution> actual = company.getSalaryDistribution(10000);
		assertIterableEquals(expected, actual);
	}
	@Test
	void testGetEmployeesByDepartment() {
		List<Employee> expected = new ArrayList();
		expected.add(empl3);
		expected.add(empl4);
		List<Employee> actual = company.getEmployeesBySalary(6000,15000);
		actual.sort(Comparator.comparingLong(Employee::id));
		assertIterableEquals(expected, actual);  
	}
	@Test
	void testGetEmployeesBySalary(){
		List<Employee> expected = new ArrayList();
		expected.add(empl1);
		expected.add(empl2);
		List<Employee> actual = company.getEmployeesByDepartment("Back End");
		actual.sort(Comparator.comparingLong(Employee::id));
		assertIterableEquals(expected, actual);  
	}
	@Test
	void testGetEmployeesByAge(){
		List<Employee> expected = new ArrayList();
		expected.add(empl4);
		List<Employee> actual = company.getEmployeesByAge(18, 30);
		assertIterableEquals(expected, actual);  
	}
	@Test
	void testUpdateSalary() {
		Employee empl = company.updateSalary(111222,22000);
		assertTrue(empl.salary()==22000);
		List<Employee> actualEmployees = company.getEmployees();
		assertEquals(actualEmployees.size(),5);
		Employee newMax= company.getEmployeer(111222);
		assertEquals(newMax.salary(),22000);
		
	}
	
	@Test
	void testUpdateDepartment() {
		Employee empl = company.updateDepartment(111222,"Front end");
		assertTrue(empl.department().equals("Front end"));
		List<Employee> actualEmployees = company.getEmployees();
		assertEquals(actualEmployees.size(),5);
		Employee newMax= company.getEmployeer(111222);
		assertEquals(newMax.department(),"Front end");
	}

}
