package telran.people;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;
import java.util.stream.Collectors;

public class CompanyImpl implements Company {

	private HashMap<Long, Employee> employees = new HashMap<>();
	private TreeMap<Integer, List<Employee>> employeesSalary = new TreeMap<>();
	private HashMap<String, List<Employee>> employeesDepartment = new HashMap<>();
	private static final long serialVersionUID = 1L;
	
	private CompanyImpl() {}
	
	public static Company createCompany(String fileName) throws Exception {
		//if file exists it restore Company from file, otherwise returns empty CompanyImpl object
		File source = new File(fileName);
		CompanyImpl company;
		if(source.exists()){
			try (ObjectInputStream input =
					new ObjectInputStream(new FileInputStream(new File(fileName)))) {
				company = (CompanyImpl) input.readObject();
			} 
		} else {
			company = new CompanyImpl();
		}		
		return company;
	}

	@Override
	public Iterable<Employee> getAllEmployees() {
		return getIterable(employees.values());
	}

	@Override
	public void addEmployee(Employee empl) throws Exception {
		if(employees.containsKey(empl.getId())) {
			throw new IllegalArgumentException(
					String.format("Employee with ID: %n already exists", empl.getId()));
		}
		employees.put(empl.getId(), empl);
		employeesSalary.computeIfAbsent(empl.getSalary(), k -> new ArrayList<Employee>()).add(empl);
		employeesDepartment.computeIfAbsent(empl.getDepartment(), k -> new ArrayList<Employee>()).add(empl);
	}

	@Override
	public void save(String filePath) throws Exception {
		try (ObjectOutputStream output =
				new ObjectOutputStream(new FileOutputStream(new File(filePath)))) {
			output.writeObject(this);			
		}
	}

	@Override
	public Iterable<Employee> getEmployeesDepartment(String department) {
		return getIterable(employeesDepartment.get(department));
	}

	@Override
	public Iterable<Employee> getEmployeesSalary(int salaryFrom, int salaryTo) {
		return employeesSalary.subMap(salaryFrom, true, salaryTo, true)
				.values()
				.stream()
				.flatMap(e -> e.stream())
				.collect(Collectors.toCollection(HashSet::new));
	}

	@Override
	public Employee getEmployee(long id) {
		Employee res = employees.get(id);
		if(res == null) {
			throw new NoSuchElementException(String.format("Employee with ID: %n does not exist", id));
		}
		return res;
	}

	private Iterable<Employee> getIterable(Collection<Employee> values) {
		return values.stream().collect(Collectors.toCollection(HashSet::new));
	}

}
