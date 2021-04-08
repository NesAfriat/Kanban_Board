package BuissnessLayer;

public class Product {
    private double Price;
    private int CatalogID;
    private int id;
    private String manfucator;
    private String name;


    public Product(double Price, int CatalogID, String manfucator, String name, int id){
        this.Price=Price;
        this.CatalogID=CatalogID;
        this.id=id;
        this.manfucator=manfucator;
        this.name=name;
    }


    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public int getCatalogID() {
        return CatalogID;
    }

    public void setCatalogID(int catalogID) {
        CatalogID = catalogID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getManfucator() {
        return manfucator;
    }

    public void setManfucator(String manfucator) {
        this.manfucator = manfucator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



}
