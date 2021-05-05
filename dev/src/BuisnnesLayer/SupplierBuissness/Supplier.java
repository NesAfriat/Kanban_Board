package BuisnnesLayer.SupplierBuissness;

import BuisnnesLayer.paymentMethods;

import java.util.ArrayList;
import java.util.List;

public class Supplier implements ISupplier {
    //the payment methods in the system

    private int ContactIdCounter; // increment then insert new contact. start with def value - 0
    private int id;
    private String SupplierName;
    private String BankAccount ;
    private paymentMethods payment;
    private List<Contact> ListOfContacts;
    private int SupplierIdCounter;





    public Supplier(int id, String SupplierName, paymentMethods payment, String BankAccount, int SupplierIdCounter , String contactName, String contactEmail, String phoneNumber)
    {
       this.ContactIdCounter=0;
       this.ListOfContacts  = new ArrayList<Contact>();
       ListOfContacts.add(new Contact(ContactIdCounter,contactName,contactEmail,phoneNumber));
       ContactIdCounter++;
       this.id=id;
       this.SupplierName=SupplierName;
       this.payment=payment;
       this.BankAccount=BankAccount;
       this.SupplierIdCounter=SupplierIdCounter;
    }

    @Override
    public void addNewContact( String contactName, String phoneNumber, String Email) {
        Contact newContact=new Contact(ContactIdCounter,contactName,phoneNumber,Email);
        ListOfContacts.add(newContact);
        incContactIdCounter();
    }

    @Override
    public void removeContact(int contactID) {
        if(ListOfContacts.size()==1){
            throw new IllegalArgumentException("you cannot deleat this contact its the last contact");
        }
        for (Contact contact:ListOfContacts) {
            if(contact.getId()==contactID){
                ListOfContacts.remove(contact);
                return;//ככה יוצאים מפונקציה? אחרת זה יזרוק לנו שגיאה בלי סיבה
            }
        }
        //the contact is not exist
        throw new IllegalArgumentException("the contact is not exist");
    }

    public Contact getContact(int ContactId){
        for (Contact contact:ListOfContacts
             ) {
            if(contact.getId()==ContactId){
                return contact;
            }

        }
        throw new IllegalArgumentException("the contact is not exist");
    }

    // this function inc the Contact id counter when we insert anew contact to whe list
    public void incContactIdCounter() {
        this.ContactIdCounter++;
    }

    // this function dec the Contact id counter

    public void decContactIdCounter() {
        this.ContactIdCounter--;
    }


    public void setContactIdCounter(int contactIdCounter) {
        ContactIdCounter = contactIdCounter;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSupplierName() {
        return SupplierName;
    }

    public void setSupplierName(String supplierName) {
        SupplierName = supplierName;
    }

    public String getBankAccount() {
        return BankAccount;
    }

    public void setBankAccount(String bankAccount) {
        BankAccount = bankAccount;
    }

    public paymentMethods getPayment() {
        return payment;
    }

    public void setPayment(paymentMethods payment) {
       this.payment = payment;
    }

    public List<Contact> getListOfContacts() {
        return ListOfContacts;
    }

    public void setListOfContacts(List<Contact> listOfContacts) {
        ListOfContacts = listOfContacts;
    }




}
