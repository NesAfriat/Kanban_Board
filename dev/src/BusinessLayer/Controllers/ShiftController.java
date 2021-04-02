package BusinessLayer.Controllers;

import BusinessLayer.InnerLogicException;
import BusinessLayer.Shifts.ShiftSchedule;
import BusinessLayer.Shifts.ShiftType;
import BusinessLayer.Shifts.WorkDay;
import BusinessLayer.Workers.WorkersList;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ShiftController {
    private ShiftSchedule calendar;
    private WorkDay currentDay;
    private ShiftType currentShiftType;
    private WorkersList workers;
    private boolean isAdminAuthoried;

    public ShiftController(WorkersList workers){
        this.workers = workers;
        calendar = new ShiftSchedule();
        isAdminAuthoried = false;
    }

    public void login(boolean isAdminAuthoried){
        this.isAdminAuthoried = isAdminAuthoried;
    }

    public void logout(){
        this.isAdminAuthoried = false;
    }

    public void setCurrentDay(String date) throws Exception {
        currentDay = calendar.getWorkDay(date);
    }

    public WorkDay getWorkDay(String date) throws InnerLogicException {
        dateValidation(date);
        return calendar.getWorkDay(date);
    }

    public void setCurrentShiftType(String shiftType){
        if (shiftType.equals("Morning"))
            currentShiftType = ShiftType.Morning;
        else
            currentShiftType = ShiftType.Evening;
    }

    private void dateValidation(String date) throws InnerLogicException {
        String result;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate localDate = LocalDate.parse(date, formatter);
            result = localDate.format(formatter);
        } catch (DateTimeParseException e) {
            throw new InnerLogicException("invalid date");
        }
        if (!result.equals(date)) throw new InnerLogicException("invalid date");
    }


}
