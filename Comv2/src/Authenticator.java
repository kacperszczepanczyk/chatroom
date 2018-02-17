
public class Authenticator {

	private MySQLConnection mySQL;
	static String separator = ";";
	private String userNameAndPassword;

	Authenticator(String userNameAndPassword) {
		mySQL = new MySQLConnection();
		this.userNameAndPassword = userNameAndPassword;
	}

	public boolean authoriseUser() {
		System.out.println("username: " + extractUserName() + " password: " + extractPassword());
	/*	if (mySQL.getPassword(extractUserName()).equals(extractPassword())) {
			return true;
		} else {
			return false;
		}*/
		return true;
	}

	public String extractUserName() {
		return userNameAndPassword.split(separator)[0];
	}

	private String extractPassword() {
		String [] s;
		s = userNameAndPassword.split(separator);
		return s[1];
		//return userNameAndPassword.split(separator)[1];
	}

}
