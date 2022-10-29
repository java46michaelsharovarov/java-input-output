package telran.people.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import telran.people.Company;
import telran.people.CompanyImpl;
import telran.people.Employee;

import java.io.*;
import java.util.*;

class CompanyFunctionalTest {

	private static final String TEST_DATA_FILE = "company-test.data";
	private static final String DEPARTMENT1 = "department1";
	private static final String DEPARTMENT2 = "department2";
	private static final int SALARY1 = 10000;
	private static final int SALARY3 = 15000;
	private static final int SALARY4 = 20000;
	private static final long ID = 123;
	static File file;
	static Company company;
	static Employee empl1 = new Employee(ID, "name1", DEPARTMENT1, SALARY1);
	static Employee empl2 = new Employee(ID + 1, "name1", DEPARTMENT1, SALARY1);
	static Employee empl3 = new Employee(ID + 2, "name1", DEPARTMENT2, SALARY3);
	static Employee empl4 = new Employee(ID + 3, "name1", DEPARTMENT2, SALARY4);
	static Employee allEmployees[] = {empl1, empl2, empl3, empl4};
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		file = new File(TEST_DATA_FILE);
		company = CompanyImpl.createCompany(TEST_DATA_FILE);
		for(Employee empl: allEmployees) {
			company.addEmployee(empl);
		}
		company.save(TEST_DATA_FILE);		
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		file.delete();
	}

	@Test
	void addExistedTest() {
		assertThrows(Exception.class, ()->company.addEmployee(empl1));
	}

	@Test
	void getEmployee() {
		assertEquals(empl1, company.getEmployee(ID));
		assertNull(company.getEmployee(-1));
	}
	
	@Test
	void getAllEmployees() {
		runArrayIterableTest(allEmployees, company.getAllEmployees());
	}
	
	private void runArrayIterableTest(Employee[] array, Iterable<Employee> iterable) {
		int index = 0;
		Set<Employee> setEmployees = new HashSet<>(Arrays.asList(array));
		for(Employee empl: iterable) {
			assertTrue(setEmployees.contains(empl));
			index++;
		}
		assertEquals(array.length, index);		
	}

	@Test
	void getEmployeesSalary() {
		Employee[] expected = {empl1, empl2, empl3};
		runArrayIterableTest(expected, company.getEmployeesSalary(SALARY1, SALARY3));
		Employee[] expectedEmpty = {};
		runArrayIterableTest(expectedEmpty, company.getEmployeesSalary(1, 2));
	}
	
	@Test
	void getEmployeesDepartment() {
		Employee[] expected = {empl1, empl2};
		runArrayIterableTest(expected, company.getEmployeesDepartment(DEPARTMENT1));
		Employee[] expectedEmpty = {};
		runArrayIterableTest(expectedEmpty, company.getEmployeesDepartment("xxx"));		
	}
	
	@Test
	void saveRestore() throws Exception{
		Company savedCompany = CompanyImpl.createCompany(TEST_DATA_FILE);
		assertIterableEquals(company.getAllEmployees(), savedCompany.getAllEmployees());
	}

}
