package ui;

import exceptions.EmptyMenuException;
import network.Restaurant;
import org.json.JSONException;
import resturant.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class DisplayScreenUI extends JFrame implements ActionListener {
    private JLabel label;
    private JTextField field;
    private JTextArea screen;
    private String operation = "";
    private String choice = "";
    private String name = "";
    private String quan = "";
    private String yesorno = "";
    private String rname = "";
    private String inputfromfield = "";
    private boolean isClicked;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton button5;
    private JButton button6;
    private JButton button1;
    private SharableMenu starter;
    private NonSharableMenu mainCourse;

/*
    public LabelChanger() {
        gltest();
        super("Restaurant Orders");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(500, 600));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        setLayout(new FlowLayout());



        JButton btn = new JButton("Change");

        btn.setActionCommand("myButton");
        btn.addActionListener(this); //sets "this" class as an action listener for btn.
        //that means that when the btn is clicked,
        //this.actionPerformed(ActionEvent e) will be called.
        //You could also set a different class, if you wanted
        //to capture the response behaviour elsewhere
        label = new JLabel("flag");
        screen = new JTextArea(20, 30);
        field = new JTextField(10);


        add(screen);
        add(field);
        add(btn);
        add(label);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }
*/

    //Requires: Proper Spelling for choices, and not wrong choices, customers assumed as Adults at the moment
    //Modifies: Menu,Order
    //Effects: Displays the ui where the user makes selections and bill is shown, throws an InvalidInputException
    // if the given menu option is not selected,
    //throws IO exception when problems in reading or writing to a file, JSON exception when problem ith JSON object
    public DisplayScreenUI() throws IOException, JSONException {

        // https://stackoverflow.com/questions/27285233/why-does-a-jbutton-not-show-unless-text-is-set-after-jbutton-is-added-to-jpanel
        super("Restaurant Orders");
        isClicked = false;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 1100));
//       ((JPanel) getContentPane()).setBorder(new EmptyBorder(0, 0, 0, 0));
        GridBagLayout layout = new GridBagLayout();
        setLayout(layout);
        GridBagConstraints gbc = new GridBagConstraints();
        screen = new JTextArea(20, 50);
        //screen.setText("fff");


        button2 = new JButton("2");
        button2.addActionListener(this);

        button3 = new JButton("3");
        button3.addActionListener(this);

        button4 = new JButton("4");
        button4.addActionListener(this);

        button5 = new JButton("5");
        button5.addActionListener(this);

        button6 = new JButton("Easter Egg");
        button6.addActionListener(this);

        gbc.weightx = 0;
        gbc.weighty = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        add(screen, gbc);
        // Put constraints on different buttons
        //gbc.fill = GridBagConstraints.HORIZONTAL;


//        label = new JLabel("flag");
//
//        gbc.gridx = 1;
//        gbc.gridy = 2;
//        add(label, gbc);


        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);


        starter = new SharableMenu("Starter");
        mainCourse = new NonSharableMenu("Main Course");
        Order orderNumber;
        try {
            starter.load("./data/input.txt", 0);
            mainCourse.load("./data/input.txt", 1);
        } catch (EmptyMenuException emptyMenu) {
            screen.setText("No item in the menu file");
        } catch (IOException e) {
            screen.setText("IOE exception");
        } finally {
            screen.setText(" Loading Process");
        }


        screen.setText("Please select the restaurant:\n");
        Restaurant r = new Restaurant();
        ArrayList<String> names = r.getRestaurantNames();
        int counter = 0;
        button2.setText(names.get(counter));
        counter++;
        button3.setText(names.get(counter));
        counter++;
        button4.setText(names.get(counter));
        counter++;
        button5.setText(names.get(counter));
        counter++;

//        for (String i : names) {
//            //System.out.println(i);
//
//
//            result = result + i + " \n";
//        }

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(button2, gbc);


        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(button3, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        add(button4, gbc);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        add(button5, gbc);


        button1 = new JButton();


        button1.setAction(new AbstractAction() {
            public void actionPerformed(ActionEvent ae) {
                inputfromfield = field.getText();
                field.setText("");
                synchronized (button1) {
                    button1.notify();

                }
            }
        });


//        btn.setActionCommand("myButton");
//        btn.addActionListener(this);
        field = new JTextField(30);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(field, gbc);
        field.setVisible(false);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 2;
        add(button1, gbc);
        button1.setText("Submit");
        button1.setVisible(false);

        Customer c1;


        synchronized (button1) {
            try {
                button1.wait();
            } catch (InterruptedException ex) {
                System.out.println("Interrupted");
            }
        }

        if (inputfromfield.equals(starter.getName())) {
            //starter.showTypeOfMenu();
            starter.save("./data/output.txt");
            screen.setText("Please enter your name :");
            synchronized (button1) {
                try {
                    button1.wait();
                } catch (InterruptedException ex) {
                    System.out.println("Interrupted");
                }
            }
            name = inputfromfield;
            c1 = new Adult(name, starter);
            orderNumber = new Order(c1, starter);
            starter.addObserver(orderNumber);
            String items = starter.stringTypeOfMenu();
            screen.setText(items + "\n");
            screen.append(name + " make desired selections here" + "\n");
            while (true) {

                screen.append("Choose from an option from above or choose quit" + "\n");
                synchronized (button1) {
                    try {
                        button1.wait();
                    } catch (InterruptedException ex) {
                        System.out.println("Interrupted");
                    }
                }
                choice = inputfromfield;
                if (choice.equals("quit")) {
                    break;
                }
                screen.append("What quantity would you like?" + "\n");
                synchronized (button1) {
                    try {
                        button1.wait();
                    } catch (InterruptedException ex) {
                        System.out.println("Interrupted");
                    }
                }
                quan = inputfromfield;
                screen.append("you selected: " + quan + " " + choice + "\n");
                double quantity = Double.parseDouble(quan);
                orderNumber.addItem(starter, choice, quantity);

            }

            while (true) {
                screen.setText("Would you like to edit or remove an item? (e/r/none)" + "\n");
                synchronized (button1) {
                    try {
                        button1.wait();
                    } catch (InterruptedException ex) {
                        System.out.println("Interrupted");
                    }
                }
                yesorno = inputfromfield;
                if (yesorno.equals("none")) {
                    break;
                } else if (yesorno.equals("r")) {
                    screen.append("What item would you like to remove ?" + "\n");
                    synchronized (button1) {
                        try {
                            button1.wait();
                        } catch (InterruptedException ex) {
                            System.out.println("Interrupted");
                        }
                    }
                    choice = inputfromfield;
                    orderNumber.updateItem(0, 1, choice);
                } else if (yesorno.equals("e")) {
                    screen.append("What item would you like to edit ?" + "\n");
                    synchronized (button1) {
                        try {
                            button1.wait();
                        } catch (InterruptedException ex) {
                            System.out.println("Interrupted");
                        }
                    }
                    choice = inputfromfield;
                    screen.append("How many " + choice + "s would you like to want?" + "\n");
                    synchronized (button1) {
                        try {
                            button1.wait();
                        } catch (InterruptedException ex) {
                            System.out.println("Interrupted");
                        }
                    }
                    quan = inputfromfield;
                    double quantity = Double.parseDouble(quan);
                    orderNumber.updateItem(1, quantity, choice);
                }
            }
            double numberOfItems = orderNumber.selectionsMade();
            screen.setText("These are the number of items you have selected: " + numberOfItems + "\n");
            screen.append(name + " your Total Bill is: " + orderNumber.calculateTotalBill(starter) + "\n");
            screen.append("Please exit from the window");
            gbc.gridx = 0;
            gbc.gridy = 4;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridwidth = 2;
            add(button6, gbc);

        } else if (inputfromfield.equals(mainCourse.getName())) {
//            mainCourse.showTypeOfMenu();
//            mainCourse.save("./data/output2.txt");
//            System.out.println("Please enter your name :");
//            name = scanner.nextLine();
//            c1 = new Adult(name, mainCourse);
//            orderNumber = new Order(c1, mainCourse);
//            mainCourse.addObserver(orderNumber);

            mainCourse.save("./data/output.txt");
            screen.setText("Please enter your name :");
            synchronized (button1) {
                try {
                    button1.wait();
                } catch (InterruptedException ex) {
                    System.out.println("Interrupted");
                }
            }
            name = inputfromfield;
            c1 = new Adult(name, starter);
            orderNumber = new Order(c1, mainCourse);
            mainCourse.addObserver(orderNumber);
            String items = mainCourse.stringTypeOfMenu();
            screen.setText(items + "\n");
            screen.append(name + " make desired selections here" + "\n");
            while (true) {

                screen.append("Choose from an option from above or choose quit" + "\n");
                synchronized (button1) {
                    try {
                        button1.wait();
                    } catch (InterruptedException ex) {
                        System.out.println("Interrupted");
                    }
                }
                choice = inputfromfield;
                if (choice.equals("quit")) {
                    break;
                }
                screen.append("What quantity would you like?" + "\n");
                synchronized (button1) {
                    try {
                        button1.wait();
                    } catch (InterruptedException ex) {
                        System.out.println("Interrupted");
                    }
                }
                quan = inputfromfield;
                screen.append("you selected: " + quan + " " + choice + "\n");
                double quantity = Double.parseDouble(quan);
                orderNumber.addItem(mainCourse, choice, quantity);

            }

            while (true) {
                screen.setText("Would you like to edit or remove an item? (e/r/none)" + "\n");
                synchronized (button1) {
                    try {
                        button1.wait();
                    } catch (InterruptedException ex) {
                        System.out.println("Interrupted");
                    }
                }
                yesorno = inputfromfield;
                if (yesorno.equals("none")) {
                    break;
                } else if (yesorno.equals("r")) {
                    screen.append("What item would you like to remove ?" + "\n");
                    synchronized (button1) {
                        try {
                            button1.wait();
                        } catch (InterruptedException ex) {
                            System.out.println("Interrupted");
                        }
                    }
                    choice = inputfromfield;
                    orderNumber.updateItem(0, 1, choice);
                } else if (yesorno.equals("e")) {
                    screen.append("What item would you like to edit ?" + "\n");
                    synchronized (button1) {
                        try {
                            button1.wait();
                        } catch (InterruptedException ex) {
                            System.out.println("Interrupted");
                        }
                    }
                    choice = inputfromfield;
                    screen.append("How many " + choice + "s would you like to want?" + "\n");
                    synchronized (button1) {
                        try {
                            button1.wait();
                        } catch (InterruptedException ex) {
                            System.out.println("Interrupted");
                        }
                    }
                    quan = inputfromfield;
                    double quantity = Double.parseDouble(quan);
                    orderNumber.updateItem(1, quantity, choice);
                }
            }
            double numberOfItems = orderNumber.selectionsMade();
            screen.setText("These are the number of items you have selected: " + numberOfItems + "\n");
            screen.append(name + " your Total Bill is: " + orderNumber.calculateTotalBill(starter) + "\n");
            screen.append("Please exit from the window");

            gbc.gridx = 0;
            gbc.gridy = 4;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridwidth = 2;
            add(button6, gbc);

        } else if (inputfromfield.equals("quit")) {
            screen.setText("Please exit from the window");

            gbc.gridx = 0;
            gbc.gridy = 4;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridwidth = 2;
            add(button6, gbc);
        }


//        while (!inputfromfield.equals("quit")) {
//            //System.out.println(inputfromfield);
//            screen.setText("Please select the restaurant:\n");
//            //inputfromfield = "";
//
//            //Restaurant r = new Restaurant();
//            String result = "h";
////            ArrayList<String> names = r.getRestaurantNames();
////            for (String i : names) {
////                //System.out.println(i);
////                result = result + i + " \n";
////            }
//            String result = "h";
//        }

        //System.out.println("he;llo");
    }

    //https://stackoverflow.com/questions/37782698/click-on-jbutton-to-display-image
    //Modifies: this
    //Effects: checks which button is clicked and performs the actions accordingly
    @Override
    public void actionPerformed(ActionEvent e) {
        //this is the method that runs when Swing registers an action on an element
        //for which this class is an ActionListener
        restaurantCheck(e);
        if (e.getSource() == button6) {
            BufferedImage myPicture = null;
            try {
                myPicture = ImageIO.read(new File("./data/tobs.jpg"));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            ImageIcon image = new ImageIcon(myPicture);
            label = new JLabel(image);
            screen.setVisible(false);
            button1.setVisible(false);
            field.setVisible(false);
            button6.setVisible(false);

            add(label);
        }


    }

    private void restaurantCheck(ActionEvent e) {
        if (e.getSource() == button2) {
            buttonPressed(button2);

        } else if (e.getSource() == button3) {
            buttonPressed(button3);


        } else if (e.getSource() == button4) {
            buttonPressed(button4);


        } else if (e.getSource() == button5) {
            buttonPressed(button5);


        }
    }

    private void buttonPressed(JButton button) {
        inputfromfield = button.getText();
        System.out.println(inputfromfield);
        button2.setVisible(false);
        button3.setVisible(false);
        button4.setVisible(false);
        button5.setVisible(false);
        button1.setVisible(true);
        field.setVisible(true);
        screen.setText("Welcome to " + inputfromfield + "\n");

        screen.append("Please enter a Menu option (" + starter.getName() + " or "
                + mainCourse.getName() + " or quit):) and press the submit button");
    }

    //Effects: //throws IO exception when problems in reading or writing to a file, JSON exception
    // when problem with a JSON object

    public static void main(String[] args) throws IOException, JSONException {

//        JFrame jf = new JFrame("Window");
//        final JButton button = new JButton("click to continue");
//
//        button.setAction(new AbstractAction() {
//            public void actionPerformed(ActionEvent ae) {
//                synchronized (button) {
//                    button.notify();
//                }
//            }
//        });
//        jf.getContentPane().add(button, BorderLayout.CENTER);
//        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        jf.pack();
//        jf.setVisible(true);
//
//        synchronized(button) {
//            try {
//                button.wait();
//            } catch (InterruptedException ex) {
//                System.out.println("Interrupted");
//            }
//        }
//        System.out.println("After button clicked");

        new DisplayScreenUI();
    }

}
