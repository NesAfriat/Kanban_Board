package DataLayer.Workers_DAL;

import BusinessLayer.Workers_BusinessLayer.Shifts.WorkDay;
import BusinessLayer.Workers_BusinessLayer.Workers.Worker;

import java.util.HashMap;
import java.util.Map;

class IdentityMap {
    private static IdentityMap instance = null;

    public static IdentityMap getInstance() {
        if (instance == null){
            instance = new IdentityMap();
        }
        return instance;
    }

    private Map<String, Worker> workerMap;
    private Map<String, WorkDay> workDayMap;
    private IdentityMap(){
        this.workDayMap = new HashMap<>();
        this.workerMap = new HashMap<>();
    }

    public void addWorkDay(WorkDay workDay) {
        workDayMap.put(workDay.getDate(),workDay);
    }
}
