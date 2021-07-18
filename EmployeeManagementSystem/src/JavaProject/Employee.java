package JavaProject;


public class Employee extends Department {
	
	private static int idCounter = 1;

	// Attributes
	private int id;
	private String name;
	private int salary;
	private Department.DeptEnum department;
	
	
	// Default constructor
	public Employee() {
		this.id = idCounter++;
		this.name = null;
		this.salary = 0;
		this.department = null;
	}
	
	// Overloaded Constructor
	public Employee(String name, int salary, Department.DeptEnum dept) {
		this.id = idCounter++;
		this.name = name;
		this.salary = salary;
		this.department = dept;
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public Department.DeptEnum getDepartment() {
		return department;
	}

	public void setDepartment( Department.DeptEnum department) {
		this.department = department;
	}
	
	// getters and setters
	
	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", salary=" + salary + ", department=" + department + "]";
	}
	
	
}