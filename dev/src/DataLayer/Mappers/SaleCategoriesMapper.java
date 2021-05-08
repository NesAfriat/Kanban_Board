package DataLayer.Mappers;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SaleCategoriesMapper extends Mapper{
    public SaleCategoriesMapper() {
        super();
        create_table();
    }

    @Override
    void create_table() {
//        File f = new File(db_name);
        String SaleTable = "CREATE TABLE IF NOT EXISTS SaleCategory(\n"+
                            "\tsaleID INTEGER,\n"+
                            "\tcatName TEXT,\n"+
                            "\tdiscount_percent REAL,\n"+
                            "\tdescription TEXT,\n"+
                            "\tstart_date TEXT,\n"+
                            "\tend_date TEXT,\n"+

                            "\tPRIMARY KEY (saleID, catName),\n"+
                            "\tFOREIGN KEY (catName) REFERENCES Categories(catName)\n"+
                            ");";
//        String sql = "BEGIN TRANSACTION;" + GeneralProductTable + "COMMIT;";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            // create a new tables
            stmt.execute(SaleTable);
            //TODO: in DataController - need to activate loadData
//            if (!identityMap.initialized){
//                LoadPreData();
//                identityMap.initialized = true;
//            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
