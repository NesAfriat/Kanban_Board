package DataLayer.Workers_DAL;

import BusinessLayer.Workers_BusinessLayer.InnerLogicException;
import BusinessLayer.Workers_BusinessLayer.Shifts.Shift;
import BusinessLayer.Workers_BusinessLayer.Shifts.ShiftType;
import BusinessLayer.Workers_BusinessLayer.Shifts.WorkDay;
import BusinessLayer.Workers_BusinessLayer.Workers.Constraint;
import BusinessLayer.Workers_BusinessLayer.Workers.ConstraintType;
import BusinessLayer.Workers_BusinessLayer.Workers.Job;
import BusinessLayer.Workers_BusinessLayer.Workers.Worker;
import BusinessLayer.Workers_BusinessLayer.WorkersUtils;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class WorkerDataController {
    private final IdentityMap identityMap;
    private final String connectionPath = "jdbc:sqlite:WorkersDB.db";

    public WorkerDataController() {
        this.identityMap = IdentityMap.getInstance();
    }

    private Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(connectionPath);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public void SaveData(){
        Collection<Worker> workers = identityMap.getAllWorkers();
        for (Worker worker : workers){
            saveWorker(worker);
        }

        Collection<WorkDay> workDays = identityMap.getAllWorkDays();
        for (WorkDay workDay : workDays){
            saveWorkDay(workDay);
        }
    }
    public Worker getWorker(String ID){
        Worker worker = identityMap.getWorker(ID);
        if (worker == null){
            worker = selectWorker(ID);
            if (worker != null)
                identityMap.addWorker(worker);
        }
        return worker;
    }

    private Worker selectWorker(String id) {
        Worker worker = null;
        List<Job> occupations = selectOccupations(id);
        List<Constraint> constraints = selectConstraints(id);

        String sql = "SELECT * FROM Worker WHERE ID = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql);){
            pstmt.setString(1,id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String ID = rs.getString(1);
                String Name = rs.getString(2);
                String BankAccount = rs.getString(3);
                double Salary = rs.getDouble(4);
                String EducationFund = rs.getString(5);
                int vacationDaysPerMonth = rs.getInt(6);
                int sickDaysPerMonth = rs.getInt(7);
                String startWorkingDate = rs.getString(8);
                String endWorkingDate = rs.getString(9);
                worker = new Worker(ID, Name, BankAccount, Salary, EducationFund, vacationDaysPerMonth, sickDaysPerMonth, startWorkingDate);
                if (endWorkingDate != null)
                    worker.fireWorker(endWorkingDate);
                worker.setConstraints(constraints);
                for (Job occupation : occupations) {
                    worker.addOccupation(occupation);
                }
            }
        } catch (SQLException | InnerLogicException e) {
            e.printStackTrace();
        }
        return worker;
    }

    private List<Constraint> selectConstraints(String id) {
        List<Constraint> constraints = new LinkedList<>();
        String sql = "SELECT * FROM Constraints WHERE Worker_ID = ?;";

        try (Connection conn = this.connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql);){
            pstmt.setString(1,id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String Date = rs.getString(2);
                String ShiftType = rs.getString(3);
                String ConstraintType = rs.getString(4);
                Constraint constraint = new Constraint(Date, WorkersUtils.parseShiftType(ShiftType), WorkersUtils.parseConstraintType(ConstraintType));
                constraints.add(constraint);
            }
        } catch (SQLException | InnerLogicException e) {
            e.printStackTrace();
        }
        return constraints;
    }

    private List<Job> selectOccupations(String id) {
        List<Job> occupations = new LinkedList<>();
        String sql = "SELECT * FROM Occupation WHERE Worker_ID = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql);){
            pstmt.setString(1,id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String job = rs.getString(2);
                occupations.add(WorkersUtils.parseJob(job));
            }
        } catch (SQLException | InnerLogicException e) {
            e.printStackTrace();
        }
        return occupations;
    }

    public void addWorker(Worker worker){
        IdentityMap.getInstance().addWorker(worker);
    }

    public void addWorkDay(WorkDay workDay) {
        IdentityMap.getInstance().addWorkDay(workDay);
    }

    private boolean insertOrIgnoreWorker(Worker worker, Connection conn) {
        boolean inserted = false;
        String statement = "INSERT OR IGNORE INTO Worker (ID, Name, BankAccount, Salary, EducationFund, vacationDaysPerMonth, " +
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

        try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
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

    private void insertOrIgnoreOccupation(String Worker_ID, Job occupation, Connection conn) {
        String statement = "INSERT OR IGNORE INTO Occupation (Worker_ID, Job) VALUES (?,?)";
        try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
            pstmt.setString(1, Worker_ID);
            pstmt.setString(2, occupation.toString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void removeOccupation(String Worker_ID, Job occupation){
        String sql = "DELETE FROM Occupation WHERE Worker_ID = ? AND Job = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // set the corresponding param
            pstmt.setString(1, Worker_ID);
            pstmt.setString(2, occupation.toString());
            // execute the delete statement
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertOrIgnoreConstraint(String Worker_ID, Constraint constraint, Connection conn){
        String statement = "INSERT OR IGNORE INTO Constraints (Worker_ID, Date, ShiftType, ConstraintType) VALUES (?,?,?,?)";
        try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
            pstmt.setString(1, Worker_ID);
            pstmt.setString(2, constraint.getDate());
            pstmt.setString(3, constraint.getShiftType().toString());
            pstmt.setString(4, constraint.getConstraintType().toString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void removeConstraint(String Worker_ID, String Date, ShiftType ShiftType){
        String sql = "DELETE FROM Constraints WHERE Worker_ID = ? AND Date = ? AND ShiftType = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // set the corresponding param
            pstmt.setString(1, Worker_ID);
            pstmt.setString(2, Date);
            pstmt.setString(3, ShiftType.toString());
            // execute the delete statement
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateWorker(Worker worker, Connection conn){
        String statement = "UPDATE Worker SET ID = ? , "
                + "Name = ?, "
                + "BankAccount = ?, "
                + "Salary = ?, "
                + "EducationFund = ?, "
                + "vacationDaysPerMonth = ?, "
                + "sickDaysPerMonth = ?, "
                + "startWorkingDate = ?, "
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

        try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
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

    private void saveWorker(Worker worker) {
        try (Connection conn = connect()) {
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

    private void saveOccupations(String Worker_ID, List<Job> occupations, Connection conn) {
        for (Job occupation : occupations) {
            insertOrIgnoreOccupation(Worker_ID, occupation, conn);
        }
    }

    private void saveWorkDay(WorkDay workDay) {
        Shift morning = workDay.getShift(ShiftType.Morning);
        Shift evening = workDay.getShift(ShiftType.Evening);
        if (morning != null) {
            saveShift(workDay.getDate(), morning, "Morning");
        }
        if (evening != null) {
            saveShift(workDay.getDate(), evening, "Evening");
        }
    }

    private void saveShift(String date, Shift shift, String shiftType) {
        try (Connection conn = connect()) {
            boolean inserted = insertOrIgnoreShift(date, shift, shiftType, conn);
            if (!inserted) updateShift(date, shift, shiftType, conn);
            for (Job job : WorkersUtils.getShiftWorkers()) {
                if (shift != null) {
                    for (Worker worker : shift.getCurrentWorkers(job)) {
                        insertOrWorkerInShift(worker.getId(), date, shiftType, job.toString(), conn);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean insertOrIgnoreShift(String date, Shift shift, String shiftType, Connection conn) {
        String statement = "INSERT OR IGNORE INTO Shift (Date, ShiftType, Cashier_Amount,  Storekeeper_Amount, Usher_Amount," +
                " Guard_Amount, DriverA_Amount, DriverB_Amount, DriverC_Amount) VALUES (?,?,?,?,?,?,?,?,?)";
        boolean inserted = false;
        try (PreparedStatement pstmt = conn.prepareStatement(statement)) {

            if (shift != null) {

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
                if (sqlRetVal != 0) inserted = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inserted;
    }

    private void updateShift(String date, Shift shift, String shiftType, Connection conn) {
        String statement = "UPDATE Shift SET Date = ? , "
                + "ShiftType = ?, "
                + "Cashier_Amount = ?, "
                + "Storekeeper_Amount = ?, "
                + "Usher_Amount = ?, "
                + "Guard_Amount = ?, "
                + "DriverA_Amount = ?, "
                + "DriverB_Amount = ?, "
                + "DriverC_Amount = ? "
                + "WHERE Date = ? AND ShiftType = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(statement)) {

            if (shift != null) {

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
            e.printStackTrace();
        }
    }

    private void insertOrWorkerInShift(String workerID, String date, String shiftType, String job, Connection conn) {
        String statement = "INSERT OR IGNORE INTO Workers_In_Shift (Worker_ID, Date, ShiftType, Job) VALUES (?,?,?,?)";

        try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
            pstmt.setString(1, workerID);
            pstmt.setString(2, date);
            pstmt.setString(3, shiftType);
            pstmt.setString(4, job);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void removeShift(String date, String shiftType) {
        String sql = "DELETE FROM Shift WHERE Date = ? AND ShiftType = ?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(1, date);
            pstmt.setString(2, shiftType);
            // execute the delete statement
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public WorkDay getWorkDay(String date){
        WorkDay workDay = identityMap.getWorkDay(date);
        if(workDay == null){
            workDay = buildWorkDay(date);
            identityMap.addWorkDay(workDay);
        }
        return workDay;
    }

    public List<WorkDay> getWorkDaysFromDate(String date){
        List<String> dates = selectDateOfFromDate(date);
        List<WorkDay> workDays = new LinkedList<>();
        for (String d: dates) {
            workDays.add(getWorkDay(d));
        }
        return workDays;
    }

    private List<String> selectDateOfFromDate(String date){
        List<String> dates = new LinkedList<>();
        String sql = "SELECT Date FROM Shift WHERE Date >= ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){

            pstmt.setString(1,date);
            ResultSet rs  = pstmt.executeQuery();

            while(rs.next()){
                dates.add(rs.getString("Date"));
            }
        }catch (SQLException  e) {
            e.printStackTrace();
        }
        return dates;
    }


    private WorkDay buildWorkDay(String date){
        Shift DBmorning = selectShift(date, "Morning");
        Shift DBevening = selectShift(date, "Evening");

        boolean hasMorning = DBmorning != null;
        boolean hasEvening = DBevening != null;

        WorkDay workDay = new WorkDay(hasMorning, hasEvening, date);
        if(hasMorning) {
            Shift workDayMorning = workDay.getShift(ShiftType.Morning);
            for (Job job: WorkersUtils.getShiftWorkers()) {
                try {
                    workDayMorning.setAmountRequired(job, DBmorning.getAmountRequired(job));
                    List<Worker> workersToInsert = selectWorkersInShiftByJob(date, "Morning", job.name());
                    for (Worker worker: workersToInsert) {
                        try {
                            workDayMorning.addWorker(job, worker);
                        } catch (InnerLogicException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (InnerLogicException e) {
                    e.printStackTrace();
                }
            }
        }
        if(hasEvening) {
            Shift workDayEvening = workDay.getShift(ShiftType.Evening);
            for (Job job: WorkersUtils.getShiftWorkers()) {
                try {
                    workDayEvening.setAmountRequired(job, DBevening.getAmountRequired(job));
                    List<Worker> workersToInsert = selectWorkersInShiftByJob(date, "Evening", job.name());
                    for (Worker worker: workersToInsert) {
                        try {
                            workDayEvening.addWorker(job, worker);
                        } catch (InnerLogicException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (InnerLogicException e) {
                    e.printStackTrace();
                }
            }
        }
        return workDay;

    }

    private Shift selectShift(String date,String shiftType){
        Shift outputShift = null;
        String sql = "SELECT * FROM Shift WHERE Date = ? AND ShiftType = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){

            pstmt.setString(1,date);
            pstmt.setString(2,shiftType);
            ResultSet rs  = pstmt.executeQuery();

            if(rs.next()){
                outputShift = new Shift();
                if(rs.getBoolean("Approved")) outputShift.approveShift();
                for (Job job: WorkersUtils.getShiftWorkers()) {
                    String jobAmountColumn = job.name() + "_Amount";
                    outputShift.setAmountRequired(job, rs.getInt(jobAmountColumn));
                }
            }
        }catch (SQLException | InnerLogicException e) {
            e.printStackTrace();
        }
        return outputShift;
    }

    private List<Worker> selectWorkersInShiftByJob(String date, String shiftType, String job){
        List<Worker> outputWorkers = new LinkedList<>();
        String sql = "SELECT Worker_ID"
                + "FROM Workers_In_Shift WHERE Date = ? AND ShiftType = ? AND Job = ?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){

            // set the value
            pstmt.setString(1,date);
            pstmt.setString(2,shiftType);
            pstmt.setString(3,job);

            //
            ResultSet rs  = pstmt.executeQuery();

            // loop through the result set
            while (rs.next()) {
                outputWorkers.add(getWorker(rs.getString("Worker_ID")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return outputWorkers;
    }

}



