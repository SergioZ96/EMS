package JavaProject;

import java.io.Serializable;
import java.util.ArrayList;

public class Employee extends Department implements Serializable{
	
	private static final long serialVersionUID = -6048262654478901337L;
	
	private static int idCounter = 1;
	private static ArrayList<Employee> employeeList = new ArrayList<Employee>();

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
		this.employeeList.add(this);
	}
	
	// Overloaded Constructor
	public Employee(String name, int salary, Department.DeptEnum dept) {
		this.id = idCounter++;
		this.name = name;
		this.salary = salary;
		this.department = dept;
		this.employeeList.add(this);
	}
	
	// getters and setters
	
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
	
	public static ArrayList<Employee> getEmployees() {
		return employeeList;
	}
	
	public static void setEmployees(ArrayList<Employee> empList) {
		Employee.employeeList = empList;
	}
	
	public static void setIdCounter(int counter) {
		idCounter = counter;
	}
	
	
	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", salary=" + salary + ", department=" + department + "]";
	}
	
	
}