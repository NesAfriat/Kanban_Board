package BusinessLayer.SupplierBuissness;

import BusinessLayer.paymentMethods;

import java.util.List;

public interface ISupplier {

    //this function add new contact to the supplier list
    void addNewContact(String contactName, String phoneNumber, String Email);

    //this function remove contact from supplier contact list, if the contact do not exist return false
    void removeContact(int contactID);

    // public void setPayment(paymentMethods payment);

    String getBankAccount();

    void setBankAccount(String bankAccount);

    List<Contact> getListOfContacts();

    String getSupplierName();

    void setSupplierName(String supplierName);

    paymentMethods getPayment();

    void setPayment(paymentMethods payment);

    int getId();

    Contact getContact(int ContactId);
}

