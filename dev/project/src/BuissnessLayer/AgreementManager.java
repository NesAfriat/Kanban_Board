package BuissnessLayer;

import java.util.HashMap;
import java.util.List;

public class AgreementManager {
    // List<Product> ProductsInTheSystem ;//לא מיותר?
    private HashMap<Integer, IAgreement> SupplierAgreement; //- SupplierAgreement : hashmap<SupplierId : int ,agreement :Agreement>
    private int idProductCounter;// this id is for product constructor we need it for db

    public AgreementManager() {
        // ProductsInTheSystem=new LinkedList<>();
        SupplierAgreement = new HashMap<>();
        idProductCounter = 0;
    }

    public void AddNewAgreement(IAgreement agreement) {
        SupplierAgreement.put(agreement.getSupplierID(), agreement);

    }

    public void AddNewAgreement(int id, DeliveryMode deliveryMode, List<Integer> daysOfDelivery, int NumOfDaysFromDelivery, Product product) {
        product.setId(idProductCounter);
        idProductCounter++;
        SupplierAgreement.put(id,new Agreement(id,deliveryMode,daysOfDelivery,NumOfDaysFromDelivery,product));

    }

    public IAgreement GetAgreement(int SupplierId) {
        if(!isSupplierExist(SupplierId)){
            throw new IllegalArgumentException("the Supplier IS nOT exsist");
        }
        return SupplierAgreement.get(SupplierId);

    }
    public IAgreement RemoveAgreement(int SupplierId) {

        return SupplierAgreement.remove(SupplierId);

    }
    public void AddProduct(int SupplierId,double Price, int CatalogID, String manfucator, String name){
        if(!isSupplierExist(SupplierId)){
            throw new IllegalArgumentException("the Supplier IS nOT exsist");
        }
        if(GetAgreement(SupplierId).isProductExist(CatalogID)){
            throw new IllegalArgumentException("the Product already exsist");
        }

        (SupplierAgreement.get(SupplierId)).AddPrudact(Price,CatalogID,manfucator,name,idProductCounter);
        idProductCounter++;
   }
   public boolean isSupplierExist(Integer suplplierId){
        return SupplierAgreement.containsKey(suplplierId);
   }
}
 //   public void ReplaceAgreement(int SupplierId,IAgreement agreement){
   //     SupplierAgreement.put(SupplierId,agreement);
  //  }

//    public void AddProduct(Product product){
//        if (!ProductsInTheSystem.contains(product)) {
//            ProductsInTheSystem.add(product);
//            idProductCounter++;
//        }
//    }
//    public void RemoveProduct(Product product){
//        if (ProductsInTheSystem.contains(product)) {
//            ProductsInTheSystem.remove(product);
//        }
//        else throw new IllegalArgumentException("the product is not exist in this System");
//    }
//}
