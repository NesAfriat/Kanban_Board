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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ShiftController {
    private ShiftSchedule calendar;
    private final HashMap<String, Job> jobs = new HashMap<>();
    private WorkDay currentDay;
    private ShiftType currentShiftType;
    private WorkersList workersList;
    private boolean isAdminAuthorized;

    public ShiftController(WorkersList workers){
        currentDay = null;
        currentShiftType = null;

        jobs.put("Cashier",Job.Cashier);
        jobs.put("Storekeeper",Job.Storekeeper);
        jobs.put("Usher",Job.Usher);
        jobs.put("Guard",Job.Guard);
        jobs.put("Shift_Manager",Job.Shift_Manager);
        jobs.put("HR_Manager",Job.HR_Manager);
        jobs.put("Branch_Manager",Job.Branch_Manager);
        jobs.put("Assistant_Branch_Manager",Job.Assistant_Branch_Manager);

        this.workersList = workers;
        calendar = new ShiftSchedule();
        // TODO: remove this lines (testing purposes)
        try{
            calendar.addWorkDay(true, true, "01/02/2020");
            calendar.addWorkDay(true, false, "02/02/2020");
            calendar.addWorkDay(false, false, "03/02/2020");
            calendar.getWorkDay("01/02/2020").addWorker(Job.Shift_Manager, workers.getWorker("321"), ShiftType.Evening);
            calendar.getWorkDay("01/02/2020").addWorker(Job.Shift_Manager, workers.getWorker("123"), ShiftType.Morning);
            calendar.getWorkDay("02/02/2020").addWorker(Job.Shift_Manager, workers.getWorker("321"), ShiftType.Morning);
            //calendar.getWorkDay("03/02/2020").addWorker(Job.Shift_Manager, workers.getWorker("321"), ShiftType.Evening);
        }
        catch (Exception ignored) {

        }
        isAdminAuthorized = false;
    }

    public void setAdminAuthorization(boolean isAdminAuthorized){
        this.isAdminAuthorized = isAdminAuthorized;
    }



    public void setCurrentDay(String date) throws InnerLogicException {
        currentDay = calendar.getWorkDay(date);
    }

    public WorkDay getWorkDay(String date) throws InnerLogicException {
        dateValidation(date);
        return calendar.getWorkDay(date);
    }


    public void setCurrentShiftType(String shiftType) throws InnerLogicException {
        currentShiftType = parseShiftType(shiftType);
    }

    public Shift addWorkerToCurrentShift(String id, String job) throws InnerLogicException {
        throwIfNotAdmin();
        if(currentDay == null || currentShiftType == null)
            throw new InnerLogicException("tried to add worker to shift but no shift hav been chosen");
        Worker workerToAdd = workersList.getWorker(id);
        Job role = parseJob(job);
        return currentDay.addWorker(role, workerToAdd, currentShiftType);
    }



    public Worker removeFromFutureShifts(Worker worker, String date) throws InnerLogicException {
        dateValidation(date);
        List<WorkDay> workDays = calendar.getWorkDaysFrom(date);
        for (WorkDay workDay: workDays) {
            workDay.removeFromFutureShifts(worker);
        }
        return worker;
    }

    public List<Worker> getAvailableWorkers(String job) throws InnerLogicException {

        Job role = parseJob(job);
        List<Worker> listOfWorkers= workersList.getWorkersByJob(role);
        List<Worker> relevantWorkers = new LinkedList<>();
        for (Worker worker: listOfWorkers) {
            if(worker.canWorkInShift(currentDay.getDate(), currentShiftType)) relevantWorkers.add(worker);
        }
        return relevantWorkers;
    }

    private void dateValidation(String date) throws InnerLogicException {
        String result;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate localDate = LocalDate.parse(date, formatter);
            result = localDate.format(formatter);
        } catch (DateTimeParseException e) {
            throw new InnerLogicException("invalid date");
        }
        if (!result.equals(date)) throw new InnerLogicException("invalid date");
    }

    private void throwIfNotAdmin() throws InnerLogicException {
        if(!isAdminAuthorized) throw new InnerLogicException("non admin worker tried to change shift");
    }

    private ShiftType parseShiftType(String shiftType) throws InnerLogicException {
        if ("Morning".equals(shiftType)) return ShiftType.Morning;
        else if ("Evening".equals(shiftType)) return ShiftType.Evening;
        else throw new InnerLogicException("invalid shift type");
    }

    private ConstraintType parseConstraintType(String constraintType) throws InnerLogicException {
        if ("Cant".equals(constraintType)) return ConstraintType.Cant;
        else if ("Want".equals(constraintType)) return ConstraintType.Want;
        else throw new InnerLogicException("invalid constraint type");
    }

    private Job parseJob(String job) throws InnerLogicException {
        Job role = jobs.get(job);
        if (role == null){
            throw new InnerLogicException("invalid constraint type");
        }
        return role;
    }

    public Shift getCurrentShift() throws InnerLogicException {
        if (currentDay == null){
            throw new InnerLogicException("There's no current shift");
        }
        return currentDay.getCurrentShift(currentShiftType);
    }
}
