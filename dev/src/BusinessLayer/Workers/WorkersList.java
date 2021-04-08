package BusinessLayer.Workers;

import BusinessLayer.InnerLogicException;

import java.util.LinkedList;
import java.util.List;

public class WorkersList {
    private List<Worker> workers;

    public WorkersList(){
        this.workers = new LinkedList<>();
        try {
            workers.add(new Worker(true, "admin", "000000000", "a", 123, "a", 0, 0, "01/01/0001"));
        } catch (InnerLogicException ignored) { }

    }

    public List<Worker> getWorkersByJob(Job job){
        LinkedList<Worker> output = new LinkedList<>();
        for (Worker worker: workers) {
            if(worker.canWorkInJob(job)) output.add(worker);
        }
        return output;
    }

    public Worker getWorker(String id) throws InnerLogicException {
        for (Worker worker: workers) {
            if(worker.getId().equals(id)) return  worker;
        }
        throw new InnerLogicException("there is no worker with that id in the system");
    }

    public Worker addWorker(boolean isAdmin, String name, String id, String bankAccount, double salary, String educationFund,
                          int vacationDaysPerMonth, int sickDaysPerMonth, String startWorkingDate) throws InnerLogicException {
        for (Worker worker: workers) {
            if (worker.getId().equals(id)) throw new InnerLogicException("the system already have worker with the id: " + id);
        }
        Worker newWorker =new Worker(isAdmin, name, id, bankAccount, salary, educationFund, vacationDaysPerMonth,
                sickDaysPerMonth, startWorkingDate);
        workers.add(newWorker);
        return newWorker;
    }

    public Worker fireWorker(String id, String endWorkingDate) throws InnerLogicException {
        Worker firedWorker = getWorker(id);// throws exception if the id not good
        firedWorker.fireWorker(endWorkingDate);
        return firedWorker;
    }

    public boolean contains(String id){
        for (Worker worker: workers) {
            if(worker.getId().equals(id)) return true;
        }
        return false;
    }

}
