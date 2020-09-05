package resturant;

import java.util.*;

public class Order implements Observer {
    private static int UPDATE = 1;

    private int id;
    private HashMap<String, Double> itemsAndQuan;
//    public ArrayList<Bill> bills;
    protected Customer customer;

    public Order(Customer c, Menu m) {
        this.itemsAndQuan = new HashMap<>();
        this.customer = c;
        this.id = c.hashCode();
        m.addOrder(this);
//        bills = new ArrayList<>();

    }


//
//    public void addBill(Bill b) {
//        if (!bills.contains(b)) {
//            bills.add(b);
//            b.addOrder(this);
//        }
//    }
//
//    public void removeBill(Bill b) {
//        if (bills.contains(b)) {
//            bills.remove(b);
//            b.removeOrder(this);
//        }
//    }

    //Effects: checks whether or not a given item is in the Hash map of items and quantity
    public boolean contains(String name) {
        return itemsAndQuan.containsKey(name);
    }

    //Modifies: this
    //Effects: adds the item and the quantity into Hash map of items and quantity as key and value respectively if
    // present in the Menu
    public void addItem(Menu m, String itemName, double quantity) {
        if (m.isPresent(itemName)) {
            itemsAndQuan.put(itemName, quantity);
        } //else Throw error
    }

    //Requires: for remove option the item must be in order, not sure if it is not and gives error or not was working
    // fine for me without
    //Modifies: this
    //Effects: update or removes items and their quantities from the itemsandquans HASH map
    public void updateItem(int mode, double quantity, String itemName) {
        if (mode == UPDATE) {
            this.itemsAndQuan.replace(itemName, quantity);
        } else {
            this.itemsAndQuan.remove(itemName);
        }
    }

    //Effects: returns the number of selections made quantity wise by look at the itemsandQauns HashMap
    public double selectionsMade() {
        double selection = 0;
        for (Map.Entry me : itemsAndQuan.entrySet()) {
            selection += (double) me.getValue();
        }
        //int selections = 5;  test number right now
        return selection;

    }

    //Effects: Tells user what que his order is in
    public void queue() {
        System.out.println("Your in the following Queue");
    }


    //Effects: Calculates the total bill, by getting the price from the menu and the customers respective bill factor.
    public double calculateTotalBill(Menu m) {
        double totalPrice = 0.00;
        for (Map.Entry me : itemsAndQuan.entrySet()) {
            if (m.getMenuAndPrice().get(me.getKey()) != null) {
                double price = m.getMenuAndPrice().get(me.getKey()) * (double) me.getValue();
                totalPrice += price;
            }
        }
        return totalPrice * customer.billFactor();
    }


    //Effects: Observes changes in menu, to be reflected here. Item removed in menu, is removed from the order
    @Override
    public void update(Observable o, Object arg) {
        updateItem(0, 0, (String) arg);
        System.out.println(arg + " is removed from Menu ");
        System.out.println(arg + " is removed from your Order ");
    }
}
