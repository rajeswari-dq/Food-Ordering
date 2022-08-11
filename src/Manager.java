import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;


public class Manager extends UserAll {
	String username;
	public boolean managerLogin(Scanner sc) throws SQLException {
		if(super.login(sc, "manager"))
			this.username=super.username;
		return true;
		
	}
	public void addCuisine(Scanner sc) throws SQLException {
		System.out.println("Available Cuisines\n");
		Cuisine.displayCuisine();
		System.out.println("Enter the name of the cuisine to be added");
		String cuisine= sc.nextLine().strip();
		String query ="insert into cuisine(name) values('"+cuisine+"');";
		Statement st=DbConnection.getConnection().createStatement();
		st.executeUpdate(query);
	    String query1="CREATE TABLE "+cuisine
	    		+" (foodid int NOT NULL auto_increment,cuisineid int NOT NULL,foodname varchar(45),price decimal(10,2),PRIMARY KEY (foodid),UNIQUE KEY `foodname_UNIQUE` (foodname),FOREIGN KEY (cuisineid) REFERENCES cuisine(cuisineid))auto_increment=101;";
		st.executeUpdate(query1);
		System.out.println("Do you want to add food items?\n1 -Yes\n2 -No");
		if(Integer.parseInt(sc.nextLine())==1)
			this.addFood(sc,cuisine);
		else
			return;
	}
	public void addFood(Scanner sc,String cuisine) throws SQLException {
		System.out.println("Available Foods\n");
		Food.displayFoods(cuisine);
		int cuisineid =Cuisine.getCuisineId(cuisine);
		System.out.println("Enter number of new food items to be added");
		int count=Integer.parseInt(sc.nextLine());
		String query= String.format("INSERT INTO %s (cuisineid,foodname,price)values (?, ?, ?)",  cuisine);
		PreparedStatement stmt=DbConnection.getConnection().prepareStatement(query);
		stmt.setInt(1, cuisineid);
		for(int i=1;i<=count;i++) {
			System.out.println("Enter the name of the food "+i+" :");
			String food = sc.nextLine().strip();
			stmt.setString(2, food);
			System.out.println("Enter the price of the food :");
			float price=(float) Double.parseDouble(sc.nextLine());
			stmt.setFloat(3,price);
			stmt.executeUpdate();
		}
		Food.displayFoods(cuisine);
		return;
		
	}
	public void addFood(Scanner sc) throws SQLException {
		Cuisine.displayCuisine();
		System.out.println("Enter the cuisine for the food :");
		String cuisine=sc.nextLine().strip();
		addFood(sc,cuisine);
		return;
	}
	public void editFood(Scanner sc) throws SQLException {
		Cuisine.displayCuisine();
		System.out.println("Enter the cuisine for the food :");
		String cuisine=sc.nextLine().strip();
		//int cuisineId=Cuisine.getCuisineId(cuisine);
		Food.displayFoods(cuisine);
		System.out.println("Enter the id of the food item to be edited ");
		int foodid=Integer.parseInt(sc.nextLine());
		System.out.println("What do you want to do?\n1 - Edit Price\n2 - Delete Food Item");
		int option=Integer.parseInt(sc.nextLine());
		if (option ==1 ) {
			System.out.println ("Enter new price : ");
			float price=(float) Double.parseDouble(sc.nextLine());
			String query=String.format("update %s set price = ? where foodid= ?",cuisine);
			PreparedStatement st=DbConnection.getConnection().prepareStatement(query);
			st.setDouble(1, price);
			st.setInt(2, foodid);
			st.executeUpdate();
		}
		else {
			String query=String.format("Delete from %s where foodid = ?", cuisine);
			PreparedStatement st=DbConnection.getConnection().prepareStatement(query);
			st.setInt(1, foodid);
			st.executeUpdate();
		}
		System.out.println("Want to edit more ?\n1 - yes\n2 -no ");
		int editmore=Integer.parseInt(sc.nextLine());
		if (editmore==1)
			editFood(sc);
		else
			return;
	}
	public void addManager(Scanner sc) throws SQLException {
		super.addUser(sc,"manager");
		/*System.out.println("Enter your user name (mail ID)");
		String username=sc.nextLine().strip();
		while(! Verification.validateEmail(username)) {
			System.out.println("Enter valid mail id ");
			username=sc.nextLine().strip();
		}
		while ( ! Verification.checkForUsernameAvaliability("manager", username)) {
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
		String query="insert into manager (username,name,password) values (?,?,?);";
		PreparedStatement st=DbConnection.getConnection().prepareStatement(query);
		st.setString(1,username);
		st.setString(2, name);
		st.setString(3, pwd);
		st.execute();
		*/
		return;
	}
	
}
/*List<String> nameList = new ArrayList<String>();
while(resultSet.next()) {
    nameList.add(resultSet.getString("password"));
}
System.out.println(nameList);*/
