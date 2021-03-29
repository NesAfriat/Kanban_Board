package BusinessLayer.Controllers;

import BusinessLayer.Shifts.ShiftSchedule;
import BusinessLayer.Shifts.ShiftType;
import BusinessLayer.Shifts.WorkDay;
import BusinessLayer.Workers.WorkersList;

public class ShiftController {
    private ShiftSchedule calendar;
    private WorkDay currentDay;
    private ShiftType currentShiftType;
    private WorkersList workers;

    public ShiftController(WorkersList workers){
        this.workers = workers;
        calendar = new ShiftSchedule();
    }

    public void setWorkDay(String date) throws Exception {
        currentDay = calendar.getWorkDay(date);
    }

    public void setCurrentShiftType(String shiftType){
        if (shiftType.equals("Morning"))
            currentShiftType = ShiftType.Morning;
        else
            currentShiftType = ShiftType.Evening;
    }


}
