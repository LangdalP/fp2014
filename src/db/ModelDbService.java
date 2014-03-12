package db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Attendee;
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

//        new ModelDbService().getGroups();
//        new ModelDbService().addGroup(new Group("test5"));
//        new ModelDbService().getEmployee("test@epost.no");
//        new ModelDbService().getEmployees();
        new ModelDbService().getAttendees("test3");
        
        System.out.println("test");
    }

    public ModelDbService() {
    }

    public List<Group> getGroups() {
        String sql = "select * from gruppe";
        List<Group> groups = new ArrayList<>();
        try (PreparedStatement ps = DbConnection.getInstance().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Group group = new Group(rs.getString("navn"));
                groups.add(group);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (Group group : groups){
        	System.out.println(group.getGroupName());
        }
        return groups;
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

    public List<Attendee> getAttendees(String avtale_id) {
        String sql = "select * from deltager_ansatt where avtale_id = ?";
        List<Attendee> attendees = new ArrayList<>(); 
        try (PreparedStatement ps = DbConnection.getInstance().prepareStatement(sql)) {
        	ps.setString(1, avtale_id);
        	ResultSet rs = ps.executeQuery();
            while (rs.next()) {
            	Employee employee = getEmployee(rs.getString("epost"));
            	int attendeeInt =  rs.getInt("deltagelse_status");
            	boolean attendeeStatus = false;
            	boolean hasResponded = false;
            	System.out.println(attendeeStatus);
            	if(attendeeInt == 0){ //0 betyr ikke svart
            		hasResponded = false;
            	}
            	else if(attendeeInt == 1){ //1 betyr ikke deltar
            		hasResponded = true;
            	}
            	else if(attendeeInt == 2){ //2 betyr deltar
            		hasResponded = true;
            		attendeeStatus = true;
            	}
            	Date lastNotification = rs.getDate("sist_varslet");
            	Date alarmTime = rs.getDate("alarm_tid");
            	boolean hasAlarm = rs.getBoolean("alarm_satt");
            	Attendee attendee = new Attendee(employee, hasResponded, attendeeStatus, lastNotification, hasAlarm, alarmTime);
                attendees.add(attendee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (Attendee attendee : attendees) {
        	System.out.println(attendee.getEmployee().getUsername() + ", " + attendee.getHasResponded() + ", " + attendee.getAttendeeStatus() + ", " 
        + attendee.getLastNotification() + ", " + attendee.getHasAlarm() + ", " + attendee.getAlarmTime());
        }
        return attendees;
    }
    
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
    
    public void addAttendee(Attendee attendee, Meeting meeting, Employee employee, Group group {
        String sql = "insert into deltager_ansatt(avtale_id, epost, gruppe_navn, deltagelse_status, sist_varslet, alarm_tid, alarm_satt) values(?, ?, ?, ?, ?, ?, ?)";
//        		"insert into ansatt(epost,navn,passord) values(?, ?, ?)";
        try (PreparedStatement ps = DbConnection.getInstance().prepareStatement(sql)) {
            ps.setString(1, meeting.getMeetingID());
            ps.setString(2, employee.getUsername());
            ps.setString(3, group.getGroupName());
            ps.setBoolean(4, attendee.getAttendeeStatus());
            ps.setTimestamp(5, new java.sql.Timestamp(attendee.getLastNotification().getTime())); 
//            new java.sql.Date(1999, 1,1);
            
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
