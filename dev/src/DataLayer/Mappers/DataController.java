package DataLayer.Mappers;

<<<<<<< HEAD
import BuisnnesLayer.GeneralProduct;
import BuisnnesLayer.Item;
=======
import BuisnnesLayer.Category;
import BuisnnesLayer.Item;
import DataLayer.PersistanceObjects.ItemPer;

import java.sql.*;
import java.util.LinkedList;
>>>>>>> stock_Data


public class DataController {
    private static DataController instance = null;
    private ItemMapper itemMapper;
<<<<<<< HEAD
    private GeneralProductMapper generalProductMapper;
=======
    private CategoriesMapper CategoriesMapper;
>>>>>>> stock_Data

    public static DataController getInstance() {
        if (instance == null) {
            instance = new DataController();
        }
        return instance;
    }

    private DataController() {
        itemMapper = new ItemMapper();
<<<<<<< HEAD
        generalProductMapper = new GeneralProductMapper();
=======
        CategoriesMapper= new CategoriesMapper();
>>>>>>> stock_Data
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

<<<<<<< HEAD
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

=======
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
    public boolean setFather(Category cat,Category father_cat) {

        return CategoriesMapper.setFather(cat,father_cat);
    }
    public LinkedList<Category> loadAllCategoreis() {
     return  CategoriesMapper.loadAllCategories();
    }
>>>>>>> stock_Data
}
