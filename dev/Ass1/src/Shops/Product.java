package Shops;

import java.util.LinkedList;
import java.util.List;

public class Product {

    /*
    questions 1) when to add to hashmap?
                a) when entering the products || b) when entering the suppliers

    */
    private String name;
    private int id;
    private List<Supplier> suppList;
    public Product(String name, int id){
        this.name=name;
        this.id=id;
        suppList=new LinkedList<>();
    }
    public Product(String name, int id,List suppList){
        this.name=name;
        this.id=id;
        this.suppList=suppList;
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

    public List getSuppList() {
        return suppList;
    }

    public void setSuppList(List suppList) {
        this.suppList = suppList;
    }
    public void addToSuppList(Supplier supp)
    {
        suppList.add(supp);
    }
    public void RemoveFromSuppList(Supplier supp)
    {
         suppList.remove(supp);
    }
    public String toString(){

        return  "Product ID: "+id +", " + name+"\n";

    }
}
