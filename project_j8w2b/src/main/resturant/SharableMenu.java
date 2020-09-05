package resturant;

import java.util.Observer;

public class SharableMenu extends Menu {
    public SharableMenu(String type) {
        super(type);
    }

    //Effects returns whether a menu is sharable or not
    @Override
    public boolean isSharable() {
        return true;
    }
}
