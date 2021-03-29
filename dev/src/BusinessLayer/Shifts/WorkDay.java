package BusinessLayer.Shifts;

import BusinessLayer.Workers.Job;
import BusinessLayer.Workers.Worker;

import java.time.LocalDate;

public class WorkDay {
    private Shift morningShift;
    private Shift eveningShift;
    private final String date;

    public WorkDay(boolean hasMorning, boolean hasEvening, String date){
        if (hasMorning) morningShift = new Shift();
        if (hasEvening) eveningShift = new Shift();
        this.date = date;
    }

    public Shift addWorker(Job job, Worker worker, ShiftType shiftType) throws Exception {
        Shift current = getCurrentShift(shiftType);
        if (morningShift != null && morningShift.isWorking(worker)){
            throw new Exception(worker.toString() + "is already working at this day");
        }
        if (eveningShift != null && eveningShift.isWorking(worker)){
            throw new Exception(worker.toString() + "is already working at this day");
        }
        current.addWorker(job, worker);
        return current;
    }

    public Shift removeWorker(Job job, Worker worker, ShiftType shiftType) throws Exception {
        Shift current = getCurrentShift(shiftType);
        current.removeWorker(job,worker);
        return current;
    }

    public Shift approveShift(ShiftType shiftType) throws Exception {
        Shift shift = getCurrentShift(shiftType);
        shift.approveShift();
        return shift;
    }

    public Shift addRequiredJob(Job role, int required, ShiftType shiftType) throws Exception{
        Shift shift = getCurrentShift(shiftType);
        shift.addRequiredJob(role,required);
        return shift;
    }

    public Shift setAmountRequired(Job role, int required, ShiftType shiftType) throws Exception {
        Shift shift = getCurrentShift(shiftType);
        shift.setAmountRequired(role,required);
        return shift;
    }

    public Shift getCurrentShift(ShiftType shiftType) throws Exception {
        if (shiftType == ShiftType.Morning){
            if (morningShift == null){
                throw new Exception("This WorkDay does not have a morning Shift");
            }
            return morningShift;
        }
        if (eveningShift == null){
            throw new Exception("This WorkDay does not have an evening Shift");
        }
        return eveningShift;
    }

    public String getDate(){return date;}

}
