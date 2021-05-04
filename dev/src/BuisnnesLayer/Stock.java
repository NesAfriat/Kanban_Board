package BuisnnesLayer;

import BuisnnesLayer.Item;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;


public class Stock {
    private LinkedList<Item> defects;                                   //all defect items
    private  productManager productManager;

    public Stock(productManager productManager) {
        this.defects = new LinkedList<>();
        this.productManager=productManager;
    }


    public Category getCategory(String cat) throws Exception {
        return this.productManager.getCategory(cat);
    }
    public LinkedList<Item> getDefects() {
        return defects;
    }

    @Override
    public String toString() {
        return "Stock{" +
                ", defects=" + defects +
                '}';
    }




    /**
     * add a new category to the stock
     *
     * @param category
     */
    public void addCategory(String category) throws Exception {
        this.productManager.addCategory(category);
    }

    /**
     * add a new item to the defects
     *
     * @param item
     */
    public void addDefect(Item item) {
        defects.add(item);
    }

    /**
     * for the controller to check if item in defects
     *
     * @param item
     * @return
     */
    private boolean isItem_in_Defects(Item item) {
        return defects.contains(item);
    }

    /**
     * add items of a specific product to the stock
     *
     * @param product_id
     * @param quantity
     * @param location
     * @param supplied_date
     * @param creation_date
     * @param expiration_date
     */
    public LinkedList<Integer> addItems(Integer product_id, Integer quantity, String location, Date supplied_date, Date creation_date, Date expiration_date) throws Exception {
        return this.productManager.addItems(product_id, quantity, location, supplied_date, creation_date, expiration_date);
    }

    /**
     * update the location of a specific product
     *
     * @param item_id
     * @param product_id
     * @param new_location
     */
    public void update_location(Integer item_id, Integer product_id, String new_location) throws Exception {
        this.productManager.update_location(item_id,product_id, new_location);
    }

    /**
     * first it get all the defected items of each product, and then return all defected items
     *
     * @return
     */
    public LinkedList<Item> get_defected_items() {
        defects.addAll(this.productManager.getDefects());
        return defects;
    }

    public void remove_product(Integer product_id) throws Exception {
        this.productManager.remove_product(product_id);
    }

    public void remove_category(String cat_name) throws Exception {
        this.productManager.remove_category(cat_name);
    }

    public void set_father(String cat_name, String cat_father_name) throws Exception {
        this.productManager.set_father(cat_name, cat_father_name);
    }

    public LinkedList<GeneralProduct> get_category_products(String cat_name) throws Exception {
        return this.productManager.get_category_products(cat_name);
    }


    public GeneralProduct get_product(Integer product_id) throws Exception {
        return this.productManager.get_product(product_id);
    }

    public void clear_defected() {
        defects.clear();
    }



    public void removeItem(Integer product_id, Integer item_id) throws Exception {
        this.productManager.removeItem(product_id, item_id);
    }

    public void update_product_min_amount(Integer product_id, Integer min_amount) throws Exception {
        this.productManager.update_product_min_amount(product_id, min_amount);
    }



    public LinkedList<GeneralProduct> get_missing_products() {
        return this.productManager.get_missing_products();
    }
    public HashMap<Integer, Integer> get_missing_products_with_amounts(){
        return this.productManager.get_missing_products_with_amounts();
    }


    public void set_item_defected(Integer product_id, Integer item_id) throws Exception {
        defects.addLast(this.productManager.set_item_defected(product_id,item_id));
    }

    public LinkedList<String> get_product_categories(GeneralProduct product) {
        return this.productManager.get_product_categories(product);
    }


    public LinkedList<String> get_all_products() {
        return this.productManager.get_all_products();
    }

    public LinkedList<String> get_all_categories() {
        return this.productManager.get_all_categories();
    }

    public void update_product_selling_price(int product_id, double price) throws Exception {
        this.productManager.update_product_selling_price(product_id, price);
    }


    ///////////////////////TEST FUNCTIONS/////////////////////////


    public boolean getDefectedItem_Test(int item_id) {
        for (Item i : defects)
            if (i.getItem_id() == item_id)
                return true;
        return false;
    }

    public LinkedList<Item> get_product_items(Integer product_id) throws Exception {
        return this.productManager.get_product_items(product_id);
    }
    //for inside use
    public boolean check_category_exist(String cat_name) {
        return this.productManager.check_category_exist(cat_name);
    }


    public boolean check_product_exist(String prod_name) {
        return this.productManager.check_product_exist(prod_name);
    }


}
