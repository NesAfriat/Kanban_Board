package PresentationLayer;

import BusinessLayer.Workers_BusinessLayer.Workers_Facade;
import PresentationLayer.Workers_PresentationLayer.Workers_Main_Menu;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main_Menu {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Workers_Facade workers_facade = new Workers_Facade();
    // protected static final Transport_Facade transport_facade = new Transport_Facade();
    private boolean firstRun;
    public Main_Menu(){
        firstRun = true;
    }
    public void start() {
        Workers_Main_Menu workers_menu = new Workers_Main_Menu(workers_facade);
        // Transport_Main_Menu transport_menu = new Transport_Menu(transport_facade);

        if (firstRun) {
            System.out.println("Welcome to Super-Lee system!");
            System.out.println("Do you want to load database?");
            boolean load = getInputYesNo();
            if (load) {
                workers_menu.testingDataUpload();
            }
            firstRun = false;
        }
        boolean run = true;
        while (run) {
            System.out.println("1) Enter workers manage system");
            System.out.println("2) Enter transport manage system");
            System.out.println("2) Exit");
            System.out.print("Option: ");
            int option = getInputInt();
            switch (option) {
                case 1:
                    workers_menu.start();
                    break;
                case 2:
                    //transport_menu.start();
                    System.out.println("Not implemented yet");
                    break;
                case 3:
                    run = false;
                    System.out.println("Goodbye!");
                default:
                    System.out.println("No such option");
            }
        }

    }


    private boolean getInputYesNo() {
        System.out.println("1) Yes");
        System.out.println("2) No");
        System.out.print("Option: ");
        int option = getInputInt();
        if (option == 1)
            return true;
        else if (option == 2)
            return false;
        else{
            System.out.println("There's no such option");
            return getInputYesNo();
        }
    }

    private int getInputInt() {
        while (!scanner.hasNextInt()){
            System.out.println("Please enter a number");
            scanner.next();
        }
        return scanner.nextInt();
    }
}
