package JavaProject;

abstract class Department {
	
	public static enum DeptEnum {
		ACCOUNTING, RESEARCH_DEVELOPMENT, HUMAN_RESOURCES, SALES_MARKETING,
		IT_SERVICES, MANAGEMENT, NONE
		
	}
	
	static void printDepartments() {
		System.out.println(java.util.Arrays.asList(DeptEnum.values()));
	}
	
	public static Employee getEmpsFromDepts(Department department) {
		return new Employee();
	}
	

	public static DeptEnum getEnum(String value) {
		DeptEnum defaultEnum = DeptEnum.NONE;
		for(DeptEnum v : DeptEnum.values()) {
			if(v.name().equals(value)) {
				return v;
			}
		}
		return defaultEnum;
	}
	
}
