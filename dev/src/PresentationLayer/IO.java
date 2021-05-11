package PresentationLayer;
import BuisnnesLayer.FacedeModel.facade;

import java.util.*;

public class IO {
    private static IO single_instance = null;
    private SupplierFunctionality supplierFunctionality;
    private  OrderFunctionality orderFunctionality;
    private  inventPresentation InventPresentation;

    facade Facade;

    private IO(){
        Facade=facade.getInstance();
        supplierFunctionality=new SupplierFunctionality(Facade);
        orderFunctionality=new OrderFunctionality(Facade);
        InventPresentation=new inventPresentation();
    }
    public static IO getInstance()
    {

        if (single_instance == null)
            single_instance = new IO();



        return single_instance;
    }

    public void Start_Menu() throws Exception {
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        String[] arrFunctionality=new String[]{"Supplier Functionality","order Functionality","inventory Functionality","exit"};
//        System.out.println("__  _  __ ____ |  |   ____  ____   _____   ____  \n" +
//                "\\ \\/ \\/ // __ \\|  | _/ ___\\/  _ \\ /     \\_/ __ \\ \n" +
//                " \\     /\\  ___/|  |_\\  \\__(  <_> )  Y Y  \\  ___/ \n" +
//                "  \\/\\_/  \\___  >____/\\___  >____/|__|_|  /\\___  >\n" +
//                "             \\/          \\/            \\/     \\/ ");
//        System.out.println("\n");

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
                    InventPresentation.main_window();
                    break;

                case "4":
                    return;

                default:
                    System.out.println("Not within bounds");
            }
        }


    }//    public Response addNewSupplier(int id, String name, String bankAccount, paymentMethods paymentMethods,DeliveryMode deliveryMode,List<Integer> daysOfDelivery,int NumOfDaysFromDelivery)







}
