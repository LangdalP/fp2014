package model;

import java.util.ArrayList;
import java.util.List;

public class Group {
	
	private String groupName;
	private List<Employee> employees;
	
	public Group(String groupName){
		this.groupName = groupName;
		employees = new ArrayList<Employee>();
	}
	
	public Group(String groupName, List<Employee> employees) {
		this.groupName = groupName;
		this.employees = employees;
	}

	public String getGroupName() {
		return groupName;
	}

	public List<Employee> getEmployees() {
		return employees;
	}
	
	public void addEmployees(Employee employee){
		employees.add(employee);
	}
	
	
	

}
