package BuisnnesLayer;


public class ProductSupplier {
    private double Price;
    private int CatalogID;
    private int id;
    private String name;


    public ProductSupplier(double Price, int CatalogID, int id,String name){
        this.Price=Price;
        this.CatalogID=CatalogID;
        this.id=id;
        this.name=name;
    }
    public String getName(){return name;}

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







}
