package telran.employee.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import telran.employee.DepartmentSalary;
import telran.employee.Employee;
import telran.employee.SalaryDistribution;

public class CopmanyImplemention implements Company {
		
		HashMap<Long, Employee> employees = new HashMap<>(); //most effective structure for the interface methods
		TreeMap<Integer, Collection<Employee>> employeesSalary = new TreeMap<>();
		TreeMap<LocalDate, Collection<Employee>> employeesAge = new TreeMap<>();
		HashMap<String, Collection<Employee>> employeesDepartment = new HashMap<>();

		@Override
		public boolean addEmployeer (Employee empl) {
			boolean res = false;
			Employee emplRes = employees.putIfAbsent(empl.id(), empl);
			if (emplRes == null) {
				res = true;
				addEmployeeSalary(empl);
				addEmployeeAge(empl);
				addEmployeeDepartment(empl);
			}
			return res;
		}

		private <T> void addToIndex(Employee empl, T key, Map<T, Collection<Employee>> map) {
			map.computeIfAbsent(key, k -> new HashSet<>()).add(empl);
		}
		private <T> void removeFromIndex(Employee empl, T key, Map<T, Collection<Employee>> map) {

			Collection<Employee> employeesCol = map.get(key);
			employeesCol.remove(empl);
			if (employeesCol.isEmpty()) {
				map.remove(key);
			}
		}

		private void addEmployeeSalary(Employee empl) {
			addToIndex(empl, empl.salary(), employeesSalary);

		}

		private void addEmployeeAge(Employee empl) {
			addToIndex(empl, empl.birthDate(), employeesAge);

		}

		private void addEmployeeDepartment(Employee empl) {
			addToIndex(empl, empl.department(), employeesDepartment);

		}

		@Override
		public Employee removeEmployeer(long id) {
			Employee res = employees.remove(id);
			if (res != null) {
				removeEmployeeSalary(res);
				removeEmployeeAge(res);
				removeEmployeeDepartment(res);
			}
			return res;
		}

		

		private void removeEmployeeSalary(Employee empl) {
			int salary = empl.salary();
			removeFromIndex(empl, salary, employeesSalary);

		}

		private void removeEmployeeAge(Employee empl) {
			removeFromIndex(empl, empl.birthDate(), employeesAge);

		}

		private void removeEmployeeDepartment(Employee empl) {
			removeFromIndex(empl, empl.department(), employeesDepartment);

		}

		@Override
		public Employee getEmployeer(long id) {

			return employees.get(id);
		}

		@Override
		public List<Employee> getEmployees() {

			return new ArrayList<>(employees.values());
		}

		@Override
		public List<DepartmentSalary> getDepartmentSalaryDistribution() {
			return employees.values().stream().collect(Collectors.groupingBy(Employee::department,
					Collectors.averagingInt(Employee::salary))).
					entrySet().stream().map(e -> new DepartmentSalary(e.getKey(), e.getValue())).toList();
		}

		@Override
		public List<SalaryDistribution> getSalaryDistribution(int interval) {
			Map<Integer, Long> mapIntervalNumbers = employees.values().stream()
					.collect(Collectors.groupingBy(e -> e.salary() / interval, Collectors.counting()));
			return mapIntervalNumbers.entrySet().stream()
					.map(e -> new SalaryDistribution(e.getKey() * interval, e.getKey() * interval + interval, e.getValue().intValue()))
					.sorted((sd1, sd2) -> Integer.compare(sd1.min(), sd2.min())).toList();
		}

		@Override
		public List<Employee> getEmployeesByDepartment(String department) {
			 List<Employee> empl = employees.values()
			            .stream()
			            .filter(e -> e.department().equals(department))
			            .collect(Collectors.toList());
			    return empl;
		}

		@Override
		public List<Employee> getEmployeesBySalary(int salaryFrom, int salaryTo) {
			 List<Employee> salaryEmployeer = employees.values().stream().filter(e->
			 e.salary() >= salaryFrom && e.salary() <= salaryTo).collect(Collectors.toList());
			return salaryEmployeer;
		}

		@Override
		public List<Employee> getEmployeesByAge(int ageFrom, int ageTo) {
			
			
			LocalDate dateTo = LocalDate.now().minusYears(ageFrom);
			LocalDate dateFrom = LocalDate.now().minusYears(ageTo);
			return employeesAge.subMap(dateFrom, true, dateTo, true).values().stream()
					.flatMap(col -> col.stream()
					.sorted((empl1, empl2) -> Long.compare(empl1.id(), empl2.id())))
					.toList();
		}

		private int getAge(LocalDate birthDate) {
			
			return (int)ChronoUnit.YEARS.between(birthDate, LocalDate.now());
		}
		

		@Override
		public Employee updateSalary(long id, int newSalary) {
			Employee employee = getEmployeer(id);
			removeEmployeer(id);
			Employee newEmployee = new Employee (id, employee.name(), employee.department(), newSalary, employee.birthDate());
			addEmployeer(newEmployee);
			return newEmployee;
		}

		@Override
		public Employee updateDepartment(long id, String department) {
			Employee employee = getEmployeer(id);
			removeEmployeer(id);
			Employee newEmployee = new Employee (id, employee.name(), department, employee.salary(), employee.birthDate());
			addEmployeer(newEmployee);
			return newEmployee;
		}
		

	}
