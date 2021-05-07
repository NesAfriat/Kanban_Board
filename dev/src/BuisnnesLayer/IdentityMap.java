package BuisnnesLayer;

import BuisnnesLayer.Category;
import BuisnnesLayer.GeneralProduct;
import BuisnnesLayer.Item;
import BuisnnesLayer.SupplierBuissness.Contact;
import BuisnnesLayer.SupplierBuissness.Supplier;

import java.util.LinkedList;

public class IdentityMap {
    private static IdentityMap instance = null;
    private LinkedList<Item> itemList;
    private LinkedList<GeneralProduct> generalProductList;
    private LinkedList<Category> categoryList;
    private LinkedList<Supplier> suppliersList;
    private LinkedList<Contact> contactsList;

    public static IdentityMap getInstance() {
        if (instance == null) {
            instance = new IdentityMap();
        }
        return instance;
    }

    private IdentityMap() {
        itemList = new LinkedList<>();
        categoryList = new LinkedList<>();
        suppliersList = new LinkedList<>();
        generalProductList = new LinkedList<>();
        contactsList = new LinkedList<>();
    }

    //================================================================================
    //add an item to the identityMap
    public void addItem(Item item) {
        itemList.add(item);
    }

    //TODO: need to add empty constructor for each created object
    //if this function return null - go to the db
    public Item getItem(int item_id, int gp_id) {
        Item output = null;
        for (Item item : itemList) {
            if (item.getItem_id() == item_id && item.getProduct_id() == gp_id)
                output = item;
        }
        return output;
    }

    //TODO: make sure remove doesnt stop the for
    public Item removeItem(int item_id, int gp_id) {
        Item output = null;
        for (Item item : itemList) {
            if (item.getItem_id() == item_id && item.getProduct_id() == gp_id)
                output = item;
            itemList.remove(item);
        }
        return output;
    }

    //================================================================================
    public void addGeneralProduct(GeneralProduct product) {
        generalProductList.add(product);
    }

    //TODO: need to add empty constructor for each created object
    //if this function return null - go to the db
    public GeneralProduct getGeneralProduct(int gp_id) {
        GeneralProduct output = null;
        for (GeneralProduct prod : generalProductList) {
            if (prod.getProduct_id() == gp_id)
                output = prod;
        }
        return output;
    }

    //TODO: make sure remove doesnt stop the for
    public GeneralProduct removeGeneralProd(int gp_id) {
        GeneralProduct output = null;
        for (GeneralProduct prod : generalProductList) {
            if (prod.getProduct_id() == gp_id)
                output = prod;
            generalProductList.remove(prod);
        }
        return output;
    }

    //================================================================================
    //TODO: check if object exist in the list before adding
    public void addCategory(Category category) {
        if (!categoryList.contains(category))
            categoryList.add(category);
    }

    //if this function return null - go to the db
    public Category getCatregoy(String cat) {
        Category output = null;
        for (Category c : categoryList) {
            if (c.getCategory_name().equals(cat))
                output = c;
        }
        return output;
    }

    public Category removeCategory(Category category) {
        Category output = null;
        output = categoryList.remove();
        return output;
    }

    //================================================================================
    //add an item to the identityMap
    public void addSupplier(Supplier sup) {
        suppliersList.add(sup);
    }

    //TODO: need to add empty constructor for each created object
    //if this function return null - go to the db
    public Supplier getSupplier(int supplier_id) {
        Supplier output = null;
        for (Supplier sup : suppliersList) {
            if (sup.getId() == supplier_id)
                output = sup;
        }
        return output;
    }

    //TODO: make sure remove doesnt stop the for
    public Supplier removeSupplier(int supplier_id) {
        Supplier output = null;
        for (Supplier sup : suppliersList) {
            if (sup.getId() == supplier_id) {
                output = sup;
                suppliersList.remove(sup);
            }
        }
        return output;
    }

    //================================================================================
    //add an item to the identityMap
    public void addContact(Contact con) {
        contactsList.add(con);
    }

    //TODO: need to add empty constructor for each created object
    //if this function return null - go to the db
    public Contact getContact(int con_id) {
        Contact output = null;
        for (Contact con: contactsList) {
            if (con.getId() == con_id)
                output = con;
        }
        return output;
    }

    //TODO: make sure remove doesnt stop the for
    public Contact removeContact(int con_id) {
        Contact output = null;
        for (Contact con: contactsList) {
            if (con.getId() == con_id) {
                output = con;
                contactsList.remove(con);
            }
        }
        return output;
    }
}
