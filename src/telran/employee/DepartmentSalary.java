package telran.employee;

import java.io.Serializable;

public record DepartmentSalary(String department, double salary) implements Serializable{

}
