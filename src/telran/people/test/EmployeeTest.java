package telran.people.test;

import telran.people.Employee;

public class EmployeeTest extends Employee {

	private static final long serialVersionUID = 1L;
	public Employee employee;
	
	public EmployeeTest(long id, String name, String department, int salary) {
		super(id, name, department, salary);		
	}
	
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

}
