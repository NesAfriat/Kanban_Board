package DataLayer.Mappers;

import BuisnnesLayer.Category;
import BuisnnesLayer.GeneralProduct;
import BuisnnesLayer.Reports.*;
import java.sql.*;
import java.util.LinkedList;

public class ReportsMapper extends Mapper {
    private Reports_CategoriesMapper rcm = new Reports_CategoriesMapper();

    @Override
    void create_table() {
        String ReportsTable = "CREATE TABLE IF NOT EXISTS Reports(\n" +
                "\trepID INTEGER PRIMARY KEY,\n" +
                "\tsubject TEXT,\n" +
                "\tcreation_date TEXT,\n" +
                "\ttime_range TEXT,\n" +
                "\tdata TEXT\n" +
                ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            // create a new tables
            stmt.execute(ReportsTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Report getReport(int rID) {
        Report report = null;
        try (Connection conn = connect()) {
            String statement = "SELECT * FROM Reports WHERE repID=? ";

            try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                pstmt.setInt(1, rID);

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
                            report = new ReportStock(repID, time_range, categoriesList);
                        case "missing":
                            report = new ReportMissing(repID, time_range, categoriesList);
                        case "defects":
                            report = new ReportDefects(repID, time_range, categoriesList);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return report;
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
