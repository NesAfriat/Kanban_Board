package BusinessLayer.Workers;
import BusinessLayer.InnerLogicException;
import BusinessLayer.Shifts.*;
import org.omg.CORBA.CODESET_INCOMPATIBLE;

import java.util.LinkedList;
import java.util.List;

public class Worker {
    private boolean isAdmin;
    private List<Job> occupations;
    private List<Constraint> constraints;
    private String name;
    private String id;
    private String bankAccount;
    private double salary;
    private String educationFund;
    private int vacationDaysPerMonth;
    private int sickDaysPerMonth;
    private String startWorkingDate;
    private String endWorkingDate;

    public Worker(boolean isAdmin, String name, String id, String bankAccount, double salary, String educationFund,
                  int vacationDaysPerMonth, int sickDaysPerMonth, String startWorkingDate) throws InnerLogicException {
        if(name == null || id == null || bankAccount == null || educationFund == null || startWorkingDate == null ||
                salary < 0 || sickDaysPerMonth < 0 || vacationDaysPerMonth < 0)
            throw new InnerLogicException("invalid worker details");
        this.isAdmin = isAdmin;
        this.name = name;
        this.id = id;
        this.bankAccount = bankAccount;
        this.salary = salary;
        this.educationFund = educationFund;
        this.vacationDaysPerMonth = vacationDaysPerMonth;
        this.sickDaysPerMonth = sickDaysPerMonth;
        this.startWorkingDate = startWorkingDate;
        this.endWorkingDate = null;
        this.constraints = new LinkedList<>();
        this.occupations = new LinkedList<>();
    }
    public Worker(String name){
        this.name = name;
        this.constraints = new LinkedList<>();
        this.occupations = new LinkedList<>();
    }
    public Constraint addConstraint(String date, ShiftType shiftType, ConstraintType constraintType ) throws InnerLogicException {
        for (Constraint con: constraints) {
            if(con.compareShift(date, shiftType)){
                throw new InnerLogicException("this shift already has constraint");
            }
        }
        Constraint con = new Constraint(date, shiftType, constraintType);
        this.constraints.add(con);
        return con;
    }

    public Constraint removeConstraint(String date, ShiftType shiftType) throws InnerLogicException {
        Constraint output = null;
        for (Constraint con: constraints) {
            if(con.compareShift(date, shiftType)){
                output = con;
                break;
            }
        }
        if(output == null) throw new InnerLogicException("tried to remove non-existing constraint");
        constraints.remove(output);
        return output;
    }

    public boolean canWorkInShift(String date, ShiftType shiftType){
        for (Constraint con: constraints) {
            if(con.compareShift(date, shiftType) && con.getConstraintType() == ConstraintType.Cant){
               return false;
            }
        }
        return endWorkingDate == null;
    }

    public boolean canWorkInJob(Job job){
        return occupations.contains(job) && endWorkingDate != null;
    }

    public void addOccupations(Job job) throws InnerLogicException {
        if (occupations.contains(job)) throw new InnerLogicException("this worker already qualfied to work as " + job.name());
        this.occupations.add(job);
    }

    public void fireWorker(String endWorkingDate) throws InnerLogicException {
        if(endWorkingDate == null) throw new InnerLogicException("tried to fire worker that was already fired");
        this.endWorkingDate = endWorkingDate;
    }

    public String getId() {
        return id;
    }

    public boolean getIsAdmin(){
        return isAdmin;
    }

    public String getName() {
        return this.name;
    }

    public String getBankAccount(){
        return this.bankAccount;
    }

    public double getSalary(){
        return this.salary;
    }

    public String getEducationFund(){
        return this.educationFund;
    }

    public int getVacationDaysPerMonth(){
        return this.vacationDaysPerMonth;
    }

    public int getSickDaysPerMonth(){
        return this.sickDaysPerMonth;
    }

    public String getStartWorkingDate(){
        return this.startWorkingDate;
    }

    public String getEndWorkingDate(){
        return this.endWorkingDate;
    }

    public List<Job> getOccupations(){
        return new LinkedList<Job>(occupations);
    }

    public List<Constraint> getConstraints(){
        LinkedList<Constraint> output =  new LinkedList<Constraint>();
        for (Constraint constraint: constraints) {
            output.add(new Constraint(constraint));
        }
        return output;
    }
}
