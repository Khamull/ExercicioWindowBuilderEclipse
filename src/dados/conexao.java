package dados;
import java.sql.*;
import javax.swing.*;

public class conexao {
	
	  
	public static Connection connect = null;
	public static Connection conecta(){
	try{
		  // This will load the MySQL driver, each DB has its own driver
	      Class.forName("com.mysql.jdbc.Driver");
	      // Setup the connection with the DB
	      connect = DriverManager.getConnection("jdbc:mysql://localhost/noeltintas","root","");
	      //JOptionPane.showMessageDialog(null, "Conexão bem sucedida!");
	      return connect;
	  }catch(Exception e)
	  {
		  JOptionPane.showMessageDialog(null, e);
		  return null;
	  }	/*finally
	  {
		close();
	  }*/
	}
	// You need to close the resultSet
	  public static void close() {
	    try {
	      if (connect != null) {
	        connect.close();
	      }
	    } catch (Exception e) {

	    }
	  }
}
