package Tests;
import BusinessLayer.Controllers.ShiftController;
import BusinessLayer.InnerLogicException;
import BusinessLayer.Shifts.Shift;
import BusinessLayer.Shifts.ShiftType;
import BusinessLayer.Workers.ConstraintType;
import BusinessLayer.Workers.Worker;
import PresentationLayer.Menu;
import org.junit.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class ShiftUnitTests {
    private static Shift shift;
    private static Worker worker;
    private static DateTimeFormatter formatter;


    @BeforeClass
    public static void initFields() {
        formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
    }

    @Before
    public void initTest() {
       shift = new Shift();
    }

    @Test
    public void addConstraint_FutureShift_InLegalRange_Allow() {
        //arrange


        //act


        //assert

    }

}