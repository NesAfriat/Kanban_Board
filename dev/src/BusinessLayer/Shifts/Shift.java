package BusinessLayer.Shifts;

import BusinessLayer.InnerLogicException;
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


    // assign new worker to the shift
    public void addWorker(Job role, Worker worker) throws InnerLogicException {
        JobArrangement jobArrangement = getJobArrangement(role);

        if (jobArrangement.amountAssigned == jobArrangement.required){
            throw new InnerLogicException("Reached maximum required, remove worker first.");
        }
        jobArrangement.workers.add(worker);
        jobArrangement.amountAssigned++;
    }

    // remove worker from the shift
    public void removeWorker(Job role, Worker worker) throws InnerLogicException {
        JobArrangement jobArrangement = getJobArrangement(role);
        if (jobArrangement.workers.remove(worker)){
            jobArrangement.amountAssigned--;
        }
        else {
            throw new InnerLogicException("no such worker working at this position at current shift");
        }
    }

    // verify that shift contains the role and return the job arrangement for this role
    private JobArrangement getJobArrangement(Job role) throws InnerLogicException {
        JobArrangement jobArrangement = currentWorkers.get(role);
        if (jobArrangement == null){
            throw new InnerLogicException("No such role required for the shift");
        }
        return jobArrangement;
    }

    // get the amount of required workers for specific role in the shift
    public int getAmountRequired(Job role) throws InnerLogicException {
        JobArrangement jobArrangement = getJobArrangement(role);
        return jobArrangement.required;
    }

    public List<Worker> getCurrentWorkers(Job role) throws InnerLogicException {
        // TODO: return a copy of the list
        return getJobArrangement(role).workers;
    }

    public int getCurrentWorkersAmount(Job role) throws InnerLogicException {
        JobArrangement jobArrangement = getJobArrangement(role);
        return jobArrangement.amountAssigned;
    }


    public void setAmountRequired(Job role, int required) throws InnerLogicException {
        JobArrangement jobArrangement = getJobArrangement(role);
        if (jobArrangement.amountAssigned > required){
            throw new InnerLogicException("Can not set required workers to be less than assigned worker. please remove a worker first");
        }
        jobArrangement.required = required;
    }

    public void addRequiredJob(Job role, int required) throws InnerLogicException {
        if (currentWorkers.get(role) != null){
            throw new InnerLogicException("This role is already required for the shift");
        }
        currentWorkers.put(role, new JobArrangement(required));
    }

    public boolean isApproved(){
        return approved;
    }

    public void approveShift() throws InnerLogicException {
        JobArrangement jobArrangement = getJobArrangement(Job.Shift_Manager);
        if (jobArrangement.amountAssigned != 1){
            throw new InnerLogicException("Can not approve a shift without a shift manager");
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

    public List<Job> getJobs() {
        List<Job> jobs = new LinkedList<>();
        currentWorkers.forEach((job, jobArrangement) -> {
            jobs.add(job);
        });
        return jobs;
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
}
