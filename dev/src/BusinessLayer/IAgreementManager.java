package BusinessLayer;

import java.util.List;

public interface IAgreementManager {

    ProductSupplier getProduct(int supplierID, int CatalogId);

    List<ProductSupplier> getAllProductsOfSupplier(int SupplierId);

    List<ProductSupplier> getAllProductsInTheSystem();

    Agreement getAgreement(int SupplierID);

    //public Agreement AddAgreement(Agreement agreement);

    void AddNewAgreement(IAgreement agreement);

    void AddProduct(int SupplierId, double Price, int CatalogID);


}
