package BusinessLayer.Responses;
import BusinessLayer.Workers.Constraint;
import BusinessLayer.Workers.Job;
import BusinessLayer.Workers.Worker;

import java.util.LinkedList;
import java.util.List;

public class WorkerResponse {
    private boolean isAdmin;
    private List<Job> occupations;
    private List<ConstraintResponse> constraints;
    private String name;
    private String id;
    private String bankAccount;
    private double salary;
    private String educationFund;
    private int vacationDaysPerMonth;
    private int sickDaysPerMonth;
    private String startWorkingDate;
    private String endWorkingDate;

    public WorkerResponse(Worker worker){
        this.isAdmin = worker.getIsAdmin();
        this.name = worker.getName();
        this.id = worker.getId();
        this.bankAccount = worker.getBankAccount();
        this.salary = worker.getSalary();
        this.educationFund = worker.getEducationFund();
        this.vacationDaysPerMonth = worker.getVacationDaysPerMonth();
        this.sickDaysPerMonth = worker.getSickDaysPerMonth();
        this.startWorkingDate = worker.getStartWorkingDate();
        this.endWorkingDate = worker.getEndWorkingDate();
        this.occupations = worker.getOccupations();
        this.constraints = new LinkedList<>();
        copyConstraints(worker.getConstraints());
    }

    private void copyConstraints(List<Constraint> otherConstraints){
        for (Constraint constraint: otherConstraints) {
            this.constraints.add(new ConstraintResponse(constraint));
        }
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

}
