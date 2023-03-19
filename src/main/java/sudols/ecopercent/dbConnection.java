package sudols.ecopercent;

import java.sql.Connection;
import java.sql.DriverManager;

public class dbConnection {
    public Connection conn(String dbname, String user, String pass) {

        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", user, pass);
            if (conn != null) {
                System.out.println("Connection");
            } else {
                System.out.println("Connection Failed");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return conn;
    }
}
