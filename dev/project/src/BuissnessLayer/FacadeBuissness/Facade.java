package BuissnessLayer.FacadeBuissness;

import BuissnessLayer.*;
import BuissnessLayer.OrderBuissness.Order;
import BuissnessLayer.OrderBuissness.OrderControler;
import BuissnessLayer.Product;
import BuissnessLayer.FacadeBuissness.ResponseObjects.*;
import BuissnessLayer.SupplierBuissness.Contact;
import BuissnessLayer.SupplierBuissness.ISupplier;
import BuissnessLayer.SupplierBuissness.SuppliersControler;

import java.time.LocalDate;
import java.util.*;

public class Facade implements FacadeInterface {
    private static Facade single_instance = null;
    private SuppliersControler suppliersControler;
    private OrderControler orderControler;


    private Facade(){
        AgreementManager agreementManager=new AgreementManager();
        suppliersControler = new SuppliersControler(agreementManager);
        orderControler = new OrderControler(agreementManager);
    }

    public static Facade getInstance()
    {
        if (single_instance == null)
            single_instance = new Facade();
        return single_instance;
    }

    //---------------------------------------------------------------------------------------------------
    //in this section we represent all the functionality of the system
    //---------------------------------------------------------------------------------------------------
    @Override
    public Response addNewSupplier(int id, String name, String bankAccount, paymentMethods paymentMethods, DeliveryMode deliveryMode, List<Integer> daysOfDelivery, int NumOfDaysFromDelivery,String contactName,String contactEmail,String phoneNumber,double Price, int CatalogID, String manfucator, String productName)
    {
        try {
            Product product=new Product(Price,CatalogID,manfucator,productName,0);
            suppliersControler.addNewSupplier(id,name,bankAccount,paymentMethods,deliveryMode,daysOfDelivery,NumOfDaysFromDelivery,contactName,contactEmail,phoneNumber,product);
            return new Response();
            }
            catch (Exception e){
            return new Response(e.getMessage());
            }

    }

    @Override
    public Response removeSupplier(int SupId) {
        try {
            suppliersControler.RemoveSupplier(SupId);
            orderControler.RemoveAllSupllierConstOrders(SupId);
            return new Response();
        }
        catch (Exception e){
            return new Response(e.getMessage());
        }
    }

    @Override
    public Response setSupplierPayment(paymentMethods paymentMethods, int SupplierId) {
        try {
            suppliersControler.SetPayment(paymentMethods, SupplierId);
            return new Response();
        }
        catch (Exception e){
            return new Response(e.getMessage());
        }
    }
//SetDeliveryMode()
    @Override
    public Response SetDeliveryMode(int SupId,DeliveryMode deliveryMods, List<Integer> daysOfDelivery,int numOfDaysFromOrder) {
    try {
        suppliersControler.SetDeliveryMode(SupId,deliveryMods, daysOfDelivery,numOfDaysFromOrder);
        return new Response();
    }
    catch (Exception e){
        return new Response(e.getMessage());
    }
}

    @Override
    public Response addNewProductToAgreement(int SupplierId,double Price, int CatalogID, String manfucator, String name) {
        try {
            suppliersControler.addNewProductToAgreement(SupplierId,Price,CatalogID,manfucator,name);
            return new Response();
        }
        catch (Exception e){
            return new Response(e.getMessage());
        }
    }

    @Override
    public Response removeProductFromSupplier(int SupId, int CatalogID) {
        try {
            suppliersControler.removeProductFromSupplier(CatalogID,SupId);
            return new Response();
        }
        catch (Exception e){
            return new Response(e.getMessage());
        }
    }
    //            facade.removeProductFromOrder(id,catalogId);
    @Override
    public Response RemovePrudactFromOrders(int SupId, int CatalogID) {
        try {
            orderControler.RemovePrudactFromOrders(SupId,CatalogID);
            return new Response();
        }
        catch (Exception e){
            return new Response(e.getMessage());
        }
    }


    @Override
    public Response setProductPrice(int SupId, int CatalogID, double price) {
        try {
            suppliersControler.setProductPrice(SupId,CatalogID,price);
            orderControler.ReCalculateTotalPayment(SupId);
            return new Response();
        }
        catch (Exception e){
            return new Response(e.getMessage());
        }
    }

    @Override
    public Response addNewDiscountByQuantitiyToProduct(int SupId, int CatalogID, int Quantitiy, double newPrice) {
        try {
            suppliersControler.addNewDiscountByQuantitiyToProduct(SupId,CatalogID,Quantitiy,newPrice);
            orderControler.ReCalculateTotalPayment(SupId);
            return new Response();
        }
        catch (Exception e){
            return new Response(e.getMessage());
        }
    }
    @Override
    public Response setExtraDiscountToSupplier(int SupId, int ExtraDiscount) {
        try {
            suppliersControler.setExtraDiscountToSupplier(SupId,ExtraDiscount);
            orderControler.ReCalculateTotalPayment(SupId);
            return new Response();
        }
        catch (Exception e){
            return new Response(e.getMessage());
        }
    }

    @Override
    public Response addNewContactMember(int SupId, String contactName, String contactEmail, String phoneNumber) {
        try {
            suppliersControler.addNewContact(SupId,contactName,contactEmail,phoneNumber);
            return new Response();
        }
        catch (Exception e){
            return new Response(e.getMessage());
        }
    }

    @Override
    public ResponseT getSupplier(int SupId) {
        try {
            ISupplier supplier=suppliersControler.getSupplier(SupId);
            SupplierResponse sr=copySupplier(supplier);
            return new ResponseT(sr);
        }
        catch (Exception e){
            return new ResponseT(e.getMessage(),null);
        }
    }

    private SupplierResponse copySupplier(ISupplier supplier){
        int id=supplier.getId();
        String bankAcount=supplier.getBankAccount();
        String name=supplier.getSupplierName();
        List<Contact> contacts=supplier.getListOfContacts();
        paymentMethods paymentMethods=supplier.getPayment();
        // copy al contacts
        List<contactResponse> contactsResponse=new LinkedList<>();
        for(int i=0;i<contacts.size();i++){
            contactResponse cr=new contactResponse(contacts.get(i).getId(),contacts.get(i).getContactName(),contacts.get(i).getPhoneNumber(),contacts.get(i).getEmail());
            contactsResponse.add(cr);
        }
        SupplierResponse sr=new SupplierResponse(id,name,bankAcount,paymentMethods,contactsResponse);
        return sr;
    }



    @Override
    public ResponseT getAllSuppliers() {
        try {
            List<ISupplier> listOfSupplier=suppliersControler.getAllSuppliers();
            List<SupplierResponse> supplierResponseList=new LinkedList<>();
            for (ISupplier supplier:listOfSupplier
                 ) {
                SupplierResponse supplierResponse=copySupplier(supplier);
                supplierResponseList.add(supplierResponse);
            }
            return new ResponseT(supplierResponseList);
        }
        catch (Exception e){
            return new ResponseT(e.getMessage(),null);
        }
    }

    @Override
    public ResponseT getAgreement(int SupId) {
        try {
            IAgreement agreement=suppliersControler.getAgreement(SupId);
            AgreementResponse AG=copyAgreement(agreement);
            return new ResponseT(AG);
        }
        catch (Exception e){
            return new ResponseT(e.getMessage(),null);
        }
    }

    @Override
    public Response RemoveContact(int SupId,int ContactID) {
        try {
            suppliersControler.removeContact(SupId,ContactID);
            return new Response();

        }catch (Exception e){
            return new Response(e.getMessage());

        }
    }


    private AgreementResponse copyAgreement(IAgreement agreement){
        List<Product> productList=new ArrayList<Product>(agreement.getProducts().values());
        List<productResponse> productResponseList=new LinkedList<>();
        for (Product p:productList
             ) {
            productResponseList.add(copyProduct(p));
        }//    private List<DeliveryMode> deliveryModes ;

        int ExtraDiscount=agreement.getExtraDiscount();
        int SupplierId=agreement.getSupplierID();
        DeliveryMode deliveryModes=agreement.getDeliveryMode();

        List<Integer> daysofDelivery=new LinkedList<>();
        if(deliveryModes==DeliveryMode.DeliveryByDay){
        for (Integer day:agreement.getDaysOfDelivery()
             ) {
            daysofDelivery.add(day);
        }
        }
        int numofDaysFromOrde=agreement.getNumOfDaysFromOrder();
        HashMap<Integer,HashMap<Integer,Double>> DiscountProductsRespone=new HashMap<>();
        HashMap<Integer,HashMap<Integer,Double>> DiscountProducts = agreement.getDiscountByProductQuantity();
        for (Integer CatalogId:DiscountProducts.keySet()) {
            HashMap<Integer,Double> disquntquantity=DiscountProducts.get(CatalogId);
            HashMap<Integer,Double> disquntquantityResponse=new HashMap<>();
            for (Integer quantyty:disquntquantity.keySet()
                 ) {
                disquntquantityResponse.put(quantyty,disquntquantity.get(quantyty));
            }
            DiscountProductsRespone.put(CatalogId,disquntquantityResponse);
        }
        AgreementResponse agreementResponse=new AgreementResponse(DiscountProductsRespone,productResponseList,ExtraDiscount,SupplierId,deliveryModes,daysofDelivery,numofDaysFromOrde);
        return agreementResponse;
        
    }

    @Override
    public Response addNewOrder(int SupId,HashMap<Integer,Integer> productQuantity,boolean isConstant) {
        try {
            orderControler.AddOrder(SupId,productQuantity,isConstant);
            return new Response();
        }
        catch (Exception e){
            return new Response(e.getMessage());
        }
    }

    @Override
    public Response addProductToOrder(int SupId,int OrderId, int CatalogID, int quantity) {
        try {
            orderControler.addProductToOrder(CatalogID,quantity,OrderId,SupId);
            return new Response();
        }
        catch (Exception e){
            return new Response(e.getMessage());
        }
    }
    @Override
    public Response changeProductQuantityFromOrder(int SupId,int OrderId, int CatalogID, int quantity) {
        try {
            orderControler.changeProductQuantityFromOrder(CatalogID,quantity,OrderId,SupId);
            return new Response();
        }
        catch (Exception e){
            return new Response(e.getMessage());
        }
    }

    @Override
    public Response RemovDiscountByQuantitiyToProduct(int SupId, int CatalogID, int Quantitiy) {
        try{
            suppliersControler.RemovDiscountByQuantitiyToProduct(SupId,CatalogID,Quantitiy);
            return new Response();

        }catch (Exception e){
            return new Response(e.getMessage());
        }
    }

    @Override
    public Response removeOrder(int OrderID) {
        try {
            orderControler.RemoveOrder(OrderID);
            return new Response();
        }
        catch (Exception e){
            return new Response(e.getMessage());
        }

    }

    @Override
    public ResponseT getOrder(int OrderID) {
        try {
            Order order=orderControler.GetOrder(OrderID);
            orderResponse orderResponse=copyOrder(order);
            return new ResponseT(orderResponse);
        }
        catch (Exception e){
            return new ResponseT(e.getMessage(),null);
        }
    }

    @Override
    public ResponseT getAllOrders() {
        try {
            List<Order> orderList=orderControler.getAllOrders();
            List<orderResponse> orderResponseList=new LinkedList<>();
            for (Order order:orderList
                 ) {
                orderResponseList.add(copyOrder(order));
            }
            return new ResponseT(orderResponseList);
        }catch (Exception e){
            return new ResponseT(e.getMessage(),null);
        }
    }

    private productResponse copyProduct(Product product){
        int id=product.getId();
        double price=product.getPrice();
        int catalogID=product.getCatalogID();
        String manfucator=product.getManfucator();
        String name=product.getName();
        productResponse orderResponse=new productResponse(price,catalogID,id,manfucator,name);
        return orderResponse;
    }

    private orderResponse copyOrder(Order order){
        int id=order.GetId();
        int supplierId=order.getSupplierID();

        HashMap<Integer,Integer> quantity=new HashMap<>();
        for (Integer key:order.getProductQuantity().keySet()
             ) {
            quantity.put(key,order.getProductQuantity().get(key));
        }
        double TotalPayment=order.GetTotalPayment();
        LocalDate dateTime=order.GetDateTime();

        orderResponse orderResponse=new orderResponse(id,supplierId,dateTime,quantity,TotalPayment,order.isConstant());
        return orderResponse;
    }





}
