package DataLayer.Mappers;

import BuisnnesLayer.Agreement;
import BuisnnesLayer.ProductSupplier;

import java.sql.*;
import java.util.HashMap;

public class AgreementDeliveryDaysMapper extends Mapper{
    public AgreementDeliveryDaysMapper() {
        super();
        create_table();
    }


    @Override
    void create_table() {
        String Table = "CREATE TABLE IF NOT EXISTS AgreementDeliveryDays(\n" +
                        "\tdeliveryDay INTEGER,\n" +
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

    public void addDaysDelivery(Agreement agr){
        try (Connection conn = connect()) {
            String statement = "SELECT * FROM AgreementDeliveryDays WHERE supID=? ";

            try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                pstmt.setInt(1, agr.getSupplierID());

                ResultSet rs = pstmt.executeQuery();
                while(rs.next()) {
                    String day = rs.getString(1);

                    agr.addDeliveryDay(Integer.parseInt(day));

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
