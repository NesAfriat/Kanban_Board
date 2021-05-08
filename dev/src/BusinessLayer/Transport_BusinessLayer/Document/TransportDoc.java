package BusinessLayer.Transport_BusinessLayer.Document;
import BusinessLayer.Transport_BusinessLayer.Drives.Driver;
import BusinessLayer.Transport_BusinessLayer.Drives.License;
import BusinessLayer.Transport_BusinessLayer.Drives.Truck;
import BusinessLayer.Transport_BusinessLayer.Shops.*;

import java.util.*;

import BusinessLayer.Transport_BusinessLayer.etc.Tuple;

public class TransportDoc {
    int id;
    String TransDate=null;
    String LeftOrigin=null;
    Truck truck=null;
    Driver driver=null;

    public int getVersion() {
        return version;
    }

    int version;

    Store origin=null;
    // store,int
    HashMap<Integer, Store> destinationStore;
    HashMap<Integer, Supplier> destinationSupplier;
    ArrayList<Integer> allStops = new ArrayList<>();
    Area area=null;
    double truckWeightDep=-1;
    List<Triple<Product, Integer, Store>> productList;
    public TransportDoc upDates=null;
    public TransportDoc(int id) {
        this.id = id;
        destinationStore = new HashMap<>();
        destinationSupplier = new HashMap<>();
        productList = new LinkedList<>();
        upDates=null;
        version=0;
    }
    public int getId() {
        return id;
    }

    public TransportDoc(int id, String transDate, String leftOrigin, Truck truck, Driver driver, Store origin, HashMap<Integer, Store> destinationStore, HashMap<Integer, Supplier> destinationSupplier, Area area, double truckWeightDep, List<Triple<Product, Integer, Store>> productList,ArrayList<Integer >allStops,int version) {
        this.id = id;
        TransDate = transDate;
        LeftOrigin = leftOrigin;
        this.truck = truck;
        this.driver = driver;
        this.origin = origin;
        this.destinationStore = destinationStore;
        this.destinationSupplier = destinationSupplier;
        this.area = area;
        this.truckWeightDep = truckWeightDep;
        this.productList = productList;
        this.upDates = null;
        this.allStops=allStops;
        this.version =version+1;
    }
    public String toString(){
        String acc="";
        acc = "id "+ id  +(TransDate!=null? ", Trasnport Date "+ TransDate.toString():"") + (LeftOrigin!=null? ", Time Left Origin "+ LeftOrigin.toString():"") + (truck!=null?", Truck: "+ truck.getName():"")
                +"\n" + (driver!=null?"Driver "+ driver.getName():"") + (origin!=null?", Origin "+ origin.getName():"") + (area!=null?", Area "+ area:"") + (truckWeightDep!=-1?", Truck weight "+ truckWeightDep:"" )+"\n ";
        return acc;
    }
    public String destinationsString(){
        String acc="";
        Collections.sort(allStops);

        for (Integer i : allStops)
        {
            if(destinationStore.containsKey(i))
                 acc=acc+ "Stop<"+i+"> "+"id: "+ destinationStore.get(i).getId()+ " name: "+ destinationStore.get(i).getName() +"\n"  ;
            else
                acc=acc+ "Stop<"+i+"> "+"id: "+ destinationSupplier.get(i).getId()+ " name: "+ destinationSupplier.get(i).getName()+"\n" ;
        }
        return acc+"\n";
    }
    public String productsString(){
        String acc="";
        for (Triple<Product, Integer, Store>  trip : productList)
        {
            acc=acc+ "Product: " + trip.getFirst().getName() + ", Amount: " +trip.getSecond() + ", To Store:" + trip.getThird().getName() +"\n";
        }
        return acc;
    }
    public Driver getDriver(){
        return driver;
    }
    public Area getArea(){
        return area;
    }
    public double getTruckWeightDep() {
        return truckWeightDep;
    }

    public void setTruckWeightDep(double truckWeightDep) throws Exception {
        if(truckWeightDep>truck.getTruckType().getMaxWeight())
            throw new Exception("exceeded max weight for truck");
        this.truckWeightDep = truckWeightDep;

    }
    public void addVersionCount(){
        this.version++;
    }

    public Store getOrigin() {
        return origin;
    }

    public void setOrigin(Store origin) throws Exception {
        if (this.origin == null) {
            this.origin=origin;
            this.area = origin.getArea();
        } else {
            if (this.area == origin.getArea())
                this.origin=origin;
            else
                throw new Exception("wrong area\n");
        }

        this.origin = origin;

    }

    public String getTransDate() {
        return TransDate;
    }

    public void setTransDate(String transDate) {
        TransDate = transDate;
    }

    public String getLeftOrigin() {
        return LeftOrigin;
    }

    public void setLeftOrigin(String leftOrigin) {
        LeftOrigin = leftOrigin;
    }



    public List<Store> getStores() {
        List<Store> storeLinked = new LinkedList<>();
        Iterator StoreIterator = destinationStore.entrySet().iterator();
        while (StoreIterator.hasNext()) {
            Map.Entry mapElement = (Map.Entry) StoreIterator.next();
            storeLinked.add((Store) mapElement.getValue());

        }
        return storeLinked;
    }

    public void addStore(Store st, int place) throws Exception {
        if (destinationStore.isEmpty()) {
            destinationStore.put(place, st);
            this.area = st.getArea();
        } else {
            if (this.area == st.getArea())
                if(!destinationStore.containsKey(place)) {
                    destinationStore.put(place, st);
                }
                else{
                    throw new Exception("someone already in this place ");
                }

            else
                throw new Exception("wrong area\n");
        }
        allStops.add(place);

    }

    public void addSupplier(Supplier st, int place) throws Exception {
        if (destinationSupplier.isEmpty()) {
            destinationSupplier.put(place, st);
            this.area = st.getArea();
        } else {
            if (this.area == st.getArea())
                if(!destinationSupplier.containsKey(place)) {
                    destinationSupplier.put(place, st);
                }
            else{
                throw new Exception("someone already in this place ");
                }
            else
                throw new Exception("wrong area\n");
        }
        allStops.add(place);
    }

    public void addProduct(Triple<Product, Integer, Store> trip) {
        productList.add(trip);
    }

    public Tuple<List<Product>, Area> retAvaliableSupp() {
        List<Product> pd = new LinkedList<>();
        for (Triple<Product, Integer, Store> t : productList) {
            if (!pd.contains(t.getFirst()))
                pd.add(t.getFirst());
        }
        Tuple<List<Product>, Area> tuple = new Tuple<>(pd, area);
        return tuple;
    }

    public void addTruck(Truck tk) {
        this.truck = tk;
    }

    public Truck getTruck(){
        return this.truck;
    }

    public void addDriver(Driver dr) throws Exception {
        boolean correctLicense = false;
        if(truck!=null) {
            for (License ls : truck.getTruckType().getLicensesForTruck()) {
                if (ls.compareTo(dr.getLicense()) == 0)
                    correctLicense = true;
            }
        }
        else
            correctLicense=true;
        if (correctLicense)
            this.driver = dr;
        else
            throw new Exception("the driver does not have the proper license\n");
    }

    public void addWeightWhenLeaving(double weight) {
        truckWeightDep = weight;
    }

    public void change(TransportDoc doc) {
        this.upDates = doc;
    }

    public void removeDestination(int place) throws Exception {
        boolean removed =false;
        if(destinationStore.containsKey(place))
        {
            destinationStore.remove(place);
            removed=true;
            Integer i = place;
            allStops.remove(i);

        }
        if(destinationSupplier.containsKey(place))
        {
            destinationSupplier.remove(place);
            removed=true;
            allStops.remove(place);

        }
            if(!removed)
                throw new Exception("destination does not exist");
    }
    public void removeProduct(int prod,int store) throws Exception {
        boolean removed =false;

        for (Triple<Product,Integer,Store> trip: productList)
        {
            if(trip.getFirst().getId()==prod && trip.getThird().getId()==store)
            {
                productList.remove(trip);
                removed=true;
            }
        }
        if(!removed)
            throw new Exception("destination does not exist");
    }
    public TransportDoc copyDeep(){

        HashMap<Integer, Store> destinationStoreCopy = new HashMap<>();
        HashMap<Integer, Supplier> destinationSupplierCopy = new HashMap<>();
        destinationStoreCopy.putAll(destinationStore);
        destinationSupplierCopy.putAll(destinationSupplier);
        List<Triple<Product, Integer, Store>> productListCopy = new LinkedList<>();
        for(Triple<Product,Integer,Store> trip : productList)
        {
            Triple<Product,Integer,Store> tr = new Triple<Product,Integer, Store>(trip.getFirst(),trip.getSecond(),trip.getThird());
            productListCopy.add(tr);

        }
        ArrayList<Integer> stopL= (ArrayList<Integer>) allStops.clone();

        return new TransportDoc(id,TransDate,LeftOrigin,truck,driver,origin,destinationStoreCopy,destinationSupplierCopy,area,truckWeightDep,productListCopy,stopL,version);
    }

    public HashMap<Integer, Store> getDestinationStore() {
        return destinationStore;
    }

    public HashMap<Integer, Supplier> getDestinationSupplier() {
        return destinationSupplier;
    }

    public List<Triple<Product, Integer, Store>> getProductList() {
        return productList;
    }

    public TransportDoc getUpDates() {
        return upDates;
    }
}
