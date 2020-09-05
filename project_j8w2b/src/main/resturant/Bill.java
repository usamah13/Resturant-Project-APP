//package resturant;
//
//import java.util.ArrayList;
//
//public class Bill {
//    private int items;
//    public ArrayList<Order> order;
//
//    public Bill(int items) {
//        this.items = items;
//        order = new ArrayList<>();
//    }
//
//    //Effects: returns the amount of the bill according to the item selections
//    public double calculateBill(double discount) {
//        int i = 5;
//        double b = i * discount * items;
//        return b;
//
//    }
//
//    public void addOrder(Order o) {
//        if (!order.contains(o)) {
//            order.add(o);
//            o.addBill(this);
//        }
//    }
//
//    public void removeOrder(Order o) {
//        if (order.contains(o)) {
//            order.remove(o);
//            o.removeBill(this);
//        }
//    }
//}
