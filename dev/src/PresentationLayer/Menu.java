package PresentationLayer;
import BusinessLayer.Facade;
import BusinessLayer.Responses.*;
import BusinessLayer.Workers.Worker;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;


public class Menu {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    private static final Scanner scanner = new Scanner(System.in);;
    private static final Facade facade = new Facade();

    public static void main(String[] args){
        System.out.println("Enter ID number for login: ");
        String ID = scanner.next();
        ResponseT<WorkerResponse> worker = facade.login(ID);
        if (worker.ErrorOccurred()){
            System.out.println(ANSI_RED + worker.getErrorMessage() + ANSI_RESET);
            System.exit(0);
        }
        printPrettyConfirm("Hello, "+worker.value.getName()+"!");
        if (args != null) {
            facade.addConstraint("01/02/2020", "Morning", "Cant");
            facade.addConstraint("01/02/2020", "Evening", "Cant");
            facade.addConstraint("24/04/2020", "Morning", "Want");
            facade.addConstraint("01/04/2020", "Morning", "Want");
        }
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
                viewShiftArrangement();
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

    private static void viewShiftAsAdmin(){
        viewShiftArrangement();
        AdminMenu();
    }

    private static void viewShiftAsWorker(){
        viewShiftArrangement();
        WorkerMenu();
    }
    private static void viewShiftArrangement() {
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
                viewShiftArrangement();
                break;
            case 2:
                WorkersManageMenu();
                break;
            case 3:
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

    private static void WorkersManageMenu() {
        System.out.println("1) Add Worker");
        System.out.println("2) Fire Worker");
        System.out.println("3) Get Worker");
        System.out.println("4) Previous");
        System.out.print("Option: ");
        int option = getUserInput();
        switch (option){
            case 1:
                AddWorker();
                break;
            case 2:
                FireWorker();
                break;
            case 3:
                GetWorker();
                break;
            case 4:
                AdminMenu();
            case 5:
                System.exit(0);
            default:
                System.out.println("No such option");
                AdminMenu();
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
        WorkersManageMenu();
    }

    //TODO
    private static void FireWorker(){

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
            printPrettyConfirm("Worker added successfully to the system, details: ");
            printPrettyConfirm(workerResponse.value.toString());
        }
        WorkerMenu();
    }

    private static void printPrettyConfirm(String message) {
        System.out.println(ANSI_BLUE + message + ANSI_RESET);
    }

    private static void printPrettyError(String errorMessage) {
        System.out.println(ANSI_RED + errorMessage + ANSI_RESET);
    }
}
