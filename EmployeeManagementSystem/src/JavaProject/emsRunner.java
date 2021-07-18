package JavaProject;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;


public class emsRunner {

	public static void main(String[] args) throws IOException{
		
		ArrayList<Employee> employees = fileToEmpAL(); 
		toCSV(employees);
		
		
		
	}
	
	public static ArrayList<Employee> fileToEmpAL() throws IOException{
		
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
		
		return empArrayList;
		
	}
	
	public static void toCSV(ArrayList<Employee> employees) {
		
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
			
			for(Employee e : employees) {
				String csvString = e.getId() + "," + e.getName() + "," + e.getSalary() + "," + e.getDepartment().name() + "\n";
				System.out.print(csvString);
				bufferedWriter.write(csvString);
			}
			
			// to actually write to file
			bufferedWriter.flush();
			
		} catch(IOException e) {
			
		}
		
		
	}
	
	// Create and add new employees
	
	
	// Read employees
	
	// Update employee info
	
	// Delete employees

}
