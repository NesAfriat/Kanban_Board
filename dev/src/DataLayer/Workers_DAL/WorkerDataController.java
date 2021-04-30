package DataLayer.Workers_DAL;

import BusinessLayer.Workers_BusinessLayer.Shifts.Shift;
import BusinessLayer.Workers_BusinessLayer.Shifts.ShiftType;
import BusinessLayer.Workers_BusinessLayer.Shifts.WorkDay;
import BusinessLayer.Workers_BusinessLayer.Workers.Constraint;
import BusinessLayer.Workers_BusinessLayer.Workers.Job;
import BusinessLayer.Workers_BusinessLayer.Workers.Worker;
import BusinessLayer.Workers_BusinessLayer.WorkersUtils;

import java.sql.*;
import java.util.List;

public class WorkerDataController {
    private final IdentityMap identityMap;
    private final String connectionPath = "jdbc:sqlite:WorkersDB.db";

    public WorkerDataController(){
        this.identityMap = IdentityMap.getInstance();
    }

    private Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(connectionPath);
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

    public void addWorker(Worker worker){
        String statement = "INSERT INTO Workers (ID, Name, BankAccount, Salary, EducationFund, vacationDaysPerMonth, " +
                "sickDaysPerMonth, startWorkingDate, endWorkingDate) VALUES (?,?,?,?,?,?,?,?,?)";
        String ID = worker.getId();
        addOccupation(ID, worker.getOccupations());
        addConstraint(ID, worker.getConstraints());
        String Name = worker.getName();
        String BankAccount = worker.getBankAccount();
        double Salary = worker.getSalary();
        String EducationFund = worker.getEducationFund();
        int vacationDaysPerMonth = worker.getVacationDaysPerMonth();
        int sickDaysPerMonth = worker.getSickDaysPerMonth();
        String startWorkingDate = worker.getStartWorkingDate();
        String endWorkingDate = worker.getEndWorkingDate();

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(statement)){
            pstmt.setString(1, ID);
            pstmt.setString(2, Name);
            pstmt.setString(3, BankAccount);
            pstmt.setDouble(4, Salary);
            pstmt.setString(5, EducationFund);
            pstmt.setInt(6, vacationDaysPerMonth);
            pstmt.setInt(7, sickDaysPerMonth);
            pstmt.setString(8, startWorkingDate);
            pstmt.setString(9, endWorkingDate);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addOccupation(String Worker_ID, List<Job> occupations){
        String statement = "INSERT INTO Occupation (Worker_ID, Job) VALUES (?,?)";
        try (Connection conn = connect()){
            for (Job occupation: occupations){
                try (PreparedStatement pstmt = conn.prepareStatement(statement)){
                    pstmt.setString(1, Worker_ID);
                    pstmt.setString(2, occupation.toString());
                    pstmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addConstraint(String Worker_ID, List<Constraint> constraints){
        String statement = "INSERT INTO Constraint (Worker_ID, Date, ShiftType, ConstraintType) VALUES (?,?,?,?)";
        try (Connection conn = connect()){
            for (Constraint constraint: constraints){
                try (PreparedStatement pstmt = conn.prepareStatement(statement)){
                    pstmt.setString(1, Worker_ID);
                    pstmt.setString(2, constraint.getDate());
                    pstmt.setString(3, constraint.getShiftType().toString());
                    pstmt.setString(4, constraint.getConstraintType().toString());
                    pstmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
