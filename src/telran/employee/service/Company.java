package telran.employee.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import telran.employee.Employee;


public interface Company {
boolean addEmployeer(Employee eml);
Employee removeEmployeer(long id);
Employee getEmployeer(long id);
List<Employee> getEmployees();
default void restore(String dataFile) throws ClassNotFoundException
{
	
	try(ObjectInputStream input = new ObjectInputStream(new FileInputStream(dataFile)))
	{
		List <Employee> employees = (List <Employee>) input.readObject();
		 employees.forEach(this::addEmployeer);
    } catch (IOException e) {
        e.printStackTrace();
    }
}
	
default void save(String dataFile)
{
	try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(dataFile)))
	{
		List <Employee> employees = getEmployees();
		output.writeObject(employees);
    } catch (IOException e) {
        e.printStackTrace();
    }
}

}

