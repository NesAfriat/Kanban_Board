package Presentation;
import Cont.BusinessController;
import etc.Tuple;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Facade {
    private BusinessController bc;
    private Scanner sc;
    int input;


    public Facade ()
    {
        bc = new BusinessController();
        sc = new Scanner(System.in);
    }

    public int displayMenu(){

        //todo truck and driver check + also in change menu
        print("Please choose an option:\n\n");
        print ("1) New document\n");
        print ("2) Display all Store List \n");
        print ("3) Display all Product List \n");
        print ("4) Display Stores in Doc Area \n");
        print ("5) Display Supplier List for chosen products \n");
        print ("6) Display Truck List \n");
        print ("7) Display Driver List \n");
        print ("8) Add Store to Transport \n");
        print ("9) Add Product to Transport \n");
        print ("10) Add Supplier to Transport \n");
        print ("11) Add Truck to Transport \n");
        print ("12) Add Driver to Transport \n");
        print ("13) Add Origin to Transport \n");
        print ("14) Add Transport Date to Transport \n");
        print ("15) Add Departure time to Transport \n");
        print ("16) make changes \n");
        print ("17) print document \n");




        input = sc.nextInt();
        return input;
    }

    public void runProgram(){
        int a,b,c,d;
        String str;
        switch (displayMenu()) {
            case 1:
               print("Your Delivery Document ID is " +bc.createNewDelivery()+ ". It is important, keep it!\n");
               break;
            case 2:
                print(bc.getAllStores());
                break;
            case 3:
                print(bc.getAllProducts());
                break;
            case 4:
                print("please enter your doc ID\n");
                a=sc.nextInt();
                print(bc.getAvaliableStoresString(a));
                break;
            case 5:
                print("please enter your doc ID\n");
                a=sc.nextInt();
                print(bc.returnAvaliableSupplierString(a));
                break;
            case 6:
                print(bc.getTrucksString());
                break;
            case 7:
                print("please enter your truck License Plate\n");
                a=sc.nextInt();
                try {
                    print(bc.getDriversString(a));
                } catch (Exception e) {
                    print(e.toString());
                }
                break;
            case 8:
                print("please enter your doc ID, the store id and the stop the store will be in the Transport\n");
                try {
                     a=sc.nextInt();
                     b= sc.nextInt();
                     c= sc.nextInt();
                    bc.addStore(a, b, c);
                }catch (Exception e){
                    print(e.toString());
                }
                break;

            case 9:
                print("please enter your doc ID, the Product ID, The amount of items and the Store ID\n");
                try {
                    a=sc.nextInt();
                    b= sc.nextInt();
                    c= sc.nextInt();
                    d=sc.nextInt();
                    bc.addProductsToDoc(a, new Tuple<>(b,c), d);
                }catch (Exception e){
                    print(e.toString());
                }
                break;


            case 10:
                print("please enter your doc ID, the supplier id and the stop the supplier will be in the Transport\n");
                try {
                    a=sc.nextInt();
                    b= sc.nextInt();
                    c= sc.nextInt();
                    bc.addSupplier(a, b, c);
                }catch (Exception e){
                    print(e.toString());
                }
                break;
            case 11:
                print("please enter your doc ID  and truck License plate\n");
                try {
                    a=sc.nextInt();
                    b= sc.nextInt();
                    bc.addTruck(a, b);
                }catch (Exception e){
                    print(e.toString());
                }
                break;
            case 12:
                print("please enter your doc ID  and driver ID\n");
                try {
                    a=sc.nextInt();
                    b= sc.nextInt();
                    bc.addDriver(a, b);
                }catch (Exception e){
                    print(e.toString());
                }
                break;

            case 13:
                print("please enter your doc ID  and Store ID\n");
                a=sc.nextInt();
                b= sc.nextInt();
                try {
                    bc.setOrigin(a, b);
                }catch (Exception e)
                {
                    print(e.toString());
                }

            case 14:
                print("please enter your doc ID, the day, the month and the year of Transport (DD/MM/YYYY) \n");
                a=sc.nextInt();
                str= sc.nextLine();
                try {
                    Date date = new SimpleDateFormat("dd/MM/yyyy").parse(str);
                    bc.setTransportDate(a,date);
                }catch (Exception e)
                {
                    print(e.toString());
                }
            case 15:
                print("please enter your doc ID and the Departure time in this format (dd-MM-yyyy HH:mm:ss) \n");
                a=sc.nextInt();
                str= sc.nextLine();
                try {
                    SimpleDateFormat formatter=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    Date date=formatter.parse(str);
                    bc.setDepartureTime(a,date);


                }catch (Exception e)
                {
                    print(e.toString());
                }
            case 16:
                makeChanges();
                break;
            case 17:
                printDoc();
                break;
        }
    }

    private void makeChanges() {
        int a,b,c;
        print("Please choose an option:\n\n");
        print ("1) return \n");
        print ("2) Edit Truck \n");
        print ("3) Edit Driver \n");
        print ("4) Edit Truck and Driver \n");
        print ("5) Edit Truck Weight on Departure \n");
        print ("6) Remove Destination \n");
        print ("7) Remove Product By Store \n");


        switch (sc.nextInt()) {

            case 1:
                break;
            case 2:
                print("please enter your doc ID and new truck License Plate \n");
                a= sc.nextInt();
                b= sc.nextInt();
                try {
                    bc.editDocTruck(a, b);
                }catch (Exception e){
                    print(e.toString());
                }
                break;
            case 3:
                print("please enter your doc ID  and new truck Driver ID \n");
                a= sc.nextInt();
                b=sc.nextInt();
                try {
                    bc.editDocDriver(a, b);
                }catch (Exception e){
                    print(e.toString());
                }
                break;
            case 4:
                print("please enter your doc ID, new truck License Plate and new Driver ID \n");
                a= sc.nextInt();
                b=sc.nextInt();
                c=sc.nextInt();
                try {
                    bc.editDocTruck(a, b);
                    bc.editDocDriver(a, c);
                }catch (Exception e){
                    print(e.toString());
                }
                break;
            case 5:
                print("please enter your doc ID, new truck weight on departure \n");
                a= sc.nextInt();
                b=sc.nextInt();
                try {
                    bc.editTruckWeightDep(a, b);
                }catch (Exception e){
                    print(e.toString());
                }
                break;

            case 6:
                print("please enter your doc ID  and driver ID\n");
                 a= sc.nextInt();
                 b=sc.nextInt();
                try {
                    bc.removeDestination(a, b);
                }catch (Exception e){
                    print(e.toString());
                }
                break;
            case 7:
                print("please enter your doc ID, product ID and store ID\n");
                 a= sc.nextInt();
                 b= sc.nextInt();
                 c= sc.nextInt();
                try {
                    bc.removeProduct(a, b,c);
                }catch (Exception e){
                    print(e.toString());
                }
                break;




        }



        }
    private void printDoc() {
        int a,b;
        print("Please choose an option:\n\n");
        print ("1) Print Basic Info \n");
        print ("2) Print Stops \n");
        print ("3) Print Products \n");

        switch (sc.nextInt()) {


            case 1:
                print("please enter your doc ID, will only print initialised variables \n");
                a= sc.nextInt();
                try {
                    print(bc.docInfo(a));
                }
                catch (Exception e)
                {
                    print(e.toString()+"\n");
                }
                break;
            case 2:
                print("please enter your doc ID\n");
                a= sc.nextInt();
                print(bc.docDestinations(a));
                break;
            case 3:
                print("please enter your doc ID\n");
                a= sc.nextInt();
                print(bc.docProducts(a));
                break;


        }



    }





    public void loadData()  {

        bc.loadData();
    }


    static void print(String m)
    {
        System.out.print(m);
    }




}
