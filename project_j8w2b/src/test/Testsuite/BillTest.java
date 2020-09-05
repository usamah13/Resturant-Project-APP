//package Testsuite;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import resturant.*;
//
//import static org.junit.jupiter.api.Assertions.*;
//public class BillTest {
//    private Bill b1;
//    private Bill b2;
//    private Bill b3;
//    private Bill b4;
//    private Bill b5;
//    private Child c;
//    private Adult a;
//    private Elder e;
//    Order o1;
//    Order o2;
//    Order o3;
//    private Menu m = new SharableMenu("m");
//
//
//    @BeforeEach
//    void runBefore () {
//        c = new Child("Baby",m);
//        a = new Adult("Adult",m);
//        e = new Elder("Elder",m);
//        o1 = new Order(c,m);
//        o2 = new Order(a,m);
//        o3 = new Order(e,m);
//    }
//
//    @Test
//
//    void testAddRemove() {
//        b1 = new Bill(1);
//        b2 = new Bill(20);
//        b1.addOrder(o1);
//        assertTrue(o1.bills.contains(b1));
//        assertTrue(b1.order.contains(o1));
//        b1.addOrder(o2);
//        assertTrue(o2.bills.contains(b1));
//        assertTrue(b1.order.contains(o2));
//        o1.addBill(b2);
//        assertTrue(o1.bills.contains(b2));
//        assertTrue(b2.order.contains(o1));
//        b1.removeOrder(o2);
//        assertFalse(o2.bills.contains(b1));
//        assertFalse(b1.order.contains(o2));
//        o1.removeBill(b2);
//        assertFalse(o1.bills.contains(b2));
//        assertFalse(b2.order.contains(o1));
//
//
//    }
//
//
//    @Test
//    // checking if no items gives 0 bill.
//    void calculateBill0(){
//        b3 = new Bill(0);
//        assertEquals(0.0, b3.calculateBill(c.billFactor()));
//        assertEquals(0.0, b3.calculateBill(a.billFactor()));
//        assertEquals(0.0, b3.calculateBill(e.billFactor()));
//    }
//    @Test
//    // checking if no items gives 1, 10, 100 bill.
//    void calculateBillReasonableRange(){
//        b1 = new Bill(1);
//        b2 = new Bill(20);
//        b4 = new Bill(279);
//        assertEquals(5, b1.calculateBill(a.billFactor()));
//        assertEquals(2.5, b1.calculateBill(c.billFactor()));
//        assertEquals(1.0, b1.calculateBill(e.billFactor()));
//        assertEquals(100,b2.calculateBill(a.billFactor()));
//        assertEquals(1395, b4.calculateBill(a.billFactor()));
//
//
//
//    }
//    @Test
//    //Checking if Bill method works on absurd range
//    void calculateBillAbsurdRange() {
//        b5 = new Bill(2000);
//        assertEquals(10000, b5.calculateBill(a.billFactor()));
//
//    }

//}