package DataLayer.Mappers;

import BuisnnesLayer.GeneralProduct;
import BuisnnesLayer.Item;
import BuisnnesLayer.Category;
import BuisnnesLayer.Reports.Report;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;


public class DataController {
    private static DataController instance = null;
    private ItemMapper itemMapper;
    private GeneralProductMapper generalProductMapper;
    private CategoriesMapper categoriesMapper;
    private ReportsMapper reportsMapper;

    public static DataController getInstance() {
        if (instance == null) {
            instance = new DataController();
        }
        return instance;
    }

    private DataController() {
        itemMapper = new ItemMapper();
        generalProductMapper = new GeneralProductMapper();
        categoriesMapper = new CategoriesMapper();
        reportsMapper = new ReportsMapper();
    }

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

    //GeneralProduct Actions:
    //If we want to retrive an product which was not in the business
    public GeneralProduct getGP(int product_id) {
        GeneralProduct gp = generalProductMapper.getGeneralProduct(product_id);
        //TODO: need to add here itemsList and supplierProductList
        return gp;
    }

    //If we want to make entire new record of an item
    public boolean insertGP(GeneralProduct obj) {
        return generalProductMapper.insertProduct(obj);
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
}
