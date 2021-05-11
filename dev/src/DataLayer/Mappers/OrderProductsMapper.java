package DataLayer.Mappers;


import BuisnnesLayer.OrderBuissness.Order;

import java.sql.*;

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
    public boolean update(int orderId,int catalogID,int quantity) {
        boolean updated = false;
        try (Connection conn = connect()) {
            String statement = "UPDATE OrderProducts SET oID=?, catalogID=?, quantity=?";

            try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                pstmt.setInt(1, orderId);
                pstmt.setInt(2, catalogID);
                pstmt.setInt(3, quantity);

                updated = pstmt.executeUpdate() != 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return updated;
    }
    //removeProduct
    public boolean delete(int orderId,int catalogID) {
        boolean deleted = false;
        try (Connection conn = connect()) {
            String statement = "DELETE FROM OrderProducts WHERE oID=  AND catalogID=?";

            try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                pstmt.setInt(1, orderId);
                pstmt.setInt(2, catalogID);
                deleted = pstmt.executeUpdate() != 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return deleted;
    }

    public boolean insetProduct(int orderId,int catalogID,int quantity) {
        boolean output = false;
        try (Connection conn = connect()) {//String statement = "UPDATE OrderProducts SET oID=?, catalogID=?, quantity=?";
            boolean inserted = false;
            String statement = "INSERT OR IGNORE INTO OrderProducts(oID, catalogID, quantity) " +
                    "VALUES (?,?,?)";

            try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                pstmt.setInt(1, orderId);
                pstmt.setInt(2, catalogID);
                pstmt.setInt(3, quantity);

                output = pstmt.executeUpdate() != 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return output;
    }


    public void addProductsToOrder(Order order){
        try (Connection conn = connect()) {
            String statement = "SELECT * FROM OrderProducts WHERE oID=? ";

            try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                pstmt.setInt(1, order.GetId());

                ResultSet rs = pstmt.executeQuery();
                while(rs.next()) {
                    int catalogID = rs.getInt(2);
                    int quantity=rs.getInt(3);
                    order.insertProductToOrderForDal(catalogID,quantity);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }






}
