import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Verification {
	public static boolean checkForUsernameAvaliability(String table,String username) throws SQLException{
		String query= String.format("select username from %s where username= '%s';",table,username);
		Statement st = DbConnection.getConnection().createStatement();
		ResultSet rs=st.executeQuery(query);
		if(!rs.next())
			return true;
		return false;
	
	}
	public static boolean validateEmail(String email) {
		Pattern emailPattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
	    Matcher matcher = emailPattern.matcher(email);
	    return matcher.find();
	}
	public static boolean validatePassword(String password) {
		Pattern emailPattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\S+$).{8,20}$");
	    Matcher matcher = emailPattern.matcher(password);
	    return matcher.find();
	}
	public static boolean verifyLogin(Scanner sc ,String table,String username,String password) throws SQLException {
		String dbpwd = null;
		String query =String.format("select password from %s where username='%s'",table,username);
		//System.out.println(query);
		Statement st = DbConnection.getConnection().createStatement();
		ResultSet resultSet=st.executeQuery(query);
		if(! resultSet.next()) 
			return false;
		dbpwd=resultSet.getString("password");
		//System.out.println(dbpwd);
		String pwdhex="select MD5('"+password+"');";
		Statement stmt=DbConnection.getConnection().createStatement();
		ResultSet rs=stmt.executeQuery(pwdhex);
		rs.next();
		String pwd=rs.getString(1);
		if (pwd.equals(dbpwd))
			return true;
		return false;
	}

}
