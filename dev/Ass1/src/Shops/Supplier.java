package Shops;

import java.util.LinkedList;
import java.util.List;

public class Supplier extends Site {


    private List<Product> productsServed;

    //basic constructor
    public Supplier(String name, int id, String phoneNumber, String contact, Area storeArea) {
        super(name, id, phoneNumber, contact, storeArea);
        productsServed = new LinkedList<>();
    }

    //constructor for full reload
    public Supplier(String name, int id, String phoneNumber, String contact, Area storeArea, List<Product> productsServed) {
        super(name, id, phoneNumber, contact, storeArea);
        this.productsServed = productsServed;
    }
    public String toString() {
        return "Supplier ID: " + id + ", " + name + ", " + StoreArea + ", " + contact + ", " + phoneNumber + "\n";
    }


    public List<Product> getProductsServed() {
        return productsServed;
    }

    public void setProductsServed(List<Product> productsServed) {
        this.productsServed = productsServed;
    }

    public void addProduct(Product prod)
    {
        productsServed.add(prod);
    }
    public void removeProduct(Product prod)
    {
        productsServed.remove(prod);
    }


}
