package telran.employee;

import java.io.Serializable;
import java.util.ArrayList;

import telran.employee.service.Company;
import telran.games.dto.FromTo;
import telran.games.dto.UpdateDepartment;
import telran.games.dto.UpdateSalaryDate;
import telran.net.ApplProtocol;
import telran.net.Request;
import telran.net.Response;
import telran.net.ResponseCode;

public class CompanyProtocol implements ApplProtocol {
	private Company company;

	public CompanyProtocol(Company company) {
		this.company = company;
	}

	@Override
	public Response getResponse(Request request) {
		Serializable requestData = request.requestData();
		String requestType = request.requestType();
		Response response = null;
		Serializable responseData = 0;
		try {
			responseData = switch (requestType) {
			case "employee/add" -> employee_add(requestData);
			case "employee/remove" -> employee_remove(requestData);
			case "employee/get" -> employee_get(requestData);
			case "employee/allEmployees" -> employees_all(requestData);
			case "employee/getDepartmentSalaryDistrib" -> employees_getDepartmentSalaryDistrib(requestData);
			case "employee/getSalaryDistrInterval" -> employees_getSalaryDistrInterval(requestData);
			case "employee/getEmplDepartment" -> employees_getEmplDepartment(requestData);
			case "employee/getEmployeesBySal" -> employees_getEmployeesBySal(requestData);
			case "employee/getEmployeesByAge" -> employees_getEmployeesByAge(requestData);
			case "employee/salary/update" -> employee_salary_update(requestData);
			case "employee/department/update" -> employee_department_update(requestData);
			default -> 0;
			};
			response = responseData == (Integer)0 ? new Response(ResponseCode.WRONG_TYPE, requestType)
					: new Response(ResponseCode.OK, responseData);
		} catch (Exception e) {
			response = new Response(ResponseCode.WRONG_DATA, e.getMessage());
		}

		return response;
	}


	private Serializable employee_add(Serializable requestData) {
		Employee empl = (Employee) requestData;
		return company.addEmployeer(empl);
	}
	
	private Serializable employee_remove(Serializable requestData) {
		long id = (long) requestData;
		return company.removeEmployeer(id);
	}
	
	private Serializable employee_get(Serializable requestData) {
		long id = (long) requestData;
		return company.getEmployeer(id);
	}
	
	private Serializable employees_all(Serializable requestData) {
		
		return new ArrayList<>(company.getEmployees());
	}
	
	private Serializable employees_getDepartmentSalaryDistrib(Serializable requestData) {
		return new ArrayList<>(company.getDepartmentSalaryDistribution());
	}

	private Serializable employees_getSalaryDistrInterval(Serializable requestData) {
		Integer interval = (Integer) requestData;
		return new ArrayList<> (company.getSalaryDistribution(interval));
	}
	
	private  Serializable employees_getEmplDepartment(Serializable requestData) {
		String department = (String) requestData;
		return new ArrayList<> (company.getEmployeesByDepartment(department));
		
	}
	
	private Serializable employees_getEmployeesBySal(Serializable requestData) {
		FromTo fromTo = (FromTo) requestData;
		int salaryFrom =fromTo.from();
		int salaryTo =fromTo.to();
		return new ArrayList<>(company.getEmployeesBySalary(salaryFrom, salaryTo));	
		}
	

	private Serializable employees_getEmployeesByAge(Serializable requestData) {
		FromTo fromTo = (FromTo) requestData;
		int ageFrom =fromTo.from();
		int ageTo =fromTo.to();
		return new ArrayList<>(company.getEmployeesByAge(ageFrom, ageTo));	
		}
	


	
	private Serializable employee_salary_update(Serializable requestData) {
		UpdateSalaryDate data = (UpdateSalaryDate) requestData;
		long id = data.id();
		int newSalary = data.salary();
		return company.updateSalary(id, newSalary);
	}
	
	private Serializable employee_department_update(Serializable requestData) {
		UpdateDepartment data = (UpdateDepartment) requestData;
		long id = data.id();
		String newDepartment = data.department();
		return company.updateDepartment(id, newDepartment);
	}
}