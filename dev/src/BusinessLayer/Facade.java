package BusinessLayer;

import BusinessLayer.Controllers.ShiftController;
import BusinessLayer.Controllers.WorkerController;
import BusinessLayer.Responses.*;
import BusinessLayer.Shifts.Shift;
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
            shiftController.setAdminAuthorization(worker.getIsAdmin());
            return new ResponseT<>(new WorkerResponse(worker));
        }catch (InnerLogicException e){
            return new ResponseT<>(e.getMessage());
        }
    }

    public Response logout(){
        try {
            workerController.logout();
            shiftController.setAdminAuthorization(false);
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

    public ResponseT<WorkerResponse>fireWorker(String id, String endWorkingDate){
        try{
            Worker firedWorker = workerController.fireWorker(id, endWorkingDate);
            shiftController.removeFromFutureShifts(firedWorker, firedWorker.getEndWorkingDate());
            return new ResponseT<>(new WorkerResponse(firedWorker));
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

    public ResponseT<WorkerResponse> addOccupationToWorker(String id, String job){
        try{
            Worker worker = workerController.addOccupation(id, job);
            return new ResponseT<>(new WorkerResponse(worker));
        }catch (InnerLogicException e){
            return new ResponseT<>(e.getMessage());
        }
    }

    public ResponseT<WorkerResponse> removeOccupationToWorker(String id, String job){
        try{
            Worker worker = workerController.removeOccupation(id, job);
            return new ResponseT<>(new WorkerResponse(worker));
        }catch (InnerLogicException e){
            return new ResponseT<>(e.getMessage());
        }
    }

    public ResponseT<ShiftResponse> addWorkerToCurrentShift(String id, String job){//assuming that current workday was chosen
        try{
            Shift changedShift = shiftController.addWorkerToCurrentShift(id, job);
            return new ResponseT<>(new ShiftResponse(changedShift));
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

    public ResponseT<WorkDayResponse> addWorkDay(boolean hasMorningShift, boolean hasEveningShift, String date) {
        try {
            WorkDay newWorkDay = shiftController.addWorkDay(hasMorningShift, hasEveningShift, date);
            return new ResponseT<>(new WorkDayResponse(newWorkDay));
        } catch (InnerLogicException e) {
            return new ResponseT<>(e.getMessage());
        }
    }

    public ResponseT<ShiftResponse> chooseShift(String date, String shiftType) {
        try {
            shiftController.setCurrentDay(date);
            shiftController.setCurrentShiftType(shiftType);
            Shift shift = shiftController.getCurrentShift();
            return new ResponseT<>(new ShiftResponse(shift));
        } catch (InnerLogicException e) {
            try{
                shiftController.clearCurrentShift();
            }catch (InnerLogicException err){
                return new ResponseT<>(err.getMessage());
            }
            return new ResponseT<>(e.getMessage());
        }

    }

    public ResponseT<ShiftResponse> approveShift() {
        try {
            Shift shift = shiftController.approveShift();
            return new ResponseT<>(new ShiftResponse(shift));
        } catch (InnerLogicException e) {
            return new ResponseT<>(e.getMessage());
        }
    }

    public ResponseT<ShiftResponse> viewCurrentArrangement() {
        try {
            Shift shift = shiftController.getCurrentShift();
            return new ResponseT<>(new ShiftResponse(shift));
        } catch (InnerLogicException e) {
            return new ResponseT<>(e.getMessage());
        }
    }

    public Response exitShift() {
        try {
            shiftController.clearCurrentShift();
            return new Response();
        }catch (InnerLogicException e){
            return new Response(e.getMessage());
        }
    }

    public ResponseT<ShiftResponse> removeWorkerFromCurrentShift(String id, String role) {
        try {
            Shift shift = shiftController.removeWorkerFromCurrentShift(id, role);
            return new ResponseT<>(new ShiftResponse(shift));
        } catch (InnerLogicException e) {
            return new ResponseT<>(e.getMessage());
        }
    }

    public ResponseT<ShiftResponse> setAmountRequired(String role, int required) {
        try {
            Shift shift = shiftController.setAmountRequired(role, required);
            return new ResponseT<>(new ShiftResponse(shift));
        } catch (InnerLogicException e) {
            return new ResponseT<>(e.getMessage());
        }
    }

    public ResponseT<ShiftResponse> addRequiredJob(String role, int required) {
        try {
            Shift shift = shiftController.addRequiredJob(role, required);
            return new ResponseT<>(new ShiftResponse(shift));
        } catch (InnerLogicException e) {
            return new ResponseT<>(e.getMessage());
        }
    }

    public ResponseT<ShiftResponse> removeShift(String date, String shiftType) {
        try {
            Shift shift = shiftController.removeShift(date, shiftType);
            return new ResponseT<>(new ShiftResponse(shift));
        } catch (InnerLogicException e) {
            return new ResponseT<>(e.getMessage());
        }
    }

    public ResponseT<WorkDayResponse> addDefaultShift(String date, String shiftType) {
        try {
            WorkDay workDay = shiftController.addDefaultShift(date, shiftType);
            return new ResponseT<>(new WorkDayResponse(workDay));
        } catch (InnerLogicException e) {
            return new ResponseT<>(e.getMessage());
        }
    }

    public Response setDefaultJobsInShift(int day, String shiftType, String role, int amount) {
        try {
            shiftController.setDefaultJobsInShift(day, shiftType, role, amount);
            return new Response();
        } catch (InnerLogicException e) {
            return new Response(e.getMessage());
        }
    }


    public ResponseT<WorkDayResponse> addDefaultWorkDay(String date) {
        try {
            WorkDay workDay = shiftController.addDefaultWorkDay(date);
            return new ResponseT<>(new WorkDayResponse(workDay));
        } catch (InnerLogicException e) {
            return new ResponseT<>(e.getMessage());
        }
    }
}
