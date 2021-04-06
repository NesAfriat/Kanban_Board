package BuissnessLayer.SupplierBuissness;

public class Contact {
    private int id;
    private String ContactName;
    private String PhoneNumber;
    private String Email ;
    public Contact(int id, String ContactName, String PhoneNumber, String Email)
    {
        this.id=id;
        this.ContactName=ContactName;
        this.PhoneNumber=PhoneNumber;
        this.Email=Email;
    }

    public int getId() {
        return id;
    }

    public String getContactName() {
        return ContactName;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public String getEmail() {
        return Email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setContactName(String contactName) {
        ContactName = contactName;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
