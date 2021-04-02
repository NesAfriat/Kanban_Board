package BusinessLayer.Responses;

import BusinessLayer.InnerLogicException;
import BusinessLayer.Shifts.ShiftType;
import BusinessLayer.Shifts.WorkDay;

public class WorkDayResponse {

    private ShiftResponse morningShift;
    private ShiftResponse eveningShift;
    private String date;

    public WorkDayResponse(WorkDay workDay){
        try {
            this.morningShift = new ShiftResponse(workDay.getCurrentShift(ShiftType.Morning));
        } catch (InnerLogicException e) {
            this.morningShift = null;
        }
        try {
            this.eveningShift = new ShiftResponse(workDay.getCurrentShift(ShiftType.Evening));
        } catch (InnerLogicException e) {
            this.eveningShift = null;
        }
        this.date = workDay.getDate();
    }

    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Date: ").append(date).append("\n");
        if (morningShift != null)
            stringBuilder.append("Morning shift details:\n").append(morningShift.toString());
        if (eveningShift != null)
            stringBuilder.append("Evening shift details:\n").append(eveningShift.toString());
        return stringBuilder.toString();
    }
}
