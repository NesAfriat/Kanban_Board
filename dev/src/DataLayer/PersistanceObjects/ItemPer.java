package DataLayer.PersistanceObjects;

import java.util.Date;

public class ItemPer implements PersistanceObj{
    //TODO maybe save only the primary keys per Persistance item;
    public int item_id;
    public int product_id;
    public String location;
    public Date supplied_date;
    public Date creation_date;
    public Date expiration_date;

    public ItemPer(int item_id, int product_id, String location, Date supplied_date, Date creation_date, Date expiration_date) {
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

    public Date getSupplied_date() {
        return supplied_date;
    }

    public void setSupplied_date(Date supplied_date) {
        this.supplied_date = supplied_date;
    }

    public Date getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(Date creation_date) {
        this.creation_date = creation_date;
    }

    public Date getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(Date expiration_date) {
        this.expiration_date = expiration_date;
    }
}
