package BusinessLayer.Transport_BusinessLayer.Cont;

import BusinessLayer.Transport_BusinessLayer.Document.TransportDoc;
import BusinessLayer.Transport_BusinessLayer.Document.Triple;
import BusinessLayer.Transport_BusinessLayer.Drives.Driver;
import BusinessLayer.Transport_BusinessLayer.Drives.Truck;
import BusinessLayer.Transport_BusinessLayer.Shops.Area;
import BusinessLayer.Transport_BusinessLayer.Shops.Product;
import BusinessLayer.Transport_BusinessLayer.Shops.Store;
import BusinessLayer.Transport_BusinessLayer.Shops.Supplier;
import BusinessLayer.Transport_BusinessLayer.etc.Tuple;
import DataLayer.Transport_DAL.DALController;
import DataLayer.Transport_DAL.ProductDAL;
import DataLayer.Transport_DAL.TransportDocDAL;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class DocCont {


    HashMap<Integer,TransportDoc> theTransportBible;


    public List<TransportDoc> getUnapprovedDocs(){//returns all unapproved docs
        List<Tuple<Integer,TransportDoc>> toFilter=convertHasMapToList(theTransportBible);
        List<TransportDoc> unapprovedDos=new ArrayList<>();
        for (Tuple<Integer,TransportDoc> tuple:toFilter) {
            TransportDoc latestVersionDoc=getUpToDateDoc(tuple.y);
            if(!latestVersionDoc.isApproved()){
                unapprovedDos.add(latestVersionDoc);
            }
        }

        return unapprovedDos;
    }




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
    public void addStore(int doc, int store,int place ) throws Exception {
        getUpToDateDoc(theTransportBible.get(doc)).addStore(store,place);
    }
    public void addSupplier(int doc, int supplier , int place) throws Exception {
        getUpToDateDoc(theTransportBible.get(doc)).addSupplier(supplier,place);
    }
    public List<Integer/*Store*/> getStores(int doc){
        return getUpToDateDoc(theTransportBible.get(doc)).getStores();
    }
    public void addProducts(int doc, Triple<Integer,Integer,Integer> tp){
        getUpToDateDoc(theTransportBible.get(doc)).addProduct(tp);
    }
    public List<Integer/*Product*/> getProducts (int doc) {
        List<Integer/*Product*/ > pd = new LinkedList<>();
        List<Triple<Integer, Integer, Integer>> trip = getUpToDateDoc(theTransportBible.get(doc)).getProductList();
                for (Triple<Integer/*Product*/, Integer, Integer/*Store*/> tr : trip){
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
    public void editOrigin(int doc, Integer orig ) throws Exception {
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
    public void setOrigin (int doc, Integer store) throws Exception {
        theTransportBible.get(doc).setOrigin(store);
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
    public void approved(int doc,boolean trueToApprove) throws Exception {
        theTransportBible.get(doc).setApproved(trueToApprove);
    }
    public boolean checkApproved(int doc){
        return theTransportBible.get(doc).isApproved();
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

    public int addTranportFromSupplier(int supplierId, HashMap<Integer,Integer> productAndAmount,String date) throws Exception {//add TranportFromSupplier Without DriversAndTrucks and dates return the doc id
        int DeliveryID=newDelivery();
        addSupplier(DeliveryID, supplierId, 1);
        addStore(DeliveryID,Transport_Facade.theOneStoreId,2);
        for (Triple<Integer,Integer,Integer> t:
                convertProductAndAmountToProductAndAmountAndStore(convertHasMapToList(productAndAmount))) {
            addProducts(DeliveryID,t);
        }

        setOrigin(DeliveryID, Transport_Facade.theOneStoreId);

        setDepartureTime(DeliveryID,date);
        setTranportDate(DeliveryID,date);

        return DeliveryID;
    }

    private <T,S>List<Tuple<T,S>> convertHasMapToList(HashMap<T,S> hashMap){
        List<Tuple<T,S>> output=new LinkedList<>();
        Iterator it = hashMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            output.add(new Tuple<>((T) pair.getKey(), (S)pair.getValue()));
        }
        return output;
    }
    private List<Triple<Integer,Integer,Integer>> convertProductAndAmountToProductAndAmountAndStore(List<Tuple<Integer,Integer>> productAndAmount){
        List<Triple<Integer,Integer,Integer>> output=new LinkedList<>();
        for (Tuple t:productAndAmount) {
            output.add(new Triple(t.x,t.y,Transport_Facade.theOneStoreId));
        }
        return output;
    }
}
