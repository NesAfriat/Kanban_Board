package DataLayer.Workers_DAL;
import BusinessLayer.Workers_BusinessLayer.InnerLogicException;
import BusinessLayer.Workers_BusinessLayer.Shifts.Shift;
import BusinessLayer.Workers_BusinessLayer.Shifts.ShiftType;
import BusinessLayer.Workers_BusinessLayer.Shifts.WorkDay;
import BusinessLayer.Workers_BusinessLayer.Workers.Job;
import BusinessLayer.Workers_BusinessLayer.Workers.Worker;
import BusinessLayer.Workers_BusinessLayer.WorkersUtils;

import java.sql.*;
import java.util.List;

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

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(statement)) {

            if(shift != null) {

                int Cashier_Amount = shift.getAmountRequired(Job.Cashier);
                int Storekeeper_Amount = shift.getAmountRequired(Job.Storekeeper);
                int Usher_Amount = shift.getAmountRequired(Job.Usher);
                int Guard_Amount = shift.getAmountRequired(Job.Guard);
                int DriverA_Amount = shift.getAmountRequired(Job.DriverA);
                int DriverB_Amount = shift.getAmountRequired(Job.DriverB);
                int DriverC_Amount = shift.getAmountRequired(Job.DriverC);

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
        for (Job job:WorkersUtils.getShiftWorkers()) {
            if(shift != null) {
                for (Worker worker: shift.getCurrentWorkers(job)){

                    addWorkerToShift(worker.getId(), date, shiftType, job.toString());
                }
            }
        }
    }

    private void addWorkerToShift(String workerID, String date, String shiftType, String job){
        String statement = "INSERT INTO Workers_In_Shift (Worker_ID, Date, ShiftType, Job) VALUES (?,?,?,?)";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(statement)) {
            pstmt.setString(1, workerID);
            pstmt.setString(2, date);
            pstmt.setString(3, shiftType);
            pstmt.setString(4, job);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
