package telran.people;

import java.util.*;
import java.util.stream.Stream;
import java.io.*;

public class RandomCompanyAppl {
	
	private static final String PROPERTIES_FILE = "generation.properties";
	private static final String N_EMPLOYEES_PROPERTY = "N_Employees";
	private static final String MIN_SALARY = "MinSalary";
	private static final String MAX_SALARY = "MaxSalary";
	private static final String DEPARTMENT_PROPERTY = "Departments";
	public static final String EMPLOYEES_DATA_FILE = "employees.data";
	static long id = 123;
	static int nEmployees;
	static int minSalary;
	static int maxSalary;
	static String departments[];

	public static void main(String[] args) {
		try {
			fillConfigurationProperties();
			List<Employee> employees = getRandomEmployees();
			saveEmployees(employees);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	private static void saveEmployees(List<Employee> employees) throws Exception {
		Company company = CompanyImpl.createCompany(EMPLOYEES_DATA_FILE);
		employees.forEach(t -> {
			try {
				company.addEmployee(t);
			} catch (Exception e) {
				
			}
		});
		company.save(EMPLOYEES_DATA_FILE);		
	}
	
	private static List<Employee> getRandomEmployees() {		
		return Stream.generate(RandomCompanyAppl::getRandomEmployee)
				.limit(nEmployees).toList();
	}
	
	private static Employee getRandomEmployee() {
		id++;
		return new Employee(id, "name" + id, getRandomDepartment(),
				getRandomNumber(minSalary, maxSalary));
	}
	
	private static int getRandomNumber(int min, int max) {		
		return (int) (min + Math.random() * (max - min + 1));
	}
	
	private static String getRandomDepartment() {
		int index = getRandomNumber(0, departments.length - 1);
		return departments[index];
	}
	
	private static void fillConfigurationProperties() throws Exception{
		Properties properties = new Properties();
		try(InputStream input = new FileInputStream(PROPERTIES_FILE)) {
			properties.load(input);
			fillNEmployees(properties);
			fillSalary(properties);
			fillDepartments(properties);
			
		} catch(FileNotFoundException e) {
			throw new Exception(String.format("properties file %s doesn't exist",
					PROPERTIES_FILE));
		} 		
	}
	
	private static void fillNEmployees(Properties properties) throws Exception{
		String strValue = properties.getProperty(N_EMPLOYEES_PROPERTY);
		if(strValue == null) {
			throw new Exception(N_EMPLOYEES_PROPERTY + " doesn't exist");
		}
		try {
			nEmployees = Integer.parseInt(strValue);
		} catch (NumberFormatException e) {
			throw new Exception(String.format("property %s has no number value", N_EMPLOYEES_PROPERTY));
		}		
	}
	
	private static void fillSalary(Properties properties)throws Exception {
		fillMinMaxSalary(MIN_SALARY,properties);
		fillMinMaxSalary(MAX_SALARY, properties);
		
	}
	
	private static void fillMinMaxSalary(String property, Properties properties)throws Exception {
		String strValue = properties.getProperty(property);
		if(strValue == null) {
			throw new Exception(property + " doesn't exist");
		}
		try {
			if (property.equals(MIN_SALARY)) {
				minSalary = Integer.parseInt(strValue);
			} else {
				maxSalary = Integer.parseInt(strValue);
			}
		} catch (NumberFormatException e) {
			throw new Exception(String.format("property %s has no number value", property));
		}		
	}
	
	private static void fillDepartments(Properties properties) throws Exception {
		String strValue = properties.getProperty(DEPARTMENT_PROPERTY);
		if(strValue == null) {
			throw new Exception(DEPARTMENT_PROPERTY + " doesn't exist");
		}
		departments = strValue.split(",");		
	}

}
