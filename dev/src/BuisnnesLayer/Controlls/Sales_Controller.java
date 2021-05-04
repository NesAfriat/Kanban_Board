package BuisnnesLayer.Controlls;

import BuisnnesLayer.Sales.Sale;
import BuisnnesLayer.Sales.SaleByProduct;
import BuisnnesLayer.Sales.Sale_Category;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

public class Sales_Controller {
    private static Sales_Controller sale_C = null;
    private HashMap<Integer, Sale> sales;           //holds all sales ever created
    private Integer sales_id;

    private Sales_Controller() {
        this.sales = new HashMap<>();
        sales_id = 0;
    }

    public static Sales_Controller getInstance() {
        if (sale_C == null)
            sale_C = new Sales_Controller();
        return sale_C;
    }

    private void check_valid_string(String[] arr) throws Exception {
        for (String str : arr) {
            if (str.length() == 0)
                throw new Exception("input was '' which is invalid");
            if (str.length() >= 300)
                throw new Exception("input was too long (300) which is invalid");
        }
    }

    private void check_valid_string(LinkedList<String> lst) throws Exception {
        for (String str : lst) {
            if (str.length() == 0)
                throw new Exception("input was '' which is invalid");
            if (str.length() >= 300)
                throw new Exception("input was too long (300) which is invalid");
        }
    }

    private void check_valid_number(Number[] arr) throws Exception {
        for (Number number : arr) {
            if (number.doubleValue() < 0) {
                throw new Exception("negative number is not allowed");
            }
        }
    }

    private void check_discount(double discount) throws Exception {
        if (discount > 100) {
            throw new Exception("discount can't be above 100");
        }
    }

    private void check_valid_Dates(Date start, Date end) throws Exception {
        Date now = new Date();
        if (start.after(end))
            throw new Exception("start date can't be after end date");
        if (end.before(now))
            throw new Exception("end date can't be before today");
    }

    public void createSaleByCategory(Double discount_percent, String sale_description, Date start_date, Date end_date,
                                     LinkedList<String> affected_Category) throws Exception {
        check_valid_string(affected_Category);
        check_valid_string(new String[]{sale_description});
        check_valid_number(new Number[]{discount_percent});
        check_discount(discount_percent);
        check_valid_Dates(start_date, end_date);
        Sale newSale = new Sale_Category(sales_id, discount_percent, sale_description, start_date, end_date, affected_Category);
        sales.put(sales_id, newSale);
        sales_id++;
    }

    public void createSaleByProduct(Double discount_percent, String sale_description, Date start_date, Date end_date, LinkedList<String> affected_Products) throws Exception {
        check_valid_string(affected_Products);
        check_valid_string(new String[]{sale_description});
        check_valid_number(new Number[]{discount_percent});
        check_discount(discount_percent);
        check_valid_Dates(start_date, end_date);
        Sale newSale = new SaleByProduct(sales_id, discount_percent, sale_description, start_date, end_date, affected_Products);
        sales.put(sales_id, newSale);
        sales_id++;
    }

    public void updateSales() throws Exception {
        for (Sale s : sales.values()) {
            if (s.getEnd_date().before(new Date()))
                removeSale(s.getSale_id());
        }
    }

    public void removeSale(int sales_id) throws Exception {
        if (!sales.containsKey(sales_id))
            throw new Exception("sale id does not exist");
        else
            sales.remove(sales_id);
    }

    public LinkedList<Sale> allSales() {
        return new LinkedList<>(sales.values());
    }

    public LinkedList<Sale> getSalesByCategory(String category) throws Exception {
        check_valid_string(new String[]{category});
        LinkedList<Sale> salesByPro = new LinkedList<>();
        for (Sale s : sales.values()) {
            for (String a : s.getAffected())
                if (a.equals(category))
                    salesByPro.add(s);
        }
        return salesByPro;
    }

    public LinkedList<Sale> getSalesByProduct(String product) throws Exception {
        check_valid_string(new String[]{product});
        LinkedList<Sale> salesByProd = new LinkedList<>();
        for (Sale s : sales.values()) {
            for (String a : s.getAffected())
                if (a.equals(product))
                    salesByProd.add(s);
        }
        return salesByProd;
    }

    //inside use
    public Double getDiscountByCategory(LinkedList<String> categories) throws Exception {
        Double discount;
        Double max_discount = -1.0;
        for (String cat : categories) {
            for (Sale s : sales.values()) {
                if (s.getAffected().contains(cat)) {
                    discount = s.getDiscount_percent();
                    if (max_discount < discount)
                        max_discount = discount;
                }
            }
        }
        if (max_discount == -1.0)
            return 0.0;
        return max_discount;
    }

    //inside use
    public Double getDiscountByProduct(String product) {
        Double discount;
        Double max_discount = -1.0;
        for (Sale s : sales.values()) {
            if (s.getAffected().contains(product)) {
                discount = s.getDiscount_percent();
                if (max_discount < discount)
                    max_discount = discount;
            }
        }
        if (max_discount == -1.0)
            return 0.0;
        return max_discount;
    }

    public void update_sale_description(int sale_id, String desc) throws Exception {
        check_valid_string(new String[]{desc});
        check_valid_number(new Number[]{sale_id});
        if (!sales.containsKey(sale_id))
            throw new Exception("sale id does not exist");
        sales.get(sale_id).setSale_description(desc);
    }

    public void update_sale_discount(int sale_id, double disc) throws Exception {
        check_valid_number(new Number[]{sale_id, disc});
        check_discount(disc);
        if (!sales.containsKey(sale_id))
            throw new Exception("sale id does not exist");
        sales.get(sale_id).setDiscount_percent(disc);
    }
}

