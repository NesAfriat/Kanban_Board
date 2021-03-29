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
}
