package JavaProject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;




public class emsRunner {

	public static void main(String[] args) throws IOException{
		
		Scanner input = new Scanner(System.in);
		int userInput;
		
		// if our csv mock database doesnt already exist, obtain a list of Employees from employee.txt (starter data)
		// move each Employee into a row in csv file, and store list of Employees in separate data file
		File file = new File("ems.csv");
		if(!file.exists()) {
			fileToEmpAL(); 
			toCSV();
			storeObjects();
		}
		
		
		System.out.println("Welcome to the Employee Management System");
		
		while(true) {
			// retrieval of list of Employees from data file on startup, and setting static Employee arraylist
			Employee.setEmployees(readObjects());
			
			System.out.println("\nYou may enter an option: ");
			System.out.println("1 - View all employees\n"
							 + "2 - View employees by department\n"
							 + "3 - Add employees\n"
							 + "4 - Update employee information\n"
							 + "5 - Delete employees\n"
							 + "6 - Find out who makes too much money\n"
							 + "7 - Exit System");
			
			userInput = input.nextInt();
			
			
			if(userInput == 7) {
				System.out.println("Exiting system...");
				break;
			}
			
			switch(userInput) {
				case 1: // Show all employees
					
					showEmployees(Department.DeptEnum.NONE);
					
					break;
					
				case 2: // Show employees by department
					
					int deptNum;
					System.out.println("Enter which department:\n1 - ACCOUNTING\n2 - RESEARCH_DEVELOPMENT\n3 - HUMAN_RESOURCES\n4 - SALES_MARKETING\n"
							+ "5 - IT_SERVICES\n6 - MANAGEMENT");
					deptNum = input.nextInt();
					showEmployees(Department.DeptEnum.values()[deptNum - 1]);
					
					break;
					
				case 3:// Add employees
					
					input.nextLine(); //needed because we used nextInt() previously, which doesn't read the following new-line character
					
					String newName, department; 
					int newSalary; 
					
					System.out.println("Enter new employee name: ");
					newName = input.nextLine();
					System.out.println(newName);
					
					System.out.println("Enter new employee salary: ");
					newSalary = Integer.parseInt(input.nextLine());
					
					System.out.println("Enter new employee department: ");
					department = input.nextLine();
					
					addEmployee(newName, newSalary, Department.getEnum(department));
					
					break;
					
				case 4: // Update employee information
					
					input.nextLine(); //needed because we used nextInt() previously, which doesn't read the following new-line character
					
					updateEmployeeInfo(input);
					break;
					
				case 5: // Delete employees
					
					input.nextLine(); //needed because we used nextInt() previously, which doesn't read the following new-line character
					
					String name;
					
					System.out.println("Please enter employee's name to be removed: ");
					name = input.nextLine();
					
					System.out.println(name);
					deleteEmployee(name);
					break;
					
				case 6: // Print which employee with the highest salary
					
					input.nextLine(); //needed because we used nextInt() previously, which doesn't read the following new-line character
					
					List<Employee> highestPaid = Employee.getEmployees();
					 
					Employee max = highestPaid.stream()
							.max(Comparator.comparing(Employee::getSalary))
							.get();
					
					System.out.println(max.toString());
					 
					
				default:
					System.out.println("Enter a valid entry");
					break;
			}
		}
		
	}
	
	
	/*
	 * fileToEmpAl() 
	 * 	- reads starter data from employee.txt, converts data into Employee objects, places them in an ArrayList
	 * 	  which is set to Employees static arraylist
	 */
	
	
	public static void fileToEmpAL() throws IOException{
		
		File file = new File("resources/employee.txt");
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		try {
			
			fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(fileReader);
			
			
		} catch(FileNotFoundException e) {
			System.out.println("File Not Found");
		} catch(IOException e) {
			System.out.println("IO Exception");
		} catch(Exception e) {
			System.out.println("General Exception");
		}
		
		
		String line;
		String[] empValues;
		ArrayList<Employee> empArrayList = new ArrayList<Employee>();
		
		// null represents EOF (end of file)
		while( (line = bufferedReader.readLine()) != null ) {
			//System.out.println(line);
			empValues = line.split(" ");
			String empName = empValues[0];
			int empSalary = Integer.parseInt(empValues[1]);
			Department.DeptEnum empDepartment = Department.getEnum(empValues[2]);
			
			//System.out.println(empName);
			
			empArrayList.add(new Employee(empName, empSalary, empDepartment));
			
			
		}
		
		Employee.setEmployees(empArrayList);
	
		
	}
	
	
	/*
	 * toCSV()  
	 *  - if ems.csv does not exist, it will create the file. Formats the data in 
	 *    proper CSV form
	 */
	
	
	public static void toCSV() {
		
		File file = null;
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		
		try {
			
			file = new File("resources/ems.csv");
			if(!file.exists()) {
				file.createNewFile();
			}
			
			fileWriter = new FileWriter(file);
			bufferedWriter = new BufferedWriter(fileWriter);
			
			for(Employee e : Employee.getEmployees()) {
				String csvString = e.getId() + "," + e.getName() + "," + e.getSalary() + "," + e.getDepartment().name() + "\n";
				
				bufferedWriter.write(csvString);
			}
			
			// to actually write to file
			bufferedWriter.flush();
			bufferedWriter.close();
			
		} catch(IOException e) {
			
		}
		
		
	}
	
	/*
	 * storeObjects() stores the Employee static ArrayList into a .data file
	 * 	- utilized as another way of storing data
	 */
	
	
	public static void storeObjects() {
		
		File file = new File("resources/objectFile.data");
		
		try {
			if(!file.exists()) {
				file.createNewFile();
			}
		} catch(Exception e) {
			System.out.println("Exception caught here");
		}
		
		try(ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(file, false))){
			
			writer.writeObject(Employee.getEmployees());
			writer.close();
			
		} catch(IOException e) {
			
		}
		
	}
	
	
	/*
	 * readObjects() returns the Employee ArrayList that was stored in the .data file
	 */
	
	public static ArrayList<Employee> readObjects(){
		
		ArrayList<Employee> empList = new ArrayList<Employee>();
		File file = new File("resources/objectFile.data");
		
		
		try(ObjectInputStream reader = new ObjectInputStream(new FileInputStream(file))){
			
			@SuppressWarnings("unchecked")
			ArrayList<Employee> empArrayList = (ArrayList<Employee>) reader.readObject();
			
			return empArrayList;
			
		} catch(IOException e) {
			e.printStackTrace();
			System.out.println("IOException");
		} catch(ClassNotFoundException e) {
			System.out.println("ClassNotFoundException");
		}
		
		return empList;
		
	}
	
	/*
	 * showEmployees(DeptEnum dept) prints out either:
	 * 	- All employees if dept = Department.DeptEnum.NONE
	 * 	- The employees belonging to a specified Department.DeptEnum
	 */
	
	public static void showEmployees(Department.DeptEnum dept) throws IOException{
		
		File file = null;
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		try {
			
			file = new File("resources/ems.csv");
			fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(fileReader);
			
			String line;
			String[] employeeCSV;
			
			while( (line = bufferedReader.readLine()) != null ) {
				employeeCSV = line.split(",");
				
				if(dept.equals(Department.DeptEnum.NONE)) {
					System.out.println(employeeCSV[1] + " " + employeeCSV[2] + " " + employeeCSV[3]);
				}
				else {
					if(dept.equals(Department.getEnum(employeeCSV[3]))) {
						System.out.println(employeeCSV[1] + " " + employeeCSV[2] + " " + employeeCSV[3]);
					}
				}
				
			}
			
		} catch(FileNotFoundException e) {
			System.out.println("File Not Found");
		}
		
	}

	
	/*
	 * addEmployee(String name, int salary, Department.DeptEnum dept)
	 * 	- adds employee to csv file as well as .data file
	 */
	
	public static void addEmployee(String name, int salary, Department.DeptEnum dept) throws IOException{
		
		ArrayList<Employee> emps = Employee.getEmployees();
		
		// static function for setting counter of employee ids
		Employee.setIdCounter(emps.get(emps.size() - 1).getId() + 1);
		Employee newEmp = new Employee(name, salary, dept);
		emps.add(newEmp);
		
		Employee.setEmployees(emps);
		storeObjects();
		//System.out.println(storedEmps.size());
		
		File file = null;
		
		FileWriter fileWriter = null;
		
		BufferedWriter bufferedWriter = null;
		
		
		try {
			file = new File("resources/ems.csv");
			fileWriter = new FileWriter(file, true);
			bufferedWriter = new BufferedWriter(fileWriter);
			
			
			bufferedWriter.append(newEmp.getId() + "," + newEmp.getName() + "," + newEmp.getSalary() + "," + newEmp.getDepartment().name() + "\n");
			
			
			bufferedWriter.flush();
			//bufferedWriter.close();
			
			
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("Something is wrong");
		}
		
		for(Employee e : Employee.getEmployees()) {
			System.out.println(e.toString());
		}
		
	}
	
	
	/*
	 * updateEmployeeInfo(Scanner input) updates an Employee specified by their name.
	 * 	- allows the option to modify both salary and department
	 *  - rewrites the csv file as well as modifies attributes of the respective Employee object within the static Employee ArrayList
	 */
	
	public static void updateEmployeeInfo(Scanner input) {
		
		String inputName;
		int selection;
		System.out.println("Enter employee's name to make changes: ");
		inputName = input.nextLine();
		ArrayList<Employee> emps = Employee.getEmployees();
		
		
		while(true) {
			try {
				File file = new File("resources/ems.csv");
				File temp = new File("resources/update.csv");
				
				BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
				BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(temp));
				
				System.out.println("Enter your selection: \n1 - Change Salary\n2 - Change Department\n3 - Done");
				selection = input.nextInt();
				if(selection == 1) {
					int newSalary;
					System.out.println("Enter employee's new salary: ");
					newSalary = input.nextInt();
					String line, newCSV;
					String[] employeeCSV;
					while( (line = bufferedReader.readLine()) != null) {
						employeeCSV = line.split(",");
						if(employeeCSV[1].equals(inputName)) {
							newCSV = employeeCSV[0] + "," + employeeCSV[1] + "," + newSalary + "," + employeeCSV[3] + "\n";
							bufferedWriter.write(newCSV);
							continue;
						}
						bufferedWriter.write(line + "\n");
					}
					bufferedReader.close();
					bufferedWriter.close();
					
					file.delete();
					File originalFile = new File("resources/ems.csv");
					temp.renameTo(originalFile);
					
					//ArrayList<Employee> refreshedEmps = new ArrayList<Employee>();
					for(int i = 0; i < emps.size(); i++) {
						if(emps.get(i).getName().equals(inputName)) {
							emps.get(i).setSalary(newSalary);
							break;
						}
						
					}
					Employee.setEmployees(emps);
					storeObjects();
					
					
				}
				else if(selection == 2){
					Department.DeptEnum newDept;
					input.nextLine();
					System.out.println("Enter employee's new department: ");
					newDept = Department.getEnum(input.nextLine());
					String line, newCSV;
					String[] employeeCSV;
					while( (line = bufferedReader.readLine()) != null) {
						employeeCSV = line.split(",");
						if(employeeCSV[1].equals(inputName)) {
							newCSV = employeeCSV[0] + "," + employeeCSV[1] + "," + employeeCSV[2] + "," + newDept + "\n";
							bufferedWriter.write(newCSV);
							continue;
						}
						bufferedWriter.write(line + "\n");
					}
					
					bufferedReader.close();
					bufferedWriter.close();
					
					file.delete();
					File originalFile = new File("resources/ems.csv");
					temp.renameTo(originalFile);
					
					for(int i = 0; i < emps.size(); i++) {
						if(emps.get(i).getName().equals(inputName)) {
							emps.get(i).setDepartment(newDept);
							break;
						}
						
					}
						
					Employee.setEmployees(emps);
					storeObjects();
					
					
				}
				else if(selection == 3) {
					bufferedReader.close();
					bufferedWriter.close();
					break;
					
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	
	
	/*
	 * deleteEmployees(String name) deletes an employee by specifying their name.
	 * 	- reads and rewrites the ems.csv file as well as the .data file of Employee ArrayList
	 */
	
	public static void deleteEmployee(String name) {
		ArrayList<Employee> emps = Employee.getEmployees();
		
		File oldFile = null;
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		File newFile = null;
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		
		try {
			oldFile = new File("resources/ems.csv");
			fileReader = new FileReader(oldFile);
			bufferedReader = new BufferedReader(fileReader);
			
			newFile = new File("resources/temp.csv");
			fileWriter = new FileWriter(newFile);
			bufferedWriter = new BufferedWriter(fileWriter);
			
			String line,newCSV;
			String[] employeeCSV;
			while( (line = bufferedReader.readLine()) != null ) {
				employeeCSV = line.split(",");
				if(employeeCSV[1].equals(name)) {
					continue;
				}
				
				newCSV = line + "\n";
				bufferedWriter.write(newCSV);
			}
			
			bufferedReader.close();
			
			
			bufferedWriter.close();
			
			
			// Renames temp file to ems.csv
			oldFile.delete();
			File originalFile = new File("resources/ems.csv");
			newFile.renameTo(originalFile);
			
			
			
		} catch(IOException e) {
			
		}
		
		// Filtering out deleted employee from list
		ArrayList<Employee> refreshedEmps = new ArrayList<Employee>();
		for(Employee e : emps) {
			if(e.getName().equals(name)) {
				continue;
			}
			refreshedEmps.add(e);
		}
		
		Employee.setEmployees(refreshedEmps);
		storeObjects();

	
	}

}
