package BusinessLayer.OrderBuissness;

import BusinessLayer.ProductSupplier;

import java.time.LocalDate;
import java.util.HashMap;

public interface IOrder {

    int GetTotalPayment();

    LocalDate GetDateTime();

    int GetId();

    HashMap<Integer, ProductSupplier> getProducts();

    HashMap<Integer, Integer> getProductQuantity();

    int getSupplierID();

}
