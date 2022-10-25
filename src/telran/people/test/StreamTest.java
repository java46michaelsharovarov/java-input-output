package telran.people.test;

import static org.junit.jupiter.api.Assertions.*;
import java.io.*;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StreamTest {

	private static final long ID = 123;
	private static final String NAME = "Vasya";
	private static final String DEPARTMENT = "QA";
	private static final int SALARY = 10000;
	private static final String EMPLOYEES_FILE = "employees.data";

	@Test
	@Order(1)
	void writeEmployee() throws Exception {
		EmployeeTest empl = new EmployeeTest(ID, NAME, DEPARTMENT, SALARY);
		empl.employee = empl;
		try (ObjectOutputStream output = new ObjectOutputStream(
				new FileOutputStream(EMPLOYEES_FILE))){
			output.writeObject(empl);			
		}
	}
	
	@Test
	@Order(2)
	void readEmployee() throws Exception {
		EmployeeTest empl = null;
		EmployeeTest expected = new EmployeeTest(ID, NAME, DEPARTMENT, SALARY);
		try (ObjectInputStream input =
				new ObjectInputStream(new FileInputStream(EMPLOYEES_FILE))) {
			empl = (EmployeeTest) input.readObject();
		}
		assertEquals(expected, empl.employee);
	}

}
