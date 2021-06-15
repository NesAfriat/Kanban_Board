package BusinessLayer.SupplierBuissness;

import BusinessLayer.DeliveryMode;
import BusinessLayer.paymentMethods;

import java.util.List;

public interface ISupplierControler {

    void SetPayment(paymentMethods paymentMethods, int SupplierId);

    void addNewSupplier(int id, String name, String bankAccount, paymentMethods paymentMethods, DeliveryMode deliveryMode, List<Integer> daysOfDelivery, int NumOfDaysFromDelivery, String contactName, String contactEmail, String phoneNumber);

    //this function remove the supplier from the list
    void RemoveSupplier(int SupId);

    //this function return a supplier from the list if the supplier do not exist throw an exception
    ISupplier getSupplier(int SupId);

    List<ISupplier> getAllSuppliers();

    void setBankAccount(int SupId, String BankAccount);

    void addNewContact(int SupId, String contactName, String contactEmail, String phoneNumber);

    void removeContact(int SupId, int ContactId);

    void SetDeliveryMode(int SupId, DeliveryMode deliveryMods, List<Integer> daysOfDelivery, int numOfDaysFromOrder);

}
