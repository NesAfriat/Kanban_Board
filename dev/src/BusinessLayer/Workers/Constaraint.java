package BusinessLayer.Workers;
import BusinessLayer.Shifts.ShiftType;

import java.util.Date;

public class Constaraint {
    private Date date;
    private ShiftType shiftType;
    private ConstraintType constraintType;

    public Constaraint(Date date, ShiftType shiftType, ConstraintType constraintType) {
        this.date = date;
        this.shiftType =shiftType;
        this.constraintType = constraintType;
    }

    public boolean compareShift(Date date, ShiftType shiftType){
        return (this.date.getYear() == date.getYear() && this.date.getMonth() == date.getMonth() &&this.date.getDay() == date.getDay() && this.shiftType == shiftType);
    }

    public ConstraintType getConstraintType() {
        return constraintType;
    }
}
