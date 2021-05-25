package BusinessLayer.Transport_BusinessLayer.Shops;

public class Store extends Site{

    public Store(String name, int id, String phoneNumber, String contact, Area storeArea) {
        super(name, id, phoneNumber, contact, storeArea);
    }


    public String toString() {
        return "Store ID: " + id + ", " + name + ", " + StoreArea + ", " + contact + ", " + phoneNumber + "\n";
    }

}
