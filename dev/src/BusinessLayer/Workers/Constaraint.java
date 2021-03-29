package BusinessLayer.Workers;
import BusinessLayer.Shifts.ShiftType;

import java.util.Date;

public class Constaraint {
    private String date;
    private ShiftType shiftType;
    private ConstraintType constraintType;

    public Constaraint(String date, ShiftType shiftType, ConstraintType constraintType) {
        this.date = date;
        this.shiftType =shiftType;
        this.constraintType = constraintType;
    }

    public boolean compareShift(String date, ShiftType shiftType){
        return (date.equals(date) && this.shiftType == shiftType);
    }

    public ConstraintType getConstraintType() {
        return constraintType;
    }
}
