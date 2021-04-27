package BusinessLayer.Transport_BusinessLayer.Shops;

public class Product {


    private String name;
    private int id;

    public Product(String name, int id){
        this.name=name;
        this.id=id;

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


    public String toString(){

        return  "Product ID: "+id +", " + name+"\n";

    }
}
