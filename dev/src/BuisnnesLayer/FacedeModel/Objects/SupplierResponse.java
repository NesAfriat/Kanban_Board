package BuisnnesLayer.FacedeModel.Objects;

import BuisnnesLayer.paymentMethods;

import java.util.List;

public class SupplierResponse {
    public final int id;
    public final String supplierName;
    public final String bankAccount;
    public final paymentMethods paymentMethods;
    public final List<contactResponse> contacts;

    public SupplierResponse(int id, String supplierName, String bankAccount, paymentMethods paymentMethods, List<contactResponse> contacts) {
        this.id = id;
        this.supplierName = supplierName;
        this.bankAccount = bankAccount;
        this.paymentMethods = paymentMethods;
        this.contacts = contacts;
    }

}
