package BuisnnesLayer.OrderBuissness;

import BuisnnesLayer.ProductSupplier;

import java.time.LocalDate;
import java.util.HashMap;

public interface IOrder {

    public int GetTotalPayment();
    public LocalDate GetDateTime();
    public int GetId();
    public HashMap<Integer, ProductSupplier> getProducts();
    public HashMap<Integer, Integer> getProductQuantity();
    public int getSupplierID();

}
