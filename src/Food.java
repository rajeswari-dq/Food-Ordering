import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Food {
	public static void displayFoods(String cuisine ) throws SQLException {
		String query=String.format("Select * from %s ",cuisine);
		Statement st=DbConnection.getConnection().createStatement();
		ResultSet foodset=st.executeQuery(query);
		System.out.println("*** AVAILABLE FOODS ***");
		while(foodset.next()) {
			System.out.println(foodset.getInt("foodid")+" "+foodset.getString("foodname")+" "
					+foodset.getDouble("price"));
		}
		System.out.println("***********************");
		return;
	}
	public static String getFoodname(String cuisine,int foodid) throws SQLException {
		String query="select foodname from "+cuisine+" where foodid = "+foodid;
		Statement st = DbConnection.getConnection().createStatement();
		ResultSet rs =st.executeQuery(query);
		rs.next();
		return rs.getString("foodname");
	}
	public static double getFoodPrice(String cuisine,int foodid) throws SQLException {
		String query="select price from "+cuisine+" where foodid = "+foodid;
		Statement st = DbConnection.getConnection().createStatement();
		ResultSet rs =st.executeQuery(query);
		rs.next();
		return rs.getDouble("price");
	}

}
