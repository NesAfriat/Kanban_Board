package BusinessLayer.Workers_BusinessLayer;

import BusinessLayer.Workers_BusinessLayer.Shifts.ShiftType;
import BusinessLayer.Workers_BusinessLayer.Workers.ConstraintType;
import BusinessLayer.Workers_BusinessLayer.Workers.Job;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class WorkersUtils {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static final HashMap<String, Job> jobs = new HashMap<String, Job>(){{
        put("Cashier",Job.Cashier);
        put("Storekeeper",Job.Storekeeper);
        put("Usher",Job.Usher);
        put("Guard",Job.Guard);
        put("Shift_Manager",Job.Shift_Manager);
        put("HR_Manager",Job.HR_Manager);
        put("Branch_Manager",Job.Branch_Manager);
        put("Assistant_Branch_Manager",Job.Assistant_Branch_Manager);
        put("DriverA",Job.DriverA);
        put("DriverB",Job.DriverB);
        put("DriverC",Job.DriverC);

    }};


    public static void dateValidation(String date) throws InnerLogicException {
        String result;
        try {
            LocalDate localDate = LocalDate.parse(date, formatter);
            result = localDate.format(formatter);
        } catch (DateTimeParseException e) {
            throw new InnerLogicException("invalid date");
        }
        if (!result.equals(date)) throw new InnerLogicException("invalid date");
    }

    public static int getWeekDayFromDate(String date) throws InnerLogicException {
        dateValidation(date);
        int dayOfWeek;
        try {
            LocalDate localDate = LocalDate.parse(date, formatter);
            dayOfWeek =localDate.getDayOfWeek().getValue();
            return (dayOfWeek  % 7)+ 1; // adjust to israeli numbering of the week days.
        } catch (DateTimeParseException e) {
            throw new InnerLogicException("invalid date");
        }
    }

    public static void notPastDateValidation(String date) throws InnerLogicException {
        try {
            LocalDate localDate = LocalDate.parse(date, formatter);
            if(localDate.isBefore(LocalDate.now())) throw new InnerLogicException("invalid Date (past date)");
        } catch (DateTimeParseException e) {
            throw new InnerLogicException("invalid date");
        }
    }

    //return true is the date is in <= "range" days then new
    public static boolean isDateIsInMoreThanNumDays(String date, int num) throws InnerLogicException {
        dateValidation(date);
        LocalDate localDate = LocalDate.parse(date, formatter);
        LocalDate now = LocalDate.now();
        return localDate.minusDays(num).isAfter(now);
    }

    public static boolean isInPastMonth(String date) throws InnerLogicException {
        dateValidation(date);
        LocalDate localDate = LocalDate.parse(date, formatter);
        LocalDate now = LocalDate.now();
        if(now.getYear() > localDate.getYear()) return true;
        return now.getYear() == localDate.getYear() && now.getMonthValue() > localDate.getMonthValue();
    }

    public static ShiftType parseShiftType(String shiftType) throws InnerLogicException {
        if ("Morning".equals(shiftType)) return ShiftType.Morning;
        else if ("Evening".equals(shiftType)) return ShiftType.Evening;
        else throw new InnerLogicException("invalid shift type");
    }

    public static ConstraintType parseConstraintType(String constraintType) throws InnerLogicException {
        if ("Cant".equals(constraintType)) return ConstraintType.Cant;
        else if ("Want".equals(constraintType)) return ConstraintType.Want;
        else throw new InnerLogicException("invalid constraint type");
    }

    public static Job parseJob(String job) throws InnerLogicException {
        Job role = jobs.get(job);
        if (role == null){
            throw new InnerLogicException("invalid job type");
        }
        return role;
    }

    public static List<Job> getShiftWorkers() {
        List<Job> shiftWorkers = new LinkedList<>();
        shiftWorkers.add(Job.Cashier);
        shiftWorkers.add(Job.Storekeeper);
        shiftWorkers.add(Job.Usher);
        shiftWorkers.add(Job.Guard);
        shiftWorkers.add(Job.Shift_Manager);
        shiftWorkers.add(Job.DriverA);
        shiftWorkers.add(Job.DriverB);
        shiftWorkers.add(Job.DriverC);
        return shiftWorkers;
    }

}
