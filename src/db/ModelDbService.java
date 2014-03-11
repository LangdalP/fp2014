package db;

import model.Group;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: Christoffer Buvik
 * Date: 11.03.14
 * Time: 10:49
 */
public class ModelDbService {

    public static void main(String[] args) {
        new ModelDbService().getGroup();
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
        return group;
    }

}
