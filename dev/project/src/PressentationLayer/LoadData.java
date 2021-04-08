package PressentationLayer;

import BuissnessLayer.DeliveryMode;
import BuissnessLayer.FacadeBuissness.Facade;
import BuissnessLayer.paymentMethods;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class LoadData {
    Facade facade;
    public LoadData(){
        facade=Facade.getInstance();
    }

    public void LoadALLData(){
        LoadSupplier1();
        LoadSupplier2();
        //add new order
        HashMap<Integer,Integer> productQuantity=new HashMap<>();
        productQuantity.put(111,1000);
        productQuantity.put(222,1000);
        facade.addNewOrder(123,productQuantity,true);
        //set new extra discount
        facade.setExtraDiscountToSupplier(123,10);



    }
    public void LoadSupplier1(){
        //add new Supplier to the system
        facade.addNewSupplier(123,"Drinks123","88899988", paymentMethods.Cash, DeliveryMode.Pickup,null,-1,"alon","alon@gmail.com","058967411",2,8888,"osem","chips");
        //add new contact members to the supplier
        facade.addNewContactMember(123,"daniel","daniel@gmail.com","0526767222");
        facade.addNewContactMember(123,"avi","avi@gmail.com","0589654766");
        //add new products too supplier 123
        facade.addNewProductToAgreement(123,5,111,"Cola","Kola Zero");
        facade.addNewProductToAgreement(123,4,222,"Cola","Coca Kola");
        // add discount bu quantity
        facade.addNewDiscountByQuantitiyToProduct(123,111,500,4.5);

        HashMap<Integer,Integer> productQuantity=new HashMap<>();
        productQuantity.put(111,1000);
        productQuantity.put(222,1000);
        facade.addNewOrder(123,productQuantity,true);
        //set new extra discount
        facade.setExtraDiscountToSupplier(123,10);
    }


    public void LoadSupplier2(){
        List DaliveryByDAY=new LinkedList();
        DaliveryByDAY.add(1);
        DaliveryByDAY.add(3);
        //add new Supplier to the system
        facade.addNewSupplier(888,"yerkyerk","769852398", paymentMethods.BankTransfers, DeliveryMode.DeliveryByDay,DaliveryByDAY,-1,"ben","ben@gmail.com","0539854788",8,999,"solo","gamba");
        //add new contact members to the supplier
        facade.addNewContactMember(888,"asi","asi@gmail.com","0526568222");
        facade.addNewContactMember(888,"avner","avner@gmail.com","0589652656");
        //add new products too supplier 123
        facade.addNewProductToAgreement(888,5,333,"yerkDarom","apple");
        facade.addNewProductToAgreement(888,4,2212,"yerkDarom","banana");
        // add discount bu quantity
        facade.addNewDiscountByQuantitiyToProduct(888,333,1000,2);

        HashMap<Integer,Integer> productQuantity=new HashMap<>();
        productQuantity.put(333,1000);
        productQuantity.put(2212,1000);
        facade.addNewOrder(123,productQuantity,true);
    }


}
