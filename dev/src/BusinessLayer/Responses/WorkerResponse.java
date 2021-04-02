package BusinessLayer.Responses;
import BusinessLayer.Workers.Job;
import BusinessLayer.Workers.Worker;

import java.util.List;

public class WorkerResponse {
    private boolean isAdmin;
    private List<Job> occupations;
    private List<ConstraintResponse> constaraints;
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
    }

    public boolean getIsAdmin(){return isAdmin;}
}
