package DataLayer.Mappers;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class AgreementProductsMapper extends Mapper{
    public AgreementProductsMapper() {
        super();
        create_table();
    }


    @Override
    void create_table() {
        String AgreementProductsTable = "CREATE TABLE IF NOT EXISTS AgreementProducts(\n" +
                                        "\tsupID INTEGER,\n" +
                                        "\tcatalogID INTEGER,\n" +
                                        "\t\n" +
                                        "\tPRIMARY KEY(supID, catalogID),\n" +
                                        "\tFOREIGN KEY(supID) REFERENCES Agreement(supID),\n" +
                                        "\tFOREIGN KEY(catalogID) REFERENCES SuppliersProducts(catalogID)\n" +
                                        "\t\n" +
                                        ");\n";
        //        String sql = "BEGIN TRANSACTION;" + itemTable + "COMMIT;";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            // create a new tables
            stmt.execute(AgreementProductsTable);
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
