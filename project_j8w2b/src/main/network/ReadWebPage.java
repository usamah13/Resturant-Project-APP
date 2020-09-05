package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class ReadWebPage {
    // From Deliverable 10 CPSC 210 edx webpage, for the requirement
//shamelessly quoting from: http://zetcode.com/articles/javareadwebpage/

    //Effects: Printing out Welcome message from a given URL.
    public static void main(String[] args) throws MalformedURLException, IOException {


        BufferedReader br = null;

        try {
            String theURL = "https://www.students.cs.ubc.ca/~cs-210/2018w1/welcomemsg.html"; //this can point to any URL
            URL url = new URL(theURL);
            br = new BufferedReader(new InputStreamReader(url.openStream()));

            String line;

            StringBuilder sb = new StringBuilder();

            //int i = 0;
            while ((line = br.readLine()) != null) {
//            line = br.readLine();
//            while (i <= 10) {

                sb.append(line);
                sb.append(System.lineSeparator());
//                i += 1;
            }

            System.out.println(sb);
        } finally {

            if (br != null) {
                br.close();
            }
        }
    }
}

