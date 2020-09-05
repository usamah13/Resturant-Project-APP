package ui;


import exceptions.EmptyMenuException;
import exceptions.EmptyStringException;
import exceptions.InvalidInputException;
import network.Restaurant;
import org.json.JSONException;
import resturant.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;


public class DisplayScreen {
    // from logging calculator repositories on the edx site.
    //ArrayList<Order> operationLog = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);
    private String operation = "";
    private String choice = "";
    private String name = "";
    private String quan = "";
    private String yesorno = "";
    private String rname = "";

    //Requires: Proper Spelling for choices, and not wrong choices and customers assumed as Adults at the moment
    //Modifies: Menu,Order
    //Effects: Displays the interface where the user makes selections and bill is shown, throws an InvalidInputException
    // if the given menu option is not selected, throws Empty String Exception when no string is entered for menu option
    //throws IO exception when problems in reading or writing to a file, JSON exception when problem ith JSON object
    public DisplayScreen() throws InvalidInputException, IOException, EmptyStringException, JSONException {
        //showMenu();
        SharableMenu starter = new SharableMenu("Starter");
        NonSharableMenu mainCourse = new NonSharableMenu("Main Course");
        Order orderNumber;
        try {
            starter.load("./data/input.txt", 0);
            mainCourse.load("./data/input.txt", 1);
        } catch (EmptyMenuException emptyMenu) {
            System.out.println("No item in the menu file");
        } catch (IOException e) {
            System.out.println("IOE exception");
        } finally {
            System.out.println(" Loading Process");
        }


        String starterMenu = starter.getName();
        String mainMenu = mainCourse.getName();
        while (true) {
            Restaurant r = new Restaurant();
            System.out.println("Please select the restaurant:");
            r.showRestaurants();
            rname = scanner.nextLine();

            if (!r.checkRestaurantName(rname)) {
                break;

            }
            intro();
            Customer c1;
            System.out.println(name + " please select a Menu option (" + starterMenu + " or "
                    + mainMenu + " or quit):)");
            operation = scanner.nextLine();
            isEmptyCheck(starterMenu, mainMenu);
            System.out.println("you selected: " + operation);

            if (operation.equals(starter.getName())) {
                starter.showTypeOfMenu();
                starter.save("./data/output.txt");
                System.out.println("Please enter your name :");
                name = scanner.nextLine();
                c1 = new Adult(name, starter);
                orderNumber = new Order(c1, starter);
                starter.addObserver(orderNumber);

            } else if (operation.equals(mainCourse.getName())) {
                mainCourse.showTypeOfMenu();
                mainCourse.save("./data/output2.txt");
                System.out.println("Please enter your name :");
                name = scanner.nextLine();
                c1 = new Adult(name, mainCourse);
                orderNumber = new Order(c1, mainCourse);
                mainCourse.addObserver(orderNumber);

            } else if (operation.equals("quit")) {
                break;
            } else {
                break;
            }
            while (!choice.equals("quit")) {
                System.out.println("Choose from an option from above or choose quit");
                choice = scanner.nextLine();
                if (choice.equals("quit")) {
                    break;
                }
                System.out.println("you selected: " + choice);
                System.out.println("What quantity would you like?");
                quan = scanner.nextLine();
                double quantity = Double.parseDouble(quan);
                orderNumber.addItem(starter, choice, quantity);
            }

            orderNumber.queue();
            confirmation();
            System.out.println("Would you like to edit or remove an item? (e/r)");
            yesorno = scanner.nextLine();
            if (yesorno.equals("r")) {
                System.out.println("What item would you like to remove ?");
                choice = scanner.nextLine();
                orderNumber.updateItem(0, 1, choice);
            } else if (yesorno.equals("e")) {
                System.out.println("What item would you like to edit ?");
                choice = scanner.nextLine();
                System.out.println("How many " + choice + "s would you like to add?");
                quan = scanner.nextLine();
                double quantity = Double.parseDouble(quan);
                orderNumber.updateItem(1, quantity, choice);
            }

            starter.removeItem("apple");
            System.out.println(orderNumber.contains("apple"));
//            System.out.println(starter.getMenuAndPrice().toString());
//            System.out.println(starter.getMenu().toString());
//            System.out.println(starter.getOrder().toString());
//            starter.save("./data/output3.txt");

            double numberOfItems = orderNumber.selectionsMade();
            userItems(numberOfItems);
            System.out.println("Your Total Bill is: " + orderNumber.calculateTotalBill(starter));
//            Bill bill = new Bill((int) numberOfItems);
//            Child c = new Child("Baby");
//            Adult a = new Adult("Adult");
//            Elder e = new Elder("Elder");
//            double amount1 = bill.calculateBill(c.billFactor());
//            double amount2 = bill.calculateBill(a.billFactor());
//            double amount3 = bill.calculateBill(e.billFactor());
//            System.out.println("Your total is : " + amount1);
//            System.out.println("Your total is : " + amount2);
//            System.out.println("Your total is : " + amount3);
            break;
        }
    }

    //Effects: throws an InvalidInputException
    // if the given menu option is not selected, throws Empty String Exception when no string is entered for menu option
    private void isEmptyCheck(String starterMenu, String mainMenu) throws EmptyStringException, InvalidInputException {
        if (operation.isEmpty()) {
            throw new EmptyStringException();
        } else if (!operation.equals(starterMenu) && !operation.equals(mainMenu) && !operation.equals("quit")) {
            throw new InvalidInputException();
        }
    }


    //Effects: show the intro message
    public void showMenu() {
        System.out.println("Welcome to ABC restaurant");
    }

    // Effects show the message of selecting a food from the list
    public void intro() {
        System.out.println("Please choose what type of of food would you like from the list");
    }

    //Effects: Shows the number of items you have selected
    public void userItems(double itemsSelected) {
        System.out.println("These are the number of items you have selected: " + itemsSelected);
    }

    //Effects: Prints a confirmation message
    public void confirmation() {
        System.out.println("Please confirm or edit the selections you have made");
    }

   //Effects: //throws IO exception when problems in reading or writing to a file,
   // JSON exception when problem ith JSON object
    public static void main(String[] args) throws IOException, JSONException {
        int i = 0;
        try {
            DisplayScreen testObject = new DisplayScreen();
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
            ;
        } catch (UnsupportedEncodingException e) {
            System.out.println("Unsupported encoding");
            ;
        } catch (EmptyStringException emptyString) {
            System.out.println("You didn't enter anything");
            i += 1;
        } catch (InvalidInputException invalidInput) {
            System.out.println("Invalid entry");
            i += 1;
        } finally {
            System.out.println("Number of errors is " + i);
        }
    }

}