package test;

import BuissnessLayer.*;
import BuissnessLayer.OrderBuissness.OrderControler;
import BuissnessLayer.Product;
import BuissnessLayer.SupplierBuissness.Contact;
import BuissnessLayer.SupplierBuissness.ISupplier;
import BuissnessLayer.SupplierBuissness.SuppliersControler;

import java.util.HashMap;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class SuppliersControlerTest {
    SuppliersControler suppliersControler;
    OrderControler orderControler;
    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        AgreementManager agreementManager=new AgreementManager();
        suppliersControler=new SuppliersControler(agreementManager);
        orderControler=new OrderControler(agreementManager);
    }


    @org.junit.jupiter.api.AfterEach
    void tearDown(){
    }

    @org.junit.jupiter.api.Test
    void getAgreement() {
    }

    @org.junit.jupiter.api.Test
    void setPayment() {
        //set payment of supplier what not exsist
        assertThrows(IllegalArgumentException.class,
                ()->{
                    suppliersControler.SetPayment(paymentMethods.Cash,123);
                }
        );
        Product product=new Product(8,999,"solo","gamba",0);
        suppliersControler.addNewSupplier(123,"Moshe","abcd", paymentMethods.Cash, DeliveryMode.DeliveryAfterOrder,new LinkedList<>(),-1,"ben","ben@gmail.com","0539854788",product);
        suppliersControler.SetPayment(paymentMethods.BankTransfers,123);
        ISupplier supplier= suppliersControler.getSupplier(123);
        assertEquals(supplier.getPayment(),paymentMethods.BankTransfers);
    }

    @org.junit.jupiter.api.Test
    void addNewProductToAgreement() {
        //add new product to supplier what not exist
        assertThrows(IllegalArgumentException.class,
                ()->{
                    suppliersControler.addNewProductToAgreement(123,80,123,"fd","fd");
                }
        );
        Product product=new Product(8,999,"solo","gamba",0);
        suppliersControler.addNewSupplier(123,"Moshe","abcd", paymentMethods.Cash, DeliveryMode.DeliveryAfterOrder,new LinkedList<>(),-1,"ben","ben@gmail.com","0539854788",product);        suppliersControler.addNewProductToAgreement(123,30,123456,"osem","bamba");
        assertEquals(suppliersControler.getAgreement(123).getProducts().get(123456).getName(),"bamba");
        //add the same product
        assertThrows(IllegalArgumentException.class,
                ()->{
                    suppliersControler.addNewProductToAgreement(123,30,123456,"osem","bamba");
                }
        );
        // add with null name
        assertThrows(IllegalArgumentException.class,
                ()->{
                    suppliersControler.addNewProductToAgreement(123,30,12345679,"osem",null);
                }
        );
        // add with  "" -empty name
        assertThrows(IllegalArgumentException.class,
                ()->{
                    suppliersControler.addNewProductToAgreement(123,30,1234567205,"osem","");
                }
        );

        // add with  "" -empty manfucator
        assertThrows(IllegalArgumentException.class,
                ()->{
                    suppliersControler.addNewProductToAgreement(123,30,123456467,"","bamba");
                }
        );

        // add with null  namfucator
        assertThrows(IllegalArgumentException.class,
                ()->{
                    suppliersControler.addNewProductToAgreement(123,30,128734567,null,"bamba");
                }
        );


    }

    @org.junit.jupiter.api.Test
    void addNewSupplier() {
        Product product=new Product(8,999,"solo","gamba",0);
        suppliersControler.addNewSupplier(123,"Moshe","abcd", paymentMethods.Cash, DeliveryMode.DeliveryAfterOrder,new LinkedList<>(),-1,"ben","ben@gmail.com","0539854788",product);        assertTrue(suppliersControler.isSupplierExist(123));
        String Name=suppliersControler.getSupplier(123).getSupplierName();
        assertEquals(Name,"Moshe");

        //check if when i add 2 same suppliers its throw
        try {
            suppliersControler.addNewSupplier(123,"Moshe","abcd", paymentMethods.Cash, DeliveryMode.DeliveryAfterOrder,new LinkedList<>(),-1,"ben","ben@gmail.com","0539854788",product);            assertTrue(false);
        }catch (Exception e){
            assertTrue(true);
        }
    }


    @org.junit.jupiter.api.Test
    void removeSupplier() {
        // remove supplier what not exist
        assertThrows(IllegalArgumentException.class,
                ()->{
            suppliersControler.RemoveSupplier(123);
                }
        );
        //remove supplier What exist
        Product product=new Product(8,999,"solo","gamba",0);
        suppliersControler.addNewSupplier(123,"Moshe","abcd", paymentMethods.Cash, DeliveryMode.DeliveryAfterOrder,new LinkedList<>(),-1,"ben","ben@gmail.com","0539854788",product);        suppliersControler.RemoveSupplier(123);
        assertTrue(!suppliersControler.isSupplierExist(123));
    }

    @org.junit.jupiter.api.Test
    void getSupplier() {
        //get supplier what not exist
        assertThrows(IllegalArgumentException.class,
                ()->{
                    suppliersControler.getSupplier(122);
                }
        );
        Product product=new Product(8,999,"solo","gamba",0);
        suppliersControler.addNewSupplier(123,"Moshe","abcd", paymentMethods.Cash, DeliveryMode.DeliveryAfterOrder,new LinkedList<>(),-1,"ben","ben@gmail.com","0539854788",product);        ISupplier supplier= suppliersControler.getSupplier(123);
        assertEquals(supplier.getId(),123);

    }

    @org.junit.jupiter.api.Test
    void getAllSuppliers() {
    }

    @org.junit.jupiter.api.Test
    void setBankAccount() {
        //set Bank account of supplier what not exsist
        assertThrows(IllegalArgumentException.class,
                ()->{
            suppliersControler.setBankAccount(123,"DSFDSF");
                });
        Product product=new Product(8,999,"solo","gamba",0);
        suppliersControler.addNewSupplier(123,"Moshe","abcd", paymentMethods.Cash, DeliveryMode.DeliveryAfterOrder,new LinkedList<>(),-1,"ben","ben@gmail.com","0539854788",product);        suppliersControler.setBankAccount(123,"DSFDSF");
        ISupplier supplier=suppliersControler.getSupplier(123);
        assertEquals(supplier.getBankAccount(),"DSFDSF");

        //bank ackoun empty ""
        assertThrows(IllegalArgumentException.class,
                ()->{
                    suppliersControler.setBankAccount(123,"");
                });


        //bank ackount null
        assertThrows(IllegalArgumentException.class,
                ()->{
                    suppliersControler.setBankAccount(123,null);
                });
    }

    @org.junit.jupiter.api.Test
    void addNewContact() {
        //add new Contact to Supplier what not exist
        assertThrows(IllegalArgumentException.class,
                ()->{
                    suppliersControler.addNewContact(123,"yosi","sdd","0538265477");
                }
        );
        Product product=new Product(8,999,"solo","gamba",0);
        suppliersControler.addNewSupplier(123,"Moshe","abcd", paymentMethods.Cash, DeliveryMode.DeliveryAfterOrder,new LinkedList<>(),-1,"ben","ben@gmail.com","0539854788",product);        suppliersControler.addNewContact(123,"yosi","sdd@gmail.com","0538265477");
        Contact contact=suppliersControler.getSupplier(123).getContact(1);
        assertEquals(contact.getContactName(),"yosi");
        // add with wrong phone number format
        assertThrows(IllegalArgumentException.class,
                ()->{
                    suppliersControler.addNewContact(123,"yosi","sdd@gmail.com","053477");
                }
        );

// add with null phone
        assertThrows(IllegalArgumentException.class,
                ()->{
                    suppliersControler.addNewContact(123,"yosi","sdd@gmail.com",null);
                }
        );
// add with empty phone

        assertThrows(IllegalArgumentException.class,
                ()->{
                    suppliersControler.addNewContact(123,"yosi","sdd@gmail.com","");
                }
        );

        // add with incorrect format of email
        assertThrows(IllegalArgumentException.class,
                ()->{
                    suppliersControler.addNewContact(123,"yosi","sdil.com","0869874533");
                }
        );
    }

    @org.junit.jupiter.api.Test
    void removeContact() {
        //remove contact from supplier that not exsist
        assertThrows(IllegalArgumentException.class,
                ()->{
                    suppliersControler.removeContact(123,15);
                }
        );
        Product product=new Product(8,999,"solo","gamba",0);
        suppliersControler.addNewSupplier(123,"Moshe","abcd", paymentMethods.Cash, DeliveryMode.DeliveryAfterOrder,new LinkedList<>(),-1,"ben","ben@gmail.com","0539854788",product);        suppliersControler.addNewContact(123,"yosi","sdd@gmail.com","0538265477");
        System.out.println(suppliersControler.getSupplier(123).getListOfContacts().get(0).getId());
        suppliersControler.removeContact(123,suppliersControler.getSupplier(123).getListOfContacts().get(0).getId());
        assertEquals(suppliersControler.getSupplier(123).getListOfContacts().size(),1);
    }


    @org.junit.jupiter.api.Test
    void setProductPrice() {
        //set product price of supplier what not exsist
        assertThrows(IllegalArgumentException.class,
                ()->{
                    suppliersControler.setProductPrice(1232,123,2);
                }
        );

        Product product=new Product(8,999,"solo","gamba",0);
        suppliersControler.addNewSupplier(123,"Moshe","abcd", paymentMethods.Cash, DeliveryMode.DeliveryAfterOrder,new LinkedList<>(),-1,"ben","ben@gmail.com","0539854788",product);        suppliersControler.addNewProductToAgreement(123,30,123456,"osem","bamba");
        suppliersControler.setProductPrice(123,123456,5);
        assertEquals(suppliersControler.getAgreement(123).getProducts().get(123456).getPrice(),5);

        //set price of product what not exsist
        assertThrows(IllegalArgumentException.class,
                ()->{
                    suppliersControler.setProductPrice(123,23456,5);
                }
        );


    }

    @org.junit.jupiter.api.Test
    void removeProductFromSupplier() {
        //remove product from supplier what not exist
        assertThrows(IllegalArgumentException.class,
                ()->{
                    suppliersControler.removeProductFromSupplier(12,123);
                }
        );
        Product product=new Product(8,999,"solo","gamba",0);
        suppliersControler.addNewSupplier(123,"Moshe","abcd", paymentMethods.Cash, DeliveryMode.DeliveryAfterOrder,new LinkedList<>(),-1,"ben","ben@gmail.com","0539854788",product);        // remove product what not exsist
        assertThrows(IllegalArgumentException.class,
                ()->{
                    suppliersControler.removeProductFromSupplier(12,123);
                }
        );
        suppliersControler.addNewProductToAgreement(123,30,123456,"osem","bamba");
        suppliersControler.removeProductFromSupplier(123456,123);
        assertTrue(!suppliersControler.getAgreement(123).isProductExist(123456));


    }

    @org.junit.jupiter.api.Test
    void addNewDiscountByQuantitiyToProduct() {
        //add new discount to supplier what not exsist
        assertThrows(IllegalArgumentException.class,
                ()->{
                    suppliersControler.addNewDiscountByQuantitiyToProduct(333,333,333,333);
                }
        );
        Product product=new Product(8,999,"solo","gamba",0);
        suppliersControler.addNewSupplier(123,"Moshe","abcd", paymentMethods.Cash, DeliveryMode.DeliveryAfterOrder,new LinkedList<>(),-1,"ben","ben@gmail.com","0539854788",product);        //add new Discount to product what not exsist
        assertThrows(IllegalArgumentException.class,
                ()->{
                    suppliersControler.addNewDiscountByQuantitiyToProduct(123,333,333,333);
                }
        );

        //add discount
        suppliersControler.addNewProductToAgreement(123,30,123456,"osem","bamba");
        suppliersControler.addNewDiscountByQuantitiyToProduct(123,123456,333,20);
        HashMap<Integer,Integer> productQuantity =new HashMap<>();
        productQuantity.put(123456,400);
        orderControler.AddOrder(123,productQuantity,false);
        assertEquals( orderControler.GetOrder(0).GetTotalPayment(),8000);

    }

    @org.junit.jupiter.api.Test
    void setExtraDiscountToSupplier() {
        //set too supplier what not exsist
        assertThrows(IllegalArgumentException.class,
                ()->{
                    suppliersControler.setExtraDiscountToSupplier(4545,4);
                }
        );
        Product product=new Product(8,999,"solo","gamba",0);
        suppliersControler.addNewSupplier(123,"Moshe","abcd", paymentMethods.Cash, DeliveryMode.DeliveryAfterOrder,new LinkedList<>(),-1,"ben","ben@gmail.com","0539854788",product);        suppliersControler.setExtraDiscountToSupplier(123,10);
        suppliersControler.addNewProductToAgreement(123,30,123456,"osem","bamba");
        HashMap<Integer,Integer> productQuantity =new HashMap<>();
        productQuantity.put(123456,10);
        orderControler.AddOrder(123,productQuantity,false);

        System.out.println(orderControler.GetOrder(0).GetTotalPayment());
        assertEquals(orderControler.GetOrder(0).GetTotalPayment(),270);


    }



    @org.junit.jupiter.api.Test
    void RemovDiscountByQuantitiyToProduct() {
        //remove extra discount from supplier what not exsist
        assertThrows(IllegalArgumentException.class,
                ()->{
                    suppliersControler.RemovDiscountByQuantitiyToProduct(123,123,212);
                }
        );
        //remove exstra discount from product what not exsist
        Product product=new Product(8,999,"solo","gamba",0);
        suppliersControler.addNewSupplier(123,"Moshe","abcd", paymentMethods.Cash, DeliveryMode.DeliveryAfterOrder,new LinkedList<>(),-1,"ben","ben@gmail.com","0539854788",product);        //remove new Discount to product what not exsist
        assertThrows(IllegalArgumentException.class,
                ()->{
                    suppliersControler.RemovDiscountByQuantitiyToProduct(123,123,112);
                }
        );
        suppliersControler.addNewProductToAgreement(123,30,123456,"osem","bamba");
        //remove discount what not exsist
        assertThrows(IllegalArgumentException.class,
                ()->{
                    suppliersControler.RemovDiscountByQuantitiyToProduct(123,123,112);
                }
        );
        suppliersControler.addNewDiscountByQuantitiyToProduct(123,123456,333,20);
        suppliersControler.RemovDiscountByQuantitiyToProduct(123,123456,333);
        assertThrows(IllegalArgumentException.class,
                ()->{
                    suppliersControler.RemovDiscountByQuantitiyToProduct(123,123456,333);
                }
        );

    }


}