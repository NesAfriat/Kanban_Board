package PresentationLayer;
import BusinessLayer.Facade;
import BusinessLayer.Responses.ConstraintResponse;
import BusinessLayer.Responses.ResponseT;
import BusinessLayer.Responses.WorkerResponse;

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
        System.out.println("Hello "+worker.value.getName()+".");
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
        int option = scanner.nextInt();
        switch (option){
            case 2:
                viewConstraints();
                break;
            case 3:
                addConstraint();
                break;
        }
    }

    private static void addConstraint() {
        System.out.println("Enter Date <DD/MM/YYYY>: ");
        String date = scanner.next();
        System.out.println("Enter Shift Type (Morning/Evening): ");
        String shiftType = scanner.next();
        System.out.println("Enter Constraint Type (Cant/Want): ");
        String constraintType = scanner.next();
        ResponseT<ConstraintResponse> constraint = facade.addConstraint(date, shiftType, constraintType);
        if (constraint.ErrorOccurred()){
            System.out.println(ANSI_RED + constraint.getErrorMessage() + ANSI_RESET);
        }
        else {
            System.out.println("Constraint added successfully");
        }
        WorkerMenu();
    }

    private static void viewConstraints() {
    }

    private static void AdminMenu() {

    }
}
