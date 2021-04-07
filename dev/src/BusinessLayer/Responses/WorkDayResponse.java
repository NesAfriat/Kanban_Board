package BusinessLayer.Responses;

import BusinessLayer.InnerLogicException;
import BusinessLayer.Shifts.ShiftType;
import BusinessLayer.Shifts.WorkDay;

public class WorkDayResponse {

    private ShiftResponse morningShift;
    private ShiftResponse eveningShift;
    private String date;

    public WorkDayResponse(WorkDay workDay){

        if (workDay.getShift(ShiftType.Morning) != null){
             this.morningShift = new ShiftResponse(workDay.getShift(ShiftType.Morning));
        }
        else {
            morningShift = null;
        }
        if (workDay.getShift(ShiftType.Evening) != null){
            this.eveningShift = new ShiftResponse(workDay.getShift(ShiftType.Evening));
        }
        else {
            eveningShift = null;
        }

        this.date = workDay.getDate();
    }

    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Date: ").append(date).append("\n");
        if (morningShift == null && eveningShift == null){
            stringBuilder.append("There's no shifts at this work day\n");
        }
        if (morningShift != null)
            stringBuilder.append("Morning shift details:\n").append(morningShift.toString());
        if (eveningShift != null)
            stringBuilder.append("\nEvening shift details:\n").append(eveningShift.toString());
        return stringBuilder.toString();
    }

    public String approvedToString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Date: ").append(date).append("\n");
        if (morningShift == null && eveningShift == null){
            stringBuilder.append("There's no shifts at this work day\n");
        }
        if (morningShift != null)
            if(morningShift.isApproved()){
                stringBuilder.append("Morning shift details:\n").append(morningShift.toString());
            }else{
                stringBuilder.append("Morning shift is not yet approved\n");
            }

        if (eveningShift != null)
            if(eveningShift.isApproved()){
                stringBuilder.append("\nEvening shift details:\n").append(eveningShift.toString());
            }else{
                stringBuilder.append("Evening shift is not yet approved\n");
            }
        return stringBuilder.toString();
    }

    public String getDate() {
        return date;
    }

    public String Settings() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Date: ").append(date).append("\n");
        if (morningShift == null && eveningShift == null){
            stringBuilder.append("There's no shifts at this work day\n");
        }
        if (morningShift != null)
            stringBuilder.append(morningShift.Settings());

        if (eveningShift != null)
            stringBuilder.append(eveningShift.Settings());
        return stringBuilder.toString();
    }
}
