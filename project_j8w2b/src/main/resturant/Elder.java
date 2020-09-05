package resturant;

public class Elder extends Customer {

    public Elder(String name, Menu m) {
        super(name, m);
    }

    //Effects: Returns the by what amount the bill will be factored for an Elder
    @Override
    public double billFactor() {
        return 0.2;
    }
}
