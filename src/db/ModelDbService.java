package db;

import model.Group;
import model.Meeting;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
        new ModelDbService().addGroup(new Group("test5"));

    }

    public ModelDbService() {
    }

    private Group getGroup() {
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

    private void addGroup(Group group) {
        String sql = "insert into gruppe (navn) values(?)";
        try (PreparedStatement ps = DbConnection.getInstance().prepareStatement(sql)) {
            ps.setString(1, group.getGroupName());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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


}
