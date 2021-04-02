package BusinessLayer.Controllers;

import BusinessLayer.Shifts.ShiftType;
import BusinessLayer.Workers.Constraint;
import BusinessLayer.Workers.ConstraintType;
import BusinessLayer.Workers.Worker;
import BusinessLayer.Workers.WorkersList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class WorkerController {
    private Worker loggedIn;
    private WorkersList workersList;

    public WorkerController() {
        this.workersList = new WorkersList();
        //this is for testing TODO: remove this.
        try {
            workersList.addWorker(true, "tsuri", "123", null, 123, null, 1, 1, null);
            workersList.addWorker(false, "dan", "321", null, 123, null, 1, 1, null);

        } catch (Exception e) {
        }
        //TODO: remove until here
    }

    public WorkersList getWorkersList() {
        return this.workersList;
    }

    public Worker login(String id) throws Exception {
        if (loggedIn != null) throw new Exception("tried to login while another user is already logged in");
        loggedIn = workersList.getWorker(id); // if the id isn't belong to any user this line will throw the right exception.
        return loggedIn;
    }

    public void addWorker(boolean isAdmin, String name, String id, String bankAccount, double salary, String educationFund,
                          int vacationDaysPerMonth, int sickDaysPerMonth, String startWorkingDate) throws Exception {

        if (loggedIn == null) throw new Exception("cant add new worker to the system because no worker is logged in");
        if (!loggedIn.getIsAdmin())
            throw new Exception("cant add new worker to the system because loggedIn is not admin");
        if (workersList.contains(id)) throw new Exception("the system already has worker with the ID: " + id);
        workersList.addWorker(isAdmin, name, id, bankAccount, salary, educationFund, vacationDaysPerMonth,
                sickDaysPerMonth, startWorkingDate);
    }

    public Constraint addConstraint(String date, String shiftType, String constraintType) throws Exception {
        ShiftType st;
        ConstraintType ct;

        if (shiftType.equals("Morning")) st = ShiftType.Morning;
        else if (shiftType.equals("Evening")) st = ShiftType.Evening;
        else throw new Exception("invalid shift type");

        if (constraintType.equals("Cant")) ct = ConstraintType.Cant;
        else if (constraintType.equals("Want")) ct = ConstraintType.Want;
        else throw new Exception("invalid constraint type");
        String result;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate localDate = LocalDate.parse(date, formatter);
            result = localDate.format(formatter);
        } catch (DateTimeParseException e) {
            throw new Exception("invalid date");
        }
        if (!result.equals(date)) throw new Exception("invalid date");

        return loggedIn.addConstraint(date, st, ct);
    }
}



//     private void isValidDateFormat(String date) throws Exception {
//        String dateParts[] = date.split("/");
//        if(dateParts[0].length() != 2) throw new Exception("invalid Date");
//        if(dateParts[1].length() != 2) throw new Exception("invalid Date");
//        if(dateParts[2].length() != 4) throw new Exception("invalid Date");
//        isOnlyNumbers(dateParts[])
//     }
//
//     private boolean isOnlyNumbers(String str){
//        for(int i = 0; i < str.length(); i++){
//            if(str.charAt(i) < '0' || str.charAt(i) > '9') return false;
//        }
//     }
//}
