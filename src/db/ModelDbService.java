package db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.xml.internal.bind.v2.TODO;

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
       
        
        new ModelDbService().addEmployee(new Employee("even.hansen.kalender.no", "Even Hansen"));
      
        
//        new ModelDbService().getUpcomingMeetingsInMeetingRoom("Rom424");
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
        return groups;
    }
    
    // Skal ikkje vere nødvendig å bruke
    public void addGroup(Group group) {
        String sql = "insert into gruppe (navn) values(?)";
        try (PreparedStatement ps = DbConnection.getInstance().prepareStatement(sql)) {
            ps.setString(1, group.getGroupName());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Employee getEmployeeWithPassword(String username) {return getEmployee(username, true);}
    public Employee getEmployee(String username) { return getEmployee(username, false);}
    private Employee getEmployee(String username, boolean withPassword) {
        String sql = "select * from ansatt where epost = ?";
        Employee employee = null;
        try (PreparedStatement ps = DbConnection.getInstance().prepareStatement(sql)) {
        	ps.setString(1, username);
        	ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                employee = new Employee(rs.getString("epost"), rs.getString("navn"));
                if (withPassword) employee.setPassword(rs.getString("passord"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employee;
    }

    public Map<String, Employee> getEmployees() {
        String sql = "select * from ansatt";
        Map<String, Employee> employees = new HashMap();
        try (PreparedStatement ps = DbConnection.getInstance().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Employee employee = new Employee(rs.getString("epost"), rs.getString("navn"));
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
 

    // Hentar alle møter om before = null, hentar alle gamle møte om before == true, og hentar alle nye møter om before == false
    public Map<String,Meeting> getMapMeetings(Boolean before) {
        //mer effektivt å hente ut alle employees èn gang.
        Map<String, Employee> employeeMap = getEmployees();

        Map<String, Meeting> mapMeetings = new HashMap<>();
        String sql = "select * from deltager_ansatt da\n" +
                "join avtale av on av.id = da.avtale_id\n" +
                "join ansatt an on an.epost = da.epost\n" +
                "left join avtale_moterom am on am.id = av.id";   //left join tillater at avtale_moterom ikke finnes.

        if (before != null && before == true)  sql += " where dato < ?";
        else if (before != null && before == false) sql += " where dato > ?";

        try (PreparedStatement ps = DbConnection.getInstance().prepareStatement(sql)) {
            if (before != null) {
                Date nowDate = new Date();
                ps.setTimestamp(1, new java.sql.Timestamp(nowDate.getTime()));
            }
            Meeting meeting = null;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id");
                if (mapMeetings.get(id) == null){
                    meeting = new Meeting(rs.getString("id"));
                    meeting.setMeetingTime(new Date(rs.getTimestamp("dato").getTime()));
                    meeting.setDuration(rs.getInt("varighet"));
                    meeting.setMeetingLocation(rs.getString("sted"));

                    Employee owner = employeeMap.get(rs.getString("eier_ansatt"));
                    meeting.setMeetingOwner(owner);
                    meeting.setLastChanged(new Date(rs.getTimestamp("dato").getTime()));
                    String moterom = rs.getString("moterom_navn");
                    if (moterom != null) {
                        meeting.setMeetingRoom(new MeetingRoom(moterom, rs.getInt("eksternt_antall")));
                    }


                    mapMeetings.put(meeting.getMeetingID(), meeting);
                }

                int attendeeInt =  rs.getInt("deltagelse_status");
                Employee employee = employeeMap.get(rs.getString("epost"));
                boolean attendeeStatus = false;
                boolean hasResponded = false;
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
                mapMeetings.get(id).addAttendee(attendee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mapMeetings;
    }
    
    public void addAttendee(Meeting meeting, Attendee attendee) {
        String sql = "insert into deltager_ansatt(avtale_id, epost, deltagelse_status, sist_varslet, alarm_tid, alarm_satt) values(?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = DbConnection.getInstance().prepareStatement(sql)) {
            ps.setString(1, meeting.getMeetingID());
            ps.setString(2, attendee.getEmployee().getUsername());
            ps.setBoolean(3, attendee.getAttendeeStatus());
            ps.setTimestamp(4, new java.sql.Timestamp(attendee.getLastNotification().getTime()));
            if (attendee.getHasAlarm()) ps.setTimestamp(5, new java.sql.Timestamp(attendee.getAlarmTime().getTime()));
            else ps.setNull(5, Types.TIMESTAMP);
            ps.setBoolean(6, attendee.getHasAlarm());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void removeAttendee(Meeting meet, Attendee att) {
        String sql = "DELETE FROM deltager_ansatt where epost = ?";
        try (PreparedStatement ps = DbConnection.getInstance().prepareStatement(sql)) {
            ps.setString(1, att.getEmployee().getUsername());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void addMeetingRoom(MeetingRoom meetingRoom) {
        String sql = "insert into moterom(moterom_navn, maks_antall) values( ?, ?)";
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
        for (Attendee att : meeting.getAttendees())  addAttendee(meeting, att);
        if (meeting.getMeetingRoomBooked()) addMeetingRoomBooking(meeting, meeting.getMeetingRoom());
    }
    
    public void removeMeetingById(String meetingID) {

    }

    public List<Employee> getEmployeesInGroup(Group group) {
        String sql = "select a.epost, a.navn, a.passord from ansatt a join gruppe_person gp on a.epost = gp.epost  where gp.navn = ?";
        List<Employee> emps = new ArrayList<>();
        try (PreparedStatement ps = DbConnection.getInstance().prepareStatement(sql)) {
            ps.setString(1, group.getGroupName());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Employee employee = new Employee(rs.getString("epost"), rs.getString("navn"));
                emps.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emps;
    }

    public List<Meeting> getUpcomingMeetingsInMeetingRoom(String roomName) {
        String sql = "select a.id, a.dato, a.varighet, a.sted, a.eier_ansatt, a.sist_endret from avtale a " +
                "natural join avtale_moterom am " +
                "where am.moterom_navn = ?";
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
        return meetings;
    }
    
   /* private void addMeetingsToMeetingRoom(MeetingRoom room) {
        String sql = 	"select a.id, a.dato, a.varighet, a.sted, a.eier_ansatt, a.sist_endret from avtale a " +
                "natural join avtale_m�terom am " +
                "where am.m�terom_navn = ?";
        try (PreparedStatement ps = DbConnection.getInstance().prepareStatement(sql)) {
            ps.setString(1, room.getName());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Meeting meeting = new Meeting(rs.getString("id"));
                room.addUpcomingMeeting(meeting);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/
    
    /* Gjorde endring: Metoda var addExternalAttendee, men i praksis vil
     * vi som regel berre sette eksternt_antall. Søg at vi hadde eigen metode for
     * dette (dvs. updateExternalAttendee), så gav nytt navn til metoden slik at
     * den kan virke som generell booking-metode for meetingroom
     */
    public void addMeetingRoomBooking(Meeting meeting, MeetingRoom meetingRoom) {
        String sql = "insert into avtale_moterom values(?, ?, ?)";
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
        String sql = "update avtale_moterom set eksternt_antall=? where id=?";
        try (PreparedStatement ps = DbConnection.getInstance().prepareStatement(sql)) {
            ps.setInt(1, meeting.getGuestAmount());
            ps.setString(2, meeting.getMeetingID());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void updateMeetingRoom(Meeting meeting, MeetingRoom meetingRoom) {
        String sql = "update avtale_moterom set eksternt_antall=? where id=?";
        try (PreparedStatement ps = DbConnection.getInstance().prepareStatement(sql)) {
            ps.setString(1, meetingRoom.getName());
            ps.setString(2, meeting.getMeetingID());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
   
    /*
     * Fjerna MeetingRoom fra parameter (input), sidan det ikkje bli brukt til noko
     */
    public void updateMeeting(Meeting meeting) {
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
    	String sql = "select * from moterom";
    	Map<String, MeetingRoom> roomsMap = new HashMap<>();
    	try (PreparedStatement ps = DbConnection.getInstance().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
            	MeetingRoom room = new MeetingRoom(rs.getString("moterom_navn"), rs.getInt("maks_antall"));
            	roomsMap.put(room.getName(), room);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	return roomsMap;
    }

    public Map<String, Group> getMapGroups() {
    	String sql = "select *, gp.navn as gruppenavn, a.navn as ansattnavn from gruppe_person gp\n" +
                "join ansatt a on a.epost = gp.epost ";
        Map<String, Group> groupsMap = new HashMap<>();
        try (PreparedStatement ps = DbConnection.getInstance().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String gruppenavn = rs.getString("gruppenavn");
                if (groupsMap.get(gruppenavn) == null){
                    Group group = new Group(gruppenavn);
                    groupsMap.put(gruppenavn, group);
                }

                Employee emp = new Employee(rs.getString("epost"), rs.getString("ansattnavn"));
                groupsMap.get(gruppenavn).addEmployees(emp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return groupsMap;
    }
}
