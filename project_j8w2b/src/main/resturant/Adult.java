package resturant;

public class Adult extends Customer {

    public Adult(String name, Menu m) {
        super(name, m);
    }


    //Effects: Returns the by what amount the bill will be factored for an adult
    @Override
    public double billFactor() {
        return 1;
    }
}
