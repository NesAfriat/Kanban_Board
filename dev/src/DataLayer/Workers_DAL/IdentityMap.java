package DataLayer.Workers_DAL;

import BusinessLayer.Workers_BusinessLayer.Shifts.WorkDay;
import BusinessLayer.Workers_BusinessLayer.Workers.Worker;

import java.util.HashMap;
import java.util.Map;

//TODO: make as singleton
public class IdentityMap {
    private Map<String, Worker> workerMap;
    private Map<String, WorkDay> workDayMap;
    public IdentityMap(){
        this.workDayMap = new HashMap<>();
        this.workerMap = new HashMap<>();
    }

    public void addWorkDay(WorkDay workDay) {
        workDayMap.put(workDay.getDate(),workDay);
    }
}
