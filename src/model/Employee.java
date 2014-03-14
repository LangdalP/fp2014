package model;

import java.io.Serializable;

public class Employee implements Serializable{
	
	private static final long serialVersionUID = -3677933420657644865L;
	
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


    @Override
    public String toString() {
        return "Employee{" +
                "username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
