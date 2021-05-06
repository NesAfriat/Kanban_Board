package PresentationLayer;



import BuisnnesLayer.GeneralProduct;
import BuisnnesLayer.ProductSupplier;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class main {
    public static void main(String[] args) throws Exception {
        IO io=IO.getInstance();
        io.Start_Menu();

        //tests for dal:
//        GeneralProduct prod = new GeneralProduct(1, "kankan", "osem", 2, 2, 1, 5.1);
//        prod.addItems(10, "storage", getDate("2021-05-06"), getDate("2021-05-2"), getDate("2021-05-27"));
    }

//    public static Date getDate(String date) throws ParseException {
//        String pattern = "yyyy-MM-dd";
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
//        return simpleDateFormat.parse(date);
//    }
}