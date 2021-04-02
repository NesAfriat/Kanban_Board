package PresentationLayer;
import BusinessLayer.Facade;
import BusinessLayer.Responses.ConstraintResponse;
import BusinessLayer.Responses.ResponseT;
import BusinessLayer.Responses.WorkerResponse;
import BusinessLayer.Shifts.WorkDay;

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
        String ID = scanner.nextLine();
        ResponseT<WorkerResponse> worker = facade.login(ID);
        if (worker.ErrorOccurred()){
            System.out.println(ANSI_RED + worker.getErrorMessage() + ANSI_RESET);
            System.exit(0);
        }
        printPrettyConfirm("Hello, "+worker.value.getName()+"!");
        facade.addConstraint("01/02/2020", "Morning", "Cant");
        facade.addConstraint("01/02/2020", "Evening", "Cant");
        facade.addConstraint("24/04/2020", "Morning", "Want");
        facade.addConstraint("01/04/2020", "Morning", "Want");
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
        System.out.println("5) Exit");
        System.out.print("Option: ");
        int option = getUserInput();
        switch (option){
            case 1:
                viewShiftArrangement();
                break;;
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
                System.exit(0);
            default:
                System.out.println("No such option");
                WorkerMenu();
        }

    }

    private static void viewShiftArrangement() {
        System.out.println("Enter Date <DD/MM/YYYY>: ");
        String date = scanner.next();
        ResponseT<WorkDayResponse> workDay = facade.viewShiftArrangement(date);

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

    }

    private static void printPrettyConfirm(String message) {
        System.out.println(ANSI_BLUE + message + ANSI_RESET);
    }

    private static void printPrettyError(String errorMessage) {
        System.out.println(ANSI_RED + errorMessage + ANSI_RESET);
    }
}
