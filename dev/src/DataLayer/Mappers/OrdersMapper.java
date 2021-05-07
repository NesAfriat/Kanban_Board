package DataLayer.Mappers;

import BuisnnesLayer.OrderBuissness.Order;
import BuisnnesLayer.ProductSupplier;

import java.sql.*;

public class OrdersMapper extends Mapper{
    public OrdersMapper() {
        super();
        create_table();
    }

    @Override
    void create_table() {
//        File f = new File(db_name);
        String OrdersTable = "CREATE TABLE IF NOT EXISTS Orders(\n" +
                "\tsupID INTEGER,\n" +
                "\toID INTEGER,\n" +
                "\tdatetime TEXT,\n" +
                "\ttotalPayment REAL,\n" +
                "\tconstant INTEGER,\n" +

                "\tPRIMARY KEY (supID, oID),\n" +
                "\tFOREIGN KEY (supID) REFERENCES Suppliers (supID)\n" +
                 ");";
//        String sql = "BEGIN TRANSACTION;" + GeneralProductTable + "COMMIT;";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            // create a new tables
            stmt.execute(OrdersTable);
            //TODO: in DataController - need to activate loadData
//            if (!identityMap.initialized){
//                LoadPreData();
//                identityMap.initialized = true;
//            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Order getOrder(int sup_id, int oID) {
        Order obj = null;
        try (Connection conn = connect()) {
            String statement = "SELECT * FROM Orders WHERE supID=? AND oID=? ";

            try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                pstmt.setInt(1, sup_id);
                pstmt.setInt(2, oID);

                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    int sup = rs.getInt(1);
                    int orderID = rs.getInt(2);
                    String date = rs.getString(3);
                    double pay = rs.getDouble(4);
                    int con = rs.getInt(5);

                    obj = new Order(orderID,sup,date,pay,con);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return obj;
    }

    public boolean update(Order o) {
        boolean updated = false;
        try (Connection conn = connect()) {
            String statement = "UPDATE Orders SET supID=?, oID=? datetime=?, totalPayment=?, constant=? WHERE supID=? AND oID=? ";

            try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                pstmt.setInt(1, o.getSupplierID());
                pstmt.setInt(2, o.GetId());
                pstmt.setInt(3, o.getDateTime()); //TODO need to add function for this
                pstmt.setInt(4, o.getTotalPayment()); //TODO need to add function for this
                pstmt.setInt(5, o.isConstant()); //TODO need to set true == 0, else false
                pstmt.setInt(6, o.getSupplierID());
                pstmt.setInt(7, o.GetId());
                updated = pstmt.executeUpdate() != 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return updated;
    }

    //TODO: not sure if it will be used
    public boolean delete(Order o) {
        boolean deleted = false;
        try (Connection conn = connect()) {
            String statement = "DELETE FROM Orders WHERE supID=? AND oID=?";

            try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                pstmt.setInt(1, o.getSupplierID());
                pstmt.setInt(2 ,o.GetId());
                deleted = pstmt.executeUpdate() != 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return deleted;
    }

    //TODO: make sure the dates are added properly!
    public boolean insertOrder(Order o) {
        boolean output = false;
        try (Connection conn = connect()) {
            boolean inserted = false;
            String statement = "INSERT OR IGNORE INTO Orders(supID, oID, datetime, totalPayment, constant) " +
                    "VALUES (?,?,?,?,?)";

            try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                pstmt.setInt(1, o.getSupplierID());
                pstmt.setInt(2, o.GetId());
                pstmt.setInt(3, o.getDateTime()); //TODO need to add function for this
                pstmt.setInt(4, o.getTotalPayment()); //TODO need to add function for this
                pstmt.setInt(5, o.isConstant()); //TODO need to set true == 0, else false

                output = pstmt.executeUpdate() != 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return output;
    }
}
