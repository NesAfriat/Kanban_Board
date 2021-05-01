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

    public void addWorker(Worker worker){
        IdentityMap.getInstance().addWorker(worker);
    }

    public void addWorkDay(WorkDay workDay){
        IdentityMap.getInstance().addWorkDay(workDay);
    }

    private boolean insertOrIgnoreWorker(Worker worker, Connection conn){
        boolean inserted = false;
        String statement = "INSERT OR IGNORE INTO Workers (ID, Name, BankAccount, Salary, EducationFund, vacationDaysPerMonth, " +
                "sickDaysPerMonth, startWorkingDate, endWorkingDate) VALUES (?,?,?,?,?,?,?,?,?)";
        String ID = worker.getId();
        String Name = worker.getName();
        String BankAccount = worker.getBankAccount();
        double Salary = worker.getSalary();
        String EducationFund = worker.getEducationFund();
        int vacationDaysPerMonth = worker.getVacationDaysPerMonth();
        int sickDaysPerMonth = worker.getSickDaysPerMonth();
        String startWorkingDate = worker.getStartWorkingDate();
        String endWorkingDate = worker.getEndWorkingDate();

        try (PreparedStatement pstmt = conn.prepareStatement(statement)){
            pstmt.setString(1, ID);
            pstmt.setString(2, Name);
            pstmt.setString(3, BankAccount);
            pstmt.setDouble(4, Salary);
            pstmt.setString(5, EducationFund);
            pstmt.setInt(6, vacationDaysPerMonth);
            pstmt.setInt(7, sickDaysPerMonth);
            pstmt.setString(8, startWorkingDate);
            pstmt.setString(9, endWorkingDate);
            inserted = pstmt.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inserted;
    }

    private void insertOrIgnoreOccupation(String Worker_ID, Job occupation, Connection conn){
        String statement = "INSERT OR IGNORE INTO Occupation (Worker_ID, Job) VALUES (?,?)";
        try (PreparedStatement pstmt = conn.prepareStatement(statement)){
                    pstmt.setString(1, Worker_ID);
                    pstmt.setString(2, occupation.toString());
                    pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertOrIgnoreConstraint(String Worker_ID, Constraint constraint, Connection conn){
        String statement = "INSERT OR IGNORE INTO Constraint (Worker_ID, Date, ShiftType, ConstraintType) VALUES (?,?,?,?)";
        try (PreparedStatement pstmt = conn.prepareStatement(statement)){
            pstmt.setString(1, Worker_ID);
            pstmt.setString(2, constraint.getDate());
            pstmt.setString(3, constraint.getShiftType().toString());
            pstmt.setString(4, constraint.getConstraintType().toString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateWorker(Worker worker, Connection conn){
        String statement = "UPDATE Workers SET ID = ? , "
                + "Name = ? "
                + "BankAccount = ? "
                + "Salary = ? "
                + "EducationFund = ? "
                + "vacationDaysPerMonth = ? "
                + "sickDaysPerMonth = ? "
                + "startWorkingDate = ? "
                + "endWorkingDate = ? "
                + "WHERE ID = ?";

        String ID = worker.getId();
        String Name = worker.getName();
        String BankAccount = worker.getBankAccount();
        double Salary = worker.getSalary();
        String EducationFund = worker.getEducationFund();
        int vacationDaysPerMonth = worker.getVacationDaysPerMonth();
        int sickDaysPerMonth = worker.getSickDaysPerMonth();
        String startWorkingDate = worker.getStartWorkingDate();
        String endWorkingDate = worker.getEndWorkingDate();

        try (PreparedStatement pstmt = conn.prepareStatement(statement)){
            pstmt.setString(1, ID);
            pstmt.setString(2, Name);
            pstmt.setString(3, BankAccount);
            pstmt.setDouble(4, Salary);
            pstmt.setString(5, EducationFund);
            pstmt.setInt(6, vacationDaysPerMonth);
            pstmt.setInt(7, sickDaysPerMonth);
            pstmt.setString(8, startWorkingDate);
            pstmt.setString(9, endWorkingDate);
            pstmt.setString(10, ID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveWorker(Worker worker){
        try (Connection conn = connect()){
            if (!insertOrIgnoreWorker(worker, conn))
                updateWorker(worker, conn);
            saveOccupations(worker.getId(), worker.getOccupations(), conn);
            saveConstraints(worker.getId(), worker.getConstraints(), conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void saveConstraints(String Worker_ID, List<Constraint> constraints, Connection conn) {
        for (Constraint constraint : constraints) {
            insertOrIgnoreConstraint(Worker_ID, constraint, conn);
        }
    }

    private void saveOccupations(String Worker_ID, List<Job> occupations, Connection conn){
        for (Job occupation : occupations) {
           insertOrIgnoreOccupation(Worker_ID, occupation, conn);
        }
    }

    private void saveWorkDay(WorkDay workDay){
        Shift morning = workDay.getShift(ShiftType.Morning);
        Shift evening = workDay.getShift(ShiftType.Evening);
        if(morning != null){
            saveShift(workDay.getDate(), morning, "Morning");
        }
        if(evening != null){
            saveShift(workDay.getDate(), evening, "Evening");
        }
    }

    private void saveShift(String date, Shift shift, String shiftType){
        try (Connection conn = connect()) {
            boolean inserted = insertOrIgnoreShift(date, shift, shiftType, conn);
            if(!inserted) updateShift(date, shift, shiftType, conn);
            for (Job job:WorkersUtils.getShiftWorkers()) {
                if(shift != null) {
                    for (Worker worker: shift.getCurrentWorkers(job)){
                        insertOrWorkerInShift(worker.getId(), date, shiftType, job.toString(), conn);
                    }
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean insertOrIgnoreShift(String date, Shift shift, String shiftType, Connection conn){
        String statement = "INSERT OR IGNORE INTO Shift (Date, ShiftType, Cashier_Amount,  Storekeeper_Amount, Usher_Amount," +
                " Guard_Amount, DriverA_Amount, DriverB_Amount, DriverC_Amount) VALUES (?,?,?,?,?,?,?,?,?)";
        boolean inserted = false;
        try (PreparedStatement pstmt = conn.prepareStatement(statement)) {

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
                int sqlRetVal = pstmt.executeUpdate();
                if(sqlRetVal != 0) inserted = true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return inserted;
    }

    private void updateShift(String date, Shift shift, String shiftType, Connection conn){
        String statement = "UPDATE Workers SET Date = ? , "
                + "ShiftType = ? "
                + "Cashier_Amount = ? "
                + "Storekeeper_Amount = ? "
                + "Usher_Amount = ? "
                + "Guard_Amount = ? "
                + "DriverA_Amount = ? "
                + "DriverB_Amount = ? "
                + "DriverC_Amount = ? "
                + "WHERE Date = ? AND ShiftType = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(statement)) {

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
                pstmt.setString(10, date);
                pstmt.setString(11, shiftType);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void insertOrWorkerInShift(String workerID, String date, String shiftType, String job, Connection conn){
        String statement = "INSERT OR IGNORE INTO Workers_In_Shift (Worker_ID, Date, ShiftType, Job) VALUES (?,?,?,?)";

        try ( PreparedStatement pstmt = conn.prepareStatement(statement)) {
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
