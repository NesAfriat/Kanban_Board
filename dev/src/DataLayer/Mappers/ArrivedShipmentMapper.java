package DataLayer.Mappers;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ArrivedShipmentMapper extends Mapper{
    public ArrivedShipmentMapper(){
        super();
        create_table();
    }
    @Override
    void create_table() {
        String ArrivedShippmentTable = "CREATE TABLE IF NOT EXISTS ArrivedShipment(\n" +
                "\tgpID INTEGER PRIMARY KEY,\n" +
                "\tquantity INTEGER " +
                ");";
//        String sql = "BEGIN TRANSACTION;" + GeneralProductTable + "COMMIT;";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            // create a new tables
            stmt.execute(ArrivedShippmentTable);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
