package BuisnnesLayer;

import java.util.HashMap;
import java.util.List;

public class Agreement implements IAgreement {
    private HashMap<Integer, HashMap<Integer, Double>> DiscountByProductQuantity;//- DiscountByProductQuantity hashMap<CatalogID:int, hashMap<quantitiy :int , newPrice:int>>
    private HashMap<Integer, ProductSupplier> products; //- products: hashMap<CatalogID: int, product: Product>
    private int ExtraDiscount=0;//%
    private int SupplierID ;
    private DeliveryMode deliveryMods ;
    private List<Integer> daysOfDelivery;
    private int numOfDaysFromOrder;

    public Agreement(int SupplierID , DeliveryMode delivery,List<Integer> daysOfDelivery,int numOfDaysFromOrder)
    {
        this.SupplierID=SupplierID;
        this.products=new HashMap<>();
        deliveryMods=delivery;
        this.numOfDaysFromOrder=numOfDaysFromOrder;
        this.daysOfDelivery=daysOfDelivery;
        DiscountByProductQuantity=new HashMap<>(new HashMap<>());
    }


    public double Calculate_cost(ProductSupplier productSupplier,int quantity) {
        double cost=-1;

        if (products.containsKey(productSupplier.getCatalogID()))
        {
            double newprice=CheckAvailableDiscount(productSupplier.getCatalogID(),quantity);
            if (newprice!=-1) { cost=newprice*quantity; }
            else {cost=(productSupplier.getPrice())*quantity; }
            cost=  cost-(cost*(ExtraDiscount)/100.0);
        }

        return cost;
    }
    private double CheckAvailableDiscount(Integer CatalogID,Integer quantity)
    {
        double newprice=-1;
        HashMap<Integer, Double> Temp= DiscountByProductQuantity.get(CatalogID);
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

    public void removeDiscountQuantity(int CatalogId,int Quantiti){
        if(!DiscountByProductQuantity.containsKey(CatalogId)){
            throw new IllegalArgumentException("the product donot have discount by quantity at all");
        }
        if(!DiscountByProductQuantity.get(CatalogId).containsKey(Quantiti)){
            throw new IllegalArgumentException("the product do not have discount with this quantity");
        }
        DiscountByProductQuantity.get(CatalogId).remove(Quantiti);

    }
    public void SetDeliveryMode(DeliveryMode deliveryMods, List<Integer> daysOfDelivery,int numOfDaysFromOrder) {
        this.deliveryMods=deliveryMods;
        this.daysOfDelivery=daysOfDelivery;
        this.numOfDaysFromOrder=numOfDaysFromOrder;
    }

    public ProductSupplier AddPrudact(double Price,int CatalogID,int idProductCounter,String name)
    {
        if(isProductExist(CatalogID)){
            throw new IllegalArgumentException("the product is exist already");
        }
        if(Price<0){
            throw new IllegalArgumentException("the Price is Illegal");
        }
        ProductSupplier productSupplier=new ProductSupplier(Price,CatalogID,idProductCounter,name);
        products.put(CatalogID,productSupplier);
        return productSupplier;
    }

    public void RemovePrudact(int CatalogID)
    {
        if(!isProductExist(CatalogID)){
            throw new IllegalArgumentException("the product is not exist");
        }

        products.remove(CatalogID);
        DiscountByProductQuantity.remove(CatalogID);//check if need to ceck if exist
    }
    public void AddDiscountByProduct(int CatalogID,int quantity, double newPrice)
    {
        if(!isProductExist(CatalogID)){
            throw new IllegalArgumentException("the product is not exist");
        }
        if (newPrice<0)throw new IllegalArgumentException("Illegal Price, the price need to be positive!");
        if (quantity<0)throw new IllegalArgumentException("Illegal quantity, the quantity need to be positive!");

        if (!DiscountByProductQuantity.containsKey(CatalogID)) {
            DiscountByProductQuantity.put(CatalogID, new HashMap<Integer, Double>());
        }

         (DiscountByProductQuantity.get(CatalogID)).put(quantity,newPrice );
    }
    public void SetExtraDiscount(int ExtraDiscount)
    {
        if (ExtraDiscount<0|ExtraDiscount>100)throw new IllegalArgumentException("Illegal Discount, the Discount need to be between 0-100 %");
        this.ExtraDiscount=ExtraDiscount;
    }

    public ProductSupplier GetPrudact(int CatalogID)
    {
        if (!products.containsKey(CatalogID))throw new IllegalArgumentException("this CatalogID dose not exist");
        return products.get(CatalogID);
    }

    public void setProductPrice(int CatalogID,double price) {
        if(!isProductExist(CatalogID)){ throw new IllegalArgumentException("the product is not exists"); }

        if (price<0)throw new IllegalArgumentException("Illegal price, the price need to be positive!");

        (products.get(CatalogID)).setPrice(price);
    }

    public HashMap<Integer, HashMap<Integer, Double>> getDiscountByProductQuantity() {
        return DiscountByProductQuantity;
    }
    public boolean isProductExist(int CatalogId){
        return products.containsKey(CatalogId);
    }

    public HashMap<Integer, ProductSupplier> getProducts() {
        return products;
    }

    public int getExtraDiscount() {
        return ExtraDiscount;
    }

    public int getSupplierID() {
        return SupplierID;
    }

    public DeliveryMode getDeliveryMode() {
        return deliveryMods;
    }

    public List<Integer> getDaysOfDelivery() {
        return daysOfDelivery;
    }

    public int getNumOfDaysFromOrder() {
        return numOfDaysFromOrder;
    }

/*
    public void AddDaysOfDelivery(int day)
    {
        if (day<1 || day>7)throw new IllegalArgumentException("Illegal day, only between 1-7");
        daysOfDelivery.add(day);
    }
    public void DeleteDaysOfDelivery(int day)
    {
        if (!daysOfDelivery.contains(day))throw new IllegalArgumentException("daysOfDelivery don't contain this day ");
        daysOfDelivery.remove(day);
    }
    public void EditNumOfDaysFromOrder(int numOfDays)
    {
        numOfDaysFromOrder=numOfDays;
    }
    public void DeleteNumOfDaysFromOrder()
    {
        numOfDaysFromOrder=-1;
    }
*/

}
