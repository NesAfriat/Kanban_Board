package BuisnnesLayer;

import DataLayer.DataController;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

public class ProductManager {
    private boolean loadCategories;
    private boolean loadProducts;
    private HashMap<Integer, GeneralProduct> products;                       //all product in store by product id
    private HashMap<Category, LinkedList<GeneralProduct>> categories;        //all categories and their products

    public ProductManager() {
        this.products = new HashMap<>();
        this.categories = new HashMap<>();
        this.loadCategories = false;
        this.loadProducts = false;
    }

    /**
     * returns the category by its' name
     *
     * @return Category
     * @throws Exception
     */
    public Category getCategory(String cat) throws Exception {  //DONE
        if (!isCategory_in_Categories(cat))
            throw new Exception("there no such category");
        for (Category c : categories.keySet())
            if (c.getCategory_name().equals(cat)) {
                return c;
            }
        return null;    //not suppose to happen
    }

    //TODO: Fix update in the db -QUICK!!!!
    public Item set_item_defected(Integer product_id, Integer item_id) throws Exception {
        if (!isProduct_in_Products(product_id)) {
            throw new Exception("product doesn't exist!");
        }
        Item item = products.get(product_id).setItem_defected(item_id);
        return item;
    }

    public LinkedList<Item> getDefects() {
        if(!loadProducts) {
            loadAllProducts();
           loadProducts=true;
        }
        LinkedList<Item> output = new LinkedList<>();
        for (GeneralProduct product : products.values()) {
            output.addAll(product.getDefects());
        }
        return output;
    }

    public HashMap<Category, LinkedList<GeneralProduct>> getAllCategoriesProducts() { //DONE
        if (!loadCategories) {
            loadAllCategories();
            loadCategories = true;
        }
        if(!loadProducts) {
            loadAllProducts();
            loadProducts=true;
        }
        return categories;
    }

    /**
     * add a new product to the stock
     *
     * @throws Exception
     *///DONE
    public GeneralProduct addProduct(String product_name, Integer product_id, String manufacturer_name, Integer min_amount, String cat, Double selling_price, ProductSupplier productSupplier) throws Exception {
        GeneralProduct product;
        checkNameDuplication(product_name, manufacturer_name);
        if (isProduct_in_Products(product_id)) {
            throw new Exception("product already exist");
        } else if (!isCategory_in_Categories(cat)) {
            throw new Exception("category doesn't exist");
        } else {
            product = new GeneralProduct(product_name, product_id, manufacturer_name, min_amount, selling_price, productSupplier);
            addGPPersistence(product, cat);
            categories.get(getCategory(cat)).add(product);
            products.put(product_id, product);
        }
        return product;
    }

    //checks if a new product name already exist - if the producer name also exist with the same name-Exception
    private void checkNameDuplication(String product_name, String manufacturer_name) throws Exception {
        for (GeneralProduct p : products.values()) {
            if (p.getProduct_name().equals(product_name))
                if (p.getManufacturer_name().equals(manufacturer_name))
                    throw new Exception("a same product from this producer already exist in the system");
        }
                if(NameExistInDATA(product_name,manufacturer_name))
                    throw new Exception("a same product from this producer already exist in the system data");
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
        Category newCat = new Category(category);
        addCategoryPersistence(newCat);
        categories.put(newCat, new LinkedList<>());
    }

    /**
     * for the controller to check if product in stock
     *
     * @param product_id
     * @return
     */
    private boolean isProduct_in_Products(Integer product_id) { //DONE
        boolean exist=false;
        if(products.keySet().contains(product_id))
                exist=true;
        if(!exist)
            exist= checkProductExistByID(product_id);
        return exist;
    }



    /**
     * check if category is in categories
     *
     * @return
     */
    private boolean isCategory_in_Categories(String category) { //DONE
        boolean exist=false;
        for (Category c : categories.keySet())
            if (c.getCategory_name().equals(category)) {
                exist=true;
            }
        if(!exist) {
            exist = CheckCategoryExistDAl(category);
        }
        return exist;
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


    public void remove_product(Integer product_id) throws Exception { //DONE
        if (!isProduct_in_Products(product_id)) {
            throw new Exception("product doesnt exist");
        }
        if(!products.get(product_id).isSupplierProducHashEmpty()){
            throw new IllegalArgumentException("need to remove supplier's product before deleting product");
        }
        removeGP(products.remove(product_id));
        GeneralProduct toRemove= products.remove(product_id);
        removeFromCategories(toRemove);

    }

    private void removeFromCategories(GeneralProduct toRemove) { //DONE
        for(Category cat: categories.keySet())
            if(categories.get(cat).contains(toRemove))
                categories.get(cat).remove(toRemove);
    }


    public void remove_category(String cat_name) throws Exception { //DONE
        if (!isCategory_in_Categories(cat_name)) {
            throw new Exception("category doesnt exist");
        }
        Category father = getCategory(cat_name).removed();
        Category removed = removeCategoryPersistence(getCategory(cat_name));
        LinkedList<GeneralProduct> products = categories.remove(removed);
        categories.get(father).addAll(products);
    }

    public void RemoveSupplierProductFromGeneralProduct(int pid, int catalogIF){
        products.get(pid).RemoveSupplierProduct(catalogIF);
    }

    public void set_father(String cat_name, String cat_father_name) throws Exception {
        if (!isCategory_in_Categories(cat_name) || !isCategory_in_Categories(cat_father_name)) {
            throw new Exception("category doesnt exist!");
        }
        getCategory(cat_name).setFather_Category(getCategory(cat_father_name));
        setFatherPersistence(getCategory(cat_name), getCategory(cat_father_name));
    }

  //Done
    public LinkedList<GeneralProduct> get_category_products(String cat_name) throws Exception {
        LinkedList<GeneralProduct> prods = new LinkedList<>();
        if (!isCategory_in_Categories(cat_name)) {
            throw new Exception("category doesnt exist!");
        }
        Category c = getCategory(cat_name);
        if (c != null) {
            prods.addAll(categories.get(c));
            if(prods.isEmpty())
                prods.addAll(get_category_products_DAL(cat_name));
            if (!c.getSub_Category().isEmpty()) {
                for (Category cSub : c.getSub_Category())
                    prods.addAll(get_category_products(cSub.getCategory_name()));
            }
        }
        return prods;
    }

    public GeneralProduct get_product(Integer product_id) throws Exception { //DONE
        if (!isProduct_in_Products(product_id)) {
            throw new Exception("product doesnt exist!");
        }
        GeneralProduct gp;
        if(products.containsKey(product_id))
        gp= products.get(product_id);
        else
            gp=getGPFromDAL(product_id);
        return gp;
    }

    //TODO - doesnt work!!!!
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

    public HashMap<GeneralProduct, Integer> get_missing_General_products_with_amounts() {
        LinkedList<GeneralProduct> missingProd = get_missing_products();
        HashMap<GeneralProduct, Integer> output = new HashMap<>();
        for (GeneralProduct p : missingProd) {
            if (p.getMin_amount() > p.getTotal_amount()) {
                Integer amountToAdd = p.getMin_amount() - p.getTotal_amount();
                output.put(p, amountToAdd);
            }
        }
        return output;
    }


    //for suppliers, return Map<Product.id, product_missing_amount>
    public HashMap<Integer, Integer> get_missing_products_with_amounts() {
        LinkedList<GeneralProduct> missingProd = get_missing_products();
        HashMap<Integer, Integer> output = new HashMap<>();
        for (GeneralProduct p : missingProd) {
            if (p.getMin_amount() > p.getTotal_amount()) {
                Integer amountToAdd = p.getMin_amount() - p.getTotal_amount();
                output.put(p.getProduct_id(), amountToAdd);
            }
        }
        return output;
    }
    public LinkedList<GeneralProduct> get_missing_products() { //DONE
        LinkedList<GeneralProduct> output = new LinkedList<>();
        if(!loadProducts){
            loadAllProducts();
            loadProducts=true;
        }
        for (GeneralProduct p : products.values()) {
            if (p.getTotal_amount() < p.getMin_amount()) {
                output.add(p);
            }
        }
        return output;
    }

    //DONE
    public LinkedList<String> get_product_categories(GeneralProduct product) {
        LinkedList<String> output = new LinkedList<>();
        Category tmp;
        loadProductCategoryDal(product); //load the product category from the data
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

    //TODO: after load - does not show the products
    public LinkedList<String> get_all_products() {
        LinkedList<String> output = new LinkedList<>();
        if(!loadProducts){
            loadAllProducts();
            loadProducts = true;
        }
        for (GeneralProduct product : products.values()) {
            output.add("product ID: " + product.getProduct_id() + "- " + product.getProduct_name() + "_" + product.getManufacturer_name() + '\n');
        }
        return output;
    }
    //TODO: after load - show the 2 categories - does not show
    public LinkedList<String> get_all_categories() {
        if (!loadCategories) {
            loadAllCategories();
            loadCategories = true;
        }
        int counter = 1;
        LinkedList<String> output = new LinkedList<>();
        for (Category category : categories.keySet()) {
            output.add(counter + ") " + category.getCategory_name() + "\n");
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

    //Done
    public boolean check_category_exist(String cat_name) {
        return isCategory_in_Categories(cat_name);
    }
    //DONE
    public boolean check_product_exist(String prod_name) {
        boolean exist=false;
            for (GeneralProduct p : products.values()) {
                if (p.getProduct_name().equals(prod_name))
                exist= true;
        }
        if(!exist)
            exist= CheckGPExistByName(prod_name);
        return exist;
    }


    public boolean check_product_id_exist(int id) {
        boolean exist=false;
        for (GeneralProduct p : products.values()) {
            if(p.getProduct_id()==id)
                return true;
        }
        exist=checkProductExistByID(id);
        return exist;
    }

    public void AddProductSupplierToProductGeneral(ProductSupplier productSupplier, int generalId) {
        if (!products.containsKey(generalId)) {
            throw new IllegalArgumentException("the stock product is not exsist");
        }
        products.get(generalId).addSupplierProduct(productSupplier);
    }
    //=================================
    //Data Fucntions
    private void loadAllCategories() {
        DataController dc = DataController.getInstance();
        IdentityMap im = IdentityMap.getInstance();
        LinkedList<Category> categoriesList = dc.loadAllCategoreis();
        for (Category c : categoriesList) {
            im.addCategory(c);
            if (!categories.containsKey(c))
                categories.put(c, new LinkedList<>());
        }
    }
    private void loadAllProducts() {
        DataController dc = DataController.getInstance();
        IdentityMap im = IdentityMap.getInstance();
        LinkedList<GeneralProduct> productsList = dc.loadAllGeneralProducts();
        for (GeneralProduct gp : productsList) {
            im.addGeneralProduct(gp);
            if (!products.containsKey(gp.getProduct_id()))
                products.put(gp.getProduct_id(), gp);
            loadProductCategoryDal(gp);
        }
    }
    private GeneralProduct getGPFromDAL(Integer product_id) throws Exception {
        DataController dc = DataController.getInstance();
        IdentityMap im = IdentityMap.getInstance();
        GeneralProduct gp;
        gp=im.getGeneralProduct(product_id);
        if(gp==null) {
            gp = dc.getGP(product_id);
            im.addGeneralProduct(gp);
        }
        if (!products.containsKey(gp.getProduct_id()))
            products.put(gp.getProduct_id(),gp);
        String cat_name= dc.getGPCategory(gp);
            Category c= getCategory(cat_name);
            if(!categories.get(c).contains(gp))
                categories.get(c).add(gp);
        return gp;
    }
    private void loadProductCategoryDal(GeneralProduct gp) {
        DataController dc = DataController.getInstance();
        String catName=dc.getGPCategory(gp);
        Category gpCat= getCategoryFromDAL(catName);
        categories.get(gpCat).add(gp);
    }
    private LinkedList<GeneralProduct> get_category_products_DAL(String cat_name) throws Exception {
        DataController dc = DataController.getInstance();
        IdentityMap im = IdentityMap.getInstance();
        LinkedList<GeneralProduct> productsList = dc.get_category_products_DAL(cat_name);
        for (GeneralProduct gp : productsList) {
            im.addGeneralProduct(gp);
            if (!products.containsKey(gp.getProduct_id()))
                products.put(gp.getProduct_id(),gp);
            Category c= getCategory(cat_name);
            if(!categories.get(c).contains(gp))
                categories.get(c).add(gp);
        }
        return productsList;
    }

    private void addCategoryPersistence(Category newCat) {
        DataController dc = DataController.getInstance();
        IdentityMap im = IdentityMap.getInstance();
        im.addCategory(newCat);
        dc.insertCategory(newCat);
    }

    private Category removeCategoryPersistence(Category toRemove) {
        Category removed;
        DataController dc = DataController.getInstance();
        IdentityMap im = IdentityMap.getInstance();
        removed = im.removeCategory(toRemove);
        dc.delete(toRemove);
        return removed;
    }

    private void setFatherPersistence(Category category, Category father) {
        DataController dc = DataController.getInstance();
        dc.setFather(category, father);
    }

    private void addGPPersistence(GeneralProduct prod, String catName) {
        IdentityMap im = IdentityMap.getInstance();
        DataController dc = DataController.getInstance();
        if (!dc.insertGP(prod, catName)) {
            System.out.println("failed to insert new General Product to the database with the keys: gpID= " + prod.getProduct_id());
        }
        im.addGeneralProduct(prod);
    }

    private void removeGP(GeneralProduct product) {
        IdentityMap im = IdentityMap.getInstance();
        DataController dc = DataController.getInstance();
        product.removeItems();
        im.removeGeneralProd(product.getProduct_id());
        dc.delete(product);
    }

 private boolean NameExistInDATA(String product_name, String manufacturer_name) {
     DataController dc = DataController.getInstance();
     return dc.CheckGPInputExist(product_name,manufacturer_name);
 }

    private boolean checkProductExistByID(Integer gpID) {
        DataController dc = DataController.getInstance();
        return dc.checkPrductExist(gpID);
    }
    private boolean CheckGPExistByName(String prod_name) {
        DataController dc = DataController.getInstance();
        return dc.checkPrductExist(prod_name);
    }

    private boolean CheckCategoryExistDAl(String category) {
        boolean found=false;
        Category cat;
        IdentityMap im = IdentityMap.getInstance();
        DataController dc = DataController.getInstance();
        cat= im.getCategory(category);
        if(cat!=null) {
            found = true;
        }
        else {
            cat = dc.getCategory(category);
            if (cat != null) {
                found = true;
                im.addCategory(cat);
            }
        }
        return found;
    }

    private Category getCategoryFromDAL(String category) {
        boolean found=false;
        Category cat;
        IdentityMap im = IdentityMap.getInstance();
        DataController dc = DataController.getInstance();
        cat= im.getCategory(category);
        if(cat!=null) {
            found = true;
            categories.put(cat,new LinkedList<>());
        }
       else {
            cat = dc.getCategory(category);
            if (cat != null) {
                found = true;
                categories.put(cat,new LinkedList<>());
                im.addCategory(cat);
            }
        }
    return cat;
    }
}
