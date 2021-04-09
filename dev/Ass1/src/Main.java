import Cont.BusinessController;
import Cont.DocCont;
import Cont.DriversController;
import Drives.License;
import Drives.TruckType;
import Cont.ControllerShops;
import Presentation.Facade;
import Shops.Area;
import Shops.Product;
import Shops.Store;
import Shops.Supplier;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[] args)  {

        Facade facade= new Facade();
        facade.loadData();
        for (int i = 0; i < 1000 ; i++) {

            facade.runProgram();
        }

    }
}
