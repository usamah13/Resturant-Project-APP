package resturant;

import exceptions.EmptyMenuException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.lang.String;


public abstract class Menu extends Observable implements Loadable, Savable {
    private String type;
    private ArrayList<String> menu = new ArrayList<>();
    private HashMap<String, Double> menuAndPrice = new HashMap<>();
    private ArrayList<Order> orders = new ArrayList<>();

    public abstract boolean isSharable();

    public Menu(String type) {
        this.type = type;


    }

    public ArrayList<String> getMenu() {
        return menu;
    }

    // https://www.baeldung.com/java-observer-pattern

//    public void addObserver(Observer o) {
//        addObserver(o);
//    }
//
//    public void removeObserver(Observer o) {
//        removeObserver(o);
//    }

    public ArrayList<Order> getOrder() {
        return orders;
    }

    //Modifies: this
    //Effects: removes a given item from the menu list
    public void removeItemArray(String name) {
        for (String i : menu) {
            //System.out.println(i);
            String[] parts = i.split("-");
            //System.out.println(parts.length);
            String key = parts[0];
            if (key.equals(name)) {
                menu.remove(i);
                break;
            }
        }
    }

    public String getName() {
        return this.type;
    }

    public boolean isPresent(String item) {
        return menuAndPrice.containsKey(item);
    }

    public boolean isPresentList(String item) {
        return menu.contains(item);
    }

    public int getSize() {
        return menu.size();
    }

    public HashMap<String, Double> getMenuAndPrice() {
        return this.menuAndPrice;
    }

    public void addOrder(Order o) {
        orders.add(o);
    }

    //Modifies: this,Order
    //Effects: removes a given item from the menu's list, HashMap and also from Order
    public void removeItem(String name) {
        if (menuAndPrice.containsKey(name)) {
            removeItemArray(name);
//            System.out.println(menu.indexOf(name + "-" + menuAndPrice.get(name)));
            menuAndPrice.remove(name);
            setChanged();
            notifyObservers(name);
//            for (Order o : orders) {
//                if (o.contains(name)) {
//                    o.updateItem(0, 0, name);
//                }
//            }

        }
    }


    //Effects: prints items and prices in the menu based on the type, sharable status
    public void showTypeOfMenu() {
        String sharableStatus = "";
        String sharableItem = " this is a sharable item";
        String notSharableItem = " this is not a sharable item";
        initMenuWithPrice(menu);
        if (this.isSharable()) {
            sharableStatus = sharableItem;
        } else {
            sharableStatus = notSharableItem;
        }
        System.out.println(type + " is being shown");

        //https://beginnersbook.com/2013/12/how-to-loop-hashmap-in-java/
        for (Map.Entry me : menuAndPrice.entrySet()) {
            System.out.println(me.getKey() + " & price: " + me.getValue() + sharableStatus);
        }
        System.out.println("Make desired selections here");
    }

    //Effects: returns a string of items and prices in the menu based on the type, sharable status
    public String stringTypeOfMenu() {
        String stringTypeOfMenu = "";
        String sharableStatus = "";
        String sharableItem = " this is a sharable item";
        String notSharableItem = " this is not a sharable item";
        initMenuWithPrice(menu);
        if (this.isSharable()) {
            sharableStatus = sharableItem;
        } else {
            sharableStatus = notSharableItem;
        }
        stringTypeOfMenu = type + " is being shown" + "\n";

        //https://beginnersbook.com/2013/12/how-to-loop-hashmap-in-java/
        for (Map.Entry me : menuAndPrice.entrySet()) {
            stringTypeOfMenu += me.getKey() + " & price: " + me.getValue() + sharableStatus + "\n";
        }
        return stringTypeOfMenu;
    }



    public void addTypes(String item) {
        menu.add(item);
    }

    //Modifies: this
    //Effects: Loads, text file of items and prices into a list, throws EmptyMenuException when the file where the menu
    //has to be loaded from is empty. throws IO exception when problems in reading or writing to a file
    @Override
    public void load(String filePath, int lineIndex) throws IOException, EmptyMenuException {
        ArrayList<String> loadMenu1 = new ArrayList<>();
        //List<List<Menu>> a1;
        List<String> lines = Files.readAllLines(Paths.get(filePath));

        //https://stackoverflow.com/questions/39991058/convert-string-representation-of-array-back-to-int-array-in-java

        //System.out.println(lines.size());
        lines.set(lineIndex, lines.get(lineIndex).replace("[", ""));
        lines.set(lineIndex, lines.get(lineIndex).replace("]", ""));
        for (String i : lines.get(lineIndex).split(",")) {
            if (i.isEmpty()) {
                break;
            } else {
                loadMenu1.add(i);
            }

        }
        //ArrayList<String> loadMenu2 = new ArrayList<>();
//        lines.set(1, lines.get(1).replace("[", ""));
//        lines.set(1, lines.get(1).replace("]", ""));
//        for (String i : lines.get(1).split(",")) {
//            loadMenu2.add(i);
//
//        }
        //System.out.println(loadMenu1.isEmpty());
        if (loadMenu1.isEmpty()) {
            throw new EmptyMenuException();
        }
        menu = loadMenu1;


        // https://stackoverflow.com/questions/157944/create-arraylist-from-array
        //menu1 = (ArrayList<String>) Arrays.asList(lines.get(0).split(","));

        //menu2 = (ArrayList<String>) Arrays.asList(lines.get(0).split(","));


    }

    //https://stackoverflow.com/questions/3481828/how-to-split-a-string-in-java

    //Modifies: this
    //Effects: creates a HashMap of items(keys) and prices(value)
    public void initMenuWithPrice(ArrayList<String> menu) {
        for (String i : menu) {
            //System.out.println(i);
            String[] parts = i.split("-");
            //System.out.println(parts.length);
            String key = parts[0];
            //System.out.println(key);
            String value = parts[1];
            //System.out.println(value);
            menuAndPrice.put(key, Double.parseDouble(value));

        }
    }

    //Effects: saves the menu items and prices as a text file, throws IO exception when
    // problems in reading or writing to a file,
    @Override
    public void save(String filePath) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter(filePath, "UTF-8");
        writer.println(menu);
        //writer.println(menu2);
        writer.close(); //note -- if you miss this, the file will not be written at all.

    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) {
//            return true;
//        }
//        if (!(o instanceof Menu)) {
//            return false;
//        }
//        Menu other = (Menu) o;
//        return Objects.equals(type, other.type)
//                // && Objects.equals(tags, menu.tags)
//                && Objects.equals(menu, other.menu)
//                && Objects.equals(menuAndPrice, other.menuAndPrice);
//    }
//
//    @Override
//    public int hashCode() {
//        // return Objects.hash(description, tags, dueDate, priority, status);
//        return Objects.hash(type, menu, menuAndPrice);
//    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Menu menu1 = (Menu) o;
        return type.equals(menu1.type)
                && menu.equals(menu1.menu)
                && menuAndPrice.equals(menu1.menuAndPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, menu, menuAndPrice);
    }



     //https://www.w3schools.com/java/ref_string_compareto.asp
    //Effects: Compares if two menus are the same or not, returns 0 if equal, 1 if this menu is bigger than
     // the other size wise and for vice versa returns -1
    public int compareTo(Menu other) {
        if (this.equals(other)) {
            return 0;

        } else if (this.menu.size() > other.menu.size()) {
            return 1;
        } else {
            return -1;
        }
    }

}
