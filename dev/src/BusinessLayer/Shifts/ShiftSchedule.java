package BusinessLayer.Shifts;

import BusinessLayer.InnerLogicException;

import java.util.HashMap;
import java.util.Map;

public class ShiftSchedule {
    private Map<String, WorkDay> workDays;

    public ShiftSchedule(){
        workDays = new HashMap<>();
    }

    public void addWorkDay(boolean hasMorningShift, boolean hasEveningShift, String date) throws InnerLogicException {
        if (workDays.get(date) != null){
            throw new InnerLogicException("There's already a WorkDay at date "+ date);
        }
        WorkDay workDay = new WorkDay(hasMorningShift,hasEveningShift,date);
        workDays.put(date,workDay);
    }

    public WorkDay getWorkDay(String date) throws InnerLogicException {
        WorkDay workDay = workDays.get(date);
        if (workDay == null){
            throw new InnerLogicException("There's no WorkDay at date: " + date);
        }
        return workDay;
    }

}
