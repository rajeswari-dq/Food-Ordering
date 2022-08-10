import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Cuisine {
	int cuisineId;
	String name;
	//getting cuisine id by using Cuisine name
	public static int getCuisineId(String cuisine) throws SQLException {
		String query="select cuisineid from cuisine where name='"+cuisine+"'";
		Statement st=DbConnection.getConnection().createStatement();
		ResultSet rs=st.executeQuery(query);
		rs.next();
		return rs.getInt(1);
	}
	
	//displaying cuisines available 
	public static void displayCuisine () throws SQLException {
		System.out.println("*** CUISINES ***");
		String query ="select name from cuisine";
		Statement st=DbConnection.getConnection().createStatement();
		ResultSet rs=st.executeQuery(query);
		while(rs.next()) {
			System.out.println(rs.getString(1));
		}
		System.out.println();
		return;
		
	}
	//Displaying foods based on cuisine Id
	public static void displayFoods(String cuisine ) throws SQLException {
		String query=String.format("Select * from %s ",cuisine);
		Statement st=DbConnection.getConnection().createStatement();
		ResultSet foodset=st.executeQuery(query);
		System.out.println("*** AVAILABLE FOODS ***");
		while(foodset.next()) {
			System.out.println(foodset.getInt("foodid")+" "+foodset.getString("foodname")+" "
					+foodset.getDouble("price"));
		}
		return;
	}
}

