import Buissness.Cont.BusinessController;
import Buissness.Cont.ControllerShops;
import Buissness.Cont.DocCont;
import Buissness.Cont.DriversController;
import DTOs.DriversDTOs.DriverDTO;
import DTOs.DriversDTOs.TruckDTO;
import DTOs.ShopsDTOs.StoreDTO;
import Buissness.Drives.License;
import Buissness.Drives.TruckType;
import Buissness.Shops.Area;
import Buissness.Shops.Product;
import Buissness.Shops.Store;
import Buissness.Shops.Supplier;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class BusinessControllerTest {



  /*  @org.junit.jupiter.api.Test
    void getAvaliableStoresEmpty() {
        BusinessController bs=LoadBusinessController();
        int docId=bs.createNewDelivery();
        List<StoreDTO> emptyList=bs.getAvaliableStores(docId);
        assertEquals(0, emptyList.size());
    }
    @org.junit.jupiter.api.Test
    void checkSimpleDriverTruckInController() {
        BusinessController bs=LoadBusinessController();
        int docId=bs.createNewDelivery();
        List<TruckDTO> trucks=bs.getAvaliableTrucks();
        Optional<TruckDTO> truck=trucks.stream().filter(x->!x.getTruckType().getLicensesForTruck().contains(License.typeC)).findFirst();
        if(truck.isEmpty()){ fail();}

        int truckLicense=truck.get().getLicensePlate();
        List<DriverDTO> driver;
        try {
            driver=bs.getAvaliableDrivers(truckLicense);
            bs.addTruckAndDriver(docId, truckLicense, driver.get(0).getId());
        }
        catch(Exception e){
            fail();
        }
    }
    @org.junit.jupiter.api.Test
    void checkAddTruckAndThenDriver(){
        BusinessController bs = LoadBusinessController();
        int deliveryId = bs.createNewDelivery();
        try {
            bs.addTruck(deliveryId, 421652160);
            bs.addDriver(deliveryId, 208750760);

            assertTrue(true);
        }
        catch(Exception e){
            assertTrue(false);
        }
    }
    @org.junit.jupiter.api.Test
    void checkAddDriverAndThenTruck(){
        BusinessController bs = LoadBusinessController();
        int deliveryId = bs.createNewDelivery();
        try {
            bs.addDriver(deliveryId, 208750760);
            bs.addTruck(deliveryId, 421652160);
            assertTrue(true);
        }
        catch(Exception e){
            assertTrue(false);
        }
    }
    @org.junit.jupiter.api.Test
    void checkSameTruckForTwoDelivers() {
        BusinessController bs=LoadBusinessController();
        int docId1=bs.createNewDelivery();
        int docId2=bs.createNewDelivery();
        List<TruckDTO> trucks=bs.getAvaliableTrucks();
        Optional<TruckDTO> truck=trucks.stream().filter(x->!x.getTruckType().getLicensesForTruck().contains(License.typeB)).findFirst();
        if(truck.isEmpty()){ fail();}

        int truckLicense=truck.get().getLicensePlate();
        try {
            bs.addTruck(docId1, truckLicense);//same truck to two different Licenses
            bs.addTruck(docId2, truckLicense);


        }
        catch(Exception e){
            fail();
        }
        assertTrue(true);

    }
    @org.junit.jupiter.api.Test
    void checkDriversInDeliveryDocButStillAvailable() {
        BusinessController bs=LoadBusinessController();
        int docId1=bs.createNewDelivery();
        try {
            bs.addTruckAndDriver(docId1,  421652162 ,209889510);
            bs.addTruckAndDriver(docId1, 421652163 ,208750760 );
            List<DriverDTO> empty=bs.getAvaliableDrivers(421652160);
            assertEquals(2, empty.size());
        }
        catch(Exception e){
           fail();
        }

    }

    @org.junit.jupiter.api.Test
    void checkMaxWeightDeviation(){
        BusinessController bs=LoadBusinessController();
        int deliveryId= bs.createNewDelivery();
        boolean output=false;
        try{

            bs.addTruck(deliveryId,421652160);
            bs.addDriver(deliveryId,208750760);
            bs.editTruckWeightDep(deliveryId, 2001);
        }
        catch (Exception e){
            output=true;

        }
        if(!output) {
            fail("Truck over weight a the system did not throw exception ");
        }else{
            assertTrue(true);
        }
    }
    @org.junit.jupiter.api.Test
    void checkMaxWeightDeviationTruckSwitch(){
        BusinessController bs=LoadBusinessController();
        int deliveryId= bs.createNewDelivery();
        boolean output=false;
        try{

            bs.addTruck(deliveryId,421652160);
            bs.addDriver(deliveryId,208750760);
            bs.editTruckWeightDep(deliveryId, 2001);
        }
        catch (Exception e){
            try {
                bs.editDocTruck(deliveryId, 421652163);//todo doc not updates
                bs.editTruckWeightDep(deliveryId, 2001);
                output=true;
            }
            catch (Exception e1){
               fail("fix didn't succeed");
            }

        }
        if(!output) {
            fail("Truck over weight a the system did not throw exception ");
        }else{
            assertTrue(true);
        }


    }
    @org.junit.jupiter.api.Test
    void checkMaxWeightDeviationWeightEdit(){
        BusinessController bs=LoadBusinessController();
        int deliveryId= bs.createNewDelivery();
        boolean output=false;
        try{

            bs.addTruck(deliveryId,421652160);
            bs.addDriver(deliveryId,208750760);
            bs.editTruckWeightDep(deliveryId, 2001);
        }
        catch (Exception e){
            try {
                bs.editTruckWeightDep(deliveryId, 1990);
                output=true;
            }
            catch (Exception e1){
                fail("fix didn't succeed");
            }

        }
        if(!output) {
            fail("Truck over weight a the system did not throw exception ");
        }else{
            assertTrue(true);
        }


    }
    /*@org.junit.jupiter.api.Test
    void checkAddDriverAndThenTruck() {
        BusinessController bs = LoadBusinessController();
        int deliveryId = bs.createNewDelivery();
        try {
            bs.addDriver(deliveryId, 208750760);
            bs.addTruck(deliveryId, 421652160);
            assertTrue(false);
        }
        catch(Exception e){
            assertTrue(true);
        }
    }
    @org.junit.jupiter.api.Test
    void addDifferentAreaSuppliers() {
        BusinessController bs=LoadBusinessController();
        int docId=bs.createNewDelivery();

        try {
            bs.addSupplier(docId, 2, 1);
            bs.addSupplier(docId, 5, 2);
            fail();
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(true);
        }
    }
    @org.junit.jupiter.api.Test
    void addDifferentAreaSuppliersAndStores() {
        BusinessController bs=LoadBusinessController();
        int docId=bs.createNewDelivery();


        try {
            bs.addSupplier(docId, 2, 1);
            bs.addStore(docId, 2, 2);
            fail();
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(true);
        }
    }
    @org.junit.jupiter.api.Test
    void addDifferentSuppliersInSamePlace() {
        BusinessController bs=LoadBusinessController();
        int docId=bs.createNewDelivery();

        try {
            bs.addSupplier(docId, 3, 1);
            bs.addSupplier(docId, 4, 1);
            fail("two stores in same place");
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(true);
        }
    }

*/
    private BusinessController LoadBusinessController(){
        ControllerShops cs=LoadControllerShops();
        DriversController dc=LoadDriverController();
        return new BusinessController(dc,new DocCont(),cs);
    }
    private ControllerShops LoadControllerShops(){
        ControllerShops cs = new ControllerShops();
        Supplier sp1 = new Supplier("Rami Levi", 2, "0507133213", "Rami Ach", Area.B);
        Supplier sp2 = new Supplier("Shufersal", 3, "050713123213", "Shuf Ach", Area.C);
        Supplier sp3 = new Supplier("Mega", 4, "0501231231", "meg Ach", Area.C);
        Supplier sp4 = new Supplier("Tiv Taam", 5, "052312312", "Taami Ach", Area.A);

        cs.addSupplier(sp1);
        cs.addSupplier(sp2);
        cs.addSupplier(sp3);
        cs.addSupplier(sp4);
        //cs.ToStringSuppliers();

        Product pr1 = new Product("Cheese", 2);
        Product pr2 = new Product("Milk", 3);
        Product pr3 = new Product("Chocolate", 4);
        Product pr4 = new Product("Water", 5);


        cs.addProduct(pr1);
        cs.addProduct(pr2);
        cs.addProduct(pr3);
        cs.addProduct(pr4);
        //cs.ToStringProducts();

        Store st1 = new Store("shufersal 1", 2, "0501231213", "mr man", Area.A);
        Store st2 = new Store("shufersal 2", 3, "0501465213", "mr man", Area.B);
        Store st3 = new Store("shufersal 3", 4, "0507553113", "mr man", Area.B);
        Store st4 = new Store("shufersal 4", 5, "0501299913", "mr man", Area.C);

        cs.addStore(st1);
        cs.addStore(st2);
        cs.addStore(st3);
        cs.addStore(st4);
        //cs.ToStringStores();

        return cs;
    }
    private DriversController LoadDriverController(){
        DriversController drivers=new DriversController();
        drivers.AddNewDriver("Guy",208750760, License.typeA);
        drivers.AddNewDriver("Dan",209889510, License.typeA);
        drivers.AddNewDriver("Lebron James",986750760, License.typeB);
        drivers.AddNewDriver("Steph Curry",308450560, License.typeB);
        drivers.AddNewDriver("Omri Caspi",208759760, License.typeC);
        drivers.AddNewDriver("Deni Avdija",208650760, License.typeC);

        List<License> licenseList1=new LinkedList<License>();licenseList1.add(License.typeA);licenseList1.add(License.typeB);
        List<License> licenseList2=new LinkedList<License>();licenseList2.add(License.typeA);licenseList2.add(License.typeB);licenseList2.add(License.typeC);
        List<License> licenseList3=new LinkedList<License>();licenseList3.add(License.typeA);

        drivers.AddNewTruck("The Tank", 421652160, new TruckType("honda", 2000, 700,licenseList3));
        drivers.AddNewTruck("The Hunk", 421652161, new TruckType("honda", 2000, 700,licenseList3));
        drivers.AddNewTruck("Mad Max", 421652162, new TruckType("mazda", 3000, 1000,licenseList1));
        drivers.AddNewTruck("Big Daddy", 421652163, new TruckType("honda", 4000, 1200,licenseList2));

        return drivers;

    }
}
