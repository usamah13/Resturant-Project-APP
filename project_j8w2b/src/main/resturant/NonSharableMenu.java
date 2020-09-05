package resturant;

import java.util.Observer;

public class NonSharableMenu extends Menu {
    public NonSharableMenu(String type) {
        super(type);
    }

    //Effects returns whether a menu is sharable or not
    @Override
    public boolean isSharable() {
        return false;
    }
}
