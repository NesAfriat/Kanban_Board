package BuisnnesLayer.FacedeModel.Objects;

import BuisnnesLayer.DeliveryMode;
import java.util.HashMap;
import java.util.List;

public class AgreementResponse {
    public final HashMap<Integer,HashMap<Integer,Double>> discounts;
    public final List<productSupplierResponse> products;
    public final double ExtraDiscount;
    public final int supplierId;
    public final DeliveryMode deliveryModes;
    public final List<Integer> daysOfDelivery;
    public final int numOfDaysFromOrder;

    public AgreementResponse(HashMap<Integer, HashMap<Integer, Double>> discounts, List<productSupplierResponse> products, double extraDiscount, int supplierId, DeliveryMode deliveryModes , List<Integer> daysOfDelivery, int numOfDaysFromOrder) {
        this.discounts = discounts;
        this.products = products;
        ExtraDiscount = extraDiscount;
        this.supplierId = supplierId;
        this.deliveryModes = deliveryModes;
        this.daysOfDelivery = daysOfDelivery;
        this.numOfDaysFromOrder = numOfDaysFromOrder;
    }
}
