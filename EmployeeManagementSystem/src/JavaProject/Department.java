package JavaProject;

abstract class Department {
	
	public static enum DeptEnum {
		ACCOUNTING, RESEARCH_DEVELOPMENT, HUMAN_RESOURCES, SALES_MARKETING,
		IT_SERVICES, MANAGEMENT
	}
	
	static void printDepartments() {
		System.out.println(java.util.Arrays.asList(DeptEnum.values()));
	}
	
	public static Employee getEmpsFromDepts(Department department) {
		return new Employee();
	}
	
}
