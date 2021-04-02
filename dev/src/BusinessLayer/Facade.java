package BusinessLayer;

import BusinessLayer.Controllers.ShiftController;
import BusinessLayer.Controllers.WorkerController;
import BusinessLayer.Responses.ConstraintResponse;
import BusinessLayer.Responses.ResponseT;
import BusinessLayer.Responses.WorkDayResponse;
import BusinessLayer.Responses.WorkerResponse;
import BusinessLayer.Shifts.WorkDay;
import BusinessLayer.Workers.Constraint;
import BusinessLayer.Workers.Worker;

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


}
