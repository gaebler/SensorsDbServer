
import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Guilherme Gaebler
 *
 */
public class ServerConnection extends Thread {

    BufferedReader in;
    DataOutputStream out;
    Socket socket;
    String msg;

    public ServerConnection(Socket c) {
        try {
            this.socket = c;
            in = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
            out = new DataOutputStream(socket.getOutputStream());
            this.start();
        } catch (IOException e) {
            System.out.println("Connection:" + e.getMessage());
        }
    }

    public void run() {

        try {
            msg = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Message: " + msg);
        String[] numbers = msg.split(";");

        int values[] = new int[6];
        for (int k = 0; k < 6; k++) {
            values[k] = Integer.valueOf(numbers[k]);
            System.out.println(values[k]);
        }

        try {

            String host = "jdbc:derby://localhost:1527/SensorsDB";
            String user = "dbuser";
            String pass = "dbuser";
            Connection con = DriverManager.getConnection(host, user, pass);
            Statement stmt = con.createStatement();

            

            for (int k = 2; k < 6; k++) {
                
                java.util.Date today = new java.util.Date();
                java.sql.Timestamp ts = new java.sql.Timestamp(today.getTime());

                String sqlStmt = "INSERT INTO APP.LEITURA VALUES(";
                sqlStmt += "'" + ts + "'";
                sqlStmt += ", " + values[0];
                sqlStmt += ", " + values[1];
                sqlStmt += ", " + values[k];
                if (k == 2) {
                    sqlStmt += ", 'Umidade')";
                } else if (k == 3) {
                    sqlStmt += ", 'Temperatura')";
                } else if (k == 4) {
                    sqlStmt += ", 'Luminosidade')";
                } else if (k == 5) {
                    sqlStmt += ", 'CO')";
                }

                System.out.println(sqlStmt);
                stmt.executeUpdate(sqlStmt);
//                Thread.sleep(1);
            }
            stmt.close();
            con.close();
            
//        } catch (InterruptedException ex) {
//            Logger.getLogger(ServerConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }

        this.endConection();
    }

    @SuppressWarnings("deprecation")
    private void endConection() {
        try {
            this.socket.close();
            this.stop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}