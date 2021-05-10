package PresentationLayer;



import BuisnnesLayer.*;
import BuisnnesLayer.FacedeModel.Objects.Response;
import BuisnnesLayer.FacedeModel.facade;
import DataLayer.DataController;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class main {
    public static void main(String[] args) throws Exception {

        DataController dc = DataController.getInstance();
//        System.out.println(r.getErrorMsg());
       facade f=facade.getInstance();

//        f.addNewSupplier(123,"Drinks123","88899988", paymentMethods.Cash, DeliveryMode.Pickup,null,-1,"alon","alon@gmail.com","058967411");
//
//        Response rd= f.getInstance().addNewProductToAgreement(123,5,222,"Cola","nesti","drink",1,false);
//
////        System.out.println(rd.getErrorMsg());
//      f.addNewSupplier(888,"yerkyerk","769852398", paymentMethods.BankTransfers, DeliveryMode.Pickup,null,-1,"ben","ben@gmail.com","0539854788");
//        Response rd5=  f.getInstance().addNewProductToAgreement(888,100,777,"sf","s","fsf",2,false);
//        Response rd4=  f.getInstance().addNewProductToAgreement(888,10,333,"Cola","nesti","drink",2,true);
//System.out.println(rd4.getErrorMsg()+"dsdsdsddsdsdds");
//

        IO io=IO.getInstance();
        io.Start_Menu();

        }

}