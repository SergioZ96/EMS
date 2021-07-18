package JavaProject;


public class Employee extends Department{
	
	//Range definition for id
	private int min = 1, max = 100;

	// Attributes
	private int id;
	private String name;
	private int salary;
	private Department department;
	
	// Default constructor
	public Employee() {
		this.id = (int)(Math.random() * max) + min;
		this.name = null;
		this.salary = 0;
		this.department = null;
	}
	
	// Overloaded Constructor
	public Employee(String name, int salary, Department dept) {
		this.id = (int)(Math.random() * max) + min;
		this.name = name;
		this.salary = salary;
		this.department = dept;
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

	public Department getDepartment() {
		return department;
	}

	public void setDepartment( Department department) {
		this.department = department;
	}
	
	// getters and setters
	
	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", salary=" + salary + ", department=" + department + "]";
	}
	
	
}