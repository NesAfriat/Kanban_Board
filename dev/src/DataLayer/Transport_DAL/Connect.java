package DataLayer.Transport_DAL;

import java.sql.*;


public class Connect {
    public static Connection getConnection() {
        Connection conn = null;
        try {
            // db parameters
            String url = "jdbc:sqlite:Nituz.db";;
            // create a connection to the database
            conn = DriverManager.getConnection(url);

            return conn;




        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    public static void connect() {
        Connection conn = null;
        try {
            // db parameters
            String url = "jdbc:sqlite:C:\\Users\\guyne\\Documents\\BGU\\semester C\\HomeAssinments\\system architecture\\gitRipo\\ADSS_Group_K\\dev";
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");
            Statement st = conn.createStatement();
            ResultSet results = st.executeQuery("SELECT * FROM area");
            while(results.next()){

                System.out.println("entered");
                String m = results.getString(1);
                System.out.println("the area is: "+ m);
            }




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
