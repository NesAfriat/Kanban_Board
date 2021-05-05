package DataLayer;

import BuisnnesLayer.Category;
import BuisnnesLayer.Item;
import BuisnnesLayer.SupplierBuissness.Supplier;
import DataLayer.PersistanceObjects.ItemPer;
import DataLayer.PersistanceObjects.PersistanceObj;

import java.util.HashMap;
import java.util.LinkedList;

public class IdentityMap {
    private static IdentityMap instance = null;
    HashMap<PersistanceObj, Item> itemMap;
    HashMap<PersistanceObj, Category> catMap;
    HashMap<PersistanceObj, Supplier> supMap;


    public static IdentityMap getInstance() {
        if (instance == null){
            instance = new IdentityMap();
        }
        return instance;
    }

    private IdentityMap(){
        this.itemMap = new HashMap<>();
        this.catMap = new HashMap<>();
        this.supMap = new HashMap<>();
    }

    //add an item to the identityMap
    public void addItem(Item item, PersistanceObj itemPer) {
        itemMap.put(itemPer,item);
    }

    //if this function return null - go to the db
    public Item getItem(int item_id, int gp_id) {
        Item output = null;
        for (Item item: itemMap.values()) {
            if(item.getItem_id()==item_id && item.getProduct_id() ==gp_id)
                output = item;
        }
        return output;
    }

}
