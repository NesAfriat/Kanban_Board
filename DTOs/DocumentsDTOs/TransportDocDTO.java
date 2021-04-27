package DTOs.DocumentsDTOs;

import DTOs.DriversDTOs.DriverDTO;
import DTOs.DriversDTOs.TruckDTO;
import DTOs.ShopsDTOs.ProductDTO;
import DTOs.ShopsDTOs.StoreDTO;
import DTOs.ShopsDTOs.SupplierDTO;
import Buissness.Document.Triple;
import Buissness.Shops.Area;
import Buissness.etc.Tuple;

import java.util.Date;
import java.util.List;

public class TransportDocDTO {
    final int id;
    final Date TransDate;
    final Date LeftOrigin;
    final TruckDTO truck;
    final DriverDTO driver;
    final StoreDTO origin;
    // store,int


    final List<Tuple<Integer,SupplierDTO>> destinationSupplier;
    final List<Tuple<Integer,StoreDTO>> destinationStore;
    final Area area;
    final double truckWeightDep;
    //Product,int
    final List<Triple<ProductDTO, Integer, StoreDTO>> productList;

    final public TransportDocDTO upDates;



    public TransportDocDTO(int id, Date transDate, Date leftOrigin, TruckDTO truck, DriverDTO driver, StoreDTO origin, List<Tuple<Integer, SupplierDTO>> destinationSupplier, List<Tuple<Integer, StoreDTO>> destinationStore, Area area, double truckWeightDep, List<Triple<ProductDTO, Integer, StoreDTO>> productList, TransportDocDTO upDates) {
        this.id = id;
        TransDate = transDate;
        LeftOrigin = leftOrigin;
        this.truck = truck;
        this.driver = driver;
        this.origin = origin;
        this.destinationSupplier = destinationSupplier;
        this.destinationStore = destinationStore;
        this.area = area;
        this.truckWeightDep = truckWeightDep;
        this.productList = productList;
        this.upDates = upDates;
    }
}
