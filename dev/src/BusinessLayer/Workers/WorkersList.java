package BusinessLayer.Workers;

import BusinessLayer.InnerLogicException;
import java.util.LinkedList;
import java.util.List;

public class WorkersList {
    private List<Worker> workers;

    public WorkersList(){
        this.workers = new LinkedList<>();
        try { // default administrator worker
            Worker worker = new Worker( "admin", "000000000", "a", 123, "a", 0, 0, "01/01/0001");
            workers.add(worker);
            worker.addOccupation(Job.HR_Manager); // administrator is admin
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
        //search in db
        throw new InnerLogicException("there is no worker with that id in the system");
    }

    public Worker addWorker(String name, String id, String bankAccount, double salary, String educationFund,
                            int vacationDaysPerMonth, int sickDaysPerMonth, String startWorkingDate) throws InnerLogicException {
        if (contains(id)) throw new InnerLogicException("the system already have worker with the id: " + id);
        Worker newWorker =new Worker( name, id, bankAccount, salary, educationFund, vacationDaysPerMonth,
                sickDaysPerMonth, startWorkingDate);
        // add to map
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
