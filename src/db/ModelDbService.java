package db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Employee;
import model.Group;
import model.Meeting;

/**
 * Created with IntelliJ IDEA.
 * User: Christoffer Buvik
 * Date: 11.03.14
 * Time: 10:49
 */
public class ModelDbService {

    public static void main(String[] args) {
        System.out.println("main");
//        new ModelDbService().getAllMeeting();

//        new ModelDbService().getGroup();
//        new ModelDbService().addGroup(new Group("test5"));
//        new ModelDbService().getEmployee("test@epost.no");
          new ModelDbService().getEmployees();
        

    }

    public ModelDbService() {
    }

    public Group getGroup() {
        String sql = "select * from gruppe";
        Group group = null;
        try (PreparedStatement ps = DbConnection.getInstance().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                group = new Group(rs.getString("navn"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(group.getGroupName());
        return group;
    }

    public void addGroup(Group group) {
        String sql = "insert into gruppe (navn) values(?)";
        try (PreparedStatement ps = DbConnection.getInstance().prepareStatement(sql)) {
            ps.setString(1, group.getGroupName());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public Employee getEmployee(String username) {
        String sql = "select * from ansatt where epost = ?";
        Employee employee = null;
        try (PreparedStatement ps = DbConnection.getInstance().prepareStatement(sql)) {
        	ps.setString(1, username);
        	ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                employee = new Employee(rs.getString("epost"), rs.getString("navn"), rs.getString("passord"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(employee == null){
        	System.out.println("employee is null");
        	return null;
        }
        System.out.println(employee.getUsername() + ", " + employee.getName() + ", " + employee.getPassword());
        return employee;
    }
    
    public List<Employee> getEmployees() {
        String sql = "select * from ansatt";
        List<Employee> employees = new ArrayList<>(); 
        try (PreparedStatement ps = DbConnection.getInstance().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Employee employee = new Employee(rs.getString("epost"), rs.getString("navn"), rs.getString("passord"));
                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (Employee employee : employees) {
        	System.out.println(employee.getUsername() + ", " + employee.getName() + ", " + employee.getPassword());
		}
        return employees;
    }
    
    public void addEmployee(Employee employee) {
        String sql = "insert into ansatt(epost,navn,passord) values(?, ?, ?)";
        try (PreparedStatement ps = DbConnection.getInstance().prepareStatement(sql)) {
            ps.setString(1, employee.getUsername());
            ps.setString(2, employee.getName());
            ps.setString(3, employee.getPassword());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //hei 

//  
//    private Attendee getAttendee(){
//    	String sql = "select * from deltager_ansatt;";
//    	Attendee attendee = null;
//    	List<Attendee> attendees;
//        try (PreparedStatement ps = DbConnection.getInstance().prepareStatement(sql)) {
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//            	
//            	attendee = new Attendee(rs.getString("navn"));
//                
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        System.out.println(group.getGroupName());
//        return group;
//    }

    private List<Meeting> getAllMeeting() {
        List<Meeting> list = new ArrayList<>();
        String sql = "select * from avtale";
        Meeting meeting = null;
        try (PreparedStatement ps = DbConnection.getInstance().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                meeting = new Meeting(rs.getString("id"));

                list.add(meeting);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(meeting.getMeetingID());
        return list;
    }


}
