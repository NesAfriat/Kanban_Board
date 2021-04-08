package BuissnessLayer.OrderBuissness;

import BuissnessLayer.Product;

import java.time.LocalDate;
import java.util.HashMap;

public interface IOrder {

    public int GetTotalPayment();
    public LocalDate GetDateTime();
    public int GetId();
    public HashMap<Integer, Product> getProducts();
    public HashMap<Integer, Integer> getProductQuantity();
    public int getSupplierID();

}
