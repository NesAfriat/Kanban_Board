package BusinessLayer.Workers;
import BusinessLayer.Shifts.*;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Worker {
    private boolean isAdmin;
    private List<Job> occupations;
    private List<Constaraint> constaraints;
    private String name;
    private String id;
    private String bankAccount;
    private double salary;
    private String educationFund;
    private int vacationDaysPerMonth;
    private int sickDaysPerMonth;
    private Date startWorkingDate;
    private Date endWorkingDate;

    public Worker(boolean isAdmin, String name, String id, String bankAccount, double salary, String educationFund,
                  int vacationDaysPerMonth, int sickDaysPerMonth, Date startWorkingDate){
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
        this.constaraints = new LinkedList<>();
        this.occupations = new LinkedList<>();
    }
    public Worker(String name){
        this.name = name;
        this.constaraints = new LinkedList<>();
        this.occupations = new LinkedList<>();
    }
    public void addConstraint(Date date, ShiftType shiftType, ConstraintType constraintType ) throws Exception {
        for (Constaraint con: constaraints) {
            if(con.compareShift(date, shiftType)){
                throw new Exception("this shift already has constraint");
            }
        }
        this.constaraints.add(new Constaraint(date, shiftType, constraintType));
    }

    public void removeConstraint(Date date, ShiftType shiftType) throws Exception {
        for (Constaraint con: constaraints) {
            if(con.compareShift(date, shiftType)){
                constaraints.remove(con);
            }
        }
    }

    public boolean canWorkInShift(Date date, ShiftType shiftType){
        for (Constaraint con: constaraints) {
            if(con.compareShift(date, shiftType) && con.getConstraintType() == ConstraintType.Cant){
               return false;
            }
        }
        return true;
    }

    public boolean canWorkInJob(Job job){
        return occupations.contains(job);
    }

    public void addOccupations(Job job){
        this.occupations.add(job);
    }
}
