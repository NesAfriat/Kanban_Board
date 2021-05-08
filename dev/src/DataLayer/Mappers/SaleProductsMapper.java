package DataLayer.Mappers;

import BuisnnesLayer.OrderBuissness.Order;
import BuisnnesLayer.Sales.SaleByProduct;

import java.sql.*;

public class SaleProductsMapper extends Mapper{
    public SaleProductsMapper() {
        super();
        create_table();
    }

    @Override
    void create_table() {
//        File f = new File(db_name);
        String SaleTable = "CREATE TABLE IF NOT EXISTS SaleProducts(\n" +
                            "\tsaleID INTEGER,\n"+
                            "\tgpID INTEGER,\n"+
                            "\tdiscount_percent REAL,\n"+
                            "\tdescription TEXT,\n"+
                            "\tstart_date TEXT,\n"+
                            "\tend_date TEXT,\n"+
                            "\tPRIMARY KEY (saleID, gpID),\n"+
                            "\tFOREIGN KEY (gpID) REFERENCES GeneralProducts(gpID)\n"+
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
