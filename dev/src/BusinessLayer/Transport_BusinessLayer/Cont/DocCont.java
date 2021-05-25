package BusinessLayer.Transport_BusinessLayer.Cont;

import BusinessLayer.Transport_BusinessLayer.Document.TransportDoc;
import BusinessLayer.Transport_BusinessLayer.Document.Triple;
import BusinessLayer.Transport_BusinessLayer.Drives.Driver;
import BusinessLayer.Transport_BusinessLayer.Drives.Truck;
import BusinessLayer.Transport_BusinessLayer.Shops.Area;
import BusinessLayer.Transport_BusinessLayer.Shops.Product;
import BusinessLayer.Transport_BusinessLayer.Shops.Store;
import BusinessLayer.Transport_BusinessLayer.Shops.Supplier;
import DataLayer.Transport_DAL.DALController;
import DataLayer.Transport_DAL.ProductDAL;
import DataLayer.Transport_DAL.TransportDocDAL;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class DocCont {


    HashMap<Integer,TransportDoc> theTransportBible;







    //forTest
    public TransportDoc getDoc(int x) {
        return theTransportBible.get(x);
    }

    public int getIndex(){
        boolean cont=true;
        int index =1;
        while(cont){
            if(!theTransportBible.containsKey(index))
                return index;
            index++;
        }
        return -1;

    }







    public DocCont()
    {
        theTransportBible = new HashMap<>();
    }

    public int newDelivery()//c- return doc index for the user to use
    {
        int index = getIndex();
        TransportDoc td = new TransportDoc(index);
        theTransportBible.put(index,td);
        return index;
    }


    public Area getArea(int doc){
        return getUpToDateDoc(theTransportBible.get(doc)).getArea();
    }
    public void addStore(int doc, Store st,int place ) throws Exception {
        getUpToDateDoc(theTransportBible.get(doc)).addStore(st,place);
    }
    public void addSupplier(int doc, Supplier sp, int place) throws Exception {
        getUpToDateDoc(theTransportBible.get(doc)).addSupplier(sp,place);
    }
    public List<Store> getStores(int doc){
        return getUpToDateDoc(theTransportBible.get(doc)).getStores();
    }
    public void addProducts(int doc, Triple<Product,Integer,Store> tp){
        getUpToDateDoc(theTransportBible.get(doc)).addProduct(tp);
    }
    public List<Product> getProducts (int doc) {
        List<Product > pd = new LinkedList<>();
        List<Triple<Product, Integer, Store>> trip = getUpToDateDoc(theTransportBible.get(doc)).getProductList();
                for (Triple<Product, Integer, Store> tr : trip){
                    pd.add(tr.getFirst());
                }
        return pd;
    }
    public void addTruck(int doc, Truck tk){
        getUpToDateDoc(theTransportBible.get(doc)).addTruck(tk);
    }
    public Truck getTruck(int doc) throws Exception {
        return getUpToDateDoc(theTransportBible.get(doc)).getTruck();
    }
    public void addDriver(int doc, Driver dr) throws Exception {
        getUpToDateDoc(theTransportBible.get(doc)).addDriver(dr);
    }
    public void addWeightWhenLeaving(int doc, double truckWeight){
        getUpToDateDoc(theTransportBible.get(doc)).addWeightWhenLeaving(truckWeight);
    }
    public Driver getDriver(int doc){
        return getUpToDateDoc(theTransportBible.get(doc)).getDriver();
}

    public static TransportDoc getUpToDateDoc (TransportDoc td){

        while(td.upDates!=null)
        {
            td=td.upDates;
        }
        return td;
    }
    public void editTransDate(int doc, String transDate ){
        TransportDoc origin = theTransportBible.get(doc);
        origin = getUpToDateDoc(origin);
        TransportDoc td= origin.copyDeep();
        origin.change(td);
        td.setTransDate(transDate);
    }
    public void editLeftOrigin(int doc, String LeftOrigin ){
        TransportDoc origin = theTransportBible.get(doc);
        origin = getUpToDateDoc(origin);
        TransportDoc td= origin.copyDeep();
        origin.change(td);
        td.setLeftOrigin(LeftOrigin);
    }
    public void editTruck(int doc, Truck tk ){
        TransportDoc origin = theTransportBible.get(doc);
        origin = getUpToDateDoc(origin);
        TransportDoc td= origin.copyDeep();
        origin.change(td);
        td.addTruck(tk);
    }
    public void editDriver(int doc, Driver dr ) throws Exception {
        TransportDoc origin = theTransportBible.get(doc);
        origin = getUpToDateDoc(origin);
        TransportDoc td= origin.copyDeep();
        origin.change(td);
        td.addDriver(dr);
    }
    public void editOrigin(int doc, Store orig ) throws Exception {
        TransportDoc origin = theTransportBible.get(doc);
        origin = getUpToDateDoc(origin);
        TransportDoc td= origin.copyDeep();
        origin.change(td);
        td.setOrigin(orig);
    }
    public void editTruckWeightDep(int doc, double trWeight ) throws Exception {
        TransportDoc origin = theTransportBible.get(doc);
        origin = getUpToDateDoc(origin);
        TransportDoc td= origin.copyDeep();
        origin.change(td);
        td.setTruckWeightDep(trWeight);

    }
    public void removeDestinations(int doc, int place ) throws Exception {
        TransportDoc origin = theTransportBible.get(doc);
        origin = getUpToDateDoc(origin);
        TransportDoc td= origin.copyDeep();
        origin.change(td);
        td.removeDestination(place);

    }
    public void removeProducts(int doc, int prodId,int storeId ) throws Exception {
        TransportDoc origin = theTransportBible.get(doc);
        origin = getUpToDateDoc(origin);
        TransportDoc td= origin.copyDeep();
        origin.change(td);
        td.removeProduct(prodId,storeId);

    }
    public void setOrigin (int doc, Store st) throws Exception {
        theTransportBible.get(doc).setOrigin(st);
    }
    public String docProdString(int doc){
        TransportDoc origin = theTransportBible.get(doc);
        origin = getUpToDateDoc(origin);
        return origin.productsString();
    }
    public String docInfo(int doc){

        TransportDoc origin = theTransportBible.get(doc);
        origin = getUpToDateDoc(origin);
        return origin.toString();
    }
    public String docDestinations(int doc){

        TransportDoc origin = theTransportBible.get(doc);
        origin = getUpToDateDoc(origin);
        return origin.destinationsString();
    }

    public void setTranportDate(int doc, String str){

        theTransportBible.get(doc).setTransDate(str);
    }

    public void setTranportDateChanges(int doc, String str){
        TransportDoc origin = theTransportBible.get(doc);
        origin = getUpToDateDoc(origin);
        TransportDoc td= origin.copyDeep();
        origin.change(td);
        td.setTransDate(str);

    }
    public void setDepartureTime(int doc, String str){

        theTransportBible.get(doc).setLeftOrigin(str);
    }
    public void setDepartureTimeChanges(int doc, String date){
        TransportDoc origin = theTransportBible.get(doc);
        origin = getUpToDateDoc(origin);
        TransportDoc td= origin.copyDeep();
        origin.change(td);
        td.setLeftOrigin(date);
    }



    public void save(int DocId){
        DALController con=DALController.getInstance();
        con.tra.saveDoc(theTransportBible.get(DocId));
    }
    public void load() throws Exception {
        DALController con=DALController.getInstance();
        try {

            theTransportBible=con.tra.LoadProducts();
        } catch (SQLException throwables) {
            throw new Exception("Error Loading Documents");
        }
    }

}
