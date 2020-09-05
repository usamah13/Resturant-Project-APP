package resturant;

import exceptions.EmptyMenuException;

import java.io.IOException;

public interface Loadable {

    //Effects: Implemented in Menu, loads a file, throws IO exception when problems in reading or writing to a file,
    //throws EmptyMenuException when text file given is empty
    void load(String filePath, int lineNumber) throws IOException, EmptyMenuException;
}
