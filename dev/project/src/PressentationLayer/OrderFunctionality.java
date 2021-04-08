package PressentationLayer;

import BuissnessLayer.FacadeBuissness.Facade;
import BuissnessLayer.FacadeBuissness.ResponseObjects.Response;
import BuissnessLayer.FacadeBuissness.ResponseObjects.ResponseT;
import BuissnessLayer.FacadeBuissness.ResponseObjects.orderResponse;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;  // Import the Scanner class

public class OrderFunctionality {
    Facade facade;
    public OrderFunctionality(Facade facade){
        this.facade=facade;
    }
    public void OrderFunctionalityMenu() {
        System.out.printf("  ____          _           ______                _   _                   _ _ _         \n" +
                " / __ \\        | |         |  ____|              | | (_)                 | (_) |        \n" +
                "| |  | |_ __ __| | ___ _ __| |__ _   _ _ __   ___| |_ _  ___  _ __   __ _| |_| |_ _   _ \n" +
                "| |  | | '__/ _` |/ _ \\ '__|  __| | | | '_ \\ / __| __| |/ _ \\| '_ \\ / _` | | | __| | | |\n" +
                "| |__| | | | (_| |  __/ |  | |  | |_| | | | | (__| |_| | (_) | | | | (_| | | | |_| |_| |\n" +
                " \\____/|_|  \\__,_|\\___|_|  |_|   \\__,_|_| |_|\\___|\\__|_|\\___/|_| |_|\\__,_|_|_|\\__|\\__, |\n" +
                "                                                                                   __/ |\n" +
                "                                                                                  |___/ \n");
        String[] SupplierFunctionality = new String[]{"add New Order", "remove Order", "Print Specific Order", "print All Orders", "add Product To Order","change Product Quantity From Order","go back"};
        while (true) {
            System.out.println("please choose one option : ");
            System.out.println("\n");
            for (int i = 0; i < SupplierFunctionality.length; i++) {
                System.out.println(i + 1 + ")" + " " + SupplierFunctionality[i]);

            }
            Scanner myObj = new Scanner(System.in);  // Create a Scanner object
            String Option = myObj.nextLine();
            switch (Option) {
                case "1":
                    addNewOrder();
                    break;
                case "2":
                    removeOrder();
                    break;
                case "3":
                    PrintSpecificOrder();
                    break;
                case "4":
                    printAllOrders();
                    break;
                case "5":
                    addProductToOrder();
                    break;
                case "6":
                    changeProductQuantityFromOrder();
                    break;
                case "7":
                    return;
                default:
                    System.out.println("Not within bounds");



            }

        }}

        //this function remove an order from the system
        //public Response removeOrder(int SupId)
        public void removeOrder () {
            System.out.println("please type the order id");
            int orderId = NumberType();
            Response r = facade.removeOrder(orderId);
            if (r.ErrorOccured()) {
                System.out.println(r.ErrorMessage);
            }
        }


    //public Response addProductToOrder(int SupId,int OrderId, int CatalogID, int quantity);
    public void addProductToOrder(){
        System.out.println("please type the supplier id");
        int Supid=NumberType();
        System.out.println("please type the Order id");
        int OrderId=NumberType();
        System.out.println("please type the Catalog id of the product");
        int CatalogId=NumberType();
        System.out.println("please type the quantity of the product");
        int quantity=NumberType();
        Response r=facade.addProductToOrder(Supid,OrderId,CatalogId,quantity);
        if(r.ErrorMessage!=null){
            System.out.println(r.ErrorMessage);
        }
        else{System.out.println("Product added successfully\n");}

    }
    public void changeProductQuantityFromOrder(){
        System.out.println("please type the supplier id");
        int Supid=NumberType();
        System.out.println("please type the Order id");
        int OrderId=NumberType();
        System.out.println("please type the Catalog id of the product");
        int CatalogId=NumberType();
        System.out.println("please type the new quantity of the product");
        int quantity=NumberType();
        Response r=facade.changeProductQuantityFromOrder(Supid,OrderId,CatalogId,quantity);
        if(r.ErrorMessage!=null){
            System.out.println(r.ErrorMessage);
        }
        else{System.out.println("Product Quantity changed successfully\n");}

    }
//int id, int supplierid, LocalDate date, HashMap<Integer, Integer> quantity, double totalPayment,boolean isConstant
    //this function return a response of specific order
    public void PrintSpecificOrder(){
        System.out.println("please type the Order id");
        int OrderId=NumberType();


        ResponseT<orderResponse> response=facade.getOrder(OrderId);

        if(response.ErrorMessage!=null){
            System.out.println(response.ErrorMessage);
        }else {
            orderResponse orderResponse=response.value;
            int id=orderResponse.id;
            int SupplierId=orderResponse.Supplierid;
            HashMap<Integer,Integer> products=orderResponse.quantity;
            LocalDate date=orderResponse.date;
            double Totalpayment=orderResponse.TotalPayment;
            boolean isConstant=orderResponse.isConstant;
            System.out.printf("%-22s%-22s%-22s%-22s%-22s\n","id","SupplierId","date","Totalpayment","IFConstant");
            System.out.printf("%-22d%-22d%-22s%-22s%-22b\n",id,SupplierId,date.toString(),Totalpayment,isConstant);
            //PRINT ALL PRODUCR IN THE ORDER
            System.out.printf("%-22s%-22s\n","productId","product Quantity");

            for (Integer p:products.keySet()) {
                System.out.printf("%-22d%-22d\n",p,products.get(p));
            }

        }
    }


    //this function return a response of all the orders in the system
    //public ResponseT getAllOrders();
    public void printAllOrders(){
        ResponseT<List<orderResponse>> response=facade.getAllOrders();
        if(response.ErrorMessage!=null){
            System.out.print(response.ErrorMessage);
        }
        else {
            List<orderResponse> orderResponseList=response.value;
            for (orderResponse orderResponse:orderResponseList) {
                int id=orderResponse.id;
                int SupplierId=orderResponse.Supplierid;
                HashMap<Integer,Integer> products=orderResponse.quantity;
                LocalDate date=orderResponse.date;
                double Totalpayment=orderResponse.TotalPayment;
                boolean isConstant=orderResponse.isConstant;
                System.out.printf("%-22s%-22s%-22s%-22s%-22s\n","order id","Supplier Id","date","Total payment","Constant");
                System.out.printf("%-22d%-22d%-22s%-22s%-22b\n",id,SupplierId,date.toString(),Totalpayment,isConstant);
                //PRINT ALL PRODUCR IN THE ORDER
                System.out.printf("%-22s%-22s\n","product id","product Quantity");

                for (Integer p:products.keySet()
                ) {
                    System.out.printf("%-22d%-22d\n",p,products.get(p));
                }

            }
        }
    }

    //    public Response addNewOrder(int SupId,HashMap<Integer,Integer> productQuantity);
    public void addNewOrder(){
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println("please type the supplier id");
        int id=NumberType();
        HashMap<Integer,Integer> productQuantity=new HashMap<>();
        System.out.println("now please type the products in the order.");
        int catalogId=-1;
        int start=1;
        int quantity=-1;
        boolean tocontinue=true;
        Response response;
        while(tocontinue){
            //if(start!=1){
                System.out.println("please type the product catalog Id:");
                catalogId=NumberType();
                System.out.println("please type the product quantity in the order:");
                quantity=NumberType();
                productQuantity.put(catalogId,quantity);

                // now we want to check if the use want to add another product, if not we get out off the loop
                while (true) {
                    System.out.println("do you want to insert another product to the order?");
                    System.out.println("1) yes");
                    System.out.println("2) no");
                    int cont = NumberType();
                    if (cont != 1 && cont != 2) {
                        System.out.println("please try again! ");
                    }
                    if(cont==2){
                        tocontinue=false;
                        break;
                    }
                    else break;
                }
            //}
        }
        System.out.println("is the order constant?");
        System.out.println("1) yes");
        System.out.println("2) no");
       // while (true) {
            int isConstat = NumberType();
            if(isConstat!=1&&isConstat!=2){
                System.out.println("try again!");
            }
            else{
                if(isConstat==1){
                    Response r=facade.addNewOrder(id,productQuantity,true);
                    if(r.ErrorMessage!=null) System.out.println(r.ErrorMessage);
                    else{System.out.println("order add changed successfully\n");
                    return;
                    }
                }
                else {//==2
                    Response r=facade.addNewOrder(id,productQuantity,false);
                    if(r.ErrorMessage!=null) System.out.println(r.ErrorMessage);
                    else{System.out.println("order add changed successfully\n");
                        return;
                    }
                }
            }


    }

    private int NumberType() {
        int numb = 0;
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        boolean isOk = false;
        while (!isOk) {
            try {
                return Integer.parseInt(myObj.nextLine());
            }catch (Exception e) {
                System.out.println("Input is not a valid integer, try again");
            }
        }
        return numb;
    }
}
 /*






    //this function return a response of all the orders in the system
    public ResponseT getAllOrders();

  */