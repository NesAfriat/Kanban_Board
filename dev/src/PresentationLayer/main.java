package PresentationLayer;



import BuisnnesLayer.DeliveryMode;
import BuisnnesLayer.FacedeModel.Objects.Response;
import BuisnnesLayer.FacedeModel.facade;
import BuisnnesLayer.GeneralProduct;
import BuisnnesLayer.ProductSupplier;
import BuisnnesLayer.paymentMethods;
import DataLayer.DataController;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class main {
    public static void main(String[] args) throws Exception {

        DataController dc = DataController.getInstance();
       Response r= facade.getInstance().addNewSupplier(123,"Drinks123","88899988", paymentMethods.Cash, DeliveryMode.Pickup,null,-1,"alon","alon@gmail.com","058967411");
        System.out.println(r.getErrorMsg());

        IO io=IO.getInstance();
        io.Start_Menu();

        //tests for dal:
        }

}