package DataLayer.Mappers;

import DataLayer.PersistanceObjects.PersistanceObj;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class Mapper {
    protected final String connectionPath = "jdbc:sqlite:database.db";

    public Mapper(){

    }

    protected Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(connectionPath);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }



}
