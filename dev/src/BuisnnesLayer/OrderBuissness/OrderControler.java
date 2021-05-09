package BuisnnesLayer.OrderBuissness;

import BuisnnesLayer.AgreementManager;
import BuisnnesLayer.IAgreement;
import BuisnnesLayer.IdentityMap;
import BuisnnesLayer.ProductSupplier;
import DataLayer.DataController;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderControler {
    //private static OrderControler single_instance = null;
   // HashMap<Integer, List<Integer>> ProductsOrderedFromSupplier ;//+ ProductsOrderedFromSupplier : HashMap<supplierID:int , List<CatalogID:int>>
    private HashMap<Integer, Order> Orders ;//- Orders: hashMap<OrderID: int, order: Order>
    private AgreementManager agreementManager;
    private int idOrderCounter;

//    private HashMap<Integer, HashMap<Integer, Integer>> DiscountByProductQuantity;//- DiscountByProductQuantity hashMap<CatalogID:int, hashMap<quantitiy :int , newPrice:int>>
    public List<Order> getAllOrders(){

        return new ArrayList<Order>(Orders.values());
    }
    public OrderControler(AgreementManager agreementManager){
       // ProductsOrderedFromSupplier=new HashMap<>();
        Orders=new HashMap<>();
        this.agreementManager=agreementManager;
        idOrderCounter=0;
    }

    public void AddOrder(int SupId,HashMap<Integer,Integer> productQuantity,boolean isConstant,Integer constantorderdayfromdelivery){
        if(!CheckIfSupplierExist(SupId)){
            throw new IllegalArgumentException("the supplier is not exist in system");
        }
        //now check if all the products exist in the agreement of the supplier
        if(!checkIfAllItemsExistInTheSupplierAgreement(SupId, new ArrayList<Integer>(productQuantity.keySet()))){
            throw new IllegalArgumentException("not all product exist in the Agreement with the supplier");
        }
        if(isConstant){


            Order order = new Order(idOrderCounter, SupId, productQuantity, agreementManager.GetAgreement(SupId),true,constantorderdayfromdelivery);
            Orders.put(idOrderCounter, order);
            add_Order_to_the_data(order);

            idOrderCounter++;
        }
        else {
            Order order = new Order(idOrderCounter, SupId, productQuantity, agreementManager.GetAgreement(SupId),false,constantorderdayfromdelivery);
            Orders.put(idOrderCounter, order);
            add_Order_to_the_data(order);

            idOrderCounter++;
        }

    }
    public void create_order_Due_to_lack( HashMap<Integer, HashMap<Integer, Integer>> cheapest_supplier_products_by_quantity,Integer constantorderdayfromdelivery){
        for (Integer SupId : cheapest_supplier_products_by_quantity.keySet()) {
            AddOrder(SupId,cheapest_supplier_products_by_quantity.get(SupId),false,constantorderdayfromdelivery);
        }
    }


    // check if supplier exist in the agreements
    private boolean CheckIfSupplierExist(int SupId){
        return agreementManager.isSupplierExist(SupId);
    }
    private boolean CheckIfSupplierProvidesThePruduct(int SupId,int productId){
        return (agreementManager.GetAgreement(SupId)).isProductExist(productId);
    }

    // check if items exist in the supplier agrement
    private boolean checkIfAllItemsExistInTheSupplierAgreement(int SupId,List<Integer> productsCtalogId){
        HashMap<Integer, ProductSupplier> AgreemntProduct =agreementManager.GetAgreement(SupId).getProducts();
        for (Integer CatalogId:productsCtalogId
             ) {
            if(!AgreemntProduct.containsKey(CatalogId)){
                return false;
            }
        }
        return true;
    }

    //ok with THE DATA
    public void RemoveOrder(int OrderID,int SupId)
    {
        if(!isExsist(OrderID,SupId)){
            throw new IllegalArgumentException("the order is not in the system");
        }
        Orders.remove(OrderID);
        removeOrderFromTheData(OrderID);
    }

    //it ok withe the data
    public Order GetOrder(int OrderID,int SupId)
    {
        if(!Orders.containsKey(OrderID)){
            Order order=getOrderFromTheData(OrderID,SupId);
            Orders.put(OrderID,order);
            if(order==null){
            throw new IllegalArgumentException("the order is not in the system");
        }
            return order;

        }
        return Orders.get(OrderID);
    }

    //Ok with the DATA
    //after we remoce supliers we shold remove all constant orders of this supplier
    public void RemoveAllSupllierConstOrders(int SupiD){
        for (Order order:Orders.values()
             ) {
           if(order.isConstant() &&order.getSupplierID()==SupiD)
           {
               Orders.remove(order.GetId());
               removeOrderFromTheData(order.GetId());
           }
        }
    }


    private IAgreement GetAgreement(int supplierID)
    {
        return agreementManager.GetAgreement(supplierID);
    }


    public void addProductToOrder(Integer product,Integer Quantity,Integer OrderID,int supId){
        if(!CheckIfSupplierExist(supId)){
            throw new IllegalArgumentException("the supplier is not exist in system");
        }
        if(!isExsist(OrderID,supId)){
            throw new IllegalArgumentException("the order is not in the system");
        }
        //you canot chacg orders that no constant!!!
        if(!Orders.get(OrderID).isConstant()){
            throw new IllegalArgumentException("the Order is not Constant you cannot change order tht done already");
        }
        if(!CheckIfSupplierProvidesThePruduct(supId,product)){
            throw new IllegalArgumentException("the supplier dose not Supply this product");
        }

        Orders.get(OrderID).AddPrudactToOrder(product,Quantity,agreementManager.GetAgreement(supId));
    }
    public void changeProductQuantityFromOrder(Integer product,Integer Quantity,Integer OrderID,int supId){
        if(!CheckIfSupplierExist(supId)){
            throw new IllegalArgumentException("the supplier is not exist in system");
        }
        if(!isExsist(OrderID,supId)){
            throw new IllegalArgumentException("the order is not in the system");
        }
        //you canot chacg orders that no constant!!!
        if(!Orders.get(OrderID).isConstant()){
            throw new IllegalArgumentException("the Order is not Constant you cannot change order tht done already");
        }
        if(!CheckIfSupplierProvidesThePruduct(supId,product)){
            throw new IllegalArgumentException("the supplier dose not Supply this product");
        }
        //if(!(Orders.get(OrderID)).checkIfProductIsAlreadyExist(product)){
         //   throw new IllegalArgumentException("this Product dose not exist in this order");
       // }
       // public void EditProductQuantity(int CatalogID,int quantity,IAgreement agreement)
        Orders.get(OrderID).EditProductQuantity(product,Quantity,agreementManager.GetAgreement(supId));
    }
    public void ReCalculateTotalPayment (int SupId)
    {
        for (Order order:Orders.values())
        {
            order.ReCalculateTotalPayment(agreementManager.GetAgreement(SupId));
        }
    }



    public Boolean isExsist(int OrderId,int SupId){
        if(Orders.containsKey(OrderId)){
            return true;
        }
        Order order=getOrderFromTheData(OrderId,SupId);
        if(order!=null){
            return true;
        }
        return false;
    }


    public void RemovePrudactFromOrders (int SupId, int CatalogID)
    {
        for (Order order:Orders.values())
        {
            if (order.isConstant()&&(order.getSupplierID()==SupId)&&order.checkIfProductIsAlreadyExist(CatalogID)){
            order.RemovePrudactFromOrder(CatalogID,agreementManager.GetAgreement(SupId));
             }
        }
    }
    //================================================DATA===================================

    //insertProduct
    private Order getOrderFromTheData(int orderId,int SupId) {
        IdentityMap im = IdentityMap.getInstance();
        Order order =im.getOrder(orderId);
        if(order!=null){
            return order;
        }
        else {
            DataController dc = DataController.getInstance();
            order = dc.getOrder(SupId, orderId);
            if (order == null) {
                return null;
            }
            return order;
        }
    }

    private void removeOrderFromTheData(int orderId){
        IdentityMap im = IdentityMap.getInstance();
        DataController dc = DataController.getInstance();
        im.removeOrder(orderId);
        boolean isOk=dc.deleteOrder(Orders.get(orderId));
        if(!isOk){
        throw new IllegalArgumentException("canot remove from the datat order that not exsist");
}
    }


    //ADD ORDER
    private void add_Order_to_the_data(Order order) {
        IdentityMap im = IdentityMap.getInstance();
        DataController dc = DataController.getInstance();
        if (!dc.insetOrder(order)) {
            System.out.println("failed to insert Order to the database with the keys");
        }
        im.addOrder(order);
    }

}
