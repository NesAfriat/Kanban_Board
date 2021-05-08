package DataLayer.Mappers;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class AgreementProductDiscMapper extends Mapper{
    public AgreementProductDiscMapper() {
        super();
        create_table();
    }


    @Override
    void create_table() {
        String AgreementProductsDiscTable = "CREATE TABLE IF NOT EXISTS AgreementProductDisc(\n" +
                                            "\tsupID INTEGER,\n" +
                                            "\tcatalogID INTEGER,\n" +
                                            "\tquantity INTEGER,\n" +
                                            "\tdiscount REAL,\n" +
                                            "\tPRIMARY KEY (supID, catalogID, quantity),\n" +
                                            "\tFOREIGN KEY (supID) REFERENCES Agreement(supID),\n" +
                                            "\tFOREIGN KEY (catalogID) REFERENCES SuppliersProducts(catalogID)\n" +
                                            ");";
        //        String sql = "BEGIN TRANSACTION;" + itemTable + "COMMIT;";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            // create a new tables
            stmt.execute(AgreementProductsDiscTable);
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
