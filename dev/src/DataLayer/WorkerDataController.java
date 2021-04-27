package DataLayer;
import java.sql.*;

public class WorkerDataController {
    private IdentityMap identityMap;
    private Connection conn;

    public WorkerDataController(){
        this.identityMap = new IdentityMap();
        conn = null;
        try {
            // db parameters
            String url = "jdbc:sqlite:WorkersDB.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
