package BuissnessLayer.SupplierBuissness;

import BuissnessLayer.DeliveryMode;
import BuissnessLayer.paymentMethods;

import java.util.List;

public interface ISupplierControler {

    public void SetPayment(paymentMethods paymentMethods, int SupplierId);

    public void addNewSupplier(int id, String name, String bankAccount, paymentMethods paymentMethods, DeliveryMode deliveryMode, List<Integer> daysOfDelivery, int NumOfDaysFromDelivery);

    //this function remove the supplier from the list
    public void RemoveSupplier(int SupId);

    //this function return a supplier from the list if the supplier do not exist throw an exception
    public ISupplier getSupplier(int SupId);

    public List<ISupplier> getAllSuppliers();

    public void setBankAccount(int SupId, String BankAccount);

    public void addNewContact(int SupId, String contactName, String contactEmail, String phoneNumber);

    public void removeContact(int SupId, int ContactId);

}
