package DataLayer.Mappers;

import java.sql.Connection;

public abstract class Mapper {
    private final String connectionPath = "jdbc:sqlite:database.db";
    private Connection conn;
}
