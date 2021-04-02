package BusinessLayer;

import BusinessLayer.Controllers.ShiftController;
import BusinessLayer.Controllers.WorkerController;
import BusinessLayer.Responses.*;
import BusinessLayer.Shifts.WorkDay;
import BusinessLayer.Workers.Constraint;
import BusinessLayer.Workers.Worker;

import java.util.LinkedList;
import java.util.List;

public class Facade {
    private WorkerController workerController;
    private ShiftController shiftController;

    public Facade(){
        this.workerController = new WorkerController();
        this.shiftController = new ShiftController(workerController.getWorkersList());
    }

    public ResponseT<WorkerResponse> login(String id){
        try {
            Worker worker = workerController.login(id);
            shiftController.login(worker.getIsAdmin());
            return new ResponseT<>(new WorkerResponse(worker));
        }catch (InnerLogicException e){
            return new ResponseT<>(e.getMessage());
        }
    }

    public Response logout(){
        try {
            workerController.logout();
            shiftController.logout();
            return new Response();
        }catch (InnerLogicException e){
            return new Response(e.getMessage());
        }
    }

    public ResponseT<WorkerResponse> getLoggedWorker(){
        try{
            return new ResponseT<WorkerResponse>(new WorkerResponse(workerController.getLoggedIn()));
        }catch (InnerLogicException e){
            return new ResponseT<>(e.getMessage());
        }
    }

    public ResponseT<ConstraintResponse> addConstraint(String date, String shiftType, String constraintType){
        try{
            Constraint constraint = workerController.addConstraint(date, shiftType, constraintType);
            return new ResponseT<>(new ConstraintResponse(constraint));
        }catch (InnerLogicException e){
            return new ResponseT<>(e.getMessage());
        }
    }

    public ResponseT<ConstraintResponse> removeConstraint(String date, String shiftType) {
        try{
            Constraint constraint = workerController.removeConstraint(date, shiftType);
            return new ResponseT<>(new ConstraintResponse(constraint));
        }
        catch (InnerLogicException e){
            return new ResponseT<>(e.getMessage());
        }
    }

    public ResponseT<WorkDayResponse> viewShiftArrangement(String date) {
        try{
            WorkDay workDay = shiftController.getWorkDay(date);
            return new ResponseT<>(new WorkDayResponse(workDay));
        }
        catch (InnerLogicException e){
            return new ResponseT<>(e.getMessage());
        }
    }

    public ResponseT<WorkerResponse> addWorker(boolean isAdmin, String name, String id, String bankAccount, double salary, String educationFund,
                            int vacationDaysPerMonth, int sickDaysPerMonth, String startWorkingDate){
        try{
            Worker newWorker = workerController.addWorker(isAdmin, name, id, bankAccount, salary, educationFund, vacationDaysPerMonth,
                    sickDaysPerMonth, startWorkingDate);
            return new ResponseT<>(new WorkerResponse(newWorker));
        }catch (InnerLogicException e){
            return new ResponseT<>(e.getMessage());
        }
    }

    public ResponseT<WorkerResponse> getWorker(String id){
        try{
            Worker worker = workerController.getWorker(id);
            return new ResponseT<>(new WorkerResponse(worker));
        }catch (InnerLogicException e){
            return new ResponseT<>(e.getMessage());
        }
    }

    public ResponseT<List<WorkerResponse>> getAvailableWorkers(String job){
        try {
            List<Worker> availableWorkers = shiftController.getAvailableWorkers(job);
            List<WorkerResponse> availableWorkersResponse = new LinkedList<>();
            for (Worker worker: availableWorkers) {
                availableWorkersResponse.add(new WorkerResponse(worker));
            }
            return new ResponseT<>(availableWorkersResponse);
        }catch (InnerLogicException e){
            return new ResponseT<>(e.getMessage());
        }

    }



}
