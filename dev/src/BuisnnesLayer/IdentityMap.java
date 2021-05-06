package BuisnnesLayer;

import BuisnnesLayer.Category;
import BuisnnesLayer.GeneralProduct;
import BuisnnesLayer.Item;
import BuisnnesLayer.Reports.Report;
import BuisnnesLayer.SupplierBuissness.*;

import java.util.LinkedList;

public class IdentityMap {
    private static IdentityMap instance = null;
    private LinkedList<Item> itemList;
    private LinkedList<GeneralProduct> generalProductList;
    private LinkedList<Category> categoryList;
    private LinkedList<Supplier> suppliersList;
    private LinkedList<Report> reportsList;

    public static IdentityMap getInstance() {
        if (instance == null){
            instance = new IdentityMap();
        }
        return instance;
    }

    private IdentityMap(){
        itemList = new LinkedList<>();
        categoryList = new LinkedList<>();
        suppliersList = new LinkedList<>();
        generalProductList = new LinkedList<>();
        reportsList = new LinkedList<>();
    }

    //add an item to the identityMap
    public void addItem(Item item) {
        itemList.add(item);
    }

    //TODO: need to add empty constructor for each created object
    //if this function return null - go to the db
    public Item getItem(int item_id, int gp_id) {
        Item output = null;
        for (Item item: itemList) {
            if(item.getItem_id()==item_id && item.getProduct_id() ==gp_id)
                output = item;
        }
        return output;
    }

    //TODO: make sure remove doesnt stop the for
    public boolean removeItem(int item_id, int gp_id){
        boolean output = false;
        for (Item item: itemList) {
            if(item.getItem_id()==item_id && item.getProduct_id() ==gp_id)
                output = itemList.remove(item);
        }
        return output;
    }

    public void addGeneralProduct(GeneralProduct product){
        generalProductList.add(product);
    }
    //TODO: need to add empty constructor for each created object
    //if this function return null - go to the db
    public GeneralProduct getGeneralProduct(int gp_id) {
        GeneralProduct output = null;
        for (GeneralProduct prod: generalProductList) {
            if(prod.getProduct_id() ==gp_id)
                output = prod;
        }
        return output;
    }

    //TODO: make sure remove doesnt stop the for
    public boolean removeGeneralProd(int gp_id){
        boolean output=false;
        for (GeneralProduct prod: generalProductList) {
            if(prod.getProduct_id() ==gp_id)
                output = generalProductList.remove(prod);
        }
        return output;
    }

    ////Categories//////
 //TODO: check if object exist in the list before adding
    public void addCategory(Category category) {
        if(!categoryList.contains(category))
        categoryList.add(category);
    }
    //if this function return null - go to the db
    public Category getCategory(String cat) {
        Category output = null;
        for (Category c: categoryList) {
            if(c.getCategory_name().equals(cat))
                output = c;
        }
        return output;
    }

    public Category removeCategory(Category category){
        Category output=null;
        output = categoryList.remove();
        return output;
    }

    /////Reports//////
    public void addReport(Report report) {
        if(!reportsList.contains(report))
            reportsList.add(report);
    }
    //if this function return null - go to the db
    public Report getReport(Report report) {
        Report output = null;
        for (Report r: reportsList) {
            if(r.equals(report))
                output = r;
        }
        return output;
    }

    public Report removeReport(Report report){
        Report output=null;
        output = reportsList.remove();
        return output;
    }
}
