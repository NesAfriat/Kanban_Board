package BuisnnesLayer;


import BuisnnesLayer.Reports.Report;
import BuisnnesLayer.OrderBuissness.Order;
import BuisnnesLayer.SupplierBuissness.Contact;
import BuisnnesLayer.SupplierBuissness.Supplier;


import java.util.LinkedList;

public class IdentityMap {
    private static IdentityMap instance = null;
    private LinkedList<Item> itemList;
    private LinkedList<Item> defctedItemsList;
    private LinkedList<GeneralProduct> generalProductList;
    private LinkedList<Category> categoryList;
    private LinkedList<Supplier> suppliersList;
    private LinkedList<Report> reportsList;
    private LinkedList<Contact> contactsList;
    private LinkedList<ProductSupplier> productSuppliersList;
    private LinkedList<Order> ordersList;
    private LinkedList<Agreement> agreementsList;


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
        reportsList = new LinkedList<>();
        contactsList = new LinkedList<>();
        productSuppliersList = new LinkedList<>();
        ordersList = new LinkedList<>();
        agreementsList = new LinkedList<>();
        defctedItemsList = new LinkedList<>();
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
    //function which retrieve the info from the database
    public void addGeneralProduct(GeneralProduct product) {
        boolean flag;
        if(!generalProductList.contains(product))
            generalProductList.add(product);
        for(Item i:product.get_items()){
            flag = false;
            for(Item item: itemList){
                if (i.getItem_id().equals(item.getItem_id()) && i.getProduct_id().equals(item.getProduct_id())) {
                    flag = true;
                    break;
                }
            }
            if(!flag){
                itemList.add(i);
            }
        }
        for(ProductSupplier ps:product.getHashOfSupplierProducts().values()){
            flag = false;
            for(ProductSupplier prod: productSuppliersList){
                if (ps.getId() == prod.getId() && ps.getCatalogID() == prod.getCatalogID()) {
                    flag = true;
                    break;
                }
            }
            if(!flag){
                productSuppliersList.add(ps);
            }
        }
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
    //TODO: after the remove is done - need to remove the output's productSupplier and items from the identityMap
    public GeneralProduct removeGeneralProd(int gp_id) {
        GeneralProduct output = null;
        for (GeneralProduct prod : generalProductList) {
            if (prod.getProduct_id() == gp_id) {
                output = prod;
                generalProductList.remove(prod);
            }
        }

        return output;
    }

    ////Categories//////
 //TODO: check if object exist in the list before adding

    public void addCategory(Category category) {
        if (!categoryList.contains(category))
            categoryList.add(category);
    }

    //if this function return null - go to the db
    public Category getCategory(String cat) {
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


    /////Reports//////
    public void addReport(Report report) {
        if(!reportsList.contains(report))
            reportsList.add(report);
    }
    //if this function return null - go to the db
    public Report getReport(Report report) {
        Report output = null;
        for (Report r : reportsList) {
            if (r.equals(report))
                output = r;
        }
        return output;
    }
    public Report removeReport(Report report) {
        Report output = null;
        output = reportsList.remove();
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
    //================================================================================
    //add an item to the identityMap
    public void addPS(ProductSupplier ps) {
        productSuppliersList.add(ps);
    }

    //TODO: need to add a supID or idk how to get a specific ps
    //if this function return null - go to the db
//    public ProductSupplier getPS(int sup_id, int cat_id) {
//        ProductSupplier output = null;
//        for (ProductSupplier ps: productSuppliersList) {
//            if (ps.getId() == con_id)
//                output = con;
//        }
//        return output;
//    }
//
//    //TODO: make sure remove doesnt stop the for
//    public Contact removeContact(int con_id) {
//        Contact output = null;
//        for (Contact con: contactsList) {
//            if (con.getId() == con_id) {
//                output = con;
//                contactsList.remove(con);
//            }
//        }
//        return output;
//    }
    //================================================================================
    //add an Order to the identityMap
    public void addOrder(Order o) {
        ordersList.add(o);
    }


    //if this function return null - go to the db
    public Order getOrder(int oID) {
        Order output = null;
        for (Order o: ordersList) {
            if (o.GetId() == oID)
                output = o;
        }
        return output;
    }

    //TODO: make sure remove doesnt stop the for
    public Order removeOrder(int oID) {
        Order output = null;
        for (Order o: ordersList) {
            if (o.GetId() == oID) {
                output = o;
                ordersList.remove(o);
            }
        }
        return output;
    }
    //================================================================================
    //add an Agreement to the identityMap
    public void addAgreement(Agreement a) {
        agreementsList.add(a);
    }


    //if this function return null - go to the db
    public Agreement getAgreement(int supID) {
        Agreement output = null;
        for (Agreement a: agreementsList) {
            if (a.getSupplierID() == supID)
                output = a;
        }
        return output;
    }


    //TODO: make sure remove doesnt stop the for
    public Agreement removeAgreement(int supID) {
        Agreement output = null;
        for (Agreement a: agreementsList) {
            if (a.getSupplierID() == supID) {
                output = a;
                agreementsList.remove(a);
            }
        }
        return output;
    }

    //================================================================================
    //add an item to the identityMap
    public void addDefectedItem(Item item) {
        defctedItemsList.add(item);
    }

    //TODO: need to add empty constructor for each created object
    //if this function return null - go to the db
    public Item getDefectedItem(int item_id, int gp_id) {
        Item output = null;
        for (Item item : defctedItemsList) {
            if (item.getItem_id() == item_id && item.getProduct_id() == gp_id)
                output = item;
        }
        return output;
    }

    //TODO: make sure remove doesnt stop the for
    public Item removeDefectedItem(int item_id, int gp_id) {
        Item output = null;
        for (Item item : defctedItemsList) {
            if (item.getItem_id() == item_id && item.getProduct_id() == gp_id)
                output = item;
            defctedItemsList.remove(item);
        }
        return output;
    }
}