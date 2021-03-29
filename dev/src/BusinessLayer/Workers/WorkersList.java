package BusinessLayer.Workers;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class WorkersList {
    private List<Worker> workers;

    public WorkersList(){
        this.workers = new LinkedList<>();
    }

    public List<Worker> getWorkersByJob(Job job){
        LinkedList<Worker> output = new LinkedList<>();
        for (Worker worker: workers) {
            if(worker.canWorkInJob(job)) output.add(worker);
        }
        return output;
    }

    public Worker getWorker(String id) throws Exception {
        for (Worker worker: workers) {
            if(worker.getId().equals(id)) return  worker;
        }
        throw new Exception("there is no worker with that id in the system");
    }

    public void addWorker(boolean isAdmin, String name, String id, String bankAccount, double salary, String educationFund,
                          int vacationDaysPerMonth, int sickDaysPerMonth, String startWorkingDate) throws Exception {
        for (Worker worker: workers) {
            if (worker.getId().equals(id)) throw new Exception("the system already have worker with the id: " + id);
        }
        workers.add(new Worker(isAdmin, name, id, bankAccount, salary, educationFund, vacationDaysPerMonth,
                sickDaysPerMonth, startWorkingDate));
    }

    public boolean contains(String id){
        for (Worker worker: workers) {
            if(worker.getId().equals(id)) return true;
        }
        return false;
    }

}