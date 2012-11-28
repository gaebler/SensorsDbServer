
import java.sql.*;

/**
 * @author Guilherme Gaebler
 * 
 */
public class DBConnect {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {

            String host = "jdbc:derby://localhost:1527/SensorsDB";
            String user = "dbuser";
            String pass = "dbuser";
            Connection con = DriverManager.getConnection(host, user, pass);

            Statement stmt = con.createStatement();
            String SQL = "SELECT * FROM APP.LEITURA";

            ResultSet result = stmt.executeQuery(SQL);
            
            while (result.next()) {
                System.out.println(result.getString(3));
            }
            //close the resultset, statement and connection.
            result.close();
            stmt.close();
            con.close();

        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }
}
