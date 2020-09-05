package Testsuite;

import exceptions.EmptyMenuException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import resturant.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class MenuTest {
    SharableMenu m1;
    NonSharableMenu m2;
    SharableMenu m3;
    Order o1;
    Order o2;
    Order o3;
    Customer c1;
    Customer c2;
    Customer c3;
    private final PrintStream originalOut = System.out;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    void runBefore() {
        m1 = new SharableMenu("Menu 1");
        m2 = new NonSharableMenu("Menu 2");
        m3 = new SharableMenu("Menu 1");
        c1 = new Adult("adult",m1);
        c2 = new Adult("adult",m2);
        c3 = new Adult("adult",m1);
        o1 = new Order(c1,m1);
        o2 = new Order(c2,m2);
        o3 = new Order(c3,m1);
        System.setOut(new PrintStream(outContent));
    }


    @Test
    public void testAddTypes() {
        m1.addTypes("apple");
        assertTrue(m1.isPresentList("apple"));
        assertFalse(m1.isPresentList("banana"));
        assertFalse(m1.isPresentList("cake"));
        assertFalse(m1.isPresentList("mango"));
        assertFalse(m1.isPresentList("salad"));
        assertFalse(m1.isPresentList("water"));
        assertFalse(m1.isPresentList("fish"));
        assertFalse(m1.isPresentList("dragon fruit"));
        assertEquals(1, m1.getSize());

    }


    @Test
    void testRemoveItem() throws IOException, EmptyMenuException {
        m1.load("./data/test1.txt", 0);
        ArrayList<String> testList = m1.getMenu();
        assertEquals(2, testList.size());
        m1.showTypeOfMenu();
        m1.addOrder(o1);
        m1.addOrder(o2);
        m1.addObserver(o1);
        m1.addObserver(o2);
        assertTrue(m1.isPresent("Mango"));
        assertTrue(m1.isPresent("cake"));
        assertEquals(0, o1.selectionsMade());
        o1.addItem(m1,"Mango",2);
        o1.addItem(m1,"ice cream", 2);
        assertFalse(o1.contains("ice cream"));
        o1.addItem(m1,"cake",2);
        assertEquals(4, o1.selectionsMade());
        assertEquals(14.0, o1.calculateTotalBill(m1));
        assertTrue(o1.contains("Mango"));
        o2.addItem(m1,"Mango",2);
        assertTrue(o2.contains("Mango"));
        assertEquals(6.0, o2.calculateTotalBill(m1));
        m1.removeItem("Mango");
        m1.removeItem("ice cream");
        assertFalse(m1.isPresent("ice cream"));
        assertEquals(1, m1.getSize());
        m1.removeItemArray("ice cream");
        assertEquals(1, m1.getSize());
        assertEquals(2, o1.selectionsMade());
        assertFalse(m1.isPresent("Mango"));
        assertTrue(m1.isPresent("cake"));
        assertFalse(o2.contains("Mango"));
        assertEquals(8.0, o1.calculateTotalBill(m1));
        assertEquals(0.0, o2.calculateTotalBill(m1));
        o1.updateItem(1,5, "cake");
        assertEquals(5, o1.selectionsMade());
        assertEquals(20.0, o1.calculateTotalBill(m1));
        o1.updateItem(0,0,"cake");
        assertEquals(0.0, o1.calculateTotalBill(m1));
        ArrayList<Order> orders = m1.getOrder();
        assertTrue(orders.contains(o1));
        assertTrue(orders.contains(o2));
    }



    @Test
    public void testShowTypeOfMenu() throws IOException, EmptyMenuException {

        m1.load("./data/test1.txt", 0);
        m1.showTypeOfMenu();
        assertEquals("Menu 1 is being shown\n" + "cake & price: 4.0 this is a sharable item\n" +"Mango & price: 3.0 this is a sharable item\n"+
                "Make desired selections here\n", outContent.toString());  // From StackOverFlow
        assertTrue(m1.isPresent("Mango"));
        m1.removeItem("Mango");
        assertFalse(m1.isPresent("Mango"));
        m2.showTypeOfMenu();
        assertFalse(m2.isSharable());

        //assertEquals("Menu 2 is being shown\nMake desired selections here\n", outContent.toString());
    }

    @Test
    public void testSharable() {
        assertFalse(m2.isSharable());
        assertTrue(m1.isSharable());
    }



    @Test
    public void testSaveLoad() throws IOException {
        m1.addTypes("Mango");
        m1.addTypes("cake");
        m1.save("./data/test2.txt");
        List<String> lines = Files.readAllLines(Paths.get("./data/test2.txt"));
        try {
            m1.load("./data/test2.txt", 0);
        } catch( EmptyMenuException ee){
            fail("Menu is greater than 1");
        } catch (IOException ee){
            fail("File was present");
        }

        assertTrue(m1.isPresentList("Mango"));

    }

    @Test
    public void failLoadFewMenuItems() {
        try {
            m1.load("./data/testinput.txt", 0);
            fail("Shouldn't reach this step");
        } catch( EmptyMenuException ee){
        } catch (IOException ee){
            fail("File was present");
        }

    }
    @Test
    public void failLoadWrongFile() {
        try {
            m1.load("./data/random.txt", 0);
            fail("Shouldn't reach this step");
        } catch( EmptyMenuException ee){
            fail("No file");
        } catch (IOException ee){
        }

    }

    @Test
    public void isSharableTest() {
        assertTrue(m1.isSharable());
        assertFalse(m2.isSharable());
    }

    @Test
    public void getNameTest() {
        assertEquals("Menu 1", m1.getName());
        assertEquals("Menu 2", m2.getName());
    }

    @Test
    public void getSizeTest() {
        assertEquals(0,m1.getSize());
        assertEquals(0,m2.getSize());
    }

    @Test
    public void isPresentADDTest() throws IOException, EmptyMenuException {
        m1.load("./data/test2.txt", 0);
        m1.addTypes("Mango");
        assertTrue(m1.isPresentList("Mango"));
        assertFalse(m1.isPresentList("apple"));
    }

    @Test

    void hashCodeTest() throws IOException, EmptyMenuException {
        assertFalse(m1.equals(m2));
        assertTrue(m1.equals(m3));
        assertTrue(m1.hashCode() == m3.hashCode());
        assertFalse(m2.hashCode() == m1.hashCode());
        assertEquals(-1, m1.compareTo(m2));
        assertEquals(0,m1.compareTo(m3));
        m1.load("./data/test1.txt", 0);
        m3.load("./data/input.txt", 0);
        assertFalse(m1.equals(m3));
        assertEquals(1, m3.compareTo(m1));
        assertFalse(m1.hashCode() == m3.hashCode());
    }

}
