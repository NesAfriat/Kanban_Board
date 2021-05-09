package DataLayer.Mappers;

import BuisnnesLayer.Agreement;
import BuisnnesLayer.ProductSupplier;
import BuisnnesLayer.Sales.Sale;

import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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
                                            "\tprice REAL,\n" +
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




<<<<<<< HEAD

       }

       public boolean RemoveQuantityDiscAgreement(int SupId,int catalogId,int quantity) {

       }


      public boolean UpdateQuantityDiscAgreement(int SupId,int catalogId,int quantity,int price) {
       }


      public HashMap<Integer, HashMap<Integer, Double>> GetAllQuantityDiscAgreementOfSupplier(int SupId, Set<Integer> catalogIdOfAllProduct) {

      }



=======
>>>>>>> sup_stock_dal
    public void addQuantityDiscAgreement(Agreement agr){
        try (Connection conn = connect()) {
            String statement = "SELECT * FROM AgreementProductDisc WHERE supID=? ";

            try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                pstmt.setInt(1, agr.getSupplierID());

                ResultSet rs = pstmt.executeQuery();
                while(rs.next()) {
                    int catalogID = rs.getInt(2);
                    int amount = rs.getInt(3);
                    double price = rs.getDouble(4);
                    agr.addDiscountByProductQuantity(catalogID,amount,price);



                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
