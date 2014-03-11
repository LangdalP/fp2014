package model;

public class Employee {
	
	private String username;
	private String name;
	private String password;
	
	public Employee(String username, String name, String password){
		this.username = username;
		this.name = name;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}
	
	

}
