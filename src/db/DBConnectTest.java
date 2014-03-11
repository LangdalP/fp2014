package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnectTest {
	
	static final String SQL_DRIVER = "com.mysql.jdbc.Driver";
	static final String SQL_URL = "jdbc:mysql://mysql.stud.ntnu.no/";
	static final String DB_NAME = "pedervl_fpdb";
	static final String USER_NAME = "pedervl_fp";
	static final String USER_PW = "fp14";
	
	public static void main(String[] args) throws SQLException {
		
		Connection sqlConn = null;
		Statement sqlStatement = null;
		
		ResultSet rs = null;
		int updateQuery = 0;
		
		try {
			// Sikrar at vi har ein referanse til SQL-drivaren
			Class.forName(SQL_DRIVER).newInstance();
			
			System.out.println("Starting connection...");
			
			// Opprettar sambinding
			sqlConn = DriverManager.getConnection(SQL_URL+DB_NAME, USER_NAME, USER_PW);
			// Opprettar Statement-objekt som har ansvar for å sende spørjingar og motta resultat
			sqlStatement = sqlConn.createStatement();
			
			String queryString = 
					"SELECT * " +
					"FROM gruppe";
			
			rs = sqlStatement.executeQuery(queryString);
			while (rs.next()) {
				System.out.println(rs.getString(1));
			}
			
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			if (sqlConn != null) {
				System.out.println("Closing SQL connection...");
				sqlStatement.close();
				sqlConn.close();
			}
		}
		
	}

}
