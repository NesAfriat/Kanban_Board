package DataLayer.Mappers;

import DataLayer.Mappers.Mapper;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class OrderProductsMapper extends Mapper {
    public OrderProductsMapper() {
        super();
        create_table();
    }


    @Override
    void create_table() {
        String OrderProductsTable = "CREATE TABLE IF NOT EXISTS OrderProducts(\n" +
                                    "\toID INTEGER,\n" +
                                    "\tcatalogID INTEGER,\n" +
                                    "\tquantity INTEGER,\n" +

                                    "\tPRIMARY KEY (oID, catalogID),\n" +
                                    "\tFOREIGN KEY (oID) REFERENCES Orders(oID),\n" +
                                    "\tFOREIGN KEY (catalogID) REFERENCES SuppliersProducts(catalogID)\n" +
                                    "\t\n" +
                                    ");";
        //        String sql = "BEGIN TRANSACTION;" + itemTable + "COMMIT;";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            // create a new tables
            stmt.execute(OrderProductsTable);
            //TODO: in DataController - need to activate loadData
//                      if (!identityMap.initialized){
//                                LoadPreData();
//                                identityMap.initialized = true;
//                            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
