package telran.test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import telran.employee.Employee;
import telran.employee.service.Company;
import telran.employee.service.CopmanyImplemention;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class Company_Test {
//TODO
	final static String TEST_FILE_NAME = "test.data";
	Employee empl1 = new Employee(111222, "MAX", "Back End", 20000, LocalDate.of(1989,2,17));
	Employee empl2 = new Employee(111333, "TOLIK","Back End", 18000, LocalDate.of(1990, 3, 14));
	Employee empl3 = new Employee(111444, "Irina", "Design", 14000, LocalDate.of(1991,5,17));
	Employee empl4 = new Employee(111555, "Nadya", "Design", 15000, LocalDate.of(1994,8,27));
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
		assertThrows(IllegalArgumentException.class,()-> company.removeEmployeer(1112222));
	}

	@Test
	void testGetEmployee() {
		assertEquals(empl1, company.getEmployeer(111222));
		assertThrows(IllegalArgumentException.class,()->company.getEmployeer(111000));
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

}