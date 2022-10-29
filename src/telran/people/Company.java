package telran.people;

import java.io.Serializable;

public interface Company extends Serializable{
	
	Iterable<Employee> getAllEmployees(); //returns all employees
	Employee getEmployee(long id); //returns Employee by id
	void addEmployee(Employee empl) throws Exception; //adds new employee, exception if already exists
	void save(String filePath) throws Exception; //serialization
	Iterable<Employee> getEmployeesDepartment(String department); //returns employees of a given department
	Iterable<Employee> getEmployeesSalary(int salaryFrom, int salaryTo); //returns employees with salary in range [from, to]
	
}
