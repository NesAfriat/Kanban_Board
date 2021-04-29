package BusinessLayer.Transport_BusinessLayer.Cont;

import BusinessLayer.Transport_BusinessLayer.Document.Triple;
import BusinessLayer.Transport_BusinessLayer.Drives.Driver;
import BusinessLayer.Transport_BusinessLayer.Drives.License;
import BusinessLayer.Transport_BusinessLayer.Drives.Truck;
import BusinessLayer.Transport_BusinessLayer.Drives.TruckType;
import BusinessLayer.Transport_BusinessLayer.Shops.*;
import BusinessLayer.Transport_BusinessLayer.etc.Tuple;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Transport_Facade {
    private DriversController driversController;
    private DocCont docCont;
    private ControllerShops controllerShops;

    public Transport_Facade() {
        this.driversController = new DriversController();
        this.docCont = new DocCont();
        this.controllerShops = new ControllerShops();
    }

    public Transport_Facade(DriversController driversController, DocCont docCont, ControllerShops controllerShops) {
        this.driversController = driversController;
        this.docCont = docCont;
        this.controllerShops = controllerShops;
    }
    public int createNewDelivery() {
        return docCont.newDelivery();
    }
    /*public List<StoreDTO> getAvaliableStores(int docNum) {
        Area area=docCont.getArea(docNum);
         List< Store > storeList=controllerShops.getStoreList().stream().filter(x->x.getArea()==area).collect(Collectors.toList());
         return storeList.stream().map(DTOFactory::createStoreDTO).collect(Collectors.toList());
    }*/
    public String getAvaliableStoresString(int docNum) {
        Area area=docCont.getArea(docNum);
        return controllerShops.getStoreList().stream().filter(x->x.getArea()==area).collect(Collectors.toList()).toString();
    }
    public void addStore(int doc, int StoreNum, int place) throws Exception {
        Area area = docCont.getArea(doc);
        Optional<Store> s = controllerShops.getStoreList().stream().filter(r -> r.getId() == StoreNum).findFirst();
        if (!s.isPresent()) {
            throw new Exception("No store with this number");
        }
        if (!s.get().getArea().equals(area) & area !=null) {
            throw new Exception("store not in the same area");
        }
        docCont.addStore(doc, s.get(), place);
    }
    public void removeDestination(int doc, int place) throws Exception {
        docCont.removeDestinations(doc,place);

    }
    public void removeProduct(int doc, int prodId,int storeId) throws Exception {
        docCont.removeProducts(doc,prodId,storeId);
    }
    public String docProducts(int doc){
       return docCont.docProdString(doc);
    }
    public String docInfo(int doc){
        return docCont.docInfo(doc);
    }
    public String docDestinations(int doc){
        return docCont.docDestinations(doc);
    }
    public void addSupplier(int doc, int SupplierNum, int place) throws Exception {
        Area area = docCont.getArea(doc);
        Optional<Supplier> s = controllerShops.getSupplierList().stream().filter(r -> r.getId() == SupplierNum).findFirst();
        if (!s.isPresent()) {
            throw new Exception("No supplier with this number");
        }
        if (!s.get().getArea().equals(area)& area !=null) {
            throw new Exception("Supplier not in the same area");
        }
        docCont.addSupplier(doc, s.get(), place);
    }
   /* public List<ProductDTO> getAvailableProducts(int doc) {
        List<Product> allProducts = controllerShops.getProductList();
        return allProducts.stream().map(DTOFactory::createProductDTO).collect(Collectors.toList());
    }*/
    public void addProductsToDoc(int doc, Tuple<Integer, Integer> productAndAmounts, int storeId) throws Exception {
        Optional<Product> product = controllerShops.getProductList().stream().filter(x -> x.getId() == productAndAmounts.x).findFirst();
        if (!product.isPresent()) {
            throw new Exception("no product with this id");
        }
        Optional<Store> Store = controllerShops.getStoreList().stream().filter(x -> x.getId() == storeId).findFirst();
        if (!Store.isPresent()) {
            throw new Exception("no Store with this id");
        }
        docCont.addProducts(doc, new Triple<>(product.get(), productAndAmounts.y, Store.get()));
    }
    /*
    public List<Tuple<List<SupplierDTO>, ProductDTO>> returnAvaliableSuppliers(int doc) {
        List<Tuple<List<SupplierDTO>, ProductDTO>> output = new LinkedList<>();
        Area area = docCont.getArea(doc);
        List<Product> allproductsFromDoc = docCont.getProducts(doc);//get all products from document

        for (Product p : allproductsFromDoc) {
            List<SupplierDTO> supplierList = controllerShops.returnAvaliableSupplier(p, area).stream().map(DTOFactory::createSupplierDTO).collect(Collectors.toList());
            Tuple<List<SupplierDTO>, ProductDTO> supAndPro = new Tuple<>(supplierList, DTOFactory.createProductDTO(p));
            output.add(supAndPro);
        }
        return output;
    }
     public List<TruckDTO> getAvaliableTrucks() {
        return driversController.getNotOccupiedTrucks().stream().map(DTOFactory::createTruckDTO).collect(Collectors.toList());
    }
        public List<DriverDTO> getAvaliableDrivers(int truckLicensePlate) throws Exception {
        Optional<Truck> truckOp=driversController.getTrucks().stream().filter(x -> x.getLicensePlate() == truckLicensePlate).findFirst();
        if(truckOp.isEmpty()){
            throw new Exception("no truck with this plate");
        }
        return driversController.getCompatibleDrivers(truckOp.get()).stream().map(DTOFactory::createDriverDTO).collect(Collectors.toList());

    }

    */

    private List<Tuple<List<Supplier>, Product>> returnAvaliableSuppliersP(int doc) {
        List<Tuple<List<Supplier>, Product>> output = new LinkedList<>();
        Area area = docCont.getArea(doc);
        List<Product> allproductsFromDoc = docCont.getProducts(doc);//get all products from document

        for (Product p : allproductsFromDoc) {

            List<Supplier> supplierList = controllerShops.returnAvaliableSupplier(p, area);
            Tuple<List<Supplier>, Product> supAndPro = new Tuple<>(supplierList, p);
            output.add(supAndPro);

        }
        return output;
    }
    public  String returnAvaliableSupplierString(int doc) {
        List<Tuple<List<Supplier>, Product>> listTuple = returnAvaliableSuppliersP(doc);
        String acc = "";

        for (Tuple<List<Supplier>, Product> tup : listTuple) {

            acc = acc + tup.y.getId() + ", " + tup.y.getName() + ":\n";
            if (tup.x == null)
                acc = acc + " there are no supplier for this product in that area\n";
            else {
                for (Supplier sp : tup.x) {
                    acc = acc + "    " + sp.toString();
                }
            }
        }
        return acc;
    }

    private List<Truck> getAvaliableTrucksP() {
        return driversController.getTrucks();
    }
    public String getTrucksString(){
        return buildListToString(getAvaliableTrucksP());
    }
    public void addTruck(int doc, int truckLicensePlate) throws Exception{

        Optional<Truck> truck = driversController.getTrucks().stream().filter(x -> x.getLicensePlate() == truckLicensePlate).findFirst();
        if (!truck.isPresent()) {
            throw new Exception("truck not found");
        }
        if(docCont.getTruck(doc)!=null){
            throw new Exception("document already contain truck");
        }
        if(docCont.getDriver(doc)!=null){
            driversController.connectDriverAndTruck( docCont.getDriver(doc),truck.get());
        }
        docCont.addTruck(doc,truck.get());
    }
    public void addDriver(int doc, int driverId) throws Exception {
        if(docCont.getDriver(doc)!=null){
            throw new Exception("document already contain Driver");
        }
        //todo need to get all drivers
        Driver driver = driversController.getDrivers(driverId,);


        if(docCont.getTruck(doc)!=null){
            driversController.connectDriverAndTruck(driver, docCont.getTruck(doc));
        }

        docCont.addDriver(doc,driver);
    }
    public void addTruckAndDriver(int doc, int truckLicensePlate, int DriverId) throws Exception {//connect driver and truck ONLY in the driverController
        Optional<Truck> truck = driversController.getTrucks().stream().filter(x -> x.getLicensePlate() == truckLicensePlate).findFirst();
        if (!truck.isPresent()) {
            throw new Exception("truck not found");
        }
        
        Optional<Driver> driver = driversController.getDrivers().stream().filter(x -> x.getId() == DriverId).findFirst();
        if (!driver.isPresent()) {
            throw new Exception("driver not found");
        }
        driversController.connectDriverAndTruck(driver.get(), truck.get());
    }
    private List<Driver> getAvaliableDriversP(int truckLicensePlate) throws Exception {
        Optional<Truck> truckOp=driversController.getTrucks().stream().filter(x -> x.getLicensePlate() == truckLicensePlate).findFirst();
        if(!truckOp.isPresent()){
            throw new Exception("no truck with this plate");
        }
        return driversController.getCompatibleDrivers(truckOp.get());
    }

    public String getDriversString(int truckLicensePlate) throws Exception {
        return buildListToString(getAvaliableDriversP(truckLicensePlate));
    }
    public void addWeightWhenLeaving(int doc, double truckDepartureWeight) throws Exception {
        int truckMaxWeight = docCont.getTruck(doc).getTruckType().getMaxWeight();
        if (truckMaxWeight < truckDepartureWeight) {
            throw new Exception("truck overweight");
        }
        docCont.addWeightWhenLeaving(doc,  truckDepartureWeight);
    }
    public void editTransDate(int doc, Date transDate) {
        docCont.editTransDate(doc, transDate);
    }
    public void editLeftOrigin(int doc, Date LeftOrigin) {
        docCont.editLeftOrigin(doc, LeftOrigin);
    }
    public void setOrigin (int doc, int store) throws Exception {
        Optional<Store> s = controllerShops.getStoreList().stream().filter(r -> r.getId() == store).findFirst();
        docCont.setOrigin(doc,s.get());

    }
    public void setTransportDate(int doc, Date date){
        docCont.setTranportDate(doc,date);

    }
    public void setDepartureTime(int doc, Date date){
        docCont.setDepartureTime(doc,date);
    }
    public void editDocTruck(int doc, int tkId) throws Exception {
        Optional<Truck> newTruck = driversController.getTrucks().stream().filter(c -> c.getLicensePlate() == tkId).findFirst();
        if (!newTruck.isPresent()) {
            throw new Exception("no truck with this id");
        }
        Driver Driver = docCont.getDriver(doc);
        if(Driver!=null){
            License DriverLicense = Driver.getLicense();
            if (!newTruck.get().getTruckType().getLicensesForTruck().contains(DriverLicense)) {
                throw new Exception("new truck not have a compatible license to Driver");
            }
           //driversController.changeTruck(newTruck.get(), docCont.getTruck(doc));
        }

        docCont.editTruck(doc, newTruck.get());
    }
    public void editDocDriver(int doc, int drId) throws Exception {

        Optional<Driver> newDriver = driversController.getNotOccupiedDrivers().stream().filter(c -> c.getId() == drId).findFirst();
        if (!newDriver.isPresent()) {
            throw new Exception("no truck with this id");
        }
        if (!docCont.getTruck(doc).getTruckType().getLicensesForTruck().contains(newDriver.get().getLicense())) {
            throw new Exception("new driver not have a compatible license to Truck");
        }
       // driversController.changeDriver(newDriver.get(),docCont.getDriver(doc) );
        docCont.editDriver(doc, newDriver.get());
    }
    public void editOrigin(int doc, int origenStoreId) throws Exception {
        Optional<Store> newStore = controllerShops.getStoreList().stream().filter(x -> x.getId() == origenStoreId).findFirst();
        if (!newStore.isPresent()) {
            throw new Exception("no store with this Id");
        }
        if (!(newStore.get().getArea() == docCont.getArea(doc))) {
            throw new Exception("new store outside of delivery area");
        }

        docCont.editOrigin(doc, newStore.get());
    }
    public void editTruckWeightDep(int doc, double trWeight) throws Exception {
        int truckMaxWeight = docCont.getTruck(doc).getTruckType().getMaxWeight();
        if (truckMaxWeight < trWeight) {
            throw new Exception("new Truck Weight is over Truck Max");
        }
        docCont.editTruckWeightDep(doc, trWeight);

    }
    public static <T> String buildListToString(List<T> lt) {
        String acc="";
        for(T var:lt){
            acc=acc+var.toString();
        }
        return acc;
    }
    public String getAllStores(){

        return buildListToString(controllerShops.getStoreList());
    }
    public String getAllProducts(){
        return buildListToString(controllerShops.getProductList());
    }
    public String getAllSuppliers(){
        return controllerShops.getSupplierList().toString();
    }
    public void loadData()  {

try {
    ControllerShops cs = this.controllerShops;
    Supplier sp1 = new Supplier("Rami   Levi", 2, "0507133213", "Rami Ach", Area.B);
    Supplier sp2 = new Supplier("Shufersal", 3, "050713123213", "Shuf Ach", Area.A);
    Supplier sp3 = new Supplier("Mega", 4, "0501231231", "meg Ach", Area.A);
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

    cs.addProductToSupp(sp1, pr1);
    cs.addProductToSupp(sp1, pr2);
    cs.addProductToSupp(sp1, pr3);
    cs.addProductToSupp(sp1, pr4);
    cs.addProductToSupp(sp2, pr1);
    cs.addProductToSupp(sp2, pr2);
    cs.addProductToSupp(sp2, pr3);
    cs.addProductToSupp(sp3, pr4);
    cs.addProductToSupp(sp3, pr1);
    cs.addProductToSupp(sp3, pr2);
    cs.addProductToSupp(sp4, pr3);
    cs.addProductToSupp(sp4, pr4);
    cs.addProductToSupp(sp4, pr1);
    cs.addProductToSupp(sp4, pr2);


    cs.addProduct(pr1);
    cs.addProduct(pr2);
    cs.addProduct(pr3);
    cs.addProduct(pr4);


    Store st1 = new Store("shufersal 1", 2, "0501231213", "mr man", Area.A);
    Store st2 = new Store("shufersal 2", 3, "0501465213", "mr man", Area.B);
    Store st3 = new Store("shufersal 3", 4, "0507553113", "mr man", Area.B);
    Store st4 = new Store("shufersal 4", 5, "0507993113", "mr man", Area.B);
    Store st5 = new Store("shufersal 5", 6, "0501299913", "mr man", Area.C);

    cs.addStore(st1);
    cs.addStore(st2);
    cs.addStore(st3);
    cs.addStore(st4);


    DriversController drivers = this.driversController;
    Driver dr = new Driver("Dan", 209889510, License.typeA);
    drivers.AddNewDriver("Guy", 208750760, License.typeA);
    drivers.AddNewDriver("Dan", 209889510, License.typeA);
    drivers.AddNewDriver("Lebron James", 986750760, License.typeB);
    drivers.AddNewDriver("Steph Curry", 308450560, License.typeB);
    drivers.AddNewDriver("Omri Caspi", 208750760, License.typeC);
    drivers.AddNewDriver("Deni Avdija", 208750760, License.typeC);

    List<License> licenseList1 = new LinkedList<License>();
    licenseList1.add(License.typeA);
    licenseList1.add(License.typeB);
    List<License> licenseList2 = new LinkedList<License>();
    licenseList2.add(License.typeA);
    licenseList2.add(License.typeB);
    licenseList2.add(License.typeC);
    List<License> licenseList3 = new LinkedList<License>();
    licenseList3.add(License.typeA);

    Truck tk = new Truck("The Tank", 421652160, new TruckType("honda", 2000, 700, licenseList3));
    drivers.AddNewTruck("The Tank", 421652160, new TruckType("honda", 2000, 700, licenseList3));
    drivers.AddNewTruck("The Hunk", 421652160, new TruckType("honda", 2000, 700, licenseList3));
    drivers.AddNewTruck("Mad Max", 421652160, new TruckType("mazda", 3000, 1000, licenseList1));
    drivers.AddNewTruck("Big Daddy", 421652160, new TruckType("honda", 4000, 1200, licenseList2));

    DocCont docContro = this.docCont;

    Date date = new SimpleDateFormat("dd/MM/yyyy").parse("11/03/1998");


    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    Date date2 = formatter.parse("03-11-1998 16:32:08");
    docCont.newDelivery();
    docCont.setDepartureTime(0, date2);
    docCont.setTranportDate(0, date);
    addDriver(0, 208750760);
    addTruck(0, 421652160);

    setOrigin(0, st2.getId());
    addStore(0, st3.getId(), 1);
    addStore(0, st4.getId(), 2);
    addSupplier(0, sp1.getId(), 3);
    addWeightWhenLeaving(0, 1500);
    addProductsToDoc(0, new Tuple<>(2, 3), st3.getId());
    addProductsToDoc(0, new Tuple<>(3, 1), st4.getId());




}catch(Exception e){

}
    }
}
