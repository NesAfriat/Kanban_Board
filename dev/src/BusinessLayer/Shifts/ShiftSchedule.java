package BusinessLayer.Shifts;

import BusinessLayer.InnerLogicException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ShiftSchedule {
    private Map<String, WorkDay> workDays;

    public ShiftSchedule(){
        workDays = new HashMap<>();

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
}
