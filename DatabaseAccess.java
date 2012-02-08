import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;


public class DatabaseAccess {
	public Connection getConnection() throws Exception {
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:mydb.db");
		return conn;
	}
	
	public void createTable() throws Exception {
		Connection conn = getConnection();
		Statement st = conn.createStatement();
		st.executeUpdate("drop table if exists test;");
		st.executeUpdate("create table test (id INTEGER PRIMARY KEY, text TEXT, datetime TEXT);");
		conn.close();
	}
	
	public boolean addRecord(String text) throws Exception {
		Connection conn = getConnection();
		Statement st = conn.createStatement();
		text = text.replace("'", "");
		st.execute("insert into test (text, datetime) values ('"+text+"', '"+new Date()+"')");
		conn.close();
		return true;
	}
	
	public String getLatestRecord() throws Exception {
		Connection conn = getConnection();
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("select id, text from test order by id asc;");
		
		int id = 0;
		String text = "";
		while(rs.next()) {
			id = rs.getInt("id");
			text = rs.getString("text");
		}
		conn.close();
		return "{ \"id\":\""+id+"\", \"text\":\""+text+"\" }";
	}
}
