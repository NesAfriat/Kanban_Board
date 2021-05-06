package DataLayer.Mappers;

import BuisnnesLayer.Category;
import BuisnnesLayer.Item;

import java.sql.*;

public class CategoriesMapper extends Mapper {

    public CategoriesMapper() {
        super();
        create_table();
    }

    @Override
    void create_table() {
        String CategoriesTable = "CREATE TABLE IF NOT EXISTS Categories(\n" +
                "\tcatName VARCHAR(30) PRIMARY KEY,\n" +
                "\tfather_Category VARCHAR(30),\n" +
                "\tFOREIGN KEY (father_Category) REFERENCES Categories (catName)\n" +
                ");";
        String sql = "BEGIN TRANSACTION;" + CategoriesTable + "COMMIT;";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            // create a new tables
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Category getCategory(String cat_name) {
        Category category = null;
        try (Connection conn = connect()) {
            String statement = "SELECT * FROM Categories WHERE catName=? ";

            try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                pstmt.setString(1, cat_name);

                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    String catName = rs.getString(1);
                    String father_Category= rs.getString(2);
                    category = new Category(cat_name);
                    Category fatherCat=getCategory(father_Category);
                    if(fatherCat!=null)
                    category.setFather_Category(fatherCat);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return category;
    }

    public boolean update(Category obj) {
        boolean updated = false;
        try (Connection conn = connect()) {
            String statement = "UPDATE Categories SET catName=?, father_Category=? WHERE catName=? ";

            try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                pstmt.setString(1, obj.getCategory_name());
                Category father_Cat= obj.getFather_Category();
                if(father_Cat!=null) {
                    pstmt.setString(2, father_Cat.getCategory_name());
                    update(father_Cat);
                }
                else
                    pstmt.setString(2, null);
                updated = pstmt.executeUpdate() != 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return updated;
    }

    public boolean delete(Category obj) {
        boolean deleted = false;
        try (Connection conn = connect()) {
            String statement = "DELETE FROM Categories WHERE catName=? ";

            try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                pstmt.setString(1, obj.getCategory_name());
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
    public boolean insertCategory(Category category) {
        boolean output = false;
        try (Connection conn = connect()) {
            boolean inserted = false;
            String statement = "INSERT OR IGNORE INTO Categories (catName, father_Category) " +
                    "VALUES (?,?)";

            try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                pstmt.setString(1, category.getCategory_name());
                if(category.getFather_Category()!=null)
                pstmt.setString(2, category.getFather_Category().getCategory_name());
                else
                    pstmt.setString(2, null);
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