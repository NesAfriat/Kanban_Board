package DataLayer.Workers_DAL;
import BusinessLayer.Workers_BusinessLayer.InnerLogicException;
import BusinessLayer.Workers_BusinessLayer.Shifts.Shift;
import BusinessLayer.Workers_BusinessLayer.Shifts.ShiftType;
import BusinessLayer.Workers_BusinessLayer.Shifts.WorkDay;
import BusinessLayer.Workers_BusinessLayer.Workers.Constraint;
import BusinessLayer.Workers_BusinessLayer.Workers.Job;
import BusinessLayer.Workers_BusinessLayer.Workers.Worker;

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
