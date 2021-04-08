package test;

import BuissnessLayer.AgreementManager;
import BuissnessLayer.DeliveryMode;
import BuissnessLayer.OrderBuissness.OrderControler;
import BuissnessLayer.Product;
import BuissnessLayer.SupplierBuissness.SuppliersControler;
import BuissnessLayer.paymentMethods;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class OrderControlerTest {
    SuppliersControler suppliersControler;
    OrderControler orderControler;

    @BeforeEach
    void setUp() {
        AgreementManager agreementManager=new AgreementManager();
        suppliersControler=new SuppliersControler(agreementManager);
        orderControler=new OrderControler(agreementManager);
        Product product=new Product(8,999,"solo","gamba",0);
        suppliersControler.addNewSupplier(123,"Moshe","abcd", paymentMethods.Cash, DeliveryMode.DeliveryAfterOrder,new LinkedList<>(),-1,"ben","ben@gmail.com","0539854788",product);        suppliersControler.addNewProductToAgreement(123,30,123456,"osem","bamba");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAllOrders() {
    }

    @Test
    void addOrder() {
    }

    @Test
    void removeOrder() {
        //remove Order what not esist
        assertThrows(IllegalArgumentException.class,
                ()->{
                    orderControler.RemoveOrder(123);
                }

        );
        HashMap<Integer,Integer> productQuantity =new HashMap<>();
        productQuantity.put(123456,10);
        orderControler.AddOrder(123,productQuantity,false);
        orderControler.RemoveOrder(0);
        assertThrows(IllegalArgumentException.class,
                ()->{
                    orderControler.RemoveOrder(0);
                }

        );


    }

    @Test
    void getOrder(){
        //get order what not exsist
        assertThrows(IllegalArgumentException.class,
                ()->{
                    orderControler.GetOrder(2);
                }
        );
        HashMap<Integer,Integer> productQuantity =new HashMap<>();
        productQuantity.put(123456,10);
        orderControler.AddOrder(123,productQuantity,false);
        assertEquals(orderControler.GetOrder(0).GetTotalPayment(),300);

    }

    @Test
    void removeAllSupllierConstOrders() {
    }

    @Test
    void addProductToOrder() {
    }
}