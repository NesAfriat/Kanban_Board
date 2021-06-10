package PresentationLayer;

import BusinessLayer.Transport_BusinessLayer.Cont.Transport_Facade;
import BusinessLayer.Workers_BusinessLayer.Workers_Facade;
import BusinessLayer.WorkersToTransport_Integration;
import PresentationLayer.Transport_PresentationLayer.Transport_Menu;
import PresentationLayer.Workers_PresentationLayer.Workers_Main_Menu;

import java.util.*;

public class Main_Menu {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Workers_Facade workers_facade = new Workers_Facade();
    private static final Transport_Facade transport_facade = new Transport_Facade();
    private boolean firstRun;
    public Main_Menu(){
        firstRun = true;
    }
    public void start() {
        Workers_Main_Menu workers_menu = new Workers_Main_Menu(workers_facade);
        Transport_Menu transport_menu = new Transport_Menu(transport_facade);
        WorkersToTransport_Integration wk = workers_facade;
        transport_menu.addWorkersIntegration(wk);
        System.out.println('\n' + "----------------------------------------------------------");
        System.out.println('\n' +
                "0000  0  0  0000  0000  0000              0    0000  0000" + '\n' +
                "0     0  0  0  0  0     0  0              0    0     0   "+ '\n' +
                "0000  0  0  0000  0000  000     000000    0    0000  0000"+ '\n' +
                "   0  0  0  0     0     0 0               0    0     0   "+ '\n' +
                "0000  0000  0     0000  0  0              0000 0000  0000" + '\n');
        System.out.println("----------------------------------------------------------" + '\n');
        System.out.println("Welcome to Super-Lee system!");

        boolean run = true;
        while (run) {
            System.out.println("1) Enter workers manage system");
            System.out.println("2) Enter transport manage system");
            System.out.println("3) Exit");
            System.out.print("Option: ");
            int option = getInputInt();
            switch (option) {
                case 1:
                    workers_menu.start();
                    break;
                case 2:
                    transport_menu.mainMenu();
                    break;
                case 3:
                    run = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("No such option");
            }
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
