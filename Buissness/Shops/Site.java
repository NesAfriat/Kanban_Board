package Buissness.Shops;

public abstract class Site {


    protected String name;
    protected int id;
    protected String phoneNumber;
    protected String contact;
    protected Area StoreArea;

    public Site(String name, int id, String phoneNumber, String contact, Area storeArea) {
        this.name = name;
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.contact = contact;
        StoreArea = storeArea;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public Area getArea() {
        return StoreArea;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
