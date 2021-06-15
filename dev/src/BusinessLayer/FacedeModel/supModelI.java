package BusinessLayer.FacedeModel;

import BusinessLayer.DeliveryMode;
import BusinessLayer.FacedeModel.Objects.Response;
import BusinessLayer.FacedeModel.Objects.ResponseT;
import BusinessLayer.GeneralProduct;
import BusinessLayer.paymentMethods;

import java.util.HashMap;
import java.util.List;

public interface supModelI {
    //---------------------------------------------------------------------------------------------------

    //-----------------------------------SUPPLIER FUNCTIONALITY------------------------------------------

    //this function add new supplier to the system and return a response if exception was thrown
    Response addNewSupplier(int id, String name, String bankAccount, paymentMethods paymentMethods, DeliveryMode deliveryMode, List<Integer> daysOfDelivery, int NumOfDaysFromDelivery, String contactName, String contactEmail, String phoneNumber);

    //this function remove a supplier from the system this function return response if exception was thrown
    Response removeSupplier(int SupId);

    //this function sets supplier payment this function return a response if exception throwm
    Response setSupplierPayment(paymentMethods paymentMethods, int SupplierId);

    //this function add new Product to the agreement of the supplier this function return a response if exception thrown
    Response addNewProductToAgreement(int SupplierId, double Price, int CatalogID, String manfucator, String name, String category, int pid, boolean isexsist);

    //this function remove an Product from the supplier agreement this function return a response if exception was thrown
    Response removeProductFromSupplier(int SupId, int CatalogID);

    //this function set the price of specific Product in the supplier agreement
    Response setProductPrice(int SupId, int CatalogID, double price);

    //this function adds a new discount to Product in the supplier agreement. this function return response if exception thrown
    Response addNewDiscountByQuantitiyToProduct(int SupId, int CatalogID, int Quantitiy, double newPrice);

    Response setExtraDiscountToSupplier(int SupId, int ExtraDiscount);


    //this function adds a new contact member to supplier. this function return response if exception thrown
    Response addNewContactMember(int SupId, String contactName, String contactEmail, String phoneNumber);

    //this function return response of specific Supplier
    ResponseT getSupplier(int SupId);

    //this function return response of all suppliers in the system
    ResponseT getAllSuppliers();

    //this function return response with the agreement ogf the specified supplier
    ResponseT getAgreement(int SupId);

    Response RemoveContact(int SupId, int ContactID);

    Response SetDeliveryMode(int SupId, DeliveryMode deliveryMods, List<Integer> daysOfDelivery, int numOfDaysFromOrder);

    Response create_order_Due_to_lack(HashMap<GeneralProduct, Integer> lackMap, Integer constantorderdayfromdelivery);


    //---------------------------------------------------------------------------------------------------

    //-----------------------------------ORDER FUNCTIONALITY---------------------------------------------


    //this function add a new order to the system this function return a response if exception thrown
    Response addNewOrder(int SupId, HashMap<Integer, Integer> productQuantity, boolean isConstant, Integer constantorderdayfromdelivery);

    //this function remove an order from the system
    Response removeOrder(int SupId, int supID);

    //this function return a response of specific order
    ResponseT getOrder(int OrderId, int SupId);

    //this function return a response of all the orders in the system
    ResponseT getAllOrders();

    Response addProductToOrder(int SupId, int OrderId, int CatalogID, int quantity);


    Response changeProductQuantityFromOrder(int SupId, int OrderId, int CatalogID, int quantity);

    Response RemovDiscountByQuantitiyToProduct(int SupId, int CatalogID, int Quantitiy);

    Response RemovePrudactFromOrders(int SupId, int CatalogID);

}
