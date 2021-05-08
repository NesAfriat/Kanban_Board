package PresentationLayer;

import BusinessLayer.Transport_BusinessLayer.Cont.Transport_Facade;
import BusinessLayer.Transport_BusinessLayer.Document.TransportDoc;
import BusinessLayer.Transport_BusinessLayer.Drives.Driver;
import BusinessLayer.Transport_BusinessLayer.Drives.License;
import BusinessLayer.Transport_BusinessLayer.Drives.Truck;
import BusinessLayer.Transport_BusinessLayer.Drives.TruckType;
import BusinessLayer.Transport_BusinessLayer.Shops.Area;
import BusinessLayer.Transport_BusinessLayer.Shops.Product;
import BusinessLayer.Transport_BusinessLayer.Shops.Store;
import BusinessLayer.Transport_BusinessLayer.Shops.Supplier;
import BusinessLayer.Workers_BusinessLayer.Workers_Facade;
import BusinessLayer.Workers_Integration;
import DataLayer.Transport_DAL.TransportDocDAL;
import PresentationLayer.Transport_PresentationLayer.Transport_Menu;
import PresentationLayer.Workers_PresentationLayer.Workers_Main_Menu;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        Workers_Integration wk = workers_facade;
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

        /*if (firstRun) {

            System.out.println("Do you want to load database?");
            boolean load = getInputYesNo();
            if (load) {
               // workers_menu.testingDataUpload();
                transport_menu.loadData();
            }
            firstRun = false;
        }*/

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
                    //System.out.println("Not implemented yet");
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
