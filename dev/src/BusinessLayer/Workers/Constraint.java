package BusinessLayer.Workers;
import BusinessLayer.Shifts.ShiftType;


public class Constraint {
    private String date;
    private ShiftType shiftType;
    private ConstraintType constraintType;

    public Constraint(String date, ShiftType shiftType, ConstraintType constraintType) {
        this.date = date;
        this.shiftType =shiftType;
        this.constraintType = constraintType;
    }

    public Constraint(Constraint toCopy) {
        this.date = toCopy.date;
        this.shiftType = toCopy.shiftType;
        this.constraintType = toCopy.constraintType;
    }

    public boolean compareShift(String date, ShiftType shiftType){
        return (date.equals(date) && this.shiftType == shiftType);
    }

    public ConstraintType getConstraintType() {
        return constraintType;
    }

    public String getDate() {
        return date;
    }

    public ShiftType getShiftType() {
        return shiftType;
    }
}
