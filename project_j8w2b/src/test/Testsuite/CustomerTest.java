package Testsuite;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import resturant.*;



import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class CustomerTest {
    Adult a;
    Child c;
    Elder e;
    Child d;
    Child b;
    Order o1;
    private Menu m = new SharableMenu("m");

    @BeforeEach
    void runBefore () {
        c = new Child("Baby",m);
        a = new Adult("Adult",m);
        e = new Elder("Elder",m);
        d = new Child("Child",m);
        b = new Child("Child",m);
        o1 = new Order(b,m);
    }


    @Test
    void getNameTest(){
        assertEquals("Baby", c.getName());
        assertEquals("Adult", a.getName());
        assertEquals("Elder", e.getName());
    }

    @Test
    void hashCodeTest() {
        assertFalse(a.equals(c));
        assertFalse(e.equals(a));
        assertFalse(c.equals(e));
        assertFalse(c.equals(d));
        d.setOrder(o1);
        b.setOrder(o1);
        assertTrue(b.equals(d));
        assertFalse(c.hashCode() == d.hashCode());

    }

}

