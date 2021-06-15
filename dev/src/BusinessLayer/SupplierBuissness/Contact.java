package BusinessLayer.SupplierBuissness;

import BusinessLayer.IdentityMap;
import DataLayer.DataController;

public class Contact {
    private int id;
    private String ContactName;
    private String PhoneNumber;
    private String Email;
    private int SupId;

    public Contact(int id, String ContactName, String PhoneNumber, String Email, int SupID) {
        this.id = id;
        this.ContactName = ContactName;
        this.PhoneNumber = PhoneNumber;
        this.Email = Email;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        updateContact(this);
    }

    public String getContactName() {
        return ContactName;
    }

    public void setContactName(String contactName) {
        ContactName = contactName;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
        updateContact(this);
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
        updateContact(this);
    }


    //==========================================data==========================
    public void updateContact(Contact contact) {
        IdentityMap im = IdentityMap.getInstance();
        DataController dc = DataController.getInstance();
        if (!dc.update(contact, this.SupId)) {
            System.out.println("EROR");
        }

    }


}
