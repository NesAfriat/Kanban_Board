package BuissnessLayer;

import java.util.List;

public interface IAgreementManager {

    public Product getProduct(int supplierID, int CatalogId);

    public List<Product> getAllProductsOfSupplier(int SupplierId);

    public List<Product> getAllProductsInTheSystem();

    public Agreement getAgreement(int SupplierID);

    //public Agreement AddAgreement(Agreement agreement);

    public void AddNewAgreement(IAgreement agreement);

    public void AddProduct(int SupplierId,double Price, int CatalogID, String manfucator, String name);



    }
