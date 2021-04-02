package PresentationLayer;
import BusinessLayer.Facade;
import BusinessLayer.Responses.ConstraintResponse;
import BusinessLayer.Responses.ResponseT;
import BusinessLayer.Responses.WorkerResponse;

import java.util.Scanner;


public class Menu {
    private static final Scanner scanner = new Scanner(System.in);;
    private static final Facade facade = new Facade();

    public static void main(String[] args){
        System.out.println("Enter ID number for login: ");
        String ID = scanner.nextLine();
        ResponseT<WorkerResponse> worker = facade.login(ID);
        if (worker.ErrorOccurred()){
            System.out.println("Invalid ID: " + ID);
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
            System.out.println(constraint.getErrorMessage());
        }
        else {
            System.out.println("Constraint added successfully");
        }
        WorkerMenu();
    }

    private static void viewConstraints() {
        ResponseT<WorkerResponse> worker = facade.getLoggedWorker();
        if (worker.ErrorOccurred()){
            System.out.println(worker.getErrorMessage());
        }
        
    }

    private static void AdminMenu() {

    }
}
