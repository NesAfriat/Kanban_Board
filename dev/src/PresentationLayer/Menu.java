package PresentationLayer;
import BusinessLayer.Facade;
import BusinessLayer.Responses.ResponseT;
import BusinessLayer.Responses.WorkerResponse;
import BusinessLayer.Workers.Worker;

import java.util.Scanner;


public class Menu {
    private static final Scanner scanner = new Scanner(System.in);;
    private static final Facade facade = new Facade();

    public static void main(String[] args){
        System.out.println("Hello! Enter ID number for login: ");
        String ID = scanner.nextLine();
        ResponseT<WorkerResponse> worker = facade.login(ID);
        if (worker.ErrorOccurred()){
            System.out.println("Invalid ID: " + ID);
            System.exit(0);
        }
        else if (worker.value.getIsAdmin()){
            AdminMenu(worker);
        }
        else {
            WorkerMenu(worker);
        }
    }

    private static void WorkerMenu(ResponseT<WorkerResponse> worker) {
    }

    private static void AdminMenu(ResponseT<WorkerResponse> worker) {
    }
}
