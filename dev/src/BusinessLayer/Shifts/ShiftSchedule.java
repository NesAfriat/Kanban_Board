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

    public void setDefaultJobsInShift(int day, ShiftType shiftType, Job job, int amount) throws InnerLogicException {
        defaultWorkDayHolder.setDefaultJobInShift(day, shiftType, job, amount);
    }

    public Shift getDefaultShiftSkeleton(int day, ShiftType shiftType) throws InnerLogicException {
        Shift skeleton = new Shift();
        List<Job> shiftWorkersRoles = WorkersUtils.getShiftWorkers();
        for (Job role: shiftWorkersRoles){
            skeleton.addRequiredJob(role, defaultWorkDayHolder.getDefaultJobInShift(role, day, shiftType));
        }
        return skeleton;
    }

    public WorkDay getDefaultWorkDaySkeleton(int day) throws InnerLogicException {
        boolean hasMorning = defaultWorkDayHolder.getDefaultShiftInDay(day, ShiftType.Morning);
        boolean hasEvening = defaultWorkDayHolder.getDefaultShiftInDay(day, ShiftType.Evening);
        WorkDay skeleton = new WorkDay(hasMorning, hasEvening,"");
        List<Job> shiftWorkersRoles = WorkersUtils.getShiftWorkers();
        Shift morningSkeleton = skeleton.getShift(ShiftType.Morning);
        Shift eveningSkeleton = skeleton.getShift(ShiftType.Evening);
        for (Job role: shiftWorkersRoles){
            if(morningSkeleton != null)
                morningSkeleton.addRequiredJob(role, defaultWorkDayHolder.getDefaultJobInShift(role, day, ShiftType.Morning));
            if(eveningSkeleton != null)
                eveningSkeleton.addRequiredJob(role, defaultWorkDayHolder.getDefaultJobInShift(role, day, ShiftType.Evening));
        }
        return skeleton;
    }


    public void setDefaultShiftInDay(int dayOfTheWeek, ShiftType shiftType, boolean changeTo) throws InnerLogicException {
        defaultWorkDayHolder.setDefaultShiftInDay(dayOfTheWeek, shiftType, changeTo);
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
            shift.addRequiredJob(role, defaultWorkDayHolder.getDefaultJobInShift(role, dayOfTheWeek, shiftType));
        }

        return workDay;
    }

    public WorkDay addDefaultWorkDay(String date) throws InnerLogicException {
        if (workDays.containsKey(date)){
            throw new InnerLogicException("Workday at date: " + date + " is already exist");
        }
        int dayOfTheWeek = WorkersUtils.getWeekDayFromDate(date);
        boolean hasMorning = defaultWorkDayHolder.getDefaultShiftInDay(dayOfTheWeek, ShiftType.Morning);
        boolean hasEvening = defaultWorkDayHolder.getDefaultShiftInDay(dayOfTheWeek, ShiftType.Evening);
        WorkDay workDay = new WorkDay(hasMorning,hasEvening,date);
        List<Job> shiftWorkersRoles = WorkersUtils.getShiftWorkers();

        if (hasMorning){
            Shift shift = workDay.getShift(ShiftType.Morning);
            for (Job role: shiftWorkersRoles){
                shift.addRequiredJob(role, defaultWorkDayHolder.getDefaultJobInShift(role, dayOfTheWeek, ShiftType.Morning));
            }
        }

        if (hasEvening){
            Shift shift = workDay.getShift(ShiftType.Evening);
            for (Job role: shiftWorkersRoles){
                shift.addRequiredJob(role, defaultWorkDayHolder.getDefaultJobInShift(role, dayOfTheWeek, ShiftType.Evening));
            }
        }
        workDays.put(date,workDay);
        return workDay;
    }


    private class DefaultWorkDayHolder{

        final int weekDayMorning = 0;
        final int weekDayEvening = 1;
        final int fridayMorning = 2;
        final int fridayEvening = 3;
        final int saturdayMorning = 4;
        final int saturdayEvening = 5;

        private final Map<Job, int[]> defaultShiftSetup;
        private final boolean[][] defaultWorkDaySetup;
        
        private DefaultWorkDayHolder(){
            defaultShiftSetup = new HashMap<>();
            List<Job> jobs = WorkersUtils.getShiftWorkers();
            for (Job job: jobs) {
                int[] arr = {1, 1, 1, 1, 1, 1};
                defaultShiftSetup.put(job, arr);
            }
            
            defaultWorkDaySetup = new boolean[7][2];
            for (int i = 0; i < 5; i++){
                defaultWorkDaySetup[i][0] = true;
                defaultWorkDaySetup[i][1] = true;
            }
            defaultWorkDaySetup[5][0] = true;
            defaultWorkDaySetup[5][1] = false;
            defaultWorkDaySetup[6][0] = false;
            defaultWorkDaySetup[6][1] = false;
        }


        private void setDefaultJobInShift(int dayOfTheWeek, ShiftType shiftType, Job job, int amount) throws InnerLogicException {
            if (job == Job.Shift_Manager){
                throw new InnerLogicException("Cannot change the default amount of shift manager role");
            }
            if (amount < 0)
                throw new InnerLogicException("cannot have a negative amount of workers");
            int shiftKind = getShiftKind(dayOfTheWeek, shiftType);
            int[] defaults = defaultShiftSetup.get(job);
            if (defaults == null){
                throw new InnerLogicException("There's no a default number workers for job: " + job);
            }
            defaults[shiftKind] = amount;
        }

        private int getDefaultJobInShift(Job role, int dayOfTheWeek, ShiftType shiftType) throws InnerLogicException {
            int shiftKind = getShiftKind(dayOfTheWeek, shiftType);
            int[] defaults = defaultShiftSetup.get(role);
            if (defaults == null){
                throw new InnerLogicException("There's no a default number workers for job: " + role);
            }
            return defaults[shiftKind];
        }

        private void setDefaultShiftInDay(int dayOfTheWeek, ShiftType shiftType, boolean changeTo) throws InnerLogicException {
            if (dayOfTheWeek < 1 || dayOfTheWeek > 7)
                throw new InnerLogicException("day must be between 1 to 7");
            int numShiftType = 0;
            if(shiftType == ShiftType.Evening) numShiftType = 1;
            defaultWorkDaySetup[dayOfTheWeek-1][numShiftType] = changeTo;
        }

        private boolean getDefaultShiftInDay(int dayOfTheWeek, ShiftType shiftType) throws InnerLogicException {
            int numShiftType = 0;
            if(shiftType == ShiftType.Evening) numShiftType = 1;
            return defaultWorkDaySetup[dayOfTheWeek-1][numShiftType];
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
    }
}
