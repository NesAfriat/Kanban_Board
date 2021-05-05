package BuisnnesLayer.OrderBuissness;

import BuisnnesLayer.IAgreement;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;

//    private HashMap<Integer, HashMap<Integer, Integer>> DiscountByProductQuantity;//- DiscountByProductQuantity hashMap<CatalogID:int, hashMap<quantitiy :int , newPrice:int>>
public class Order {
    private int id;
    private int SupplierID;
    private HashMap<Integer, Integer> productQuantity; //product quantity
    private LocalDate dateTime;
    private double TotalPayment=0;
    private boolean Constant;


    public boolean isConstant() {
        return Constant;
    }

    public Order(int id, int SupplierID, HashMap<Integer, Integer> products, IAgreement agreement, boolean constant)
    {
        this.id=id;
        this.SupplierID=SupplierID;
        this.productQuantity=products;
        this.dateTime= LocalDate.now();
        this.TotalPayment=CalculateTotalPayment(products,agreement);
        this.Constant=constant;
    }

    public int getSupplierID() {
        return SupplierID;
    }

    public HashMap<Integer,Integer> getProductQuantity(){
        return productQuantity;
    }
    public void AddPrudactToOrder(Integer productCatalogID,int quantity,IAgreement agreement)
    {
        if(checkIfProductIsAlreadyExist(productCatalogID)){
            throw new IllegalArgumentException("the product is already exist in the Order");
        }
        productQuantity.put(productCatalogID,quantity);
        this.TotalPayment= CalculateTotalPayment(this.productQuantity,agreement);
    }


    public boolean checkIfProductIsAlreadyExist(Integer ProductCtalogID){
        return productQuantity.containsKey(ProductCtalogID);
    }



    public void RemovePrudactFromOrder(int CatalogID,IAgreement agreement)
    {
        if(!checkIfProductIsAlreadyExist(CatalogID)){
            throw new IllegalArgumentException("the product is not in the order you cannot deleate it");
        }
        System.out.println("CatalogID::::" + CatalogID);
        productQuantity.remove(CatalogID);
        this.TotalPayment=CalculateTotalPayment(this.productQuantity,agreement);
    }



    public void EditProductQuantity(int CatalogID,int quantity,IAgreement agreement)
    {if(!checkIfProductIsAlreadyExist(CatalogID)){
        throw new IllegalArgumentException("this Product dose not exist in this order");
    }
        productQuantity.replace(CatalogID,quantity);
        CalculateTotalPayment(this.productQuantity,agreement);
    }



    public double GetTotalPayment()
    {
        return TotalPayment;
    }
    public LocalDate GetDateTime()
    {
        return dateTime;
    }
    public int GetId()
    {
        return id;
    }

    public int GetProductQuantity(int CatalogID)
    {
        return productQuantity.get(CatalogID);
    }



    public void ReCalculateTotalPayment (IAgreement agreement)
    {
        this.TotalPayment=CalculateTotalPayment(productQuantity,agreement);
    }

    public double CalculateTotalPayment (HashMap<Integer,Integer> productQuantity,IAgreement agreement){
        double TotalPayment = 0;
        for (Integer key : productQuantity.keySet()) {
            Integer value = productQuantity.get(key);
            double newprice=CheckAvailableDiscount(key,value,agreement);
            if (newprice!=-1)
            {
                TotalPayment=TotalPayment+newprice*productQuantity.get(key);

            }
            else {
                TotalPayment=TotalPayment+((agreement.getProducts().get(key)).getPrice())*value;
            }
        }
        //calculate the total payment with the extra discount
        if (agreement.getExtraDiscount()==0){
        return TotalPayment;}
        else {
            return  TotalPayment-(TotalPayment*((agreement.getExtraDiscount())/100.0));
        }
    }


    //chek if discount is avalible
    private double CheckAvailableDiscount(Integer CatalogID,Integer quantity,IAgreement agreement)
    {
        double newprice=-1;
        HashMap<Integer, Double> Temp= (agreement.getDiscountByProductQuantity()).get(CatalogID);
        if (Temp!=null&&Temp.size()!=0) {
            for (Integer key : Temp.keySet()) {
                if (quantity >= Temp.get(key)) {
                    if (newprice==-1){newprice=Temp.get(key);}
                    else {newprice = Math.min(Temp.get(key), newprice); }
                }
            }
        }
        return newprice ;
    }





}
