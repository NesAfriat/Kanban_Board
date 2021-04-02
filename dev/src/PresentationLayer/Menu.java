package PresentationLayer;
import BusinessLayer.Facade;
import BusinessLayer.Responses.ResponseT;
import BusinessLayer.Workers.Worker;

import java.util.Scanner;


public class Menu {
    private static final Scanner scanner = new Scanner(System.in);;
    private static final Facade facade = new Facade();

    public static void main(String[] args){
        System.out.println("Hello! Enter ID number for login: ");
        String ID = scanner.nextLine();
        ResponseT<Worker> response = facade.login(ID);
    }
}
