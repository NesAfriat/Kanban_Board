package BuissnessLayer.FacadeBuissness.ResponseObjects;

import BuissnessLayer.DeliveryMode;

import java.util.HashMap;
import java.util.List;

public class AgreementResponse {
    public final HashMap<Integer,HashMap<Integer,Double>> discounts;
    public final List<productResponse> products;
    public final int ExtaraDiscount;
    public final int supplierId;
    public final DeliveryMode deliveryModes;
    public final List<Integer> daysOfDelivery;
    public final int numOfDaysFromOrder;

    public AgreementResponse(HashMap<Integer, HashMap<Integer, Double>> discounts, List<productResponse> products, int extaraDiscount, int supplierId, DeliveryMode deliveryModes , List<Integer> daysOfDelivery, int numOfDaysFromOrder) {
        this.discounts = discounts;
        this.products = products;
        ExtaraDiscount = extaraDiscount;
        this.supplierId = supplierId;
        this.deliveryModes = deliveryModes;
        this.daysOfDelivery = daysOfDelivery;
        this.numOfDaysFromOrder = numOfDaysFromOrder;
    }
}
