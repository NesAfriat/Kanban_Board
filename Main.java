import Buissness.Cont.BusinessController;
import Buissness.Cont.ControllerShops;
import Buissness.Cont.DocCont;
import Buissness.Cont.DriversController;
import Buissness.Document.TransportDoc;
import Buissness.Drives.Driver;
import Buissness.Drives.License;
import Buissness.Drives.Truck;
import Buissness.Drives.TruckType;
import Buissness.Shops.Area;
import Buissness.Shops.Product;
import Buissness.Shops.Store;
import Buissness.Shops.Supplier;
import DAL.*;
import Presentation.Facade;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
    public static void main(String[] args) throws SQLException {


        TransportDocDAL sDAL = new TransportDocDAL();
        Date date=null;
        try {
             date = new SimpleDateFormat("dd/MM/yyyy").parse("11/03/1998");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<License> licenseList3 = new LinkedList<License>();
        licenseList3.add(License.typeA);
        Truck tk = new Truck("The Tank", 421652160, new TruckType("honda", 2000, 700, licenseList3));
        Driver dr = new Driver("Dan", 209889510, License.typeA);
        Supplier sp1 = new Supplier("Rami   Levi", 2, "0507133213", "Rami Ach", Area.B);
        Product pr1 = new Product("Cheese", 2);
        Store st1 = new Store("shufersal 1", 2, "0501231213", "mr man", Area.B);

        TransportDoc tr=new TransportDoc(1,date,date,tk,dr,st1,new HashMap<>(),new HashMap<>(),Area.B,1000,new ArrayList<>(),new ArrayList<>(),0);
        sDAL.saveDoc(tr);

        //HashMap<Integer, TransportDoc> LoadProducts = sDAL.LoadProducts();

        System.out.println("i win");
        /*Facade facade= new Facade();
        for (int i = 0; i < 1000 ; i++) {

            facade.runProgram();
        }*/
    }
}
