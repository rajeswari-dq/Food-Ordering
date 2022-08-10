import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
  
public class DbConnection {
  
    private static Connection con = null;
  
    static
    {
        String url = "jdbc:mysql:// localhost:3306/food";
        String user = "root";
        String pass = "qwerty12";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, pass);
            
        }
        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    public static Connection getConnection()
    {
        return con;
    }
}

