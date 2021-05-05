package DataLayer.Mappers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class Mapper {
    protected final String connectionPath = "jdbc:sqlite:database.db";
    protected Connection conn;

    public Mapper(){

    }
    protected  void connect() {
        this.conn = null;
        try {
            conn = DriverManager.getConnection(connectionPath);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
