package resturant;

import java.util.Objects;

public abstract class Customer {
    protected String name;
    protected Order order;

    public Customer(String name, Menu m) {
        this.name = name;
        this.order = new Order(this, m);


    }

    public String getName() {
        return name;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    //Implemented in the Adult,Child,Elder
    public abstract double billFactor();


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Customer customer = (Customer) o;
        return name.equals(customer.name)
                &&
                order.equals(customer.order);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, order);
    }
}
