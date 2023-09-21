package telran.employee.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import telran.employee.Employee;

public class CopmanyImplemention implements Company {

	HashMap<Long,Employee> employees = new HashMap<>();

	@Override
	public boolean addEmployeer(Employee eml) {
		boolean flag = false;
		if (!employees.containsKey(eml.id())) {
		        employees.put(eml.id(), eml);
		        flag = true; }    
		return flag;
	}

	@Override
	public Employee removeEmployeer(long id) {
	
		if (!employees.containsKey(id)) {
	        throw new IllegalArgumentException("Employee with ID " + id + " not found");
	    }

	    return employees.remove(id);
	}

	@Override
	public Employee getEmployeer(long id) {
		
		 Employee employee = employees.get(id);
		    if (employee == null) {
		        throw new IllegalArgumentException("Employee with ID " + id + " not found");
		    }

		    return employee;
	}

	@Override
	public List<Employee> getEmployees() {
		List<Employee> ourEmployees = new ArrayList<>();
		for (HashMap.Entry<Long,Employee> entry: employees.entrySet())
		{
			Employee myEmployee = entry.getValue();
			ourEmployees.add(myEmployee);
		}
		return ourEmployees;
	}
	}
	
	

