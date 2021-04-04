package BusinessLayer.Shifts;

import BusinessLayer.InnerLogicException;
import BusinessLayer.Workers.Job;
import BusinessLayer.Workers.Worker;
import BusinessLayer.Workers.WorkersList;
import BusinessLayer.WorkersUtils;

import java.time.LocalDate;

public class WorkDay {

    private Shift morningShift;
    private Shift eveningShift;
    private final String date;

    public WorkDay(boolean hasMorning, boolean hasEvening, String date){
        if (hasMorning) morningShift = new Shift();
        if (hasEvening) eveningShift = new Shift();
        this.date = date;
        //TODO: remove
    }

    // TODO: check worker has no constraints
    public Shift addWorker(Job job, Worker worker, ShiftType shiftType) throws InnerLogicException {
        Shift current = getCurrentShift(shiftType);
        if (morningShift != null && morningShift.isWorking(worker)){
            throw new InnerLogicException(worker.getName() + " is already working at this day");
        }
        if (eveningShift != null && eveningShift.isWorking(worker)){
            throw new InnerLogicException(worker.getName() + " is already working at this day");
        }
        if (!worker.canWorkInShift(date,shiftType)){
            throw new InnerLogicException(worker.getName() + " cant work at this shift");
        }
        current.addWorker(job, worker);
        return current;
    }

//    public Shift removeWorker(Job job, Worker worker, ShiftType shiftType) throws InnerLogicException {
//        Shift current = getCurrentShift(shiftType);
//        current.removeWorker(job,worker);
//        return current;
//    }


//    public Shift addRequiredJob(Job role, int required, ShiftType shiftType) throws InnerLogicException{
//        Shift shift = getCurrentShift(shiftType);
//        shift.addRequiredJob(role,required);
//        return shift;
//    }
//
//    public Shift setAmountRequired(Job role, int required, ShiftType shiftType) throws InnerLogicException {
//        Shift shift = getCurrentShift(shiftType);
//        shift.setAmountRequired(role,required);
//        return shift;
//    }
    public Shift removeShift(ShiftType shiftType) throws InnerLogicException {
        Shift output = null;
        try {
            WorkersUtils.notPastDateValidation(date);
        }catch (InnerLogicException e){
            throw new InnerLogicException("tried to remove shift that already happened");
        }
        if(shiftType == ShiftType.Morning){
            if (morningShift == null) throw new InnerLogicException("tried to remove shift that is not exist");
            output = morningShift;
            morningShift =null;
        }
        if(shiftType == ShiftType.Evening){
            if (eveningShift == null) throw new InnerLogicException("tried to remove shift that is not exist");
            output = eveningShift;
            eveningShift =null;
        }
        return  output;
    }

    public Shift getCurrentShift(ShiftType shiftType) throws InnerLogicException {
        if (ShiftType.Morning.equals(shiftType)){
            if (morningShift == null){
                throw new InnerLogicException("This WorkDay does not have a morning Shift");
            }
            return morningShift;
        }
        else if (ShiftType.Evening.equals(shiftType)) {
            if (eveningShift == null) {
                throw new InnerLogicException("This WorkDay does not have an evening Shift");
            }
            return eveningShift;
        }
        throw new InnerLogicException("There's no such shift type");
    }

    public void removeFromFutureShifts(Worker worker) throws InnerLogicException {
        if (morningShift != null){
            Job role = morningShift.getWorkerRole(worker);
            if (role != null){
                morningShift.removeWorker(role,worker);
            }
        }
        if (eveningShift != null){
            Job role = eveningShift.getWorkerRole(worker);
            if (role != null){
                eveningShift.removeWorker(role,worker);
            }
        }
    }

    public Shift getEveningShift() {
        return eveningShift;
    }

    public Shift getMorningShift(){
        return morningShift;
    }

    public String getDate(){return date;}

}
