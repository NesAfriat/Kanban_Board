package DataLayer.Mappers;

import BuisnnesLayer.Category;
import BuisnnesLayer.Reports.Report;
import BuisnnesLayer.Reports.ReportDefects;
import BuisnnesLayer.Reports.ReportMissing;
import BuisnnesLayer.Reports.ReportStock;
import BuisnnesLayer.Sales.Sale;
import BuisnnesLayer.Sales.SaleByProduct;
import DataLayer.DataController;

import java.sql.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.function.DoubleUnaryOperator;

public class SalesMapper extends Mapper{
    private AffectedCategoriesMapper acm;
    private AffectedProductsMapper apm;
    public SalesMapper() {
        super();
        create_table();
        acm=new AffectedCategoriesMapper();
        apm= new AffectedProductsMapper();
    }

    @Override
    void create_table() {
//        File f = new File(db_name);
        String SaleTable = "CREATE TABLE IF NOT EXISTS Sales(\n" +
                            "\tsaleID INTEGER,\n"+
                            "\tdiscount_percent DOUBLE ,\n"+
                            "\tdescription TEXT,\n"+
                            "\tstart_date TEXT,\n"+
                            "\tend_date TEXT,\n"+
                            "\tPRIMARY KEY (saleID),\n"+
                            ");";
//        String sql = "BEGIN TRANSACTION;" + GeneralProductTable + "COMMIT;";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            // create a new tables
            stmt.execute(SaleTable);
            //TODO: in DataController - need to activate loadData
//            if (!identityMap.initialized){
//                LoadPreData();
//                identityMap.initialized = true;
//            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public LinkedList<Sale> getSaleByProduct(String product) {
        LinkedList<Sale> sales= new LinkedList<>();
        LinkedList<Integer> salesID= apm.getSales(product);
        for(Integer sID: salesID)
        try (Connection conn = connect()) {
            String statement = "SELECT * FROM Sales WHERE saleID=? ";

            try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                pstmt.setInt(1, sID);

                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    int saleID = rs.getInt(1);
                    Double discount_percent = rs.getDouble(2);
                    String description = rs.getString(3);
                    String start_date = rs.getString(4);
                    String end_date = rs.getString(5);
                    LinkedList<String> affected= apm.getAffectedProucts(saleID);
                    Sale newSale = new SaleByProduct(saleID, discount_percent,description, start_date, end_date,  affected);
                    sales.add(newSale);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return sales;
    }

    public LinkedList<Sale> getSaleByCategory(String category) {
        LinkedList<Sale> sales= new LinkedList<>();
        LinkedList<Integer> salesID= acm.getSales(category);
        for(Integer sID: salesID)
            try (Connection conn = connect()) {
                String statement = "SELECT * FROM Sales WHERE saleID=? ";

                try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                    pstmt.setInt(1, sID);

                    ResultSet rs = pstmt.executeQuery();
                    if (rs.next()) {
                        int saleID = rs.getInt(1);
                        Double discount_percent = rs.getDouble(2);
                        String description = rs.getString(3);
                        String start_date = rs.getString(4);
                        String end_date = rs.getString(5);
                        LinkedList<String> affected= acm.getAffectedCategories(saleID);
                        Sale newSale = new SaleByProduct(saleID, discount_percent,description, start_date, end_date,  affected);
                        sales.add(newSale);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        return sales;
    }

    public boolean insert(Report report) {
        boolean output = false;
        try (Connection conn = connect()) {
            boolean inserted = false;
            String statement = "INSERT OR IGNORE INTO Reports(repID, subject, creation_date, time_range, data) " +
                    "VALUES (?,?,?,?,?)";

            try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                pstmt.setInt(1, report.getReportID());
                pstmt.setString(2, report.getSubject());
                pstmt.setString(3, DataController.getDate(report.getCreationDate()));
                pstmt.setString(4, report.getTimeRange());
                pstmt.setString(5, report.getReportData());
                output = pstmt.executeUpdate() != 0;
                rcm.insertCategories(report);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return output;
    }

    public boolean update(Report report) {
        boolean updated = false;
        try (Connection conn = connect()) {
            String statement = "UPDATE Reports SET repID=?, subject=?, creation_date=?, time_range=?, data=? WHERE repID=? ";

            try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                pstmt.setInt(1, report.getReportID());
                pstmt.setString(2, report.getSubject());
                pstmt.setString(3, DataController.getDate(report.getCreationDate()));
                pstmt.setString(4, report.getTimeRange());
                pstmt.setString(5, report.getReportData());
                pstmt.setInt(6, report.getReportID());
                updated = pstmt.executeUpdate() != 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return updated;
    }

    public boolean delete(Report report) {
        boolean deleted = false;
        try (Connection conn = connect()) {
            String statement = "DELETE FROM Reports WHERE repID=?";

            try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                pstmt.setInt(1, report.getReportID());
                deleted = pstmt.executeUpdate() != 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return deleted;
    }

    public LinkedList<Report> loadAllReports() {
        LinkedList<Report> reports = new LinkedList<>();
        try (Connection conn = connect()) {
            String statement = "SELECT * FROM Reports  ";
            try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    int repID = rs.getInt(1);
                    String subject = rs.getString(2);
                    String creation_date = rs.getString(3);
                    String time_range = rs.getString(4);
                    String data = rs.getString(5);
                    LinkedList<String> categoriesList = rcm.getCategories(repID);
                    switch (subject.toLowerCase()) {
                        case "stock":
                            reports.add(new ReportStock(repID,time_range , categoriesList));
                        case "missing":
                            reports.add(new ReportMissing(repID, time_range, categoriesList));
                        case "defects":
                            reports.add(new ReportDefects(repID, time_range, categoriesList));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return reports;
    }



}
