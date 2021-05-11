package BuisnnesLayer.FacedeModel;

import BuisnnesLayer.*;
import BuisnnesLayer.FacedeModel.Objects.*;
import BuisnnesLayer.OrderBuissness.Order;
import BuisnnesLayer.OrderBuissness.OrderControler;
import BuisnnesLayer.SupplierBuissness.Contact;
import BuisnnesLayer.SupplierBuissness.ISupplier;
import BuisnnesLayer.SupplierBuissness.SuppliersControler;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class SupModel implements supModelI {
    private static SupModel single_instance = null;
    private SuppliersControler suppliersControler;
    private OrderControler orderControler;

    SupModel(ProductManager productManager) {
        AgreementManager agreementManager = new AgreementManager(productManager);
        suppliersControler = new SuppliersControler(agreementManager);
        orderControler = new OrderControler(agreementManager);
    }

    public static SupModel getInstance(ProductManager productManager) {
        if (single_instance == null)
            single_instance = new SupModel(productManager);
        return single_instance;
    }

    //---------------------------------------------------------------------------------------------------
    //in this section we represent all the functionality of the system
    //---------------------------------------------------------------------------------------------------
    @Override
    public Response create_order_Due_to_lack(HashMap<GeneralProduct, Integer> lackMap, Integer constantOrderDayFromDelivery) {
        try {

            orderControler.create_order_Due_to_lack(suppliersControler.get_cheapest_supplier(lackMap), constantOrderDayFromDelivery);

            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }

    }


    @Override
    public Response addNewSupplier(int id, String name, String bankAccount, paymentMethods paymentMethods, DeliveryMode deliveryMode, List<Integer> daysOfDelivery, int NumOfDaysFromDelivery, String contactName, String contactEmail, String phoneNumber) {
        try {
            suppliersControler.addNewSupplier(id, name, bankAccount, paymentMethods, deliveryMode, daysOfDelivery, NumOfDaysFromDelivery, contactName, contactEmail, phoneNumber);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }

    }

    @Override
    public Response removeSupplier(int SupId) {
        try {
            suppliersControler.RemoveSupplier(SupId);
            orderControler.RemoveAllSupllierConstOrders(SupId);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    @Override
    public Response setSupplierPayment(paymentMethods paymentMethods, int SupplierId) {
        try {
            suppliersControler.SetPayment(paymentMethods, SupplierId);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    //SetDeliveryMode()
    @Override
    public Response SetDeliveryMode(int SupId, DeliveryMode deliveryMods, List<Integer> daysOfDelivery, int numOfDaysFromOrder) {
        try {
            suppliersControler.SetDeliveryMode(SupId, deliveryMods, daysOfDelivery, numOfDaysFromOrder);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    @Override
    public Response addNewProductToAgreement(int SupplierId, double Price, int CatalogID, String manufacturer, String name, String category, int pid, boolean isExist) {
        try {
            suppliersControler.addNewProductToAgreement(SupplierId, Price, CatalogID, manufacturer, name, category, pid, isExist);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    @Override
    public Response removeProductFromSupplier(int SupId, int CatalogID) {
        try {
            suppliersControler.removeProductFromSupplier(CatalogID, SupId);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    //            facade.removeProductFromOrder(id,catalogId);
    @Override
    public Response RemoveProductFromOrders(int SupId, int CatalogID) {
        try {
            orderControler.RemovePrudactFromOrders(SupId, CatalogID);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }


    @Override
    public Response setProductPrice(int SupId, int CatalogID, double price) {
        try {
            suppliersControler.setProductPrice(SupId, CatalogID, price);
            orderControler.ReCalculateTotalPayment(SupId);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    @Override
    public Response addNewDiscountByQuantityToProduct(int SupId, int CatalogID, int Quantity, double newPrice) {
        try {
            suppliersControler.addNewDiscountByQuantitiyToProduct(SupId, CatalogID, Quantity, newPrice);
            orderControler.ReCalculateTotalPayment(SupId);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    @Override
    public Response setExtraDiscountToSupplier(int SupId, int ExtraDiscount) {
        try {
            suppliersControler.setExtraDiscountToSupplier(SupId, ExtraDiscount);
            orderControler.ReCalculateTotalPayment(SupId);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    @Override
    public Response addNewContactMember(int SupId, String contactName, String contactEmail, String phoneNumber) {
        try {
            suppliersControler.addNewContact(SupId, contactName, contactEmail, phoneNumber);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    @Override
    public ResponseT getSupplier(int SupId) {
        try {
            ISupplier supplier = suppliersControler.getSupplier(SupId);
            SupplierResponse sr = copySupplier(supplier);
            return new ResponseT(sr);
        } catch (Exception e) {
            return new ResponseT(e.getMessage());
        }
    }

    private SupplierResponse copySupplier(ISupplier supplier) {
        int id = supplier.getId();
        String bankAccount = supplier.getBankAccount();
        String name = supplier.getSupplierName();
        List<Contact> contacts = supplier.getListOfContacts();
        paymentMethods paymentMethods = supplier.getPayment();
        // copy al contacts
        List<contactResponse> contactsResponse = new LinkedList<>();
        for (int i = 0; i < contacts.size(); i++) {
            contactResponse cr = new contactResponse(contacts.get(i).getId(), contacts.get(i).getContactName(), contacts.get(i).getPhoneNumber(), contacts.get(i).getEmail());
            contactsResponse.add(cr);
        }
        return new SupplierResponse(id, name, bankAccount, paymentMethods, contactsResponse);
    }


    @Override
    public ResponseT getAllSuppliers() {
        try {
            List<ISupplier> listOfSupplier = suppliersControler.getAllSuppliers();
            List<SupplierResponse> supplierResponseList = new LinkedList<>();
            for (ISupplier supplier : listOfSupplier
            ) {
                System.out.println("Dsdsfsdfsdfdfsffffffff" + supplier.getId());
                SupplierResponse supplierResponse = copySupplier(supplier);
                supplierResponseList.add(supplierResponse);
            }
            return new ResponseT(supplierResponseList);
        } catch (Exception e) {
            return new ResponseT(e.getMessage(), null);
        }
    }

    @Override
    public ResponseT getAgreement(int SupId) {
        try {
            IAgreement agreement = suppliersControler.getAgreement(SupId);
            AgreementResponse AG = copyAgreement(agreement);
            return new ResponseT(AG);
        } catch (Exception e) {
            return new ResponseT(e.getMessage(), null);
        }
    }

    @Override
    public Response RemoveContact(int SupId, int ContactID) {
        try {
            suppliersControler.removeContact(SupId, ContactID);
            return new Response();

        } catch (Exception e) {
            return new Response(e.getMessage());

        }
    }


    private AgreementResponse copyAgreement(IAgreement agreement) {
        List<ProductSupplier> productList = new ArrayList<>(agreement.getProducts().values());
        List<productSupplierResponse> productResponseList = new LinkedList<>();
        for (ProductSupplier p : productList
        ) {
            productResponseList.add(copyProduct(p));
        }//    private List<DeliveryMode> deliveryModes ;

        double ExtraDiscount = agreement.getExtraDiscount();
        int SupplierId = agreement.getSupplierID();
        DeliveryMode deliveryModes = agreement.getDeliveryMode();

        List<Integer> daysDelivery = new LinkedList<>();
        if (deliveryModes == DeliveryMode.DeliveryByDay) {
            daysDelivery.addAll(agreement.getDaysOfDelivery());
        }
        int numOfDaysFromOrder = agreement.getNumOfDaysFromOrder();
        HashMap<Integer, HashMap<Integer, Double>> DiscountProductsResponse = new HashMap<>();
        HashMap<Integer, HashMap<Integer, Double>> DiscountProducts = agreement.getDiscountByProductQuantity();
        for (Integer CatalogId : DiscountProducts.keySet()) {
            HashMap<Integer, Double> disQuantity = DiscountProducts.get(CatalogId);
            HashMap<Integer, Double> diQuantityRes = new HashMap<>();
            for (Integer quantity : disQuantity.keySet()
            ) {
                diQuantityRes.put(quantity, disQuantity.get(quantity));
            }
            DiscountProductsResponse.put(CatalogId, diQuantityRes);
        }
        AgreementResponse agreementResponse = new AgreementResponse(DiscountProductsResponse, productResponseList, ExtraDiscount, SupplierId, deliveryModes, daysDelivery, numOfDaysFromOrder);
        return agreementResponse;

    }

    @Override
    public Response addNewOrder(int SupId, HashMap<Integer, Integer> productQuantity, boolean isConstant, Integer constantorderdayfromdelivery) {
        try {
            orderControler.AddOrder(SupId, productQuantity, isConstant, constantorderdayfromdelivery);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    @Override
    public Response addProductToOrder(int SupId, int OrderId, int CatalogID, int quantity) {
        try {
            orderControler.addProductToOrder(CatalogID, quantity, OrderId, SupId);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    @Override
    public Response changeProductQuantityFromOrder(int SupId, int OrderId, int CatalogID, int quantity) {
        try {
            orderControler.changeProductQuantityFromOrder(CatalogID, quantity, OrderId, SupId);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    @Override
    public Response RemoveDiscountByQuantityToProduct(int SupId, int CatalogID, int Quantity) {
        try {
            suppliersControler.RemovDiscountByQuantitiyToProduct(SupId, CatalogID, Quantity);
            return new Response();

        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    @Override
    public Response removeOrder(int OrderID, int supID) {
        try {
            orderControler.RemoveOrder(OrderID, supID);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }

    }

    public ResponseT getOrder(int OrderID, int supID) {
        try {
            Order order = orderControler.GetOrder(OrderID, supID);
            orderResponse orderResponse = copyOrder(order);
            return new ResponseT(orderResponse);
        } catch (Exception e) {
            return new ResponseT(e.getMessage(), null);
        }
    }

    @Override
    public ResponseT getAllOrders() {
        try {
            List<Order> orderList = orderControler.getAllOrders();
            List<orderResponse> orderResponseList = new LinkedList<>();
            for (Order order : orderList
            ) {
                orderResponseList.add(copyOrder(order));
            }
            return new ResponseT(orderResponseList);
        } catch (Exception e) {
            return new ResponseT(e.getMessage(), null);
        }
    }

    private productSupplierResponse copyProduct(ProductSupplier product) {
        int id = product.getId();
        double price = product.getPrice();
        int catalogID = product.getCatalogID();
        String name = product.getName();
        productSupplierResponse orderResponse = new productSupplierResponse(price, catalogID, id, name);
        return orderResponse;
    }

    private orderResponse copyOrder(Order order) {
        int id = order.GetId();
        int supplierId = order.getSupplierID();

        HashMap<Integer, Integer> quantity = new HashMap<>();
        for (Integer key : order.getProductQuantity().keySet()
        ) {
            quantity.put(key, order.getProductQuantity().get(key));
        }
        double TotalPayment = order.GetTotalPayment();
        LocalDate dateTime = order.GetDateTime();

        orderResponse orderResponse = new orderResponse(id, supplierId, dateTime, quantity, TotalPayment, order.isConstant());
        return orderResponse;
    }


}
