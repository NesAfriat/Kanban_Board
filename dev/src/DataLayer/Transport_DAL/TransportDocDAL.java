package DataLayer.Transport_DAL;

import BusinessLayer.Transport_BusinessLayer.Cont.DocCont;
import BusinessLayer.Transport_BusinessLayer.Document.TransportDoc;
import BusinessLayer.Transport_BusinessLayer.Document.Triple;
import BusinessLayer.Transport_BusinessLayer.Drives.License;
import BusinessLayer.Transport_BusinessLayer.Drives.Truck;
import BusinessLayer.Transport_BusinessLayer.Shops.Product;
import BusinessLayer.Transport_BusinessLayer.Shops.Store;
import BusinessLayer.Transport_BusinessLayer.Shops.Supplier;


import java.sql.*;
import java.sql.Date;
import java.util.*;

public class TransportDocDAL {

    public HashMap<Integer, TransportDoc> LoadProducts () throws SQLException {
        HashMap<Integer, TransportDoc> theTransportBible = new HashMap<>();
        ArrayList<Integer > allStops = new ArrayList<>();
        List<Product> stList = null;
        Connection conn = null;
        try{
            stList = new LinkedList<>();


            conn = Connect.getConnection();
            Statement st = conn.createStatement();
            boolean atLeastOne=true;
            int index=0;
          while(atLeastOne) {
              atLeastOne = false;
              ResultSet results = st.executeQuery("SELECT * FROM TransportDocument where version = " + index);
              while (results.next()) {
                  atLeastOne=true;
                  String driverName, area,driverLicense;
                  int id, version, driverID, truckID, originStoreID;
                  double truckWeightDep;
                  Date TransDate, LeftOrigin;

                  //transport doc primitive values
                  id = results.getInt(1);
                  version = results.getInt(2);
                  TransDate = results.getDate(3);
                  LeftOrigin = results.getDate(4);
                  driverName = results.getString(5);
                  driverID = results.getInt(6);
                  driverLicense = results.getString(7);
                  truckID = results.getInt(8);
                  originStoreID = results.getInt(9);
                  area = results.getString(10);
                  truckWeightDep = results.getDouble(11);

                  //transport doc objects
                  Truck trk = TruckDAL.findTruck(truckID);
                  Store str = StoreDAL.findStore(originStoreID);
                  License lcs =null;
                  switch(driverLicense){
                      case "typeA":
                          lcs=License.typeA;
                          break;
                      case "typeB":
                          lcs=License.typeB;
                          break;
                      case "typeC":
                          lcs=License.typeC;
                          break;
                      case "typeD":
                          lcs=License.typeD;
                          break;
                          }
                  BusinessLayer.Transport_BusinessLayer.Drives.Driver drv = new BusinessLayer.Transport_BusinessLayer.Drives.Driver(driverName,driverID,lcs);

                    //store and supplier hashmap
                  HashMap<Integer, Store> destinationStore = new HashMap<>();
                  ResultSet resultsHash = st.executeQuery("SELECT * FROM TransportStopStores where version = " + index+ " and id = "+ id);
                  while (resultsHash.next()) {
                      int stopNumber, storeIDHash;

                      stopNumber = resultsHash.getInt(2) ;
                      storeIDHash = resultsHash.getInt(3);
                      allStops.add(stopNumber);
                      Store stor = StoreDAL.findStore(storeIDHash);
                      destinationStore.put(stopNumber,stor);
                  }
                  HashMap<Integer, Supplier> destinationSupplier = new HashMap<>();
                  ResultSet resultsHash2 = st.executeQuery("SELECT * FROM TransportStopSupplier where version = " + index+ " and ID = "+ id);
                  while (resultsHash2.next()) {
                      int stopNumber, supplierIDHash;
                      stopNumber = resultsHash2.getInt(2) ;
                      supplierIDHash = resultsHash2.getInt(3);
                      allStops.add(stopNumber);
                      Supplier spr = SupplierDAL.findSupplier(supplierIDHash);
                      destinationSupplier.put(stopNumber,spr);
                  }

                    // create triple list of store product and amount
                   resultsHash = st.executeQuery("SELECT * FROM TransportDocStoreProduct where Version = " + index+ " and DocumentID = "+ id);
                  List<Triple<Product, Integer, Store>> productList = new LinkedList<>();
                  while(resultsHash.next()) {
                        int prodID, StoreID, Amount;
                        prodID = resultsHash.getInt(1);
                        StoreID = resultsHash.getInt(2);
                        Amount = resultsHash.getInt(4);


                        Product prd = ProductDAL.findProduct(prodID);
                        Store strD = StoreDAL.findStore(StoreID);
                        Triple<Product, Integer, Store> trp = new Triple<>(prd,Amount,strD);
                        productList.add(trp);

                    }
                            Collections.sort(allStops);
                          TransportDoc td = new TransportDoc(id,TransDate, LeftOrigin,trk,drv,str,destinationStore,destinationSupplier,str.getArea(),truckWeightDep,productList, allStops,index);

                          if(index==0)
                              theTransportBible.put(td.getId(),td);

                          else
                              DocCont.getUpToDateDoc(theTransportBible.get(td.getId())).upDates=td;
                              index++;
              }
          }

        } catch (
                SQLException e) {

            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return theTransportBible;
    }

    public void saveDoc(TransportDoc doc)  {

        Connection conn = null;
        try {
            if(doc !=null) {
                conn = Connect.getConnection();
                Statement st = conn.createStatement();
                //delete the not up to date records
                String delete = "DELETE FROM TransportDocument WHERE Id='" + doc.getId() + "' AND Version='" + doc.getVersion() + "'";
                st.executeUpdate(delete);
                String delete2 = "DELETE FROM TransportDocStoreProduct WHERE DocumentId='" + doc.getId() + "' AND Version='" + doc.getVersion() + "'";
                st.executeUpdate(delete2);
                String delete3 = "DELETE FROM TransportStopStores WHERE Id='" + doc.getId() + "' AND Version='" + doc.getVersion() + "'";
                st.executeUpdate(delete3);
                String delete4 = "DELETE FROM TransportStopSupplier WHERE Id='" + doc.getId() + "' AND Version='" + doc.getVersion() + "'";
                st.executeUpdate(delete4);

                //now save to all tables for id
                String insert1 = "INSERT INTO TransportDocument " + "VALUES (" + doc.getId() + "," + doc.getVersion() + ",'" + doc.getTransDate() +
                        "','" + doc.getLeftOrigin() + "','" + doc.getDriver().getName() + "'," + doc.getDriver().getId() + ",'" + LicenseToString(doc.getDriver().getLicense()) +
                        "'," + doc.getTruck().getLicensePlate() + "," + doc.getOrigin().getId() + ",'" + doc.getArea().toString() + "'," + doc.getTruckWeightDep() + ");";
                st.executeUpdate(insert1);

                Iterator itStore=doc.getDestinationStore().entrySet().iterator();
                while(itStore.hasNext()) {
                    Map.Entry pair =(Map.Entry)itStore.next();
                    String insert2 = "INSERT INTO TransportStopStore " + "VALUES (" + doc.getId() + "," + pair.getKey()+ "," + ((Store)pair.getValue()).getId()  +
                            "," + doc.getVersion() + ");";
                    st.executeUpdate(insert2);
                }

                Iterator itSupplier=doc.getDestinationStore().entrySet().iterator();
                while(itSupplier.hasNext()) {
                    Map.Entry pair =(Map.Entry)itSupplier.next();
                    String insert2 = "INSERT INTO TransportStopSupplier " + "VALUES (" + doc.getId() + "," + pair.getKey()+ "," + ((Store)pair.getValue()).getId()  +
                            "," + doc.getVersion() + ");";
                    st.executeUpdate(insert2);
                }

                List<Triple<Product, Integer, Store>> l=doc.getProductList();
                for (Triple<Product,Integer,Store> t: l) {
                    String insert2 = "INSERT INTO TransportDocStoreProduct " + "VALUES (" + t.getFirst().getId() + "," + t.getThird().getId()+ "," + doc.getId()  +
                            "," + t.getSecond() +  "," + doc.getVersion() +" );";
                    st.executeUpdate(insert2);
                }


                saveDoc(doc.upDates);
            }



        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    private String LicenseToString(License license) throws Exception {
        String output="error" ;

        switch (license) {
            case typeA:
                output = "typeA";
                break;
            case typeB:
                output = "typeB";
                break;
            case typeC:
                output = "typeC";
                break;
            case typeD:
                output = "typeD";
                break;
        }
        if(output.equals("error")){
            throw new Exception("cant convert License to string ");
        }
        return output;

    }




}
