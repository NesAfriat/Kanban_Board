package BusinessLayer.Shifts;

import BusinessLayer.InnerLogicException;
import BusinessLayer.Workers.Job;
import BusinessLayer.WorkersUtils;

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
        defaultWorkDayHolder.setDefault(day, shiftType, job, amount);
    }

    public WorkDay addDefaultShift(String date, ShiftType shiftType) throws InnerLogicException {
        WorkDay workDay = workDays.get(date);

        if (workDay != null && workDay.getShift(shiftType) != null){
            throw new InnerLogicException("Tried to add shift to a workday that already has the shift");
        }
        Shift shift;
        int dayOfTheWeek = WorkersUtils.getWeekDayFromDate(date);
        if (workDay == null){
            boolean hasMorning = shiftType.equals(ShiftType.Morning);
            boolean hasEvening = shiftType.equals(ShiftType.Evening);
            workDay = new WorkDay(hasMorning, hasEvening, date);
            workDays.put(date, workDay);
            shift = workDay.getShift(shiftType);
        }
        else {
            shift = workDay.addShift(shiftType);
        }
        List<Job> shiftWorkersRoles = WorkersUtils.getShiftWorkers();
        for (Job role: shiftWorkersRoles){
            shift.addRequiredJob(role, defaultWorkDayHolder.getDefault(role, dayOfTheWeek, shiftType));
        }

        return workDay;
    }


    private class DefaultWorkDayHolder{

        final int weekDayMorning = 0;
        final int weekDayEvening = 1;
        final int fridayMorning = 2;
        final int fridayEvening = 3;
        final int saturdayMorning = 4;
        final int saturdayEvening = 5;

        private final Map<Job, int[]> defaultSetup;

        private DefaultWorkDayHolder(){
            defaultSetup = new HashMap<>();
            List<Job> jobs = WorkersUtils.getShiftWorkers();
            for (Job job: jobs) {
                int[] arr = {1, 1, 1, 1, 1, 1};
                defaultSetup.put(job, arr);
            }
//            int[] cashiers = {1, 2, 2, 0 ,0, 0};
//            int[] storekeeper = {1, 0, 1, 0, 0, 0};
//            int[] Usher = {2, 2, 2, 0, 0, 0};
//            int[] guard = {1, 1, 1, 0, 0, 0};
//            int[] shift_manager = {1, 1, 1, 1, 1, 1};
//            defaultSetup = new HashMap<Job, int[]>(){{
//                put(Job.Cashier, cashiers);
//                put(Job.Storekeeper, storekeeper);
//                put(Job.Usher, Usher);
//                put(Job.Guard, guard);
//                put(Job.Shift_Manager, shift_manager);
//            }};
//

        }

        private void setDefault(int dayOfTheWeek, ShiftType shiftType, Job job, int amount) throws InnerLogicException {
            if (job == Job.Shift_Manager){
                throw new InnerLogicException("Cannot change the default amount of shift manager role");
            }
            if (amount < 0)
                throw new InnerLogicException("cannot have a negative amount of workers");
            int shiftKind = getShiftKind(dayOfTheWeek, shiftType);
            int[] defaults = defaultSetup.get(job);
            if (defaults == null){
                throw new InnerLogicException("There's no a default number workers for job: " + job);
            }
            defaults[shiftKind] = amount;
        }

        private int getShiftKind(int day, ShiftType shiftType) throws InnerLogicException {
            if (day < 1 || day > 7)
                throw new InnerLogicException("day must be between 1 to 7");
            if(day <= 5){
                if(shiftType == ShiftType.Morning) return weekDayMorning;
                else return weekDayEvening;
            }
            if(day == 6){
                if(shiftType == ShiftType.Morning) return fridayMorning;
                else return fridayEvening;
            }
            else{
                if(shiftType == ShiftType.Morning) return saturdayMorning;
                else return saturdayEvening;
            }
        }

        private int getDefault(Job role, int dayOfTheWeek, ShiftType shiftType) throws InnerLogicException {
            int shiftKind = getShiftKind(dayOfTheWeek, shiftType);
            int[] defaults = defaultSetup.get(role);
            if (defaults == null){
                throw new InnerLogicException("There's no a default number workers for job: " + role);
            }
            return defaults[shiftKind];
        }
    }
}
