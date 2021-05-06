package BuisnnesLayer;

import BuisnnesLayer.Category;
import BuisnnesLayer.GeneralProduct;
import BuisnnesLayer.Item;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

public class productManager {
    private HashMap<Integer, GeneralProduct> productsfuture;                       //all product in store by product id

    private HashMap<Integer, GeneralProduct> products;                       //all product in store by product id
    private HashMap<Category, LinkedList<GeneralProduct>> categories;        //all categories and their products

    public productManager(){
        this.products = new HashMap<>();
        this.categories = new HashMap<>();
    }

    /**
     * returns the category by its' name
     *
     * @return Category
     * @throws Exception
     */
    public Category getCategory(String cat) throws Exception {
        if (!isCategory_in_Categories(cat))
            throw new Exception("there no such category");
        for (Category c : categories.keySet())
            if (c.getCategory_name().equals(cat)) {
                return c;
            }
        return null;    //not suppose to happen
    }

    public Item set_item_defected(Integer product_id, Integer item_id) throws Exception {
        if (!isProduct_in_Products(product_id)) {
            throw new Exception("product doesn't exist!");
        }
        Item item = products.get(product_id).setItem_defected(item_id);
        return item;
    }

    public LinkedList<Item> getDefects(){
        LinkedList<Item> output = new LinkedList<>();
        for (GeneralProduct product : products.values()) {
            output.addAll(product.getDefects());
        }
        return output;
    }

    public HashMap<Category, LinkedList<GeneralProduct>> getCategories() {
        return categories;
    }

    /**
     * add a new product to the stock
     *
     * @throws Exception
     */
    public void addProduct(String product_name, Integer product_id, String manufacturer_name, Integer min_amount,  String cat, Double selling_price,ProductSupplier productSupplier) throws Exception {
        checkNameDuplication(product_name,manufacturer_name);
        GeneralProduct product = new GeneralProduct(product_name, product_id, manufacturer_name, min_amount,selling_price,productSupplier);
        if (isProduct_in_Products(product_id)) {
            throw new Exception("product already exist");
        }
        else
        if (!isCategory_in_Categories(cat)) {
            throw new Exception("category doesn't exist");
        } else {
            categories.get(getCategory(cat)).add(product);
        }
        products.put(product_id, product);
    }

    //checks if a new product name already exist - if the producer name also exist with the same name-Exception
    private void checkNameDuplication(String product_name, String manufacturer_name) throws Exception {
        for(GeneralProduct p: products.values())
            if (p.getProduct_name().equals(product_name))
                if(p.getManufacturer_name().equals(manufacturer_name))
                    throw new Exception("a same product from this producer already exist in the system");
    }

    /**
     * add a new category to the stock
     *
     * @param category
     */
    public void addCategory(String category) throws Exception {
        if (isCategory_in_Categories(category)) {
            throw new Exception("category already exist");
        }
        categories.put(new Category(category), new LinkedList<>());
    }

    /**
     * for the controller to check if product in stock
     *
     * @param product_id
     * @return
     */
    private boolean isProduct_in_Products(Integer product_id) {
        for (Integer p : products.keySet()) {
            if (p.equals(product_id)) {
                return true;
            }
        }
        return false;
    }

    /**
     * check if category is in categories
     *
     * @return
     */
    private boolean isCategory_in_Categories(String category) {
        for (Category c : categories.keySet())
            if (c.getCategory_name().equals(category)) {
                return true;
            }
        return false;
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
        if (!isProduct_in_Products(product_id)) {
            throw new Exception("product does not exist!");
        }
        return products.get(product_id).addItems(quantity, location, supplied_date, creation_date, expiration_date);
    }

    /**
     * update the location of a specific product
     *
     * @param item_id
     * @param product_id
     * @param new_location
     */
    public void update_location(Integer item_id, Integer product_id, String new_location) throws Exception {
        if (!isProduct_in_Products(product_id)) {
            throw new Exception("product doesn't exist");
        }
        products.get(product_id).setItem_location(item_id, new_location);
    }

    public void remove_product(Integer product_id) throws Exception {
        if (!isProduct_in_Products(product_id)) {
            throw new Exception("product doesnt exist");
        }
        if(!products.get(product_id).isSupplierProducHashEmpty()){
            throw new IllegalArgumentException("the supliier product hash is no empty you canot deleate it");
        }
        products.remove(product_id);
    }



    public void remove_category(String cat_name) throws Exception {
        if (!isCategory_in_Categories(cat_name)) {
            throw new Exception("category doesnt exist");
        }
        Category father = getCategory(cat_name).removed();
        LinkedList<GeneralProduct> products = categories.remove(getCategory(cat_name));
        categories.get(father).addAll(products);
//        categories.put(father, mergeProductLists(categories.get(father), products));
    }

    public void RemoveSupllierProductFromGeneralProduct(int pid,int catalogIF){
        products.get(pid).RemoveSupplierProduct(catalogIF);


    }

    public void set_father(String cat_name, String cat_father_name) throws Exception {
        if (!isCategory_in_Categories(cat_name) || !isCategory_in_Categories(cat_father_name)) {
            throw new Exception("category doesnt exist!");
        }
        getCategory(cat_name).setFather_Category(getCategory(cat_father_name));
    }

    public LinkedList<GeneralProduct> get_category_products(String cat_name) throws Exception {
        LinkedList<GeneralProduct> prods = new LinkedList<>();
        if (!isCategory_in_Categories(cat_name)) {
            throw new Exception("category doesnt exist!");
        }
        Category c = getCategory(cat_name);
        if (c != null) {
            prods.addAll(categories.get(c));
            if (!c.getSub_Category().isEmpty()) {
                for (Category cSub : c.getSub_Category())
                    prods.addAll(get_category_products(cSub.getCategory_name()));
            }
        }
        return prods;
    }

    public GeneralProduct get_product(Integer product_id) throws Exception {

        if (!isProduct_in_Products(product_id)) {
            throw new Exception("product doesnt exist!");
        }
        return products.get(product_id);
    }



    public void removeItem(Integer product_id, Integer item_id) throws Exception {
        if (!isProduct_in_Products(product_id)) {
            throw new Exception("product doesnt exist!");
        }
        products.get(product_id).removeItem(item_id);
    }

    public void update_product_min_amount(Integer product_id, Integer min_amount) throws Exception {
        if (!isProduct_in_Products(product_id)) {
            throw new Exception("product doesnt exist");
        }
        check_valid_amount(min_amount);
        products.get(product_id).setMin_amount(min_amount);
    }
    private void check_valid_amount(Integer amount) throws Exception {
        if (amount < 0) {
            throw new Exception("min amount cant be negative!");
        }
    }

    public HashMap<GeneralProduct, Integer> get_missing_General_products_with_amounts(){

        LinkedList<GeneralProduct> missingProd = get_missing_products();
        HashMap<GeneralProduct, Integer> output = new HashMap<>();
        for (GeneralProduct p : missingProd) {
            if (p.getMin_amount() > p.getTotal_amount()) {
                Integer amountToAdd = p.getMin_amount() - p.getTotal_amount();
                output.put(p,amountToAdd);
            }
        }
        return output;
    }


    //for suppliers, return Map<Product.id, product_missing_amount>
    public HashMap<Integer, Integer> get_missing_products_with_amounts(){

        LinkedList<GeneralProduct> missingProd = get_missing_products();
        HashMap<Integer, Integer> output = new HashMap<>();
        for (GeneralProduct p : missingProd) {
            if (p.getMin_amount() > p.getTotal_amount()) {
                Integer amountToAdd = p.getMin_amount() - p.getTotal_amount();
                output.put(p.getProduct_id(),amountToAdd);
            }
        }
        return output;
    }

    public LinkedList<GeneralProduct> get_missing_products() {
        LinkedList<GeneralProduct> output = new LinkedList<>();
        for (GeneralProduct p : products.values()) {
            if (p.getTotal_amount() < p.getMin_amount()) {
                output.add(p);
            }
        }
        return output;
    }

    public LinkedList<String> get_product_categories(GeneralProduct product) {
        LinkedList<String> output = new LinkedList<>();
        Category tmp;
        for (Category cat : categories.keySet()) {
            if (categories.get(cat).contains(product)) {
                tmp = cat;
                while (tmp != null) {
                    output.add(tmp.getCategory_name());
                    tmp = tmp.getFather_Category();
                }
            }
        }
        return output;
    }

    public LinkedList<String> get_all_products() {
        LinkedList<String> output = new LinkedList<>();
        for (GeneralProduct product : products.values()) {
            output.add("product ID: "+product.getProduct_id() + "- " + product.getProduct_name()+"_"+product.getManufacturer_name()+'\n');
        }
        return output;
    }

    public LinkedList<String> get_all_categories() {
        int counter=1;
        LinkedList<String> output = new LinkedList<>();
        for (Category category : categories.keySet()) {
            output.add(counter+ ") "+category.getCategory_name() + "\n");
            counter++;
        }
        return output;
    }

    public void update_product_selling_price(int product_id, double price) throws Exception {
        if (!isProduct_in_Products(product_id)) {
            throw new Exception("product doesnt exist!");
        }
        products.get(product_id).setSelling_price(price);
    }

    public LinkedList<Item> get_product_items(Integer product_id) throws Exception {
        if (!isProduct_in_Products(product_id)) {
            throw new Exception("product doesnt exist!");
        }
        return products.get(product_id).get_items();
    }

    //for inside use
    public boolean check_category_exist(String cat_name) {
        for (Category cat : categories.keySet()) {
            if(cat.getCategory_name().equals(cat_name))
                return true;
        }
        return false;
    }

    public boolean check_product_exist(String prod_name) {
        for (GeneralProduct p : products.values()) {
            if(p.getProduct_name().equals(prod_name))
                return true;
        }
        return false;
    }
    public boolean check_product_id_exist(int id) {
        for (GeneralProduct p : products.values()) {
            if(p.getProduct_id()==id)
                return true;
        }
        return false;
    }

    public void AddProcuctSupplierToProductGeneral(ProductSupplier productSupplier,int generalId){
        if(!products.containsKey(generalId)){
            throw new IllegalArgumentException("the stock product is not exsist");
        }
        products.get(generalId).addSupplierProduct(productSupplier);

    }

}
