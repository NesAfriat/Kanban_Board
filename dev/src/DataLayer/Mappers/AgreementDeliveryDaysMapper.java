package DataLayer.Mappers;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class AgreementDeliveryDaysMapper extends Mapper{
    public AgreementDeliveryDaysMapper() {
        super();
        create_table();
    }


    @Override
    void create_table() {
        String Table = "CREATE TABLE IF NOT EXISTS AgreementDeliveryDays(\n" +
                        "\tdeliveryDay TEXT,\n" +
                        "\tsupID INTEGER,\n" +

                        "\tPRIMARY KEY(deliveryDay, supID),\n" +
                        "\tFOREIGN KEY(supID) REFERENCES Agreement(supID)\n" +
                        ");";
        //        String sql = "BEGIN TRANSACTION;" + itemTable + "COMMIT;";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            // create a new tables
            stmt.execute(Table);
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
