package Tests;
import BusinessLayer.InnerLogicException;
import BusinessLayer.Shifts.ShiftType;
import BusinessLayer.Workers.Constraint;
import BusinessLayer.Workers.ConstraintType;
import BusinessLayer.Workers.Job;
import BusinessLayer.Workers.Worker;
import BusinessLayer.WorkersUtils;
import org.junit.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class WorkerUnitTests {
    private static Worker worker;

    static {

    }

    private static DateTimeFormatter formatter;
    private static final int LEGAL_DAYS_RANGE = 14;


    @BeforeClass
    public static void initFields() {
        formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    }

    @Before
    public void initTest(){
        try {
            worker = new Worker(false, "rami", "333333333", "1", 1, "1", 1, 1, "01/01/2018");
        } catch (InnerLogicException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addConstraint_FutureShift_InLegalRange_Allow(){
        //arrange
        LocalDate now = LocalDate.now();
        String date = (now.plusDays(LEGAL_DAYS_RANGE+1)).format(formatter);

        //act
        try {
            worker.addConstraint(date, ShiftType.Morning, ConstraintType.Cant);
        }
        catch (InnerLogicException e){
            Assert.fail(e.getMessage());
        }

        //assert
        Assert.assertFalse(worker.canWorkInShift(date, ShiftType.Morning));
    }

    @Test
    public void addConstraint_FutureShift_LegalRange_SameShift_DifferentConstraint_Fail(){
        //arrange
        LocalDate now = LocalDate.now();
        String date = (now.plusDays(LEGAL_DAYS_RANGE+1)).format(formatter);

        //act
        try {
            worker.addConstraint(date, ShiftType.Morning, ConstraintType.Cant);
        }
        catch (InnerLogicException e){
            Assert.fail("Should allow to add one constraint");
        }
        try {
            worker.addConstraint(date, ShiftType.Morning, ConstraintType.Cant);
            Assert.fail("Should have not allowed to add two constraints to the same shift.");
        }
        catch (InnerLogicException e){
            Assert.assertTrue(true);
        }
    }

    @Test
    public void addConstraint_ToFutureShift_Before2Weeks_Fail(){
        //arrange
        LocalDate now = LocalDate.now();
        String date = (now.plusDays(LEGAL_DAYS_RANGE)).format(formatter);

        //act
        try {
            worker.addConstraint(date, ShiftType.Morning, ConstraintType.Cant);
            Assert.fail("Should have not allowed to add a constraint to a shift happening in less than " + LEGAL_DAYS_RANGE +" days");
        }
        catch (InnerLogicException e){
            Assert.assertTrue(true);
        }
    }


    @Test
    public void addConstraint_ShiftInThePast_Fail(){
        //arrange

        //act

        //assert
    }


}
