package telran.people.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.HashSet;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import telran.people.Company;
import telran.people.CompanyImpl;
import telran.people.Employee;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CompanyTests {

	private static final long ID = 123;
	private static final String NAME = "Vasya";
	private static final String DEPARTMENT = "QA";
	private static final int SALARY = 10000;
	private static final long ID_1 = 1234;
	private static final String NAME_1 = "Petya";
	private static final String DEPARTMENT_1 = "Development";
	private static final int SALARY_1 = 15000;
	private static final String COMPANY_FILE = "My_company";
	private Company company;
	private Employee empl = new Employee(ID, NAME, DEPARTMENT, SALARY);
	private Employee empl1= new Employee(ID_1, NAME_1, DEPARTMENT_1, SALARY_1);
	int b;
		
	@BeforeEach
	void setUp() {
		try {			
			company = CompanyImpl.createCompany(COMPANY_FILE);	
		} catch (Exception e) {
			e.printStackTrace();
		}	
		b = 9;
	}
	@Test
	@Order(1)
	void CreateCompanyWithoutFileAndGetNonExistentEmployee () {
		File file = new File(COMPANY_FILE);
		file.delete();
		try {			
			company = CompanyImpl.createCompany(COMPANY_FILE);	
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertThrows(NoSuchElementException.class, () -> company.getEmployee(ID));
	}
	
	@Test
	@Order(2)
	void AddEmployeeWithSavingAndGetEmployee() {
		try {
			company.addEmployee(empl);
			company.save(COMPANY_FILE);
			assertEquals(empl, company.getEmployee(ID));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	@Order(3)
	void CreateCompanyFromFileWithOneEmployee() {			
		try {
			company = null;
			company = CompanyImpl.createCompany(COMPANY_FILE);
			assertEquals(empl, company.getEmployee(ID));
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}

	@Test
	@Order(4)
	void AddSecondEmployeeAndGetAllEmployees() {
		try {
			company.addEmployee(empl1);
			HashSet<Employee> expected = new HashSet<>();
			expected.add(empl);
			expected.add(empl1);
			assertEquals(expected, company.getAllEmployees());
			company.save(COMPANY_FILE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	@Order(5)
	void testGetEmployeesDepartment() {
		HashSet<Employee> expected = new HashSet<>();
		expected.add(empl);
		assertEquals(expected, company.getEmployeesDepartment(DEPARTMENT));
	}

	@Test
	@Order(6)
	void testGetEmployeesSalary() {
		HashSet<Employee> expected = new HashSet<>();
		expected.add(empl1);
		assertEquals(expected, company.getEmployeesSalary(SALARY + 1, SALARY_1));
	}
	
}
