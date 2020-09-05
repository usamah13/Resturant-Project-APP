package resturant;

public class Child extends Customer {

    public Child(String name, Menu m) {
        super(name, m);
    }

    //Effects: Returns the by what amount the bill will be factored for a Child
    @Override
    public double billFactor() {
        return 0.5;
    }
}
