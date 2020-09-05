package resturant;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public interface Savable {

    //Implemented in Menu
    //Effects: saves a file, throws IO exception when problems in reading or writing to a file,
    //throws EmptyMenuException when text file given is empty
    void save(String filePath) throws FileNotFoundException, UnsupportedEncodingException;
}
