package PresentationLayer;

import BuisnnesLayer.Controlls.Sales_Controller;
import BuisnnesLayer.Controlls.Stock_Controller;
import BuisnnesLayer.DeliveryMode;
import BuisnnesLayer.FacedeModel.Objects.Response;
import BuisnnesLayer.FacedeModel.facade;
//import BuissnessLayer.DeliveryMode;
//import BuissnessLayer.FacadeBuissness.Facade;
import BuisnnesLayer.ProductSupplier;
import BuisnnesLayer.paymentMethods;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class LoadData {
    facade facade;
    private Stock_Controller stockC;
    private Sales_Controller salesC;
    public LoadData(){
        facade=facade.getInstance();
        stockC = facade.getStockC();
        salesC = facade.getSalesC();


    }

    public void LoadALLData() throws Exception {
//        LoadSupplier1();
//        LoadSupplier2();
//        //add new order
//        HashMap<Integer,Integer> productQuantity=new HashMap<>();
//        productQuantity.put(111,1000);
//        productQuantity.put(222,1000);
//        //facade.addNewOrder(123,productQuantity,true);
//        //set new extra discount
//        facade.setExtraDiscountToSupplier(123,10);
//       // setSales();
//        setProducts();
        //setSales();
        facade.addNewSupplier(123,"Drinks123","88899988", paymentMethods.Cash, DeliveryMode.Pickup,null,-1,"alon","alon@gmail.com","058967411");
       facade.addNewProductToAgreement(123,5,222,"Cola","nesti","drink",1,false);



    }
    public void LoadSupplier1(){
        //add new Supplier to the system
        //    publi           (int id,     String name,   String bankAccount,    paymentMethods   ,    DeliveryMode ,    List<Integer> ,     NumOfDaysFromDelivery, String contactName,             contactEmail, String phoneNumber)
//        facade.addNewSupplier(123,"Drinks123","88899988", paymentMethods.Cash, DeliveryMode.Pickup,null,-1,"alon","alon@gmail.com","058967411");
//        //add new contact members to the supplier
//        facade.addNewContactMember(123,"daniel","daniel@gmail.com","0526767222");
//        facade.addNewContactMember(123,"avi","avi@gmail.com","0589654766");
//        //add new products too supplier 123
//        //    public Response ement(int SupplierId,double Price, int CatalogID, String manfucator, String name,String category,int pid, boolean isexsist) {
//        facade.addNewProductToAgreement(123,5,111,"Cola","Kola Zero","food",1,false);
//        facade.addNewProductToAgreement(123,4,222,"Cola","Coca Kola","food",2,false);
//        // add discount bu quantity
//        facade.addNewDiscountByQuantitiyToProduct(123,111,500,4.5);
//
//        HashMap<Integer,Integer> productQuantity=new HashMap<>();
//        productQuantity.put(111,1000);
//        productQuantity.put(222,1000);
//        //facade.addNewOrder(123,productQuantity,true);
//        //set new extra discount
//        facade.setExtraDiscountToSupplier(123,10);
    }


    public void LoadSupplier2(){
//        List DaliveryByDAY=new LinkedList();
//        DaliveryByDAY.add(1);
//        DaliveryByDAY.add(3);
//        //add new Supplier to the system
//        facade.addNewSupplier(888,"yerkyerk","769852398", paymentMethods.BankTransfers, DeliveryMode.DeliveryByDay,DaliveryByDAY,-1,"ben","ben@gmail.com","0539854788");
//        //add new contact members to the supplier
//        facade.addNewContactMember(888,"asi","asi@gmail.com","0526568222");
//        facade.addNewContactMember(888,"avner","avner@gmail.com","0589652656");
//        //add new products too supplier 123
//        facade.addNewProductToAgreement(888,5,333,"yerkDarom","apple","blala",1,false);
//        facade.addNewProductToAgreement(888,4,2212,"yerkDarom","banana","blala",1,false);
//        // add discount bu quantity
//        facade.addNewDiscountByQuantitiyToProduct(888,333,1000,2);
//
//        HashMap<Integer,Integer> productQuantity=new HashMap<>();
//        productQuantity.put(333,1000);
//        productQuantity.put(2212,1000);
//        Response r=facade.addNewOrder(888,productQuantity,true,1);
//        System.out.println(r.getErrorMsg());
    }

    private void setSales() throws Exception {
//        LinkedList<String> categories1 = new LinkedList<>();
//        LinkedList<String> categories2= new LinkedList<>();
//        LinkedList<String> products1 = new LinkedList<>();
//        LinkedList<String> products2 = new LinkedList<>();
//        categories1.add("snacks");
//        salesC.createSaleByCategory(10.0, "snacks for all", facade.getDate("2021-03-12"), facade.getDate("2021-06-01"), categories1);
//        categories2.add("dairies");
//        salesC.createSaleByCategory(25.0, "Dare with dairies", facade.getDate("2021-03-12"), facade.getDate("2021-06-07"), categories2);
//        products1.add("milk");
//        salesC.createSaleByProduct(10.0, "the milky day", new Date(), facade.getDate("2021-05-01"), products1);
//        products2.add("arak");
//        products2.add("wine");
//        salesC.createSaleByCategory(20.0, "drinks on me", facade.getDate("2021-01-12"), facade.getDate("2021-05-15"), products2);
    }


    private void setCategories() throws Exception {
//        stockC.createNewCategory("meat");
//        stockC.createNewCategory("food");
//        stockC.createNewCategory("dairies");
//        stockC.createNewCategory("snacks");
//        stockC.createNewCategory("drinks");
//        stockC.createNewCategory("alcohol");
//        stockC.createNewCategory("gummy-bears");
//        stockC.createNewCategory("chocolates");
//        stockC.set_father("meat", "food");
//        stockC.set_father("dairies", "food");
//        stockC.set_father("snacks", "food");
//        stockC.set_father("drinks", "food");
//        stockC.set_father("alcohol", "drinks");
//        stockC.set_father("gummy-bears", "snacks");
//        stockC.set_father("chocolates", "snacks");
    }//    public LinkedList<Integer> addItems(Integer product_id, Integer quantity, String location, Date
//    public LinkedList<Integer> addItems(Integer product_id, Integer quantity, String location, Date
//            supplied_date, Date creation_date, Date expiration_date) throws Exception {
//public Response add_product_items(Integer product_id, Integer quantity, String location, String supplied_date, String creation_date, String expiration_date) {
//        ProductManager.addProduct(product_name,product_id,  manufacturer_name,  min_amount,   cat,  selling_price, productSupplier);

    //    public void addProduct(String product_name, Integer product_id, String manufacturer_name, Integer min_amount,  String cat, Double selling_price,ProductSupplier productSupplier) throws Exception {
    private void setProducts() throws Exception {
        //        facade.addNewProductToAgreement(123,5,111,"Cola","Kola Zero","food",1,false);
//        facade.update_product_min_amount(1,5);
//        facade.update_sale_description(1,"lalala");
       // stockC.addItems(1,100,)
       // facade.s
        //facade.update();

       // ProductSupplier p=new ProductSupplier(5,111,1,"cola");
       // facade.addProduct("Milk",1, "Tara", 2, "food", 6.90, p);
//        stockC.add_product_items(2, "Milk", "Tnuva", 2, 6.87, "dairies", 8.90);
//        facade.addProduct(3, "Sussages", "Meato", 3, 7.87, "meat", 11.80);
//        stockC.addProduct(4, "Gummy-Snakes", "yummy", 3, 5.9, "gummy-bears", 6.90);
//        stockC.addProduct(5, "ChocolateBalls", "yummy", 3, 7.9, "chocolates", 8.90);
//        stockC.addProduct(6, "Arak", "Halili", 2, 65.0, "alcohol", 74.90);
//        stockC.addProduct(7, "Vodka", "Respect", 2, 69.99, "alcohol", 81.0);
//        stockC.addProduct(8, "Wine", "Galili", 2, 32.0, "alcohol", 37.50);
//        stockC.addProduct(9, "greekCheese", "Tara", 3, 10.90, "dairies", 13.90);
//        stockC.addProduct(10, "yellowCheese", "Tara", 2, 11.90, "dairies", 12.9);
//        stockC.addProduct(11, "bamba", "Osem", 3, 3.90, "snacks", 4.4);
    }//    public Response add_product_items(Integer product_id, Integer quantity, String location, String supplied_date, String creation_date, String expiration_date) {
      //  return inventModel.add_product_items(product_id,quantity,location,supplied_date,creation_date,expiration_date);
//    public Response addNewProductToAgreement(int SupplierId,double Price, int CatalogID, String manfucator, String name,String category,int pid, boolean isexist) {
//    return supModel.addNewProductToAgreement(SupplierId,Price,CatalogID,manfucator,name,category,pid,isexist);
//    }


    private void setItems() throws Exception {
//        stockC.addItems(1, 3, "store_1_c", facade.getDate("2021-03-12"), facade.getDate("2021-03-10"), facade.getDate("2021-05-01"));
//        stockC.addItems(2, 2, "store_1_c", facade.getDate("2021-03-12"), facade.getDate("2021-03-09"), facade.getDate("2021-05-01"));
//        stockC.addItems(3, 3, "store_1_b", facade.getDate("2021-03-12"), facade.getDate("2021-03-02"), facade.getDate("2021-05-01"));
//        stockC.addItems(4, 1, "store_2_a", facade.getDate("2021-03-12"), facade.getDate("2021-02-20"), facade.getDate("2021-05-03"));
//        stockC.addItems(4, 1, "storage", facade.getDate("2021-03-12"), facade.getDate("2021-02-20"), facade.getDate("2021-05-03"));
//        stockC.addItems(5, 1, "storage", facade.getDate("2021-03-12"), facade.getDate("2021-03-04"), facade.getDate("2021-05-02"));
//        stockC.addItems(6, 1, "store_5_a", facade.getDate("2021-03-12"), facade.getDate("2021-03-07"), facade.getDate("2021-05-01"));
//        stockC.addItems(7, 1, "store_5_b", facade.getDate("2021-03-12"), facade.getDate("2021-03-04"), facade.getDate("2021-05-17"));
//        stockC.addItems(8, 1, "store_5_c", facade.getDate("2021-03-12"), facade.getDate("2021-03-01"), facade.getDate("2021-05-21"));
//        stockC.addItems(8, 1, "storage", facade.getDate("2021-03-12"), facade.getDate("2021-03-01"), facade.getDate("2021-05-21"));
//        stockC.addItems(9, 2, "store_1_d", facade.getDate("2021-03-12"), facade.getDate("2021-02-25"), facade.getDate("2021-05-30"));
//        stockC.addItems(10, 1, "store_1_d", facade.getDate("2021-03-12"), facade.getDate("2021-02-28"), facade.getDate("2021-05-01"));
//        stockC.addItems(11, 2, "store_2_c", facade.getDate("2021-03-12"), facade.getDate("2021-03-05"), facade.getDate("2021-05-01"));
//        stockC.addItems(11, 1, "storage", facade.getDate("2021-03-12"), facade.getDate("2021-03-05"), facade.getDate("2021-05-01"));
    }




}
