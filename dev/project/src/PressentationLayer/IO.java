package PressentationLayer;

import BuissnessLayer.FacadeBuissness.Facade;

import java.util.*;

public class IO {
    private static IO single_instance = null;
    private SupplierFunctionality supplierFunctionality;
    private  OrderFunctionality orderFunctionality;
    Facade facade;

    private IO(){
        facade=Facade.getInstance();
        supplierFunctionality=new SupplierFunctionality(facade);
        orderFunctionality=new OrderFunctionality(facade);
    }
    public static IO getInstance()
    {

        if (single_instance == null)
            single_instance = new IO();

        return single_instance;
    }

    public void Start_Menu(){
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        String[] arrFunctionality=new String[]{"Supplier Functionality","order Functionality","Load Data","exit"};
        System.out.println("__  _  __ ____ |  |   ____  ____   _____   ____  \n" +
                "\\ \\/ \\/ // __ \\|  | _/ ___\\/  _ \\ /     \\_/ __ \\ \n" +
                " \\     /\\  ___/|  |_\\  \\__(  <_> )  Y Y  \\  ___/ \n" +
                "  \\/\\_/  \\___  >____/\\___  >____/|__|_|  /\\___  >\n" +
                "             \\/          \\/            \\/     \\/ ");
        System.out.println("\n");

        while(true) {
            System.out.println("please choose one option : ");
            System.out.println("\n");

            for (int i = 0; i < arrFunctionality.length; i++) {
                System.out.println(i + 1 + ")" + " " + arrFunctionality[i]);
            }
            String Option = myObj.nextLine();  // Read user input
            switch (Option) {
                case "1":
                    supplierFunctionality.SupplierFunctionalityMenu();
                    break;
                case "2":
                    orderFunctionality.OrderFunctionalityMenu();
                    break;
                case "3":
                   LoadData loadData= new LoadData();
                   loadData.LoadALLData();
                   break;

                case "4":
                    return;
                default:
                    System.out.println("Not within bounds");
            }
        }


    }//    public Response addNewSupplier(int id, String name, String bankAccount, paymentMethods paymentMethods,DeliveryMode deliveryMode,List<Integer> daysOfDelivery,int NumOfDaysFromDelivery)







}
