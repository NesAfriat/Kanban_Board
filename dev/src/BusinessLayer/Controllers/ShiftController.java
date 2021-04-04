package BusinessLayer.Controllers;

import BusinessLayer.InnerLogicException;
import BusinessLayer.Shifts.Shift;
import BusinessLayer.Shifts.ShiftSchedule;
import BusinessLayer.Shifts.ShiftType;
import BusinessLayer.Shifts.WorkDay;
import BusinessLayer.Workers.ConstraintType;
import BusinessLayer.Workers.Job;
import BusinessLayer.Workers.Worker;
import BusinessLayer.Workers.WorkersList;
import BusinessLayer.WorkersUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ShiftController {
    private ShiftSchedule calendar;
    private WorkDay currentDay;
    private ShiftType currentShiftType;
    private WorkersList workersList;
    private boolean isAdminAuthorized;

    public ShiftController(WorkersList workers){
        currentDay = null;
        currentShiftType = null;
        this.workersList = workers;
        calendar = new ShiftSchedule();
        isAdminAuthorized = false;
    }

    public void setAdminAuthorization(boolean isAdminAuthorized){
        this.isAdminAuthorized = isAdminAuthorized;
    }



    public void setCurrentDay(String date) throws InnerLogicException {
        currentDay = calendar.getWorkDay(date);
    }

    public WorkDay getWorkDay(String date) throws InnerLogicException {
        WorkersUtils.dateValidation(date);
        return calendar.getWorkDay(date);
    }

    public WorkDay addWorkDay(boolean hasMorningShift, boolean hasEveningShift, String date) throws InnerLogicException {
        throwIfNotAdmin();
        WorkersUtils.dateValidation(date);
        return calendar.addWorkDay(hasMorningShift, hasEveningShift, date);
    }


    public void setCurrentShiftType(String shiftType) throws InnerLogicException {
        currentShiftType = WorkersUtils.parseShiftType(shiftType);
    }

    public Shift addWorkerToCurrentShift(String id, String job) throws InnerLogicException {
        throwIfNotAdmin();
        if(currentDay == null || currentShiftType == null)
            throw new InnerLogicException("tried to add worker to shift but no shift have been chosen");
        Worker workerToAdd = workersList.getWorker(id);
        Job role = WorkersUtils.parseJob(job);
        return currentDay.addWorker(role, workerToAdd, currentShiftType);
    }

    public Shift removeWorkerFromCurrentShift(String id, String job) throws InnerLogicException {
        throwIfNotAdmin();
        Shift currentShift = getCurrentShift();
        Worker workerToRemove = workersList.getWorker(id);
        Job role = WorkersUtils.parseJob(job);
        currentShift.removeWorker(role, workerToRemove);
        return currentShift;
    }

    public Shift setAmountRequired(String role, int required) throws InnerLogicException {
        throwIfNotAdmin();
        Shift currentShift = getCurrentShift();
        Job job = WorkersUtils.parseJob(role);
        currentShift.setAmountRequired(job, required);
        return currentShift;
    }

    public Shift addRequiredJob(String role, int required) throws InnerLogicException {
        throwIfNotAdmin();
        Shift currentShift = getCurrentShift();
        Job job = WorkersUtils.parseJob(role);
        currentShift.addRequiredJob(job, required);
        return currentShift;
    }



    public Worker removeFromFutureShifts(Worker worker, String date) throws InnerLogicException {
        WorkersUtils.dateValidation(date);
        List<WorkDay> workDays = calendar.getWorkDaysFrom(date);
        for (WorkDay workDay: workDays) {
            workDay.removeFromFutureShifts(worker);
        }
        return worker;
    }

    public List<Worker> getAvailableWorkers(String job) throws InnerLogicException {
        Job role = WorkersUtils.parseJob(job);
        List<Worker> listOfWorkers= workersList.getWorkersByJob(role);
        List<Worker> relevantWorkers = new LinkedList<>();
        for (Worker worker: listOfWorkers) {
            if(worker.canWorkInShift(currentDay.getDate(), currentShiftType)) relevantWorkers.add(worker);
        }
        return relevantWorkers;
    }


    private void throwIfNotAdmin() throws InnerLogicException {
        if(!isAdminAuthorized) throw new InnerLogicException("non admin worker tried to change shifts");
    }

    public Shift approveShift() throws InnerLogicException {
        Shift shift = getCurrentShift();
        shift.approveShift();
        return shift;
    }

    public Shift getCurrentShift() throws InnerLogicException {
        if (currentDay == null){
            throw new InnerLogicException("There's no current Day");
        }
        if (currentShiftType == null){
            throw new InnerLogicException("There's no current shift");
        }
        return currentDay.getCurrentShift(currentShiftType);
    }

    public void clearCurrentShift() {
        currentDay = null;
        currentShiftType = null;
    }

    public Shift removeShift(String date, String shiftType) {

        return null;
    }
}
