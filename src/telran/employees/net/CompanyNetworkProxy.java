package telran.employees.net;

import java.util.ArrayList;
import java.util.List;

import telran.employee.DepartmentSalary;
import telran.employee.Employee;
import telran.employee.SalaryDistribution;
import telran.employee.service.Company;
import telran.games.dto.FromTo;
import telran.games.dto.UpdateDepartment;
import telran.games.dto.UpdateSalaryDate;
import telran.net.NetworkHandler;

public class CompanyNetworkProxy implements Company {
    private NetworkHandler networkHandler;
    
    public CompanyNetworkProxy (NetworkHandler networkHandler) {
    	this.networkHandler = networkHandler;
    }
	@Override
	public boolean addEmployeer(Employee eml) {
		
		return networkHandler.send("employee/add", eml);
	}

	@Override
	public Employee removeEmployeer(long id) {
		return networkHandler.send("employee/remove", id);
	}
	
	@Override
	public Employee getEmployeer(long id) {
		
		return networkHandler.send("employee/get", id);
	}

	@Override
	public List<Employee> getEmployees() {
		return networkHandler.send("employee/allEmployees",  null);
	}

	@Override
	public List<DepartmentSalary> getDepartmentSalaryDistribution() {
		
		return networkHandler.send("employee/getDepartmentSalaryDistrib",  null);
	}

	@Override
	public List<SalaryDistribution> getSalaryDistribution(int interval) {
		return networkHandler.send("employee/getSalaryDistrInterval", interval);
	}

	@Override
	public List<Employee> getEmployeesByDepartment(String department) {
		return networkHandler.send("employee/getEmplDepartment", department);
	}

	@Override
	public List<Employee> getEmployeesBySalary(int salaryFrom, int salaryTo) {
	    return networkHandler.send("employee/getEmployeesBySal", new FromTo(salaryFrom, salaryTo));
	}

	@Override
	public List<Employee> getEmployeesByAge(int ageFrom, int ageTo) {
		return networkHandler.send("employee/getEmployeesByAge", new FromTo(ageFrom, ageTo));
	}

	@Override
	public Employee updateSalary(long id, int newSalary) {
		
		return networkHandler.send("employee/salary/update", new UpdateSalaryDate(id,newSalary));
	}

	@Override
	public Employee updateDepartment(long id, String department) {
		return networkHandler.send("employee/department/update", new UpdateDepartment (id,department));
	}

}
