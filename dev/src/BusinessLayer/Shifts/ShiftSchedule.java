package BusinessLayer.Shifts;

import BusinessLayer.InnerLogicException;
import BusinessLayer.Workers.Job;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ShiftSchedule {
    private Map<String, WorkDay> workDays;
    private DefaultWorkDayHolder defaultWorkDayHolder;

    public ShiftSchedule(){
        workDays = new HashMap<>();
        defaultWorkDayHolder = new DefaultWorkDayHolder();
    }

    public WorkDay addWorkDay(boolean hasMorningShift, boolean hasEveningShift, String date) throws InnerLogicException {
        if (workDays.get(date) != null){
            throw new InnerLogicException("There's already a WorkDay at date "+ date);
        }
        WorkDay workDay = new WorkDay(hasMorningShift,hasEveningShift,date);
        workDays.put(date,workDay);
        return  workDay;
    }

    public WorkDay getWorkDay(String date) throws InnerLogicException {
        WorkDay workDay = workDays.get(date);
        if (workDay == null){
            throw new InnerLogicException("There's no WorkDay at date: " + date);
        }
        return workDay;
    }

    public List<WorkDay> getWorkDaysFrom(String date) {
        List<WorkDay> futureDays = new LinkedList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        workDays.forEach((key, workDay) -> {
            LocalDate inputDate = LocalDate.parse(date, formatter);
            LocalDate keyDate = LocalDate.parse(key, formatter);
            if (keyDate.isAfter(inputDate)){
                futureDays.add(workDay);
            }
        });

        return futureDays;
    }



    public void setDefault(int day, ShiftType shiftType, Job job, int amount) throws InnerLogicException {
        if(day < 1 || day > 7) throw new InnerLogicException("day must be between 1 to 7");
        if(amount < 0) throw new InnerLogicException("cannot have a negative amount of workers");
        defaultWorkDayHolder.setDefault(day, shiftType, job, amount);
    }



    private class DefaultWorkDayHolder{
        final int weekDayMorning = 0;
        final int weekDayEvening = 1;
        final int fridayMorning = 2;
        final int fridayEvening = 3;
        final int saturdayMorning = 4;
        final int saturdayEvening = 5;

        private Map<Job, int[]> defaultSetup;

        private DefaultWorkDayHolder(){
            int[] cashiers = {1, 2, 2, 0, 0, 0};
            int[] storekeeper = {1, 0, 1, 0, 0, 0};
            int[] Usher = {2, 2, 2, 0, 0, 0};
            int[] guard = {1, 1, 1, 0, 0, 0};
            defaultSetup = new HashMap<Job, int[]>(){{
                put(Job.Cashier, cashiers);
                put(Job.Storekeeper, storekeeper);
                put(Job.Usher, Usher);
                put(Job.Guard, guard);
            }};
        }


        private void setDefault(int day, ShiftType shiftType, Job job, int amount) throws InnerLogicException {
            int shiftKind = 0;
            day = day - 5;
            if(day > 0) shiftKind = shiftKind + day * 2;
            if(shiftType == ShiftType.Evening) shiftKind = shiftKind + 1;
            this.defaultSetup.get(job)[shiftKind] = amount;
        }
    }
}
