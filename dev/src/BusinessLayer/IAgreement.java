package BusinessLayer;

import java.util.HashMap;
import java.util.List;

public interface IAgreement {

    ProductSupplier AddPrudact(double Price, int CatalogID, int idProductCounter, String name);

    void RemovePrudact(int CatalogID);

    void AddDiscountByProduct(int CatalogID, int quantity, double newPrice);

    void SetExtraDiscount(int ExtarDiscount);

    void setProductPrice(int CatalogId, double ExtraDiscount);

    ProductSupplier GetPrudact(int CatalogID);

    HashMap<Integer, ProductSupplier> getProducts();

    int getNumOfDaysFromOrder();

    List<Integer> getDaysOfDelivery();

    DeliveryMode getDeliveryMode();

    int getSupplierID();

    double getExtraDiscount();

    HashMap<Integer, HashMap<Integer, Double>> getDiscountByProductQuantity();

    boolean isProductExist(int CatalogId);

    void removeDiscountQuantity(int CatalogId, int Quantiti);

    void SetDeliveryMode(DeliveryMode deliveryMods, List<Integer> daysOfDelivery, int numOfDaysFromOrder);

    double Calculate_cost(ProductSupplier productSupplier, int quantity);

    void RemovAllProducts();


}
