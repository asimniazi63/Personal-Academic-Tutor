import java.sql.*;

import com.mysql.jdbc.Statement;

public class Database {
	private String connectionString="jdbc:mysql://localhost/pat";
	private Connection conn = null;
	private Statement st = null;
	private PreparedStatement pst = null;
	private ResultSet rs = null;
	//connection object -->statement -->resultset
	
	public Database() {
	}
	
	
	//Starting database connection
	public Statement startDatabaseConn(){
		try {
			conn = DriverManager.getConnection(connectionString,"root","");
			st = (Statement) conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return st;
	}
	// for prepared statement
	public Connection startDatabaseConn2(){
		try {
			conn = DriverManager.getConnection(connectionString,"root","");
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	
	//Closing database connection
	public void closeDatabaseConn(){
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//	//database variables
//	private Connection conn = null;
//	private java.sql.Statement st = null;
//	private ResultSet rs = null;

}
