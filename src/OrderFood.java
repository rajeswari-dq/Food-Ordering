import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class OrderFood {
	int orderid;
	List<ArrayList> order;
	public OrderFood(int orderid) {
		this.orderid=orderid;
		order=new ArrayList<ArrayList>();
	}
	public OrderFood() {
		this.orderid=0;
	}
	public void addFoodItem(Scanner sc) throws SQLException {
		Cuisine.displayCuisine();
		System.out.println("Enter the cuisine : ");
		String cuisine = sc.nextLine().strip();
		int cuisineid=Cuisine.getCuisineId(cuisine);
		Food.displayFoods(cuisine);
		System.out.println("Enter the food id : ");
		int foodid=Integer.parseInt(sc.nextLine());
		System.out.println("Enter the quantity : ");
		int qty=Integer.parseInt(sc.nextLine());
		/*CallableStatement cs=DbConnection.getConnection().prepareCall("{?=call get_food_price(?,?)}");
		cs.registerOutParameter(1,Types.DECIMAL);
		cs.setInt(2,foodid);
		cs.setString(3,cuisine); 
		cs.execute();
		BigDecimal foodprice=cs.getBigDecimal(1);*/
		String query ="insert into orderitem(orderid,foodid,cuisineid,qty) values(?,?,?,?)";
		PreparedStatement st=DbConnection.getConnection().prepareStatement(query);
		st.setInt(1,orderid);
		st.setInt(2,foodid);
		st.setInt(3,cuisineid);
		st.setInt(4, qty);
		st.execute();
		ArrayList orderitem= new ArrayList();
		double foodprice=Food.getFoodPrice(cuisine, foodid);
		orderitem.add(Food.getFoodname(cuisine, foodid));
		orderitem.add(qty);
		orderitem.add(foodprice);
		orderitem.add(foodid);
		orderitem.add(cuisineid);
		orderitem.add(qty*foodprice);
		order.add(orderitem);
		System.out.println("Want to add another food item ?\n1 - Yes\n2 - No\n");
		int addanother =Integer.parseInt(sc.nextLine());
		if (addanother==1)
			this.addFoodItem(sc);
		return;
	}
	public void displayOrder() {
		System.out.println("YOUR ORDER\n********************\nNo  Food  qty");
		for (int i=0;i <order.size();i++)
			System.out.println(i+1+" "+order.get(i).get(0)+" "+order.get(i).get(1));
		System.out.println("********************");
		return;
	}
	public void editQty(Scanner sc) throws SQLException {
		this.displayOrder();
		System.out.println("Enter the number of the food :");
		int n=Integer.parseInt(sc.nextLine());
		while(n>order.size()||n<=0) {
			System.out.println("Enter valid option : ");
			n=Integer.parseInt(sc.nextLine());
		}
		System.out.println("Enter new Quantity :");
		int newqty=Integer.parseInt(sc.nextLine());
		int cuisineid=(int) order.get(n-1).get(4);
		int foodid=(int) order.get(n-1).get(3);
		String query="update orderitem set qty = "+newqty
				+" where cuisineid="+cuisineid
				+" and foodid="+foodid;
		Statement st = DbConnection.getConnection().createStatement();
		st.executeUpdate(query);
		order.get(n-1).set(1, newqty);
		this.displayOrder();
		return;
	}
	public void DeleteFoodItem(Scanner sc)throws SQLException {
		this.displayOrder();
		System.out.println("Enter the number of the food :");
		int n=Integer.parseInt(sc.nextLine());
		while(n>order.size()|| n<=0) {
			System.out.println("Enter valid option : ");
			n=Integer.parseInt(sc.nextLine());
		}
		int cuisineid=(int) order.get(n-1).get(4);
		int foodid=(int) order.get(n-1).get(3);
		String query="delete from orderitem where orderid="+this.orderid
				+ " and cuisineid="+cuisineid
				+" and foodid="+foodid;
		Statement st = DbConnection.getConnection().createStatement();
		st.executeUpdate(query);
		order.remove(n-1);
		this.displayOrder();
		return;
	}
	public void generateBill() throws SQLException {
		double totalcost=0;
		System.out.format("-----------------------------------------------------------------------------------------------------------------------------------");  
        System.out.print("\nProduct ID \tName\t\tQuantity\tRate \t\tTotal Price\n");  
        
		//System.out.print("YOUR ORDER\n********************\nName      Quantity    Price   Total Price\n");
		for(int item=0;item<order.size();item++) {
		System.out.format("%5d		%-16s	%5d		%9.2f	%14.2f	\n",
				item+1,order.get(item).get(0),
				order.get(item).get(1),
				order.get(item).get(2),
				order.get(item).get(5));
			totalcost+=(double)order.get(item).get(5);
		}
		System.out.println("Total amount = "+totalcost);
		System.out.format("-----------------------------------------------------------------------------------------------------------------------------------");  

		String query="update food.order set amount="+totalcost+" where orderid="+this.orderid;
		Statement st=DbConnection.getConnection().createStatement();
		st.executeUpdate(query);
		return ;
	}
}


