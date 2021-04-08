package BusinessLayer.Controllers;

import BusinessLayer.InnerLogicException;
import BusinessLayer.Shifts.Shift;
import BusinessLayer.Shifts.ShiftSchedule;
import BusinessLayer.Shifts.ShiftType;
import BusinessLayer.Shifts.WorkDay;
import BusinessLayer.Workers.Job;
import BusinessLayer.Workers.Worker;
import BusinessLayer.Workers.WorkersList;
import BusinessLayer.WorkersUtils;

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
        //WorkersUtils.notPastDateValidation(date);
        return calendar.addWorkDay(hasMorningShift, hasEveningShift, date);
    }

    public WorkDay addDefaultShift(String date, String shiftType) throws InnerLogicException {
        throwIfNotAdmin();
        WorkersUtils.dateValidation(date);
        //WorkersUtils.notPastDateValidation(date);
        WorkersUtils.isInPastMonth(date);
        ShiftType type = WorkersUtils.parseShiftType(shiftType);
        WorkDay workDay = calendar.addDefaultShift(date, type);
        return workDay;
    }

    public WorkDay addDefaultWorkDay(String date) throws InnerLogicException {
        throwIfNotAdmin();
        WorkersUtils.dateValidation(date);
        WorkersUtils.notPastDateValidation(date);
        return calendar.addDefaultWorkDay(date);
    }


    public void setCurrentShiftType(String shiftType) throws InnerLogicException {
        throwIfNotAdmin();
        throwIfCurrentWorkDayIsNotChangeable();
        currentShiftType = WorkersUtils.parseShiftType(shiftType);
    }

    public Shift addWorkerToCurrentShift(String id, String job) throws InnerLogicException {
        throwIfNotAdmin();
        if(currentDay == null || currentShiftType == null)
            throw new InnerLogicException("tried to add worker to shift but no shift have been chosen");
        throwIfCurrentWorkDayIsNotChangeable();
        Worker workerToAdd = workersList.getWorker(id);
        VerifyActiveWorker(workerToAdd);
        Job role = WorkersUtils.parseJob(job);
        return currentDay.addWorker(role, workerToAdd, currentShiftType);
    }

    private void VerifyActiveWorker(Worker worker) throws InnerLogicException {
        if (worker.getEndWorkingDate() != null){
            throw new InnerLogicException(worker.getName() + " (ID: " + worker.getId() +") is no longer working");
        }
    }

    public Shift removeWorkerFromCurrentShift(String id, String job) throws InnerLogicException {
        throwIfNotAdmin();
        throwIfCurrentWorkDayIsNotChangeable();
        Shift currentShift = getCurrentShift();
        Worker workerToRemove = workersList.getWorker(id);
        Job role = WorkersUtils.parseJob(job);
        currentShift.removeWorker(role, workerToRemove);
        return currentShift;
    }

    public Shift setAmountRequired(String role, int required) throws InnerLogicException {
        throwIfNotAdmin();
        throwIfCurrentWorkDayIsNotChangeable();
        Shift currentShift = getCurrentShift();
        Job job = WorkersUtils.parseJob(role);
        currentShift.setAmountRequired(job, required);
        return currentShift;
    }

    public Shift addRequiredJob(String role, int required) throws InnerLogicException {
        throwIfNotAdmin();
        throwIfCurrentWorkDayIsNotChangeable();
        Shift currentShift = getCurrentShift();
        Job job = WorkersUtils.parseJob(role);
        currentShift.addRequiredJob(job, required);
        return currentShift;
    }



    public Worker removeFromFutureShifts(Worker worker, String date) throws InnerLogicException {
        throwIfNotAdmin();
        WorkersUtils.dateValidation(date);
        List<WorkDay> workDays = calendar.getWorkDaysFrom(date);
        for (WorkDay workDay: workDays) {
            workDay.removeFromFutureShifts(worker);
        }
        return worker;
    }

    public List<Worker> getAvailableWorkers(String job) throws InnerLogicException {
        throwIfNotAdmin();
        Job role = WorkersUtils.parseJob(job);
        List<Worker> listOfWorkers= workersList.getWorkersByJob(role);
        List<Worker> relevantWorkers = new LinkedList<>();
        for (Worker worker: listOfWorkers) {
            if(worker.canWorkInShift(currentDay.getDate(), currentShiftType)) relevantWorkers.add(worker);
        }
        return relevantWorkers;
    }




    public Shift approveShift() throws InnerLogicException {
        throwIfNotAdmin();
        throwIfCurrentWorkDayIsNotChangeable();
        Shift shift = getCurrentShift();
        shift.approveShift();
        return shift;
    }

    public Shift getCurrentShift() throws InnerLogicException {
        throwIfNotAdmin();
        if (currentDay == null){
            throw new InnerLogicException("There's no current Day");
        }
        if (currentShiftType == null){
            throw new InnerLogicException("There's no current shift");
        }
        Shift currentShift = currentDay.getShift(currentShiftType);
        if (currentShift == null){
            throw new InnerLogicException("This workday does not have a " + currentShiftType + "shift");
        }
        return currentShift;
    }


    public void clearCurrentShift() throws InnerLogicException {
        throwIfNotAdmin();
        currentDay = null;
        currentShiftType = null;
    }

    public Shift removeShift(String date, String shift) throws InnerLogicException {
        throwIfNotAdmin();
        WorkersUtils.dateValidation(date);
        WorkersUtils.notPastDateValidation(date);
        ShiftType shiftType = WorkersUtils.parseShiftType(shift);
        WorkDay workDay = calendar.getWorkDay(date);
        return workDay.removeShift(shiftType);
    }



    public void setDefaultJobsInShift(int day, String shiftType, String job, int amount) throws InnerLogicException {
        throwIfNotAdmin();
        calendar.setDefaultJobsInShift(day, WorkersUtils.parseShiftType(shiftType), WorkersUtils.parseJob(job), amount);
    }

    public void setDefaultShiftInDay(int dayOfTheWeek, String shiftType, boolean changeTo) throws InnerLogicException {
        throwIfNotAdmin();
        calendar.setDefaultShiftInDay(dayOfTheWeek, WorkersUtils.parseShiftType(shiftType), changeTo);
    }

    public Shift getDefaultJobsInShift(int day, String shiftType) throws InnerLogicException {
        throwIfNotAdmin();
        return calendar.getDefaultShiftSkeleton(day, WorkersUtils.parseShiftType(shiftType));
    }

    public WorkDay getDefaultShiftInDay(int day) throws InnerLogicException {
        throwIfNotAdmin();
        return calendar.getDefaultWorkDaySkeleton(day);
    }

    private void throwIfNotAdmin() throws InnerLogicException {
        if(!isAdminAuthorized) throw new InnerLogicException("non admin worker tried to change shifts");
    }
    private void throwIfCurrentWorkDayIsNotChangeable() throws InnerLogicException {
        if(WorkersUtils.isInPastMonth(currentDay.getDate())) throw new InnerLogicException("edit shift isn't possible after the month it has occurred");
    }
}
