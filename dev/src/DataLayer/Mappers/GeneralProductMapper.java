package DataLayer.Mappers;

import BuisnnesLayer.GeneralProduct;
import BuisnnesLayer.Item;

import java.io.File;
import java.sql.*;

public class GeneralProductMapper extends Mapper {

    public GeneralProductMapper() {
        super();
        create_table();
    }

    @Override
    void create_table() {
//        File f = new File(db_name);
        String GeneralProductTable = "CREATE TABLE IF NOT EXISTS GeneralProducts(\n" +
                "\tgpID INTEGER PRIMARY KEY,\n" +
                "\tgpName VARCHAR(30),\n" +
                "\tgpManuName VARCHAR(30),\n" +
                "\tamountStore INTEGER,\n" +
                "\tamountStorage INTEGER,\n" +
                "\tminAmount INTEGER,\n" +
                "\tsellingPrice REAL\n" +
                ");";
//        String sql = "BEGIN TRANSACTION;" + GeneralProductTable + "COMMIT;";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            // create a new tables
            stmt.execute(GeneralProductTable);
            //TODO: in DataController - need to activate loadData
//            if (!identityMap.initialized){
//                LoadPreData();
//                identityMap.initialized = true;
//            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public GeneralProduct getGeneralProduct(int product_id) {
        GeneralProduct obj = null;
        try (Connection conn = connect()) {
            String statement = "SELECT * FROM GeneralProducts WHERE gpID=? ";

            try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                pstmt.setInt(1, product_id);

                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    int gpID = rs.getInt(1);
                    String gpName = rs.getString(2);
                    String gpManuName = rs.getString(3);
                    int amountStore = rs.getInt(4);
                    int amountStorage = rs.getInt(5);
                    int minAmount = rs.getInt(6);
                    double sellingPrice = rs.getDouble(7);
                    obj = new GeneralProduct(gpID, gpName, gpManuName, amountStore, amountStorage, minAmount, sellingPrice);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return obj;
    }

    public boolean update(GeneralProduct gp) {
        boolean updated = false;
        try (Connection conn = connect()) {
            String statement = "UPDATE GeneralProducts SET gpID=?, gpName=?, gpManuName=?, amountStore=?, amountStorage=?, minAmount=?, sellingPrice=? WHERE gpID=? ";

            try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                pstmt.setInt(1, gp.getProduct_id());
                pstmt.setString(2, gp.getProduct_name());
                pstmt.setString(3, gp.getManufacturer_name());
                pstmt.setInt(4, gp.getAmount_store());
                pstmt.setInt(5, gp.getAmount_storage());
                pstmt.setInt(6, gp.getMin_amount());
                pstmt.setDouble(7, gp.getSelling_price());
                pstmt.setInt(8, gp.getProduct_id());
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
    public boolean delete(GeneralProduct gp) {
        boolean deleted = false;
        try (Connection conn = connect()) {
            String statement = "DELETE FROM GeneralProducts WHERE gpID=?";

            try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                pstmt.setInt(1, gp.getProduct_id());
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
    public boolean insertProduct(GeneralProduct gp) {
        boolean output = false;
        try (Connection conn = connect()) {
            boolean inserted = false;
            String statement = "INSERT OR IGNORE INTO GeneralProducts(gpID, gpName, gpManuName, amountStore, amountStorage, minAmount, sellingPrice) " +
                    "VALUES (?,?,?,?,?,?,?)";

            try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                pstmt.setInt(1, gp.getProduct_id());
                pstmt.setString(2, gp.getProduct_name());
                pstmt.setString(3, gp.getManufacturer_name());
                pstmt.setInt(4, gp.getAmount_store());
                pstmt.setInt(5, gp.getAmount_storage());
                pstmt.setInt(6, gp.getMin_amount());
                pstmt.setDouble(7, gp.getSelling_price());
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

