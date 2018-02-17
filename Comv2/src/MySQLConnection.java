import java.sql.*;

public class MySQLConnection {

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/myschema";
	static final String USER = "root";
	static final String PASSWORD = "password";

	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;

	MySQLConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			if(connectToDataBase())
			getAccessToDataBaseFields();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private boolean connectToDataBase() {
		System.out.println("Connecting to database...");
		try {
			connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Connection to database failed.");
			return false;
		}
	}

	private void getAccessToDataBaseFields() {
		System.out.println("Creating statement...");
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT username, password FROM users");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void showAll() {
		try {
			while (resultSet.next()) {
				System.out.print("User name: " + resultSet.getString("username"));
				System.out.println(" Password: " + resultSet.getString("password"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void terminateConnection() {
		try {
			resultSet.close();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("MySQL connection terminated.");
	}

	public String getPassword(String username) {
		try {
			while (resultSet.next()) {
				if (resultSet.getString("username").equals(username)) {
					return resultSet.getString("password");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "Username or password not found.";
	}
}
