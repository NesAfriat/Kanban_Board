package BuissnessLayer;

import BuissnessLayer.OrderBuissness.Product;

import java.util.HashMap;
import java.util.List;

public interface IAgreement {

    public void AddPrudact(double Price, int CatalogID, String manfucator, String name, int idProductCounter);
    public void RemovePrudact(int CatalogID);
    public void AddDiscountByProduct(int CatalogID, int quantity, double newPrice);
    public void SetExtraDiscount(int ExtarDiscount);
    public void setProductPrice(int CatalogId, double ExtraDiscount);
    public Product GetPrudact(int CatalogID);
    public HashMap<Integer, Product> getProducts();
    public int getNumOfDaysFromOrder();
    public List<Integer> getDaysOfDelivery();
    public DeliveryMode getDeliveryMode();
    public int getSupplierID();
    public int getExtraDiscount();
    public HashMap<Integer, HashMap<Integer, Double>> getDiscountByProductQuantity();
    public boolean isProductExist(int CatalogId);
    public void removeDiscountQuantity(int CatalogId, int Quantiti);




}
