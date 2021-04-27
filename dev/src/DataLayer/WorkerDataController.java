package DataLayer;
import BusinessLayer.Workers_BusinessLayer.InnerLogicException;
import BusinessLayer.Workers_BusinessLayer.Shifts.Shift;
import BusinessLayer.Workers_BusinessLayer.Shifts.ShiftType;
import BusinessLayer.Workers_BusinessLayer.Shifts.WorkDay;
import BusinessLayer.Workers_BusinessLayer.Workers.Job;

import java.sql.*;

public class WorkerDataController {
    private IdentityMap identityMap;
    private Connection conn;

    public WorkerDataController(){
        this.identityMap = new IdentityMap();
        conn = null;
        try {
            // db parameters
            String url = "jdbc:sqlite:WorkersDB.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");
            String statement = "INSERT INTO Shift (Date, ShiftType, Cashier_Amount,  Storekeeper_Amount, Usher_Amount," +
                    " Guard_Amount, DriverA_Amount, DriverB_Amount, DriverC_Amount) VALUES (?,?,?,?,?,?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(statement);
            pstmt.setString(1, "21/20/2020");
            pstmt.setString(2, "Morning");
            pstmt.setInt(3, 0);
            pstmt.setInt(4, 0);
            pstmt.setInt(5, 0);
            pstmt.setInt(6, 0);
            pstmt.setInt(7, 0);
            pstmt.setInt(8, 0);
            pstmt.setInt(9, 0);

            pstmt.executeUpdate();


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:WorkersDB.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void addWorkDay(WorkDay workDay){
        Shift morning = workDay.getShift(ShiftType.Morning);
        Shift evening = workDay.getShift(ShiftType.Evening);
        if(morning != null){
            addShift(workDay.getDate(), morning, "Morning");
        }
        if(evening != null){
            addShift(workDay.getDate(), evening, "Evening");
        }
        identityMap.addWorkDay(workDay);
    }

    private void addShift(String date, Shift shift, String shiftType){
        String statement = "INSERT INTO Shift (Date, ShiftType, Cashier_Amount,  Storekeeper_Amount, Usher_Amount," +
                " Guard_Amount, DriverA_Amount, DriverB_Amount, DriverC_Amount) VALUES (?,?,?,?,?,?,?,?,?)";
        int Cashier_Amount = 0;
        int Storekeeper_Amount = 0;
        int Usher_Amount = 0;
        int Guard_Amount = 0;
        int DriverA_Amount = 0;
        int DriverB_Amount = 0;
        int DriverC_Amount = 0;
        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(statement)) {

            if(shift != null) {

                try{
                    Cashier_Amount = shift.getAmountRequired(Job.Cashier);
                }catch (InnerLogicException ignored){}
                try{
                    Storekeeper_Amount = shift.getAmountRequired(Job.Storekeeper);
                }catch (InnerLogicException ignored){
                }
                try{
                    Usher_Amount = shift.getAmountRequired(Job.Usher);
                }catch (InnerLogicException ignored){}
                try{
                    Guard_Amount = shift.getAmountRequired(Job.Guard);
                }catch (InnerLogicException ignored){}
                try{
                    DriverA_Amount = shift.getAmountRequired(Job.DriverA);
                }catch (InnerLogicException ignored){}
                try{
                    DriverB_Amount = shift.getAmountRequired(Job.DriverB);
                }catch (InnerLogicException ignored){}
                try{
                    DriverC_Amount = shift.getAmountRequired(Job.DriverC);
                }catch (InnerLogicException ignored){}

                pstmt.setString(1, date);
                pstmt.setString(2, shiftType);
                pstmt.setInt(3, Cashier_Amount);
                pstmt.setInt(4, Storekeeper_Amount);
                pstmt.setInt(5, Usher_Amount);
                pstmt.setInt(6, Guard_Amount);
                pstmt.setInt(7, DriverA_Amount);
                pstmt.setInt(8, DriverB_Amount);
                pstmt.setInt(9, DriverC_Amount);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
