package BusinessLayer.Shifts;

import java.util.HashMap;
import java.util.Map;

public class ShiftSchedule {
    private Map<String, WorkDay> workDays;

    public ShiftSchedule(){
        workDays = new HashMap<>();
    }

    public void addWorkDay(boolean hasMorningShift, boolean hasEveningShift, String date) throws Exception {
        if (workDays.get(date) != null){
            throw new Exception("There's already a WorkDay at date "+ date);
        }
        WorkDay workDay = new WorkDay(hasMorningShift,hasEveningShift,date);
        workDays.put(date,workDay);
    }
    public WorkDay getWorkDay(String date) throws Exception {
        WorkDay workDay = workDays.get(date);
        if (workDay == null){
            throw new Exception("There's no WorkDay at date: " + date);
        }
        return workDay;
    }
}
