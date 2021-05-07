package DataLayer.Mappers;

import BuisnnesLayer.GeneralProduct;
import BuisnnesLayer.Item;
import BuisnnesLayer.Category;
import BuisnnesLayer.OrderBuissness.Order;
import BuisnnesLayer.ProductSupplier;
import BuisnnesLayer.SupplierBuissness.Contact;
import BuisnnesLayer.SupplierBuissness.Supplier;
import com.sun.org.apache.xpath.internal.operations.Or;

import java.util.LinkedList;


public class DataController {
    private static DataController instance = null;
    private ItemMapper itemMapper;
    private GeneralProductMapper generalProductMapper;
    private CategoriesMapper CategoriesMapper;
    private SuppliersMapper suppliersMapper;
    private SuppliersContactsMapper suppliersContactsMapper;
    private SuppliersProductsMapper suppliersProductsMapper;
    private OrdersMapper ordersMapper;

    public static DataController getInstance() {
        if (instance == null) {
            instance = new DataController();
        }
        return instance;
    }

    private DataController() {
        itemMapper = new ItemMapper();
        generalProductMapper = new GeneralProductMapper();
        CategoriesMapper = new CategoriesMapper();
        suppliersMapper = new SuppliersMapper();
        suppliersContactsMapper = new SuppliersContactsMapper();
        suppliersProductsMapper = new SuppliersProductsMapper();
        ordersMapper = new OrdersMapper();
    }

    //================================================================================
    //TODO: return null if item does not exist
    //Item Actions:
    //If we want to retrive an item which was not in the business
    public Item getItem(int product_id, int item_id) {
        Item ip = itemMapper.getItem(product_id, item_id);
        return ip;
    }

    //If we want to make entire new record of an item
    public boolean insertItem(Item obj) {
        return itemMapper.insertItem(obj);
    }

    public boolean update(Item obj) {
        return itemMapper.update(obj);
    }

    public boolean delete(Item obj) {
        return itemMapper.delete(obj);
    }

    //================================================================================
    //GeneralProduct Actions:
    //If we want to retrive an product which was not in the business
    public GeneralProduct getGP(int product_id) {
        GeneralProduct gp = generalProductMapper.getGeneralProduct(product_id);
        //TODO: need to add here itemsList and supplierProductList
        return gp;
    }

    //If we want to make entire new record of an item
    public boolean insertGP(GeneralProduct obj, String catName) {
        return generalProductMapper.insertProduct(obj, catName);
    }

    public boolean update(GeneralProduct obj) {
        return generalProductMapper.update(obj);
    }

    public boolean delete(GeneralProduct obj) {
        return generalProductMapper.delete(obj);
    }

    //================================================================================
    public Category getCategory(String cat_name) {
        Category cat = CategoriesMapper.getCategory(cat_name);
        return cat;
    }

    public boolean insertCategory(Category category) {
        return CategoriesMapper.insertCategory(category);
    }

    public boolean update(Category obj) {
        return CategoriesMapper.update(obj);
    }

    public boolean delete(Category obj) {
        return CategoriesMapper.delete(obj);
    }

    public boolean setFather(Category cat, Category father_cat) {

        return CategoriesMapper.setFather(cat, father_cat);
    }

    public LinkedList<Category> loadAllCategoreis() {
        return CategoriesMapper.loadAllCategories();
    }

    //================================================================================
    //Suppliers Actions:
    //If we want to retrive an suplpier which was not in the business
    public Supplier getSupplier(int supplier_id) {
        Supplier sup = suppliersMapper.getSupplier(supplier_id);
        //TODO: need to add here ContactList
        return sup;
    }

    //If we want to make entire new record of an item
    public boolean insertSupplier(Supplier sup) {
        return suppliersMapper.insertSupplier(sup);
    }

    public boolean update(Supplier obj) {
        return suppliersMapper.update(obj);
    }

    public boolean delete(Supplier obj) {
        return suppliersMapper.delete(obj);
    }

    //================================================================================
    //Contacs Actions:
    //If we want to retrive an contact which was not in the business
    public Contact getContact(int sup_id, int con_id) {
        Contact cntc = suppliersContactsMapper.getContact(sup_id, con_id);
        return cntc;
    }

    //If we want to make entire new record of an item
    public boolean insertContact(Contact obj, int sup_id) {
        return suppliersContactsMapper.insertContact(obj, sup_id);
    }

    public boolean update(Contact con, int sup_id) {
        return suppliersContactsMapper.update(con, sup_id);
    }

    public boolean delete(Contact con, int sup_id) {
        return suppliersContactsMapper.delete(con, sup_id);
    }

    //================================================================================
    //ProductSuppliers Actions:
    //If we want to retrive an PS which was not in the business
    public ProductSupplier getPS(int sup_id, int catID) {
        ProductSupplier ps = suppliersProductsMapper.getProductSupplier(sup_id, catID);
        return ps;
    }

    //If we want to make entire new record of an item
    public boolean insetPS(ProductSupplier obj, int sup_id) {
        return suppliersProductsMapper.insertProduct(obj, sup_id);
    }

    public boolean update(ProductSupplier con, int sup_id) {
        return suppliersProductsMapper.update(con, sup_id);
    }

    public boolean delete(ProductSupplier con, int sup_id) {
        return suppliersProductsMapper.delete(con, sup_id);
    }

    //================================================================================
    //Orders Actions:
    //If we want to retrive an Order which was not in the business
    public Order getOrder(int sup_id, int oID) {
        Order o = ordersMapper.getOrder(sup_id, oID);
        return o;
    }

    //If we want to make entire new record of an item
    public boolean insetOrder(Order o) {
        return ordersMapper.insertOrder(o);
    }

    public boolean update(Order o) {
        return ordersMapper.update(o);
    }

    public boolean delete(Order o) {
        return ordersMapper.delete(o);
    }
}
