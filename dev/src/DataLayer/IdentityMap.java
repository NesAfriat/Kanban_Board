package DataLayer;

import BusinessLayer.Workers_BusinessLayer.Shifts.WorkDay;
import BusinessLayer.Workers_BusinessLayer.Workers.Worker;

import java.util.HashMap;
import java.util.Map;

public class IdentityMap {
    private Map<String, Worker> workerMap;
    private Map<String, WorkDay> workDayMap;
    public IdentityMap(){
        this.workDayMap = new HashMap<>();
        this.workerMap = new HashMap<>();
    }
}
