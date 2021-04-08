package BuissnessLayer.FacadeBuissness.ResponseObjects;

public class productResponse {
    public final double price;
    public final int CatalogID;
    public final int id;
    public final String manfucator;
    public final String name;


    public productResponse(double price, int catalogID, int id, String manfucator, String name) {
        this.price = price;
        CatalogID = catalogID;
        this.id = id;
        this.manfucator = manfucator;
        this.name = name;
    }
}
