package PresentationLayer;
import BusinessLayer.Facade;
import BusinessLayer.Responses.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;


public class Menu {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";


    private static final Scanner scanner = new Scanner(System.in);;
    private static final Facade facade = new Facade();

    private static void testingDataUpload(){
        facade.login("1");
        facade.addWorker(false, "dan", "2", "1", 1, "1", 1, 1 , "01/01/2018");
        facade.addWorker(false, "rami", "3", "1", 1, "1", 1, 1 , "01/01/2018");
        facade.addWorker(false, "lidor", "4", "1", 1, "1", 1, 1 , "01/01/2018");
        facade.addWorkDay(true, true, "01/01/2000");
        facade.addWorkDay(true, false, "02/01/2000");
        facade.addOccupationToWorker("2", "Shift_Manager");
        facade.addOccupationToWorker("3", "Cashier");
        facade.logout();
        facade.login("2");
        facade.addConstraint("01/01/2000","Morning", "Cant");
        facade.logout();
    }

    public static void main(String[] args){
        testingDataUpload();
        System.out.println("Enter ID number for login: ");
        String ID = scanner.next();
        ResponseT<WorkerResponse> worker = facade.login(ID);
        if (worker.ErrorOccurred()){
            System.out.println(ANSI_RED + worker.getErrorMessage() + ANSI_RESET);
            System.exit(0);
        }
        printPrettyConfirm("Hello, "+worker.value.getName()+"!");
        if (worker.value.getIsAdmin()){
            AdminMenu();
        }
        else {
            WorkerMenu();
        }
    }

    private static void WorkerMenu() {
        System.out.println("1) View shift arrangement");
        System.out.println("2) View constraints");
        System.out.println("3) Add constraint");
        System.out.println("4) Remove constraint");
        System.out.println("5) Logout");
        System.out.println("6) Exit");
        System.out.print("Option: ");
        int option = getUserInput();
        switch (option){
            case 1:
                nonAdminViewShiftArrangement();
                WorkerMenu();
                break;
            case 2:
                viewWorkerConstraints();
                break;
            case 3:
                addConstraint();
                break;
            case 4:
                removeConstraint();
                break;
            case 5:
                LogOut();
                break;
            case 6:
                System.exit(0);
            default:
                System.out.println("No such option");
                WorkerMenu();
        }

    }

    private static void LogOut() {
        Response logout = facade.logout();
        if (logout.ErrorOccurred())
            printPrettyError(logout.getErrorMessage());
        else {
            printPrettyConfirm("Logout succeed");
        }
        main(null);
    }


    private static void adminViewShiftArrangement() {
        System.out.println("Enter Date <DD/MM/YYYY>: ");
        String date = scanner.next();
        ResponseT<WorkDayResponse> workDay = facade.viewShiftArrangement(date);
        if (workDay.ErrorOccurred()){
            printPrettyError(workDay.getErrorMessage());
        }
        else {
            printPrettyConfirm(workDay.value.toString());
        }
    }

    private static void nonAdminViewShiftArrangement() {
        System.out.println("Enter Date <DD/MM/YYYY>: ");
        String date = scanner.next();
        ResponseT<WorkDayResponse> workDay = facade.viewShiftArrangement(date);
        if (workDay.ErrorOccurred()){
            printPrettyError(workDay.getErrorMessage());
        }
        else {
            printPrettyConfirm(workDay.value.approvedToString());
        }
    }

    private static int getUserInput() {
        int option = 0;
        try {
            option = scanner.nextInt();
        }
        catch (InputMismatchException e){
            getUserInput();
        }
        return option;
    }

    private static void removeConstraint() {
        System.out.println("Enter Date <DD/MM/YYYY>: ");
        String date = scanner.next();
        System.out.println("Enter Shift (Morning/Evening): ");
        String shiftType = scanner.next();
        ResponseT<ConstraintResponse> constraint = facade.removeConstraint(date, shiftType);
        if (constraint.ErrorOccurred()){
            printPrettyError(constraint.getErrorMessage());
        }
        else {
            printPrettyConfirm("Constraint removed successfully, details: ");
            printPrettyConfirm(constraint.value.toString());
        }
        WorkerMenu();
    }



    private static void addConstraint() {
        System.out.println("Enter Date <DD/MM/YYYY>: ");
        String date = scanner.next();
        System.out.println("Enter Shift (Morning/Evening): ");
        String shiftType = scanner.next();
        System.out.println("Enter Constraint Type (Cant/Want): ");
        String constraintType = scanner.next();
        ResponseT<ConstraintResponse> constraint = facade.addConstraint(date, shiftType, constraintType);
        if (constraint.ErrorOccurred()){
            printPrettyError(constraint.getErrorMessage());
        }
        else {
            printPrettyConfirm("Constraint added successfully, details: ");
            printPrettyConfirm(constraint.value.toString());
        }
        WorkerMenu();
    }



    private static void viewWorkerConstraints() {
        ResponseT<WorkerResponse> worker = facade.getLoggedWorker();
        if (worker.ErrorOccurred()){
            printPrettyError(worker.getErrorMessage());
        }
        else {
            List<ConstraintResponse> constraintResponseList = worker.value.getConstraints();
            for (ConstraintResponse constraint: constraintResponseList){
                printPrettyConfirm(constraint.toString());
            }
        }
        WorkerMenu();
        
    }

    private static void AdminMenu() {
        System.out.println("1) View shift arrangement");
        System.out.println("2) Manage Workers");
        System.out.println("3) Manage Shifts");
        System.out.println("4) Logout");
        System.out.println("5) Exit");
        System.out.print("Option: ");
        int option = getUserInput();
        switch (option){
            case 1:
                adminViewShiftArrangement();
                AdminMenu();
                break;
            case 2:
                WorkersManageMenu();
                break;
            case 3:
                ShiftsManageMenu();
                break;
            case 4:
                LogOut();
                break;
            case 5:
                System.exit(0);
            default:
                System.out.println("No such option");
                AdminMenu();
        }
    }

    private static void ShiftsManageMenu() {
        System.out.println("1) Edit shift");
        System.out.println("2) Remove shift");
        System.out.println("3) Add shifts");
        System.out.println("4) Previous");
        System.out.println("5) Exit");
        System.out.print("Option: ");
        int option = getUserInput();
        switch (option){
            case 1:
                EditShift();
                break;
            case 2:
                RemoveShift();
                break;
            case 3:
                AddShifts();
                break;
            case 4:
                AdminMenu();
            case 5:
                System.exit(0);
            default:
                System.out.println("No such option");
                ShiftsManageMenu();
        }
    }

    private static void AddShifts() {
        throw new NotImplementedException();
    }

    private static void RemoveShift() {
        throw new NotImplementedException();
    }

    private static void EditShiftMenu() {
        System.out.println("1) View current arrangement");
        System.out.println("2) Add worker to shift");
        System.out.println("3) Remove worker from shift");
        System.out.println("4) Add required job");
        System.out.println("5) Edit job required amount");
        System.out.println("6) Get available workers");
        System.out.println("7) Approve shift");
        System.out.println("8) Previous");
        System.out.print("Option: ");
        int option = getUserInput();
        switch (option){
            case 1:
                viewCurrentArrangement();
                EditShiftMenu();
                break;
            case 2:
                assignWorker();
                EditShiftMenu();
                break;
            case 3:
                removeWorker();
                EditShiftMenu();
                break;
            case 4:
                AddRequiredJob();
                EditShiftMenu();
                break;
            case 5:
                SetRequiredAmount();
                EditShiftMenu();
            case 6:
                GetAvailableWorkers();
                EditShiftMenu();
            case 7:
                ApproveShift();
                EditShiftMenu();
            case 8:
                ExitEditShiftMenu();
                ShiftsManageMenu();
                break;
            default:
                System.out.println("No such option");
                EditShiftMenu();
        }
    }

    private static void ApproveShift() {
        ResponseT<ShiftResponse> shiftResponse = facade.approveShift();
        if (shiftResponse.ErrorOccurred()){
            printPrettyError(shiftResponse.getErrorMessage());
        }
        else{
            printPrettyConfirm("Shift approved successfully");;
        }
    }

    private static void GetAvailableWorkers() {
    }

    private static void SetRequiredAmount() {
    }

    private static void ExitEditShiftMenu() {
        Response response = facade.exitShift();
        if (response.ErrorOccurred())
            printPrettyError(response.getErrorMessage());
        else {
            printPrettyConfirm("Exited from shift successfully");
        }
    }

    private static void AddRequiredJob() {
        throw new NotImplementedException();
    }

    private static void removeWorker() {
        System.out.print("Worker ID: ");
        String ID = scanner.next();
        System.out.print("Worker role: ");
        String role = scanner.next();
        ResponseT<ShiftResponse> shiftResponse = facade.removeWorkerFromCurrentShift(ID, role);
        if (shiftResponse.ErrorOccurred()){
            printPrettyError(shiftResponse.getErrorMessage());
        }
        else {
            printPrettyConfirm("Worker removed successfully from the shift.");
        }
    }

    private static void viewCurrentArrangement() {
        ResponseT<ShiftResponse> shiftResponse = facade.viewCurrentArrangement();
        if (shiftResponse.ErrorOccurred()){
            printPrettyError(shiftResponse.getErrorMessage());
        }
        else {
            printPrettyConfirm(shiftResponse.value.toString());
        }
    }

    private static void assignWorker() {
        System.out.print("Worker ID: ");
        String ID = scanner.next();
        System.out.print("Worker role: ");
        String role = scanner.next();
        ResponseT<ShiftResponse> shiftResponse = facade.addWorkerToCurrentShift(ID, role);
        if (shiftResponse.ErrorOccurred()){
            printPrettyError(shiftResponse.getErrorMessage());
        }
        else {
            printPrettyConfirm("Worker added successfully");
        }
    }

    private static void EditShift() {
        System.out.println("Enter Date <DD/MM/YYYY>: ");
        String date = scanner.next();
        System.out.println("Enter Shift (Morning/Evening): ");
        String shiftType = scanner.next();
        ResponseT<ShiftResponse> shiftResponse = facade.chooseShift(date, shiftType);
        if (shiftResponse.ErrorOccurred()){
            printPrettyError(shiftResponse.getErrorMessage());
            ShiftsManageMenu();
        }
        else {
            printPrettyConfirm("Shift selected successfully");
            EditShiftMenu();
        }
    }



    private static void WorkersManageMenu() {
        System.out.println("1) Add worker");
        System.out.println("2) Fire worker");
        System.out.println("3) Get worker details");
        System.out.println("4) Add worker occupation");
        System.out.println("5) Remove worker occupation");
        System.out.println("6) View worker constraints");
        System.out.println("7) Previous");
        System.out.print("Option: ");
        int option = getUserInput();
        switch (option){
            case 1:
                AddWorker();
                WorkersManageMenu();
                break;
            case 2:
                FireWorker();
                WorkersManageMenu();
                break;
            case 3:
                GetWorker();
                WorkersManageMenu();
                break;
            case 4:
                AddWorkerOccupation();
                WorkersManageMenu();
            case 5:
                RemoveWorkerOccupation();
                WorkersManageMenu();
            case 6:
                ViewWorkerConstraints();
                WorkersManageMenu();
            case 7:
                AdminMenu();

            default:
                System.out.println("No such option");
                WorkersManageMenu();
        }

    }

    private static void ViewWorkerConstraints() {
        System.out.print("Worker ID: ");
        String ID = scanner.next();
        ResponseT<WorkerResponse> worker = facade.getWorker(ID);
        if (worker.ErrorOccurred()){
            printPrettyError(worker.getErrorMessage());
        }
        else {
            printPrettyConfirm(worker.value.getNameID() +" Constraints: \n");
            List<ConstraintResponse> constraintResponseList = worker.value.getConstraints();
            for (ConstraintResponse constraint: constraintResponseList){
                printPrettyConfirm(constraint.toString());
            }
        }
    }

    private static void RemoveWorkerOccupation() {
        System.out.print("Worker ID: ");
        String ID = scanner.next();
        System.out.print("Job title: ");
        String job = scanner.next();
        ResponseT<WorkerResponse> workerResponse = facade.removeOccupationToWorker(ID, job);
        if (workerResponse.ErrorOccurred())
            printPrettyError(workerResponse.getErrorMessage());
        else{
            printPrettyConfirm("Removed role " + "("+job+")" + " from " + workerResponse.value.getNameID() + " successfully");
        }
    }

    private static void AddWorkerOccupation() {
        System.out.print("Worker ID: ");
        String ID = scanner.next();
        System.out.print("Job title: ");
        String job = scanner.next();
        ResponseT<WorkerResponse> workerResponse = facade.addOccupationToWorker(ID, job);
        if (workerResponse.ErrorOccurred())
            printPrettyError(workerResponse.getErrorMessage());
        else{
            printPrettyConfirm("Added new role " + "("+job+")" + " for " + workerResponse.value.getNameID() + " successfully");
        }
    }

    private static void GetWorker() {
        System.out.print("Worker ID: ");
        String ID = scanner.next();
        ResponseT<WorkerResponse> workerResponse = facade.getWorker(ID);
        if (workerResponse.ErrorOccurred()) {
            printPrettyError(workerResponse.getErrorMessage());
        } else {
            printPrettyConfirm(workerResponse.value.toString());
        }
    }

    private static void FireWorker(){
        System.out.print("Worker ID: ");
        String ID = scanner.next();
        System.out.print("Enter end working date <DD/MM/YYYY>: ");
        String date = scanner.next();
        ResponseT<WorkerResponse> workerResponse = facade.fireWorker(ID, date);
        if (workerResponse.ErrorOccurred()){
            printPrettyError(workerResponse.getErrorMessage());
        }
        else {
            printPrettyConfirm("Worker fired successfully, details: " + workerResponse.value.getNameID());
        }
    }


    private static void AddWorker() {
        System.out.println("ID: ");
        String ID = scanner.next();
        System.out.println("Name:");
        String name = scanner.next();
        System.out.println("Bank Account:");
        String bankAccount = scanner.next();
        System.out.println("Salary: ");
        double salary = scanner.nextDouble();
        System.out.println("Education Fund:");
        String educationFund = scanner.next();
        System.out.println("Vacation Days Per Month:");
        int vacationDaysPerMonth = scanner.nextInt();
        System.out.println("Sick Days Per Month:");
        int sickDaysPerMonth = scanner.nextInt();
        System.out.println("Enter Start Working Date <DD/MM/YYYY>: ");
        String date = scanner.next();
        ResponseT<WorkerResponse> workerResponse = facade.addWorker(false, name, ID, bankAccount, salary, educationFund, vacationDaysPerMonth, sickDaysPerMonth, date);
        if (workerResponse.ErrorOccurred()){
            printPrettyError(workerResponse.getErrorMessage());
        }
        else {
            printPrettyConfirm("Worker added successfully to the system, details: " + workerResponse.value.getNameID());
        }
    }

    private static void printPrettyConfirm(String message) {
        System.out.println(ANSI_BLUE + message + ANSI_RESET);
    }

    private static void printPrettyError(String errorMessage) {
        System.out.println(ANSI_RED + errorMessage + ANSI_RESET);
    }
}
