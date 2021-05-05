package DataLayer.PersistanceObjects;

public class ItemPer implements PersistanceObj{
    public int item_id;
    public int product_id;
    public String location;
    public String supplied_date;
    public String creation_date;
    public String expiration_date;

    public ItemPer(int item_id, int product_id, String location, String supplied_date, String creation_date, String expiration_date) {
        this.item_id = item_id;
        this.product_id = product_id;
        this.location = location;
        this.supplied_date = supplied_date;
        this.creation_date = creation_date;
        this.expiration_date = expiration_date;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSupplied_date() {
        return supplied_date;
    }

    public void setSupplied_date(String supplied_date) {
        this.supplied_date = supplied_date;
    }

    public String getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
    }

    public String getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(String expiration_date) {
        this.expiration_date = expiration_date;
    }
}
