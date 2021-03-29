package BusinessLayer.Shifts;

import BusinessLayer.Workers.Job;
import BusinessLayer.Workers.Worker;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class Shift {


    private boolean approved;
    private final Map<Job,JobArrangement> currentWorkers;

    public Shift(){
        approved = false;
        currentWorkers = new HashMap<>();
        currentWorkers.put(Job.Shift_Manager, new JobArrangement(1));
        // add more jobs
    }

    private static class JobArrangement {
        // Constructor
        JobArrangement(int required){
            this.required = required;
            amountAssigned = 0;
            workers = new LinkedList<>();
        }

        int required;
        List<Worker> workers;
        int amountAssigned;
    }

    // assign new worker to the shift
    public void addWorker(Job role, Worker worker) throws Exception {
        JobArrangement jobArrangement = getJobArrangement(role);

        if (jobArrangement.amountAssigned == jobArrangement.required){
            throw new Exception("Reached maximum required, remove worker first.");
        }
        jobArrangement.workers.add(worker);
        jobArrangement.amountAssigned++;
    }

    // remove worker from the shift
    public void removeWorker(Job role, Worker worker) throws Exception {
        JobArrangement jobArrangement = getJobArrangement(role);
        if (jobArrangement.workers.remove(worker)){
            jobArrangement.amountAssigned--;
        }
        else {
            throw new Exception("no such worker working at this position at current shift");
        }
    }

    // verify that shift contains the role and return the job arrangement for this role
    private JobArrangement getJobArrangement(Job role) throws Exception {
        JobArrangement jobArrangement = currentWorkers.get(role);
        if (jobArrangement == null){
            throw new Exception("No such role required for the shift");
        }
        return jobArrangement;
    }

    // get the amount of required workers for specific role in the shift
    public int getAmountRequired(Job role) throws Exception {
        JobArrangement jobArrangement = getJobArrangement(role);
        return jobArrangement.required;
    }

    public String getCurrentWorkers(Job role) throws Exception {
        JobArrangement jobArrangement = getJobArrangement(role);
        StringBuilder stringBuilder = new StringBuilder();
        int i = 1;
        for (Worker worker: jobArrangement.workers) {
            stringBuilder.append(i+") ");
            stringBuilder.append(worker.toString());
            stringBuilder.append(" ");
        }
        return stringBuilder.toString();
    }

    public int getCurrentWorkersAmount(Job role) throws Exception {
        JobArrangement jobArrangement = getJobArrangement(role);
        return jobArrangement.amountAssigned;
    }


    public void setAmountRequired(Job role, int required) throws Exception {
        JobArrangement jobArrangement = getJobArrangement(role);
        if (jobArrangement.amountAssigned > required){
            throw new Exception("Can not set required workers to be less than assigned worker. please remove a worker first");
        }
        jobArrangement.required = required;
    }

    public void addRequiredJob(Job role, int required) throws Exception {
        if (currentWorkers.get(role) != null){
            throw new Exception("This role is already required for the shift");
        }
        currentWorkers.put(role, new JobArrangement(required));
    }

    public boolean isApproved(){
        return approved;
    }

    public void approveShift() throws Exception {
        JobArrangement jobArrangement = getJobArrangement(Job.Shift_Manager);
        if (jobArrangement.amountAssigned != 1){
            throw new Exception("Can not approve a shift without a shift manager");
        }
        approved = true;
    }
    public boolean isWorking(Worker worker){
        AtomicBoolean working = new AtomicBoolean(false);
        currentWorkers.forEach((job, jobArrangement) -> {
            if (jobArrangement.workers.contains(worker)) {
            working.set(true);
            }
        });
        return working.get();
    }
}
