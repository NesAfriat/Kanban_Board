package BusinessLayer.Workers;

import BusinessLayer.Shifts.ShiftType;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class WorkersTest {
    public static void main(String Args[]){
        Worker worker = new Worker("moshe");
        worker.addOccupations(Job.Shift_Manager);
        worker.addOccupations(Job.Shift_Manager);
        try {
            worker.addConstraint("1/1/2000", ShiftType.Morning, ConstraintType.Cant);
            //worker.testPrintConstraint();
            System.out.println("Asd");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
