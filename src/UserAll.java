import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class UserAll {
	String username;
	String usertype;
	public boolean addUser(Scanner sc,String usertype) throws SQLException {
		this.usertype=usertype;
		System.out.println("Enter your user name (mail ID)");
		String username=sc.nextLine().strip();
		while(! Verification.validateEmail(username)) {
			System.out.println("Enter valid mail id ");
			username=sc.nextLine().strip();
		}
		while ( ! Verification.checkForUsernameAvaliability(usertype, username)) {
			System.out.println("User name already exists\nEnter another mail id ");
			username=sc.nextLine().strip();
		}
		System.out.println("Enter your  Name");
		String name=sc.nextLine().strip();
		System.out.println("Enter your user password");
		String password=sc.nextLine().strip();
		while(!Verification.validatePassword(password)) {
			System.out.println("Passowrd is not strong\nEnter your password : ");
			password=sc.nextLine().strip();
		}
		String pwdhex="select MD5('"+password+"');";
		Statement stmt=DbConnection.getConnection().createStatement();
		ResultSet rs=stmt.executeQuery(pwdhex);
		rs.next();
		String pwd=rs.getString(1);
		String query=String.format("insert into %s (username,name,password) values (?,?,?)",usertype);
		PreparedStatement st=DbConnection.getConnection().prepareStatement(query);
		st.setString(1,username);
		st.setString(2, name);
		st.setString(3, pwd);
		st.execute();
		return true;
	}
	public boolean login(Scanner sc,String usertype) throws SQLException {
		this.usertype=usertype;
		System.out.println("Enter user name :");
		String username =sc.nextLine().strip();
		this.username=username;
		System.out.println("Enter password :");
		String pwd=sc.nextLine().strip();
		boolean verify=Verification.verifyLogin(sc,usertype,username,pwd);
		while (! verify) {
			System.out.println("Invalid Username or Password");
			System.out.println("Enter user name :");
		    username =sc.nextLine().strip();
			System.out.println("Enter password :");
			pwd=sc.nextLine().strip();
			verify=Verification.verifyLogin(sc,usertype,username,pwd);
		}

		return true;
		
	}
}
