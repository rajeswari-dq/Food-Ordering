import java.sql.SQLException;
import java.util.Scanner;
public class Main {

	public static void main(String[] args) throws SQLException {
		/*Verification v =new Verification();
		System.out.println(v.checkForUsernameAvaliability("user", "dev1@gmail.com") );
		*/
		// TODO Auto-generated method stub
		Scanner sc=new Scanner(System.in);
		System.out.println("Are you Manager or user\n1 - Manager\n2 - User\nEnter 1 or 2: ");
		int opt =Integer.parseInt(sc.nextLine());
		if (opt==1) {
			Manager manager =new Manager();
			if (manager.managerLogin(sc)) {
				boolean exit=false;
				while (! exit) {
					System.out.println(
							"What do you wanna do?\n1 - Add Cuisine\n2 - add new Food\n3 - Edit food item\n4 - Add another manager ");
					int managerOpt = Integer.parseInt(sc.nextLine());
					switch (managerOpt) {
					case 1:
						manager.addCuisine(sc);
						break;
					case 2:
						manager.addFood(sc);
						break;
					case 3:
						manager.editFood(sc);
						break;
					case 4:
						manager.addManager(sc);
						break;
					default:
						System.out.println("Enter valid option");
	
					}
					System.out.println("Back to main menu?\n1 - yes\n2 - No(exit)");
					if(Integer.parseInt(sc.nextLine())==2)
						exit=true;
				}
			}
		}
		else {
			System.out.println("1 - Login\n2 - New user");
			int userType=Integer.parseInt(sc.nextLine());
			User user=new User();
			//user.addUser(sc);
			if (userType == 1) {
				if(user.userLogin(sc))
					user.createOrder(sc);
			}
			else if(userType==2) {
				//user.addUser(sc);
				if(user.addUser(sc,"user")) {
					System.out.println("Login to continue");
					if(user.userLogin(sc))
						user.createOrder(sc);
				}
			}
			else {
				System.out.println("Enter valid option");
			}
		}
	}

}
