package BusinessLayer.Workers;

import BusinessLayer.Shifts.ShiftType;

import java.util.Date;

public class WorkersTest {
    public static void main(String Args[]){
        Worker worker = new Worker("moshe");
        worker.addOccupations(Job.Shift_Manager);
        worker.addOccupations(Job.Shift_Manager);
        try {
            worker.addConstraint("1/1/2000", ShiftType.Morning, ConstraintType.Cant);
            //worker.testPrintConstraint();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
