package db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Attendee;
import model.Employee;
import model.Group;
import model.Meeting;
import model.MeetingRoom;


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
//        new ModelDbService().getAttendees("test3");
        
//        new ModelDbService().addAttendee(new Attendee(attendee.getEmployee("test@epost.no"), true, 2, "2014-03-11 12:00", true, "2014-03-20 12:00")); 
//        new ModelDbService().getAllMeetings();
//        Meeting meeting = new Meeting(UUID.randomUUID().toString(), new Date(), 30, "Kontorm�te", "Kontoret", , attendees, guestAmount, meetingRoom, meetingRoomBooked)
        
//        new ModelDbService().getUpcomingMeetingsInMeetingRoom("Rom424");
        
//        Meeting meeting = new Meeting("id");
//        meeting.setGuestAmount(7);
//        new ModelDbService().updateExternalAttendee(meeting);
//        
//        System.out.println("test");
        
        new ModelDbService().getUpcomingMeetingsInMeetingRoom("Rom424");
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
    
    // Skal ikkje vere n�dvendig � bruke
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
        } else {
        	System.out.println(employee.getUsername() + ", " + employee.getName() + ", " + employee.getPassword());
        }
        return employee;
    }
    
    public Map<String, Employee> getEmployees() {
        String sql = "select * from ansatt";
        Map<String, Employee> employees = new HashMap();
        try (PreparedStatement ps = DbConnection.getInstance().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Employee employee = new Employee(rs.getString("epost"), rs.getString("navn"), rs.getString("passord"));
                employees.put(rs.getString("epost"), employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
    
    // Hentar alle m�te, men uten attendees og meetingroom
    public Map<String,Meeting> getAllMeetings() {
        Map<String, Meeting> list = new HashMap<>();
        String sql = "select * from avtale";
        Meeting meeting = null;
        try (PreparedStatement ps = DbConnection.getInstance().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                meeting = new Meeting(rs.getString("id"));
                meeting.setMeetingTime(new Date(rs.getTimestamp("dato").getTime()));
                meeting.setDuration(rs.getInt("varighet"));
                meeting.setMeetingLocation(rs.getString("sted"));
                Employee owner = getEmployee(rs.getString("eier_ansatt"));
                meeting.setMeetingOwner(owner);
                meeting.setLastChanged(new Date(rs.getTimestamp("dato").getTime()));
                meeting.setMeetingRoomBooked(false);
                meeting.setMeetingRoom(null);
                
                list.put(meeting.getMeetingID(), meeting);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public Map<String,Meeting> getOldMeetings() {
    	Date nowDate = new Date();
        Map<String, Meeting> oldMeetMap = new HashMap<>();
        String sql = "select * from avtale where dato < ?";
        Meeting meeting = null;
        try (PreparedStatement ps = DbConnection.getInstance().prepareStatement(sql)) {
        	ps.setTimestamp(1, new java.sql.Timestamp(nowDate.getTime()));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                meeting = new Meeting(rs.getString("id"));
                meeting.setMeetingTime(new Date(rs.getTimestamp("dato").getTime()));
                meeting.setDuration(rs.getInt("varighet"));
                meeting.setMeetingLocation(rs.getString("sted"));
                Employee owner = getEmployee(rs.getString("eier_ansatt"));
                meeting.setMeetingOwner(owner);
                meeting.setLastChanged(new Date(rs.getTimestamp("dato").getTime()));
                meeting.setMeetingRoomBooked(false);
                meeting.setMeetingRoom(null);
                
                oldMeetMap.put(meeting.getMeetingID(), meeting);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return oldMeetMap;
    }
    
    public void addAttendee(Attendee attendee, Meeting meeting) {
        String sql = "insert into deltager_ansatt(avtale_id, epost, gruppe_navn, deltagelse_status, sist_varslet, alarm_tid, alarm_satt) values(?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = DbConnection.getInstance().prepareStatement(sql)) {
            ps.setString(1, meeting.getMeetingID());
            ps.setString(2, attendee.getEmployee().getUsername());
            ps.setString(3, null);
            ps.setBoolean(4, attendee.getAttendeeStatus());
            ps.setTimestamp(5, new java.sql.Timestamp(attendee.getLastNotification().getTime())); 
//            new java.sql.Date(1999, 1,1);
            ps.setTimestamp(6, new java.sql.Timestamp(attendee.getAlarmTime().getTime())); 
            ps.setBoolean(7, attendee.getHasAlarm());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void addMeetingRoom(MeetingRoom meetingRoom) {
        String sql = "insert into m�terom(m�terom_navn, maks_antall) values( ?, ?)";
        try (PreparedStatement ps = DbConnection.getInstance().prepareStatement(sql)) {
            ps.setString(1, meetingRoom.getName());
            ps.setInt(2, meetingRoom.getMaxPeople());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * @param meeting
     */
    public void addMeeting(Meeting meeting) {
    	String sql = "insert into avtale(id, dato, varighet, sted, eier_ansatt, sist_endret) values(?, ?, ?, ?, ?, ?)";
    	try (PreparedStatement ps = DbConnection.getInstance().prepareStatement(sql)) {
            ps.setString(1, meeting.getMeetingID());
            ps.setTimestamp(2, new java.sql.Timestamp(meeting.getMeetingTime().getTime()));
            ps.setInt(3, meeting.getDuration());
            ps.setString(4, meeting.getMeetngLocation()); // Skal vere "Kontoret" om det er booka m�terom
            ps.setString(5, meeting.getMeetingOwner().getUsername());
            ps.setTimestamp(6, new java.sql.Timestamp(meeting.getLastChanged().getTime()));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
       @todo FORSLAG: i modellen trenger vi alle ansatte fra alle grupper. Group by gruppe.navn
        metoden skal kunne brukes i konstruktøren til ModelImpl. Map<String, List<String>>
     */
    public List<Employee> getEmployeesInGroup(Group group) {
    	String sql = "select a.epost, a.navn, a.passord from ansatt a join gruppe_person gp on a.epost = gp.epost  where gp.navn = ?";
    	List<Employee> emps = new ArrayList<>();
    	try (PreparedStatement ps = DbConnection.getInstance().prepareStatement(sql)) {
            ps.setString(1, group.getGroupName());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
            	Employee employee = new Employee(rs.getString("epost"), rs.getString("navn"), rs.getString("passord"));
            	emps.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	return emps;
    }

    /*
        @todo FORSLAG: hent fra avtale_møterom. MeetingRoom skal inneholde liste med møter.
        @todo liste med møterom som vi trenger i gui vil man få fra keyset til map.
        @todo slå sammen getMeetingRooms og getUpcomingMeetingsInMeetingRoom
     */
    public List<MeetingRoom> getMeetingRooms2() {
    	String sql = "select * from m�terom";
    	List<MeetingRoom> rooms = new ArrayList<>();
    	try (PreparedStatement ps = DbConnection.getInstance().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
            	MeetingRoom room = new MeetingRoom(rs.getString("m�terom_navn"), rs.getInt("maks_antall"), null);
            	rooms.add(room);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	return rooms;
    }


    public List<Meeting> getUpcomingMeetingsInMeetingRoom(String roomName) {
    	String sql = 	"select a.id, a.dato, a.varighet, a.sted, a.eier_ansatt, a.sist_endret from avtale a " +
    					"natural join avtale_m�terom am " +
    					"where am.m�terom_navn = ?";
    	List<Meeting> meetings = new ArrayList<>();
    	try (PreparedStatement ps = DbConnection.getInstance().prepareStatement(sql)) {
    		ps.setString(1, roomName);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
            	Meeting meeting = new Meeting(rs.getString("id"));
            	meetings.add(meeting);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	for (Meeting meet : meetings) {
    		System.out.println(meet.getMeetingID());
    	}
    	
    	return meetings;
    }
    
    public void addExternalAttendee(Meeting meeting, MeetingRoom meetingRoom) {
    	String sql = "insert into avtale_m�terom(id, m�terom_navn, eksternt_antall)) values(?, ?, ?)";
    	try (PreparedStatement ps = DbConnection.getInstance().prepareStatement(sql)) {
            ps.setString(1, meeting.getMeetingID());
            ps.setString(2, meetingRoom.getName());
            ps.setInt(3, meeting.getGuestAmount());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void updateExternalAttendee(Meeting meeting) {
    	String sql = "update avtale_m�terom set eksternt_antall=? where id=?";
    	try (PreparedStatement ps = DbConnection.getInstance().prepareStatement(sql)) {
            ps.setInt(1, meeting.getGuestAmount());
            ps.setString(2, meeting.getMeetingID());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void updateMeetingRoom(Meeting meeting, MeetingRoom meetingRoom) {
    	String sql = "update avtale_m�terom set eksternt_antall=? where id=?";
    	try (PreparedStatement ps = DbConnection.getInstance().prepareStatement(sql)) {
            ps.setString(1, meetingRoom.getName());
            ps.setString(2, meeting.getMeetingID());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
   
    public void updateMeeting(Meeting meeting, MeetingRoom meetingRoom) {
    	String sql = "update avtale set dato=?, varighet=?, sted=?, eier_ansatt=?, sist_endret=? where id=?";
    	try (PreparedStatement ps = DbConnection.getInstance().prepareStatement(sql)) {
            ps.setTimestamp(1, new java.sql.Timestamp(meeting.getMeetingTime().getTime()));
            ps.setInt(2, meeting.getDuration());
            ps.setString(3, meeting.getMeetngLocation()); // Skal v�re "Kontoret" om det er booka m�terom
            ps.setString(4, meeting.getMeetingOwner().getUsername());
            ps.setTimestamp(5, new java.sql.Timestamp(meeting.getLastChanged().getTime()));
            ps.setString(6, meeting.getMeetingID());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void updateAttendee(Attendee attendee, Meeting meeting) {
    	String sql = "update deltager_ansatt set epost=?, deltagelse_status=?, sist_varslet=?, alarm_tid=?, alarm_satt=? where avtale_id=?";
    	try (PreparedStatement ps = DbConnection.getInstance().prepareStatement(sql)) {
    		ps.setString(1, attendee.getEmployee().getUsername());
            ps.setBoolean(2, attendee.getAttendeeStatus());
            ps.setTimestamp(3, new java.sql.Timestamp(attendee.getLastNotification().getTime())); 
            ps.setTimestamp(4, new java.sql.Timestamp(attendee.getAlarmTime().getTime())); 
            ps.setBoolean(5, attendee.getHasAlarm());
            ps.setString(6, meeting.getMeetingID());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Map<String, MeetingRoom> getMeetingRooms(){
    	String sql = "select * from m�terom";
    	Map<String, MeetingRoom> roomsMap = new HashMap<>();
    	try (PreparedStatement ps = DbConnection.getInstance().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
            	MeetingRoom room = new MeetingRoom(rs.getString("m�terom_navn"), rs.getInt("maks_antall"), null);
            	roomsMap.put(room.getName(), room);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	return roomsMap;
    }

    /*@todo implementer støtte for initiering av modell.  */
    public Map<String, Group> getMapGroups() {
    	String sql = "select * from gruppe";
        Map<String, Group> groupsMap = new HashMap<>();
        try (PreparedStatement ps = DbConnection.getInstance().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Group group = new Group(rs.getString("navn"));
                groupsMap.put(group.getGroupName(), group);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return groupsMap;
    }
}
