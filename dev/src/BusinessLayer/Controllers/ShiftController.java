package BusinessLayer.Controllers;

import BusinessLayer.InnerLogicException;
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

    public void login(boolean isAdminAuthorized){
        this.isAdminAuthorized = isAdminAuthorized;
    }

    public void logout(){
        this.isAdminAuthorized = false;
    }

    public void setCurrentDay(String date) throws Exception {
        currentDay = calendar.getWorkDay(date);
    }

    public WorkDay getWorkDay(String date) throws InnerLogicException {
        dateValidation(date);
        return calendar.getWorkDay(date);
    }

    public void setCurrentShiftType(String shiftType){
        if (shiftType.equals("Morning"))
            currentShiftType = ShiftType.Morning;
        else
            currentShiftType = ShiftType.Evening;
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

}
