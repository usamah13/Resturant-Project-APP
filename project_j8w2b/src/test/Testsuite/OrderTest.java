package Testsuite;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import resturant.Adult;
import resturant.Menu;
import resturant.Order;
import resturant.SharableMenu;

import java.io.PrintStream;
import java.io.ByteArrayOutputStream;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {
    // Stackoverflow way to check printer test https://stackoverflow.com/questions/1119385/junit-test-for-system-out-println
    private Order order1;
    private final PrintStream originalOut = System.out;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private Adult a;
    private Menu m = new SharableMenu("m");

    @BeforeEach
    void runBefore() {
        a = new Adult("Adult",m);
        System.setOut(new PrintStream(outContent));
        order1 = new Order(a,m);

    }

    @Test
    void testIntro() {
        order1.queue();
        assertEquals("Your in the following Queue\n", outContent.toString());
    }

    @Test
    public void testSelectionsMade() {
        assertEquals(0, order1.selectionsMade());
        assertEquals(0, order1.selectionsMade());

    }
}
