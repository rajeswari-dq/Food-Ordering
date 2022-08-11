import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class User extends UserAll{
	private String username;
	private int orderid,userId;
	
	public boolean userLogin(Scanner sc) throws SQLException{
		/*System.out.println("Enter user name :");
		String username =sc.nextLine().strip();
		System.out.println("Enter password :");
		String pwd=sc.nextLine().strip();
		boolean verify=Verification.verifyLogin(sc,"user",username,pwd);
		while (! verify) {
			System.out.println("Invalid Username or Password");
			System.out.println("Enter user name :");
		    username =sc.nextLine().strip();
			System.out.println("Enter password :");
			pwd=sc.nextLine().strip();
			verify=Verification.verifyLogin(sc,"user",username,pwd);
		}*/
		if(super.login(sc, "user"))
			this.username=super.username;
		return true ;
	}
	/*public boolean addUser(Scanner sc) throws SQLException {
		System.out.println("Enter your user name (mail ID)");
		String username=sc.nextLine().strip();
		while(! Verification.validateEmail(username)) {
			System.out.println("Enter valid mail id ");
			username=sc.nextLine().strip();
		}
		while ( ! Verification.checkForUsernameAvaliability("user", username)) {
			System.out.println("User name already exists\nEnter another mail id ");
			username=sc.nextLine().strip();
			this.addUser(sc);
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
		String query="insert into user (username,name,password) values (?,?,?);";
		PreparedStatement st=DbConnection.getConnection().prepareStatement(query);
		st.setString(1,username);
		st.setString(2, name);
		st.setString(3, pwd);
		st.execute();
		return true;
	}*/
	public void createOrder(Scanner sc) throws SQLException {
		this.generateOrderId();
		OrderFood orderfood=new OrderFood(orderid);
		orderfood.addFoodItem(sc);
		this.orderFood(sc, orderfood);
		return;
	}
	public void orderFood(Scanner sc,OrderFood orderfood) throws SQLException {
			System.out.println("1 - Add another item\n2 - Display order\n3 - edit quantity of a food item\n"
					+ "4 - Delete food item\n5 - Generate bill\n");
			int orderoption=Integer.parseInt(sc.nextLine());
			switch(orderoption) {
			case 1:
				orderfood.addFoodItem(sc);
				this.orderFood(sc, orderfood);
				break;
			case 2:
				orderfood.displayOrder();
				this.orderFood(sc, orderfood);
				break;
			case 3:
				orderfood.editQty(sc);
				this.orderFood(sc, orderfood);
				break;
			case 4:
				orderfood.DeleteFoodItem(sc);
				this.orderFood(sc, orderfood);
				break;
			case 5:
				orderfood.generateBill();
				return;
			default:
				System.out.println("Enter valid option");
			}
	}
	private void generateOrderId() throws SQLException {
		// TODO Auto-generated method stub
		this.userId=this.getUserId(this.username);
		String query="Insert into food.order (userid) values ('"+this.userId+"') ";
		Statement st=DbConnection.getConnection().createStatement();
	    st.execute(query);
	    query="select orderid from food.order where userid="+this.userId+" order by orderid desc;";
		ResultSet rs=st.executeQuery(query);
	    rs.next();
		this.orderid=rs.getInt(1);
		return;
	}
	public int getUserId(String username) throws SQLException {
		String query="select userid from user where username='"+username+"'";
		Statement st=DbConnection.getConnection().createStatement();
		ResultSet rs=st.executeQuery(query);
		rs.next();
		return rs.getInt(1);
	}


}

