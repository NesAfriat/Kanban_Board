package DataLayer;

import BuisnnesLayer.*;
import BuisnnesLayer.Reports.Report;
import BuisnnesLayer.OrderBuissness.Order;
import BuisnnesLayer.Sales.Sale;
import BuisnnesLayer.SupplierBuissness.Contact;
import BuisnnesLayer.SupplierBuissness.Supplier;
import DataLayer.Mappers.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;


public class DataController {
    private static DataController instance = null;
    private ItemMapper itemMapper;
    private DefectsItemsMapper defectsItemsMapper;
    private GeneralProductMapper generalProductMapper;
    private CategoriesMapper categoriesMapper;
    private ReportsMapper reportsMapper;
    private SuppliersMapper suppliersMapper;
    private SuppliersContactsMapper suppliersContactsMapper;
    private SuppliersProductsMapper suppliersProductsMapper;
    private OrdersMapper ordersMapper;
    private AgreementsMapper agreementsMapper;
    private SalesMapper salesMapper;


    public static Date getDate(String date) throws ParseException {
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.parse(date);
    }

    public static String getDate(Date date) {
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }
    public static DataController getInstance() {
        if (instance == null) {
            instance = new DataController();
        }
        return instance;
    }

    private DataController() {
        generalProductMapper = new GeneralProductMapper();
        suppliersMapper = new SuppliersMapper();
        itemMapper = new ItemMapper();
        defectsItemsMapper = new DefectsItemsMapper();
        categoriesMapper = new CategoriesMapper();
        reportsMapper = new ReportsMapper();
        salesMapper= new SalesMapper();
        suppliersContactsMapper = new SuppliersContactsMapper();
        suppliersProductsMapper = new SuppliersProductsMapper();
        ordersMapper = new OrdersMapper();
        agreementsMapper = new AgreementsMapper();
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
        itemMapper.addItemToProduct(gp);
        suppliersProductsMapper.addPStoProduct(gp);
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


    public Category getCategory(String cat_name) {
        Category cat = categoriesMapper.getCategory(cat_name);
        return cat;
    }

    public boolean insertCategory(Category category) {
        return categoriesMapper.insertCategory(category);
    }

    public boolean update(Category obj) {
        return categoriesMapper.update(obj);
    }

    public boolean delete(Category obj) {
        return categoriesMapper.delete(obj);
    }

    public boolean setFather(Category cat, Category father_cat) {

        return categoriesMapper.setFather(cat, father_cat);
    }

    public LinkedList<Category> loadAllCategoreis() {
        return categoriesMapper.loadAllCategories();
    }

    //=============================
    //reports
    public Report getReport(int rID) {
        Report report = reportsMapper.getReport(rID);
        return report;
    }

    public boolean insertReport(Report report) {
        return reportsMapper.insert(report);
    }

    public boolean update(Report report) {
        return reportsMapper.update(report);
    }

    public boolean delete(Report report) {
        return reportsMapper.delete(report);
    }

    public LinkedList<Report> loadAllReports() {
        return reportsMapper.loadAllReports();
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

    //================================================================================
    //Orders Actions:
    //If we want to retrive an Order which was not in the business
    public Agreement getAgreement(int sup_id) {
        Agreement a = agreementsMapper.getAgreement(sup_id);
        return a;
    }

    //If we want to make entire new record of an item
    public boolean insertAgreement(Agreement a) {
        return agreementsMapper.insertAgreement(a);
    }

    public boolean update(Agreement a) {
        return agreementsMapper.update(a);
    }

    public boolean delete(Agreement a) {
        return agreementsMapper.delete(a);
    }

    //================================================================================
    //TODO: return null if item does not exist
    //Item Actions:
    //If we want to retrive an item which was not in the business
    public Item getDefectedItem(int product_id, int item_id) {
        Item ip = defectsItemsMapper.getDefectedItem(product_id, item_id);
        return ip;
    }

    //If we want to make entire new record of an item
    public boolean insertDefected(Item obj) {
        return defectsItemsMapper.insertDefectedItem(obj);
    }

    public boolean updateDefected(Item obj) {
        return defectsItemsMapper.update(obj);
    }

    public boolean deleteDefected(Item obj) {
        return defectsItemsMapper.delete(obj);
    }

    //================================================================================
    //Sales Actions:
    public LinkedList<Sale> getSaleByProduct(String product) {
        return salesMapper.getSaleByProduct(product);
    }
    public LinkedList<Sale> getSaleByCategory(String category) {
        return salesMapper.getSaleByCategory(category);
    }
    public Sale getSaleByID(int sale_id) {
        return salesMapper.getSaleByID(sale_id);
    }
    //If we want to make entire new record of an item
    public boolean insertSaleByProduct(Sale s) {
        return salesMapper.insertSaleByProduct(s);
    }
    public boolean insertSaleByCategory(Sale s) {
        return salesMapper.insertSaleByCategory(s);
    }

    public boolean update(Sale s) {
        return salesMapper.update(s);
    }

    public boolean delete(Sale s) {
        return salesMapper.delete(s);
    }
    public LinkedList<Sale> loadAllSales() {
        return salesMapper.loadAllSales();
    }


}
